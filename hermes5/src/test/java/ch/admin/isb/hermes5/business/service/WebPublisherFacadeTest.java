/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelimport.EPFModelImporter;
import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.persistence.db.dao.TranslateableTextDAO;

public class WebPublisherFacadeTest {

	private WebPublisherFacade webPublisherFacade;

	@Before
	public void setUp() {
		webPublisherFacade = new WebPublisherFacade();
		webPublisherFacade.epfModelImporter = mock(EPFModelImporter.class);
		webPublisherFacade.modelRepository = mock(ModelRepository.class);
		webPublisherFacade.translationRepository = mock(TranslationRepository.class);
		webPublisherFacade.translateableTextDAO = mock(TranslateableTextDAO.class);
		
	}
	
    @Test
    public void testStoreZipFile() throws IOException {
        webPublisherFacade.importEPFModelFromZipFile("out".getBytes(), "fileName", "title", "version");
        verify(webPublisherFacade.epfModelImporter).importEPFModelFromZipFile("out".getBytes(), "fileName", "title", "version");
    }

    @Test
    public void addArtifact(){
        TranslationDocument translationEntity = mock(TranslationDocument.class);
        when(translationEntity.getModelIdentifier()).thenReturn("123");
        when(translationEntity.getUrlDe()).thenReturn("url/filenameDe.doc");
        
        webPublisherFacade.addOrUpdateArtifact(translationEntity , "contents".getBytes(), "filenameFr.doc",  "fr");
        verify(webPublisherFacade.epfModelImporter).addFile("123", "fr", "url/filenameFr.doc","contents".getBytes());
        verify(webPublisherFacade.translationRepository).updateTranslationEntity(translationEntity,"url/filenameFr.doc", "fr");
        verify(webPublisherFacade.modelRepository).updateStatusAndUpdateDate("123");
    }
    
    @Test
    public void deleteTranslateableText() {
        TranslateableText expected = new TranslateableText();
        when(webPublisherFacade.translateableTextDAO.deleteTranslateableText("Identifier", "rootElementIdentifier", "textIdentifier", "fr")).thenReturn(expected );
        TranslateableText actual = webPublisherFacade.deleteTranslateableText("Identifier", "rootElementIdentifier", "textIdentifier", "fr");
        assertSame(expected, actual);
        verify(webPublisherFacade.modelRepository).updateStatusAndUpdateDate("Identifier");
    }
    
}
