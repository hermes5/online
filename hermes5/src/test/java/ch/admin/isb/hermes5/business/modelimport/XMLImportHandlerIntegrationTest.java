/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.util.ResourceUtils;

public class XMLImportHandlerIntegrationTest {

    private XMLImportHandler xmlImportHandler;

    @Before
    public void setUp() {
        this.xmlImportHandler = new XMLImportHandlerIntegrationTestSupport().setupXmlImportHandler();
    }


    @Test
    public void testHandleImport() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        byte[] content = ResourceUtils.loadResource(resourceAsStream);
        ImportResult handleImport = xmlImportHandler.handleImport("modelIdentifier", content, "export.xml");
        assertNotNull(handleImport);
        assertEquals(String.valueOf(handleImport.getErrors()), 0, handleImport.getErrors().size());
        List<TranslateableText> translateableTexts = xmlImportHandler.visitor.getTranslateableTexts();
        assertEquals(506, translateableTexts.size());
    }
    @Test
    public void testHandleImportAttachmentUrls() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        byte[] content = ResourceUtils.loadResource(resourceAsStream);
        ImportResult handleImport = xmlImportHandler.handleImport("modelIdentifier", content, "export.xml");
        assertNotNull(handleImport);
        assertEquals(String.valueOf(handleImport.getErrors()), 0, handleImport.getErrors().size());
        List<TranslateableText> translateableTexts = xmlImportHandler.visitor.getTranslateableTexts();
        boolean found = false;
        for (TranslateableText translateableText : translateableTexts) {
            if(translateableText.getTextIdentifier().contains("attachment")) {
                found = true;
            }
        }
        assertTrue(found);
    }

}
