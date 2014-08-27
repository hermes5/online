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

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.customelement.ModulAufgabeTableBuilder;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.print.api.Section;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlModulRendererTest {

    private PrintXmlModulRenderer renderer;
    private Modul modul;
    private Aufgabe a1;
    private TranslationRepository translationRepository;
    private LocalizationEngine localizationEngine;

    @Before
    public void setUp() throws Exception {
        translationRepository = mock(TranslationRepository.class);
        when(translationRepository.getLocalizedText(anyString(), anyString(), anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return StringUtil.join(Arrays.asList(invocation.getArguments()), "/");
                    }
                });
        renderer = new PrintXmlModulRenderer();
        renderer.printXmlRendererUtil = new PrintXmlRendererUtil();
        renderer.modulAufgabeTableBuilder = new ModulAufgabeTableBuilder();
        renderer.velocityAdapter = mock(VelocityAdapter.class);

        modul = new Modul(discipline("m1"), null);
        a1 = aufgabe("a1", modul);
        localizationEngine = new LocalizationEngine(translationRepository, "model", "de");

    }

    @Test
    public void testResponsibleModul() {
        assertTrue(renderer.isResponsibleForPrintXml(modul));
    }

    @Test
    public void testNotResponsibleAufgabe() {
        assertFalse(renderer.isResponsibleForPrintXml(a1));
    }

    @Test
    public void testRender() {
        Section target = new Section();
        renderer.renderPrintXml(modul, target, localizationEngine, new PublishContainer(null, null, new ArrayList<Phase>(), new ArrayList<Szenario>()));
        assertEquals("model/de/" + modul.getId() + "/presentationName", target.getName());
        assertEquals("<p>model/de/disciplineid_m1/briefDescription</p><p>model/de/id_m1/mainDescription</p>",
                target.getContent());
        assertEquals(0, target.getSubsection().size());
    }
}
