/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import static ch.admin.isb.hermes5.domain.Status.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Status;

public class LocaleControllerTest {

    private LocaleController localeController;
    private EPFModel model;
    private UIViewRoot uiViewRootMock;

    @Before
    public void setUp() throws Exception {
        localeController = new LocaleController();
        localeController.context = mock(FacesContext.class);
        localeController.anwenderloesungFacade = mock(AnwenderloesungFacade.class);
        model = new EPFModel();
        model.setIdentifier("identifier");
        model.setStatusFr(Status.FREIGEGEBEN);
        model.setStatusIt(Status.IN_ARBEIT);
        model.setStatusEn(Status.UNVOLLSTAENDIG);
        when(localeController.anwenderloesungFacade.getPublishedModel()).thenReturn(model);
        uiViewRootMock = mock(UIViewRoot.class);
        when(localeController.context.getViewRoot()).thenReturn(uiViewRootMock);
        when(uiViewRootMock.getLocale()).thenReturn(Locale.FRENCH);
        localeController.init();
    }
    @Test
    public void testIsInit() {
        assertEquals("fr", localeController.getLanguage());
    }
    
    @Test
    public void testIsInitWithNotEnabledLocaleFallsBackToDE() {
        when(uiViewRootMock.getLocale()).thenReturn(Locale.ENGLISH);
        localeController.init();
        assertEquals("de", localeController.getLanguage());
        verify(uiViewRootMock).setLocale(eq(new Locale("de")));
    }

    
    @Test
    public void testIsLastDEFreigegeben() {
        setStati(IN_ARBEIT, IN_ARBEIT, UNVOLLSTAENDIG);
        assertTrue(localeController.isLast("de"));
        assertFalse(localeController.isLast("fr"));
        assertFalse(localeController.isLast("it"));
        assertFalse(localeController.isLast("en"));
    }
    @Test
    public void testIsLastDE_FR_Freigegeben() {
        setStati(FREIGEGEBEN, IN_ARBEIT, UNVOLLSTAENDIG);
        assertFalse(localeController.isLast("de"));
        assertTrue(localeController.isLast("fr"));
        assertFalse(localeController.isLast("it"));
        assertFalse(localeController.isLast("en"));
    }
    @Test
    public void testIsLastDE_FR_IT_Freigegeben() {
        setStati(FREIGEGEBEN, FREIGEGEBEN, UNVOLLSTAENDIG);
        assertFalse(localeController.isLast("de"));
        assertFalse(localeController.isLast("fr"));
        assertTrue(localeController.isLast("it"));
        assertFalse(localeController.isLast("en"));
    }
    @Test
    public void testIsLastDE_FR_IT_EN_Freigegeben() {
        setStati(FREIGEGEBEN, FREIGEGEBEN, FREIGEGEBEN);
        assertFalse(localeController.isLast("de"));
        assertFalse(localeController.isLast("fr"));
        assertFalse(localeController.isLast("it"));
        assertTrue(localeController.isLast("en"));
    }
    @Test
    public void testIsLastDE_IT_Freigegeben() {
        setStati(UNVOLLSTAENDIG, FREIGEGEBEN, UNVOLLSTAENDIG);
        assertFalse(localeController.isLast("de"));
        assertFalse(localeController.isLast("fr"));
        assertFalse(localeController.isLast("en"));
        assertTrue(localeController.isLast("it"));
    }
    
    private void setStati(Status fr, Status it, Status en) {
        model.setStatusFr(fr);
        model.setStatusIt(it);
        model.setStatusEn(en);
    }
    
    @Test
    public void testIsEnabledFR() {
        assertTrue(localeController.isEnabled("fr"));
    }

    @Test
    public void testIsEnabledDe() {
        assertTrue(localeController.isEnabled("de"));
    }

    @Test
    public void testIsEnabledEn() {
        assertFalse(localeController.isEnabled("en"));
    }

    @Test
    public void testIsEnabledIt() {
        assertFalse(localeController.isEnabled("it"));
    }

    @Test
    public void testGetLocale() {
        assertEquals(Locale.FRENCH, localeController.getLocale());
    }

    @Test
    public void testGetLanguage() {
        assertEquals("fr", localeController.getLanguage());
    }

    @Test
    public void testSetLanguage() {
        localeController.setLanguage("en");
        assertEquals(Locale.ENGLISH, localeController.getLocale());
        assertEquals("en", localeController.getLanguage());
    }

}
