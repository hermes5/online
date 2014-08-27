/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import ch.admin.isb.hermes5.business.modelutil.ComplexFieldComparator;
import ch.admin.isb.hermes5.business.modelutil.MethodElementUtil;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.business.modelutil.MethodenElementFactory;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.persistence.db.dao.EPFModelDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;

public   class ModelRepositoryIntegrationTestSupport {
    private static final String MODEL_IDENTIFIER = "model";
    private static final String MODEL_URL_DE = "urlde";
    private ReflectionTestHelper helper = new ReflectionTestHelper();
    
    public ModelRepository getInitializedModelRepository() {
        ModelRepository modelRepository = new ModelRepository();
        modelRepository.s3 = mock(S3.class);
        modelRepository.epfModelDAO = mock(EPFModelDAO.class);

        modelRepository.cacheContainer = setUpCache();

        modelRepository.elementExtractorVisitor = new ElementExtractorVisitor();
        modelRepository.elementIndexVisitor = new ElementIndexVisitor();
        modelRepository.methodLibraryUnmarshaller = new MethodLibraryUnmarshaller();
        modelRepository.methodLibraryVisitorDriver = new MethodLibraryVisitorDriver();
        modelRepository.rollenAssembler = new RollenAssembler();
        modelRepository.modulAssembler = new ModulAssembler();
        modelRepository.modulAssembler.methodenElementFactory = new MethodenElementFactory();
        modelRepository.szenarioAssembler = new SzenarioAssembler();
        modelRepository.szenarioAssembler.methodenElementFactory = new MethodenElementFactory();
        modelRepository.publishContainerAssembler = new PublishContainerAssembler();
        modelRepository.publishContainerAssembler.methodElementUtil = new MethodElementUtil();

        ComplexFieldComparator complexFieldComparator = new ComplexFieldComparator();
        Hardcoded.enableDefaults(complexFieldComparator);
        complexFieldComparator.init();
        helper.updateField(modelRepository.methodLibraryVisitorDriver, "fieldComparator", complexFieldComparator);

        Hardcoded.enableDefaults(modelRepository, modelRepository.publishContainerAssembler);
        modelRepository.init();
        return modelRepository;
    }
    public PublishContainer getHermesHandbuch(byte[] xmlFileContents) {
        ModelRepository modelRepository = this.getInitializedModelRepository();
        registerFile(xmlFileContents, modelRepository);
        PublishContainer hermesHandbuch = modelRepository.getHermesHandbuch(MODEL_IDENTIFIER);
        return hermesHandbuch;
    }
    public void registerFile(byte[] xmlFileContents, ModelRepository modelRepository) {
        EPFModel model = getEpfModel();
        when(modelRepository.epfModelDAO.getModelByIdentifier(MODEL_IDENTIFIER)).thenReturn(model);
        when(modelRepository.s3.readFile(MODEL_IDENTIFIER, "de", MODEL_URL_DE)).thenReturn(xmlFileContents);
    }
    public EPFModel getEpfModel() {
        EPFModel model = new EPFModel();
        model.setIdentifier(MODEL_IDENTIFIER);
        model.setUrlDe(MODEL_URL_DE);
        return model;
    }
    public PublishContainer getHermesWebsite(byte[] xmlFileContents) {
        EPFModel model = new EPFModel();
        ModelRepository modelRepository = this.getInitializedModelRepository();
        model.setIdentifier(MODEL_IDENTIFIER);
        model.setUrlDe(MODEL_URL_DE);
        when(modelRepository.epfModelDAO.getModelByIdentifier(MODEL_IDENTIFIER)).thenReturn(model);
        when(modelRepository.s3.readFile(MODEL_IDENTIFIER, "de", MODEL_URL_DE)).thenReturn(xmlFileContents);
        PublishContainer hermesWebsite = modelRepository.getHermesWebsite(MODEL_IDENTIFIER);
        return hermesWebsite;
    }
    
    @SuppressWarnings("unchecked")
    private CacheContainer setUpCache() {
        CacheContainer cacheContainer = mock(CacheContainer.class);
        @SuppressWarnings("rawtypes")
        Cache cache = mock(Cache.class);
        when(cache.containsKey(any())).thenReturn(false);
        when(cacheContainer.getCache()).thenReturn(cache);
        return cacheContainer;
    }

}
