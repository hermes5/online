/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.common.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.presentation.anwenderloesung.controller.LocaleController;
import ch.admin.isb.hermes5.util.Hardcoded;

public class WelcomeControllerTest {

    private static WelcomeController controller;

    @Before
    public void setUp() throws Exception {
        controller = new WelcomeController();
        controller.localeController = mock(LocaleController.class);
        Hardcoded.enableDefaults(controller);
        controller.welcomePageUrlDe = Hardcoded.configuration("http://www.egovernment.ch/dokumente/publikationen/hermes5_startseite/indexhermes.html");
        controller.welcomePageUrlFr = Hardcoded.configuration("http://www.egovernment.ch/dokumente/publikationen/hermes5_startseite/indexhermes_fr.html");
        controller.welcomePageUrlIt = Hardcoded.configuration("http://www.egovernment.ch/dokumente/publikationen/hermes5_startseite/indexhermes_it.html");
        controller.welcomePageUrlEn = Hardcoded.configuration("http://www.egovernment.ch/dokumente/publikationen/hermes5_startseite/indexhermes_en.html");
    }

    @Test
    public void testGetPageUrlDe() {
        when(controller.localeController.getLanguage()).thenReturn("de");
       assertEquals("http://www.egovernment.ch/dokumente/publikationen/hermes5_startseite/indexhermes.html", controller.getWelcomePageUrl()); 
    }
    @Test
    public void testGetPageUrlFr() {
        when(controller.localeController.getLanguage()).thenReturn("fr");
        assertEquals("http://www.egovernment.ch/dokumente/publikationen/hermes5_startseite/indexhermes_fr.html", controller.getWelcomePageUrl()); 
    }
    @Test
    public void testGetPageUrlIt() {
        when(controller.localeController.getLanguage()).thenReturn("it");
        assertEquals("http://www.egovernment.ch/dokumente/publikationen/hermes5_startseite/indexhermes_it.html", controller.getWelcomePageUrl()); 
    }
    @Test
    public void testGetPageUrlEn() {
        when(controller.localeController.getLanguage()).thenReturn("en");
        assertEquals("http://www.egovernment.ch/dokumente/publikationen/hermes5_startseite/indexhermes_en.html", controller.getWelcomePageUrl()); 
    }
    @Test
    public void testGetPageHeight() {
       assertEquals("1400px", controller.getWelcomePageHeight()); 
    }
}
