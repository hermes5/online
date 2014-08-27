/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationimport;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.word.TranslationWordAdapter;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.persistence.db.dao.TranslateableTextDAO;

public class TextelementImportHandlerTest {

    TextelementImportHandler textelementImportHandler;

    @Before
    public void setUp() throws Exception {
        textelementImportHandler = new TextelementImportHandler();
        textelementImportHandler.translateableTextDAO = mock(TranslateableTextDAO.class);
        textelementImportHandler.wordAdapter = mock(TranslationWordAdapter.class);
    }

    @Test
    public void testIsResponsible() {
        boolean result = textelementImportHandler
                .isResponsible("MethodLibrary_20120828021719/Textelemente/Rolle/Projektleiter_en.docx");
        Assert.assertTrue(result);
        result = textelementImportHandler
                .isResponsible("MethodLibrary_20120828021719/Vorlagen/hermes_core_guidances_templates_resources_Bedarfsanforderung_docx/de/Bedarfsanforderung.docx");
        Assert.assertFalse(result);
    }

    @Test
    public void testHandleImport() {
        List<String> langs = new ArrayList<String>();
        langs.add("fr");
        langs.add("en");
        String docUrl = "MethodLibrary_20120828021719_1.0.1/Textelemente/Rolle/Projektleiter_en.docx";
        List<TranslateableText> texts = new ArrayList<TranslateableText>();
        texts.add(new TranslateableText());
        when(textelementImportHandler.wordAdapter.read((ByteArrayInputStream) any(), eq("en"))).thenReturn(texts);
        when(
                textelementImportHandler.translateableTextDAO.getSpecificText(eq("model_identifier"), anyString(),
                        anyString())).thenReturn(new TranslateableText());

        textelementImportHandler.handleImport("model_identifier", "content".getBytes(), langs, docUrl);

        verify(textelementImportHandler.translateableTextDAO).merge((TranslateableText) any());
    }

}
