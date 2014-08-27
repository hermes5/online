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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.UploadedFile;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;

public class ModelUploadControllerTest {

    private ModelUploadController controller;

    @Before
    public void setUp() {
        controller = new ModelUploadController();
        controller.webPublisherFacade = mock(WebPublisherFacade.class);
        controller.messagesUtil = mock(MessagesUtil.class);
        controller.modelOverviewController = mock(ModelOverviewController.class);
        when(controller.modelOverviewController.display()).thenReturn("modelOverviewController");
    }
    

    @Test
    public void testHandleFileUpload() {
        UploadedFile mock = mock(UploadedFile.class);
        when(mock.getContents()).thenReturn("contents".getBytes());
        when(mock.getFileName()).thenReturn("fileName.zip");
        controller.setUploadedFile(mock);
        controller.setUploadTitle("title");
        controller.setUploadVersion("version");
        assertFalse(controller.isRenderSuccessDialog());
        controller.handleFileUpload();
        verify(controller.webPublisherFacade).importEPFModelFromZipFile("contents".getBytes(), "fileName.zip", "title", "version");
        assertTrue(controller.isRenderSuccessDialog());
        String redirect = controller.confirmSuccessDialog();
        assertEquals("modelOverviewController?faces-redirect=true", redirect);
    }

    @Test
    public void testHandleFileUploadNoZip() {
        UploadedFile mock = mock(UploadedFile.class);
        when(mock.getContents()).thenReturn("contents".getBytes());
        when(mock.getFileName()).thenReturn("fileName.nozip");
        controller.setUploadedFile(mock);
        controller.setUploadTitle("title");
        controller.setUploadVersion("version");
        controller.handleFileUpload();
        verify(controller.webPublisherFacade, never()).importEPFModelFromZipFile((byte[]) anyObject(), anyString(), anyString(),
                anyString());
        verify(controller.messagesUtil).addGlobalErrorMessage("Nur Zip Files sind erlaubt.");
        assertFalse(controller.isRenderSuccessDialog());
        
    }

    @Test
    public void testHandleFileUploadException() {
        UploadedFile mock = mock(UploadedFile.class);
        when(mock.getContents()).thenReturn("contents".getBytes());
        when(mock.getFileName()).thenReturn("fileName.zip");
        doThrow(new IllegalArgumentException("dummy")).when(controller.webPublisherFacade).importEPFModelFromZipFile(
                "contents".getBytes(), "fileName.zip", "title", "version");
        controller.setUploadedFile(mock);
        controller.setUploadTitle("title");
        controller.setUploadVersion("version");
        controller.handleFileUpload();
        verify(controller.messagesUtil).addGlobalErrorMessage("Beim Importieren ist ein unerwarteter Fehler aufgetreten");
        assertFalse(controller.isRenderSuccessDialog());
        
    }

}
