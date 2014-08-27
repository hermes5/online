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

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.Localizable;

public class LocalizerTest {

    private Localizer localizer;

    @Before
    public void setUp() throws Exception {

        localizer = new Localizer();
        localizer.anwenderloesungFacade = mock(AnwenderloesungFacade.class);
        localizer.localeController = mock(LocaleController.class);
        when(localizer.anwenderloesungFacade.getPublishedModel()).thenReturn(epfModel("model"));
        when(localizer.localeController.getLanguage()).thenReturn("fr");
    }
    @Test
    public void testLocalize() {
        Localizable localizable = mock(Localizable.class);
        when(localizer.anwenderloesungFacade.localize("model", localizable, "fr")).thenReturn("textFr");
        assertEquals("textFr", localizer.localize(localizable));
    }

}
