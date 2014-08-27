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

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.customelement.RelationshipTablePreprocessor;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.print.api.Section;
import ch.admin.isb.hermes5.print.api.Subsection;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlRolleRendererTest {

    private PrintXmlRolleRenderer renderer;
    private Rolle rolle;
    private TranslationRepository translationRepository;

    @Before
    public void setUp() throws Exception {
        renderer = new PrintXmlRolleRenderer();
        renderer.printXmlRendererUtil = new PrintXmlRendererUtil();
        renderer.relationshipTablePreprocessor = new RelationshipTablePreprocessor();
        renderer.velocityAdapter = mock(VelocityAdapter.class);
        rolle = rolle("21");
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
        assertTrue(renderer.isResponsibleForPrintXml(rolle));
    }

    @Test
    public void testIsNotResponsibleForPrintXml() {
        assertFalse(renderer.isResponsibleForPrintXml(ergebnis("e1")));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testRender() {
        when(renderer.velocityAdapter.mergeTemplates((Map) anyObject(), eq("relationshiptable.vm"))).thenReturn(
                "relationshiptable");
        Section target = new Section();
        renderer.renderPrintXml(rolle, target, new LocalizationEngine(translationRepository, "model", "de"), null);
        assertEquals("model/de/" + rolle.getId() + "/presentationName", target.getName());
        assertEquals("model/de/roleid_21/briefDescription", target.getContent());
        Subsection verantwortung = target.getSubsection().get(0);
        assertEquals("Verantwortung", verantwortung.getName());
        assertEquals("model/de/id_21_presentation/mainDescription", verantwortung.getContent());
        Subsection kompetenzen = target.getSubsection().get(1);
        assertEquals("Kompetenzen", kompetenzen.getName());
        assertEquals("model/de/id_21_presentation/assignmentApproaches", kompetenzen.getContent());
        Subsection faehigkeiten = target.getSubsection().get(2);
        assertEquals("Fähigkeiten", faehigkeiten.getName());
        assertEquals("model/de/id_21_presentation/skills", faehigkeiten.getContent());
        Subsection relationship = target.getSubsection().get(3);
        assertEquals("Beziehungen", relationship.getName());
        assertEquals("relationshiptable", relationship.getContent());
    }

    @Test
    public void renderBeziehungenForXml() throws IOException {
        Rolle rolle = rolle("projektleiter");
        renderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
        aufgabe("aufgabe", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        Section target = new Section();
        renderer.renderPrintXml(rolle, target, new LocalizationEngine(translationRepository, "model", "de"), null);
        String s = target.getSubsection().get(target.getSubsection().size()-1).getContent();
        System.out.println(s);
        assertFalse(s, s.contains("</a>"));
        assertTrue(s, s.contains("relationshiptable"));
        assertTrue(s, s.contains("projektleiter"));
        assertTrue(s, s.contains("modul1"));
        assertTrue(s, s.contains("ergebnis"));
        assertTrue(s, s.contains("aufgabe"));
    }
}
