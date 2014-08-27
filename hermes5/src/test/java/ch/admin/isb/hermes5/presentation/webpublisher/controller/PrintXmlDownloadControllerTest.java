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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;

public class PrintXmlDownloadControllerTest {

    private PrintXmlDownloadController controller;

    @Before
    public void setUp() throws Exception {
        controller = new PrintXmlDownloadController();
        controller.webPublisherFacade = mock(WebPublisherFacade.class);
        controller.modelOverviewController = mock(ModelOverviewController.class);
    }

    @Test
    public void testGetIdentifier() {
        assertEquals("print-xml-download", controller.getIdentifier());
    }

    @Test
    public void testDisplay() {
        String display = controller.display("identifier");
        assertEquals("print-xml-download", display);
        assertEquals("de", controller.getSelectedLanguage());
        assertEquals("Print_XML_identifier_de.xml", controller.getFilename());
        assertFalse(controller.isRenderDownloadDialog());
    }

    @Test
    public void testDisplaySelectFr() {
        selectLang("fr");
    }

    @Test
    public void testDisplaySelectDe() {
        selectLang("de");
    }

    @Test
    public void testDisplaySelectIt() {
        selectLang("it");
    }

    @Test
    public void testDisplaySelectEn() {
        selectLang("en");
    }

    private void selectLang(String selectedLanguage) {
        controller.display("identifier");
        controller.setSelectedLanguage(selectedLanguage);
        assertEquals("Print_XML_identifier_" + selectedLanguage + ".xml", controller.getFilename());
        assertFalse(controller.isRenderDownloadDialog());
    }

    @Test
    public void testTriggerExportDe() throws Exception {
        triggerExport("de");
    }

    @Test
    public void testTriggerExportFr() throws Exception {
        triggerExport("fr");
    }

    @Test
    public void testTriggerExportIt() throws Exception {
        triggerExport("it");
    }

    @Test
    public void testTriggerExportEn() throws Exception {
        triggerExport("en");
    }

    private void triggerExport(String selectedLanguage) throws IOException {
        controller.display("identifier");
        controller.setSelectedLanguage(selectedLanguage);
        when(controller.webPublisherFacade.getPrintXml("identifier", selectedLanguage)).thenReturn("xml");
        controller.triggerExport();
        assertEquals("xml", inputStreamToString(controller.getFile().getStream()));
        assertTrue(controller.isRenderDownloadDialog());
    }

    private String inputStreamToString(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }

    @Test
    public void testDownloadDialogClose() throws Exception {
        triggerExport("en");
        controller.downloadDialogClose();
        assertFalse(controller.isRenderDownloadDialog());
    }

    @Test
    public void testGetBackIdentifier() {
        when(controller.modelOverviewController.display()).thenReturn("overview");
        assertEquals("overview", controller.getBackIdentifier());
    }

}
