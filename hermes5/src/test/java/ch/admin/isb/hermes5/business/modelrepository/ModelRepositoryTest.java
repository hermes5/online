/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import static ch.admin.isb.hermes5.domain.Status.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.DeliveryProcess;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;
import ch.admin.isb.hermes5.persistence.db.dao.EPFModelDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.DateUtil;

public class ModelRepositoryTest {

    private static final String MODEL_IDENTIFIER = "modelIdentifier";
    private static final byte[] BYTES = "bytes".getBytes();
    private ModelRepository modelRepository;
    private Cache<Object, Object> cacheMock;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        modelRepository = new ModelRepository();
        modelRepository.dateUtil = mock(DateUtil.class);
        modelRepository.epfModelDAO = mock(EPFModelDAO.class);
        modelRepository.translationRepository = mock(TranslationRepository.class);
        modelRepository.methodLibraryVisitorDriver = mock(MethodLibraryVisitorDriver.class);
        modelRepository.methodLibraryUnmarshaller = mock(MethodLibraryUnmarshaller.class);
        modelRepository.elementExtractorVisitor = mock(ElementExtractorVisitor.class);
        modelRepository.elementIndexVisitor = mock(ElementIndexVisitor.class);
        modelRepository.s3 = mock(S3.class);
        modelRepository.cacheContainer = mock(CacheContainer.class);
        modelRepository.szenarioAssembler = mock(SzenarioAssembler.class);
        modelRepository.rollenAssembler = mock(RollenAssembler.class);
        when(modelRepository.szenarioAssembler.buildScenario(any(DeliveryProcess.class), any(MethodConfiguration.class), anyMap(), anyList(), anyList())).thenReturn(
                new Szenario(null));
        cacheMock = mock(Cache.class);
        when(modelRepository.cacheContainer.getCache()).thenReturn(cacheMock);
        modelRepository.init();
    }

    @Test
    public void testSaveNewModel() {
        EPFModel model = new EPFModel();
        EPFModel expected = new EPFModel();
        when(modelRepository.epfModelDAO.merge(model)).thenReturn(expected);
        assertEquals(expected, modelRepository.saveNewModel(model));
    }

    @Test
    public void testGetEPFModels() {
        EPFModel model1 = new EPFModel();
        EPFModel model2 = new EPFModel();
        when(modelRepository.epfModelDAO.getEPFModels()).thenReturn(Arrays.asList(model1, model2));
        assertEquals(Arrays.asList(model1, model2), modelRepository.getEPFModels());
    }

    @Test
    public void updateStatusAndUpdateDate() {
        EPFModel model1 = new EPFModel();
        when(modelRepository.epfModelDAO.getModelByIdentifier(MODEL_IDENTIFIER)).thenReturn(model1);
        when(modelRepository.translationRepository.getTranslationEntityStati(MODEL_IDENTIFIER, "fr")).thenReturn(
                Arrays.asList(UNVOLLSTAENDIG, FREIGEGEBEN));
        when(modelRepository.translationRepository.getTranslationEntityStati(MODEL_IDENTIFIER, "it")).thenReturn(
                Arrays.asList(IN_ARBEIT, UNVOLLSTAENDIG, FREIGEGEBEN));
        when(modelRepository.translationRepository.getTranslationEntityStati(MODEL_IDENTIFIER, "en")).thenReturn(
                Arrays.asList(FREIGEGEBEN));

        modelRepository.updateStatusAndUpdateDate(MODEL_IDENTIFIER);
        assertEquals(UNVOLLSTAENDIG, model1.getStatusFr());
        assertEquals(IN_ARBEIT, model1.getStatusIt());
        assertEquals(FREIGEGEBEN, model1.getStatusEn());
        verify(modelRepository.epfModelDAO).merge(model1);
    }

    @Test
    public void testGetSzenarienNoCache() {
        when(cacheMock.get(MODEL_IDENTIFIER)).thenReturn(null);
        EPFModel model = new EPFModel();
        model.setUrlDe("url");
        when(modelRepository.getModelByIdentifier(MODEL_IDENTIFIER)).thenReturn(model);
        when(modelRepository.s3.readFile(MODEL_IDENTIFIER, "de", "url")).thenReturn(BYTES);
        when(modelRepository.methodLibraryUnmarshaller.unmarshalMethodLibrary(any(InputStream.class))).thenReturn(
                new MethodLibrary());
        List<MethodElement> asList = Arrays.asList((MethodElement) new DeliveryProcess());
        when(modelRepository.elementExtractorVisitor.getResult(DeliveryProcess.class)).thenReturn(asList);
        List<MethodElement> configs = Arrays.asList((MethodElement) new MethodConfiguration(), new MethodConfiguration());;
        when(modelRepository.elementExtractorVisitor.getResult(MethodConfiguration.class)).thenReturn(configs );
        List<Szenario> szenarien = modelRepository.getSzenarien(MODEL_IDENTIFIER);
        assertNotNull(szenarien);
        assertEquals(2, szenarien.size());
    }

    @Test
    public void testDeleteModel() {
        EPFModel model1 = new EPFModel();
        assertFalse(model1.isDeleted());
        when(modelRepository.epfModelDAO.getModelByIdentifier(MODEL_IDENTIFIER)).thenReturn(model1);
        modelRepository.deleteModel(MODEL_IDENTIFIER);
        assertTrue(model1.isDeleted());
        verify(modelRepository.epfModelDAO).merge(model1);
        verify(modelRepository.s3).deleteModelContent(MODEL_IDENTIFIER);
    }

    @Test
    public void testGetModelByIdentifier() {
        EPFModel model1 = new EPFModel();
        when(modelRepository.epfModelDAO.getModelByIdentifier(MODEL_IDENTIFIER)).thenReturn(model1);
        assertEquals(model1, modelRepository.getModelByIdentifier(MODEL_IDENTIFIER));
    }

}
