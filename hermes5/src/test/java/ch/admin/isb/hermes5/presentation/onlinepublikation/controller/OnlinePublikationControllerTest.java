/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.onlinepublikation.controller;

import static ch.admin.isb.hermes5.domain.EPFModelBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.OnlinePublikationFacade;
import ch.admin.isb.hermes5.presentation.anwenderloesung.controller.LocaleController;
import ch.admin.isb.hermes5.util.Hardcoded;

public class OnlinePublikationControllerTest {

    private OnlinePublikationController controller;

    @Before
    public void setUp() throws Exception {
        controller = new OnlinePublikationController();
        Hardcoded.enableDefaults(controller);
        controller.onlinePublikationFacade = mock(OnlinePublikationFacade.class);
        controller.localeController = mock(LocaleController.class);
        when(controller.onlinePublikationFacade.getPublishedModel()).thenReturn(epfModel("identifier"));
        when(controller.localeController.getLanguage()).thenReturn("fr");
    }

    @Test
    public void testGetOnlinePublikationStart() {
        assertEquals("kategorie_methode_aufbau.html", controller.getOnlinePublikationStart());
    }

    @Test
    public void testGetPageContent() throws UnsupportedEncodingException {

        when(controller.onlinePublikationFacade.getPublishedFile("identifier", "fr", "/aufgabe42.html")).thenReturn(
                "html_content".getBytes("UTF8"));
        assertEquals("html_content", controller.getPageContent("aufgabe42.html"));
    }

    @Test
    public void testGetStartPageOnBlank() throws UnsupportedEncodingException {
        when(controller.onlinePublikationFacade.getPublishedFile("identifier", "fr", "/kategorie_methode_aufbau.html"))
                .thenReturn("html_content".getBytes("UTF8"));
        assertEquals("html_content", controller.getPageContent(""));
        verify(controller.onlinePublikationFacade, never()).getPublishedFile("identifier", "fr", "/");
    }

    @Test
    public void testGetStartPageOnNull() throws UnsupportedEncodingException {
        when(controller.onlinePublikationFacade.getPublishedFile("identifier", "fr", "/kategorie_methode_aufbau.html"))
                .thenReturn("html_content".getBytes("UTF8"));
        assertEquals("html_content", controller.getPageContent(null));
    }
    @Test
    public void testGetStartPageOnUnkonwn() throws UnsupportedEncodingException {
        when(controller.onlinePublikationFacade.getPublishedFile("identifier", "fr", "/kategorie_methode_aufbau.html"))
        .thenReturn("html_content".getBytes("UTF8"));
        assertEquals("html_content", controller.getPageContent("element.html"));
    }

    @Test(expected=Exception.class)
    public void testGetStartPageFailsThrowsException() throws UnsupportedEncodingException {
        when(controller.onlinePublikationFacade.getPublishedFile("identifier", "fr", "/kategorie_methode_aufbau.html"))
                .thenThrow(new RuntimeException("dummy"));
       controller.getPageContent("kategorie_methode_aufbau.html");
    }
}
