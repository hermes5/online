/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.printxml;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.print.api.Section;
import ch.admin.isb.hermes5.print.api.Subsection;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlErgebnisRendererTest {

    private PrintXmlErgebnisRenderer renderer;
    private Ergebnis ergebnis;
    private TranslationRepository translationRepository;

    @Before
    public void setUp() throws Exception {
        renderer = new PrintXmlErgebnisRenderer();
        renderer.printXmlRendererUtil = new PrintXmlRendererUtil();
        ergebnis= ergebnis("e1");
        translationRepository = mock(TranslationRepository.class);    
        when(translationRepository.getLocalizedText(anyString(), anyString(), anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return StringUtil.join(Arrays.asList(invocation.getArguments()), "/");
                    }
                });
    }

    @Test
    public void testIsResponsibleForPrintXml() {
        assertTrue(renderer.isResponsibleForPrintXml(ergebnis));
    }

    @Test
    public void testRender() {
        Section target = new Section();
        renderer.renderPrintXml(ergebnis, target, new LocalizationEngine(translationRepository, "model", "de"), null);
        assertEquals("model/de/" + ergebnis.getId() + "/presentationName", target.getName());
        assertEquals("model/de/wpid_e1/briefDescription", target.getContent());
        Subsection inhalt = target.getSubsection().get(0);
        assertEquals("Inhalt", inhalt.getName());
        assertEquals("model/de/presentation_id_e1/purpose", inhalt.getContent());
    }

}
