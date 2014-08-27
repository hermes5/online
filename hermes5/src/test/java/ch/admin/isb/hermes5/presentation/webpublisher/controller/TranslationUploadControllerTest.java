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

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.UploadedFile;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;


public class TranslationUploadControllerTest {

    private TranslationUploadController controller;

    @Before
    public void setUp() {
        controller = new TranslationUploadController();
        controller.webPublisherFacade = mock(WebPublisherFacade.class);
        controller.messagesUtil = mock(MessagesUtil.class);
        controller.artifactOverviewController = mock(ArtifactOverviewController.class);
        when(controller.artifactOverviewController.display(anyString())).thenReturn("artifactOverviewController");
    }
    


    @Test
    public void testHandleFileUpload() {
        UploadedFile mock = mock(UploadedFile.class);
        when(mock.getContents()).thenReturn("contents".getBytes());
        when(mock.getFileName()).thenReturn("fileName.zip");
        controller.display("model1");
        controller.setUploadedFile(mock);
        controller.setLanguages(Arrays.asList("fr", "it"));
        assertFalse(controller.isRenderSuccessDialog());
        controller.handleFileUpload();
        verify(controller.webPublisherFacade).importTranslationZip("model1", "contents".getBytes(),Arrays.asList("fr", "it"));
        assertTrue(controller.isRenderSuccessDialog());
        String redirect = controller.confirmSuccessDialog();
        assertEquals("artifactOverviewController?faces-redirect=true", redirect);
    }

}
