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

public class TopMenuControllerTest {

    private static TopMenuController controller;

    @Before
    public void setUp() throws Exception {
        controller = new TopMenuController();
        controller.localeController = mock(LocaleController.class);
        Hardcoded.enableDefaults(controller);
    }

    @Test
    public void testGetPageUrlDe() {
        when(controller.localeController.getLanguage()).thenReturn("de");
        assertEquals("http://www.isb.admin.ch/themen/methoden/01661/index.html?lang=de", controller.getUeberHermesUrl());
    }

    @Test
    public void testGetPageUrlFr() {
        when(controller.localeController.getLanguage()).thenReturn("fr");
        assertEquals("http://www.isb.admin.ch/themen/methoden/01661/index.html?lang=fr", controller.getUeberHermesUrl());
    }

    @Test
    public void testGetPageUrlIt() {
        when(controller.localeController.getLanguage()).thenReturn("it");
        assertEquals("http://www.isb.admin.ch/themen/methoden/01661/index.html?lang=it", controller.getUeberHermesUrl());
    }

    @Test
    public void testGetPageUrlEn() {
        when(controller.localeController.getLanguage()).thenReturn("en");
        assertEquals("http://www.isb.admin.ch/themen/methoden/01661/index.html?lang=en", controller.getUeberHermesUrl());
    }

}
