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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.MethodElementBuilder;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.print.api.Section;
import ch.admin.isb.hermes5.print.api.Subsection;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlKategorieRendererTest {

    private PrintXmlKategorieRenderer renderer;
    private Kategorie kategorie;
    private Aufgabe aufgabe1;
    private Aufgabe aufgabe2;
    private Aufgabe aufgabe3;
    private PrintXmlAufgabeRenderer aufgabeRenderer;
    private LocalizationEngine localizationEngine;

    @Before
    public void setUp() throws Exception {
        renderer = new PrintXmlKategorieRenderer();
        Hardcoded.enableDefaults(renderer);
        aufgabeRenderer = new PrintXmlAufgabeRenderer();
        renderer.printXmlRendererUtil = new PrintXmlRendererUtil();
        renderer.printXmlRendererRepository = mock(PrintXmlRendererRepository.class);
        kategorie = MethodElementBuilder.kategorie("kate");
        Modul modul = modul("m1");
        aufgabe1 = aufgabe("bb", modul);
        aufgabe2 = aufgabe("aa", modul);
        aufgabe3 = aufgabe("ää", modul);
        kategorie.getChildren().addAll(Arrays.asList(aufgabe1, aufgabe2, aufgabe3));
        aufgabeRenderer.printXmlRendererUtil = new PrintXmlRendererUtil();
        when(renderer.printXmlRendererRepository.lookupPrintXmlRenderer(aufgabe1)).thenReturn(aufgabeRenderer);
        when(renderer.printXmlRendererRepository.lookupPrintXmlRenderer(aufgabe2)).thenReturn(aufgabeRenderer);
        when(renderer.printXmlRendererRepository.lookupPrintXmlRenderer(aufgabe3)).thenReturn(aufgabeRenderer);
        TranslationRepository translationRepository = mock(TranslationRepository.class);
        when(translationRepository.getLocalizedText(anyString(), anyString(), anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return StringUtil.join(Arrays.asList(invocation.getArguments()), "/");
                    }
                });
        localizationEngine = new LocalizationEngine(translationRepository, "model", "de");
    }

    @Test
    public void testIsResponsibleForPrintXml() {
        assertTrue(renderer.isResponsibleForPrintXml(kategorie));
    }

    @Test
    public void testIsNotResponsibleForPrintXmlAufgabe() {
        assertFalse(renderer.isResponsibleForPrintXml(aufgabe1));
    }

    @Test
    public void testIsNotResponsibleForPrintXmlModul() {
        assertFalse(renderer.isResponsibleForPrintXml(modul("m1")));
    }

    @Test
    public void testRenderAufgabenSorted() {
        Section target = new Section();
        renderer.renderPrintXml(kategorie, target, localizationEngine, null);
        List<Subsection> subsection = target.getSubsection();
        assertEquals(3, subsection.size());
        assertTrue(subsection.get(0).getName(), subsection.get(0).getName().contains("aa"));
        assertTrue(subsection.get(2).getName(), subsection.get(2).getName().contains("bb"));
    }
    @Test
    public void testRenderAufgabenSortedUmlaute() {
        Section target = new Section();
        renderer.renderPrintXml(kategorie, target, localizationEngine, null);
        List<Subsection> subsection = target.getSubsection();
        assertEquals(3, subsection.size());
        assertTrue(subsection.get(0).getName(), subsection.get(0).getName().contains("aa"));
        assertTrue(subsection.get(1).getName(), subsection.get(1).getName().contains("ää"));
    }
    

}
