/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.UploadedFile;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;

public class ArtifactUploadControllerTest {

    private static final String FILENAME = "filename.png";
    private ArtifactUploadController artifactUploadController;

    @Before
    public void setUp() {
        artifactUploadController = new ArtifactUploadController();
        artifactUploadController.webPublisherFacade = mock(WebPublisherFacade.class);
        artifactUploadController.messagesUtil = mock(MessagesUtil.class);
        artifactUploadController.artifactOverviewController = mock(ArtifactOverviewController.class);
        when(artifactUploadController.artifactOverviewController.getIdentifier()).thenReturn("artifact-overview");
        when(artifactUploadController.artifactOverviewController.display()).thenReturn("artifact-overview");

    }

    @Test
    public void testGetIdentifier() {
        assertEquals("artifact-upload", artifactUploadController.getIdentifier());
    }

    @Test
    public void testDisplay() {
        TranslationDocument translationEntity = mock(TranslationDocument.class);
        assertEquals("artifact-upload", artifactUploadController.display(translationEntity, "fr", "backIdentifier"));
        assertEquals("backIdentifier", artifactUploadController.getBackIdentifier());
    }

    @Test
    public void testHandleFileUpload() {
        TranslationDocument translationEntity = new Image();
        artifactUploadController.display(translationEntity, "fr", "backIdentifier" );
        artifactUploadController.setUploadedFile(uploadedFileMock());
        assertEquals(null, artifactUploadController.handleFileUpload());
        assertTrue(artifactUploadController.isRenderSuccessDialog());
        verify(artifactUploadController.webPublisherFacade).addOrUpdateArtifact(translationEntity, "content".getBytes(),FILENAME, "fr");
    }

    private UploadedFile uploadedFileMock() {
        UploadedFile mock = mock(UploadedFile.class);
        when(mock.getContents()).thenReturn("content".getBytes());
        when(mock.getFileName()).thenReturn(FILENAME);
        return mock;
    }

    @Test
    public void testHandleFileUploadAndConfirm() {
        TranslationDocument translationEntity = new Image();
        artifactUploadController.display(translationEntity, "fr", "artifact-overview" );
        artifactUploadController.setUploadedFile(uploadedFileMock());
        assertEquals(null, artifactUploadController.handleFileUpload());
        assertTrue(artifactUploadController.isRenderSuccessDialog());
        assertEquals("artifact-overview?faces-redirect=true", artifactUploadController.confirmSuccessDialog());
        verify(artifactUploadController.artifactOverviewController).display();
    }
    
    

    @Test
    public void testHandleFileUploadException() {
        TranslationDocument translationEntity = new Image();
        artifactUploadController.display(translationEntity, "fr", "backIdentifier" );
        artifactUploadController.setUploadedFile(uploadedFileMock());
        doThrow(new RuntimeException("test")).when(artifactUploadController.webPublisherFacade).addOrUpdateArtifact(
                translationEntity, "content".getBytes(),FILENAME,  "fr");
        assertEquals(null, artifactUploadController.handleFileUpload());
        verify(artifactUploadController.messagesUtil).addGlobalErrorMessage("Beim Hochladen ist ein unbekannter Fehler aufgetreten");

        assertFalse(artifactUploadController.isRenderSuccessDialog());
        
    }

}
