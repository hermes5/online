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
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.print.api.Section;
import ch.admin.isb.hermes5.print.api.Subsection;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlAufgabeRendererTest {

    private PrintXmlAufgabeRenderer renderer;
    private Aufgabe aufgabe;
    private Aufgabe aufgabeWithoutErgebnisse;
    private Modul modul;
    private TranslationRepository translationRepository;

    @Before
    public void setUp() throws Exception {
        renderer = new PrintXmlAufgabeRenderer();
        renderer.printXmlRendererUtil = new PrintXmlRendererUtil();
        modul = modul("m1");
        aufgabe = aufgabe("a1", modul, ergebnis("e1"),ergebnis("e2"));
        aufgabeWithoutErgebnisse = aufgabe("a1", modul);
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
        assertTrue(renderer.isResponsibleForPrintXml(aufgabe));
    }

    @Test
    public void testIsNotResponsibleForPrintXml() {
        assertFalse(renderer.isResponsibleForPrintXml(modul));
    }

    @Test
    public void testRender() {
        Section target = new Section();
        renderer.renderPrintXml(aufgabe, target, new LocalizationEngine(translationRepository, "model", "de"), null);
        assertEquals("model/de/" + aufgabe.getId() + "/presentationName", target.getName());
        assertEquals("model/de/presentation_id_a1/purpose model/de/taskid_a1/briefDescription", target.getContent());
        Subsection grundidee = target.getSubsection().get(0);
        assertEquals("Grundidee", grundidee.getName());
        assertEquals("model/de/presentation_id_a1/mainDescription", grundidee.getContent());
        Subsection spezifisch = target.getSubsection().get(1);
        assertEquals("HERMES Spezifisch", spezifisch.getName());
        assertEquals("model/de/presentation_id_a1/keyConsiderations", spezifisch.getContent());
        Subsection aktivitaeten = target.getSubsection().get(2);
        assertEquals("Aktivitäten", aktivitaeten.getName());
        assertEquals("model/de/presentation_id_a1/alternatives", aktivitaeten.getContent());
        Subsection ergebnisse = target.getSubsection().get(3);
        assertEquals("Ergebnisse", ergebnisse.getName());
        assertEquals("<ul><li>model/de/wpid_e1/presentationName</li><li>model/de/wpid_e2/presentationName</li></ul>", ergebnisse.getContent());
    }
    @Test
    public void testRenderAufgabeWithoutErgebnisse() {
        Section target = new Section();
        renderer.renderPrintXml(aufgabeWithoutErgebnisse, target, new LocalizationEngine(translationRepository, "model", "de"), null);
        assertEquals(3, target.getSubsection().size());
        assertEquals("Grundidee", target.getSubsection().get(0).getName());
        assertEquals("HERMES Spezifisch", target.getSubsection().get(1).getName());
    }

}
