/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import static ch.admin.isb.hermes5.domain.EPFModelBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.util.Hardcoded;

public class SzenarienOverviewControllerTest {

    private static final String LINK_PREFIX = "/szenarien/";
    private static final Szenario SZENARIO1 = new Szenario(null);
    private static final Szenario SZENARIO2 = new Szenario(null);
    private SzenarienOverviewController controller;

    @Before
    public void setUp() throws Exception {
        controller = new SzenarienOverviewController();
        controller.anwenderloesungFacade = mock(AnwenderloesungFacade.class);
        controller.szenarioProjektdatenController = mock(SzenarioProjektdatenController.class);
        controller.szenarioWizardContext = new SzenarioWizardContext();
        controller.szenarioWizardContext.init();
        controller.szenarioEigeneElementeController = mock(SzenarioEigeneElementeController.class);
        controller.localeController = mock(LocaleController.class);

        Hardcoded.enableDefaults(controller);
    }

    @Test
    public void testGetIdentifier() {
        assertEquals("szenarien-overview", controller.getIdentifier());
    }

    @Test
    public void testGetSzenarien() {
        List<Szenario> expected = Arrays.asList(SZENARIO1, SZENARIO2);
        when(controller.anwenderloesungFacade.getPublishedModel()).thenReturn(epfModel("modelIdentifier"));
        when(controller.anwenderloesungFacade.getSzenarien("modelIdentifier")).thenReturn(expected);
        List<Szenario> szenarien = controller.getSzenarien();
        assertEquals(expected, szenarien);
    }

    @Test
    public void testCostumizeSzenario() {
        when(controller.szenarioProjektdatenController.display(SZENARIO1)).thenReturn("szenario-projektdaten");
        assertEquals("szenario-projektdaten?faces-redirect=true", controller.costumizeSzenario(SZENARIO1));
        verify(controller.szenarioEigeneElementeController).resetData();
    }

    @Test
    public void testGetSampleSzenarioLinkFor_french() {
        when(controller.localeController.getLanguage()).thenReturn("fr");

        Szenario szenario = mock(Szenario.class);
        when(szenario.getName()).thenReturn("szenario_01-Szenario-Name");

        String link = controller.getSampleSzenarioLinkFor(szenario);

        assertEquals(LINK_PREFIX + "szenario_01-Szenario-Name/start_fr.html", link);
    }

    @Test
    public void testGetSampleSzenarioLinkFor_german() {
        when(controller.localeController.getLanguage()).thenReturn("de");

        Szenario szenario = mock(Szenario.class);
        when(szenario.getName()).thenReturn("szenario_01-Szenario-Name");

        String link = controller.getSampleSzenarioLinkFor(szenario);

        assertEquals(LINK_PREFIX + "szenario_01-Szenario-Name/start_de.html", link);
    }
}
