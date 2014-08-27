/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.printxml;

import static ch.admin.isb.hermes5.domain.MethodElementBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementPostProcessor;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.Report;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.SupportingMaterial;
import ch.admin.isb.hermes5.print.api.Chapter;

public class PrintXMLBeschreibungRendererTest {

    private PrintXMLBeschreibungRenderer renderer;
    private TranslationRepository translationRepository;

    @Before
    public void setUp() throws Exception {
        renderer = new PrintXMLBeschreibungRenderer();
        renderer.printXmlRendererUtil = new PrintXmlRendererUtil();
        translationRepository = mock(TranslationRepository.class);
        renderer.customElementPostProcessor = mock(CustomElementPostProcessor.class);
        when(translationRepository.getLocalizedText(anyString(), anyString(), anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return invocation.getArguments()[2] + "_" + invocation.getArguments()[3];
                    }
                });
    }

    @Test
    public void testIsResponsibleForPrintXmlSupportingMaterial() {
        assertTrue(renderer.isResponsibleForPrintXml(new Beschreibung(new SupportingMaterial(), null)));
    }

    @Test
    public void testIsResponsibleForPrintXmlReport() {
        assertTrue(renderer.isResponsibleForPrintXml(new Beschreibung(new Report(), null)));
    }

    @Test
    public void testIsResponsibleForPrintXmlExample() {
        assertFalse(renderer.isResponsibleForPrintXml(new Rolle(new Role(), null)));
    }

    @Test
    public void testRenderPrintXml() {
        Beschreibung methodElement = new Beschreibung(supportingMaterial("Vorwort"), null);
        Chapter target = new Chapter();
        LocalizationEngine localizationEngine = new LocalizationEngine(translationRepository, "", "");
        PublishContainer hermesHandbuch = new PublishContainer(null, null, null, new ArrayList<Szenario>());
        when(renderer.customElementPostProcessor.renderCustomElements(anyString(), eq(hermesHandbuch), eq(localizationEngine))).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return String.valueOf(invocation.getArguments()[0]);
            }
        });
        renderer.renderPrintXml(methodElement, target, localizationEngine, hermesHandbuch);
        assertEquals("id_Vorwort_presentationName", target.getName());
        assertEquals("presentation_id_Vorwort_mainDescription", target.getContent());
    }

}
