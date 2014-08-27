/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.onlinepublikation;

import static ch.admin.isb.hermes5.domain.MethodElementBuilder.*;
import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.Hardcoded;

public class OnlinePublikationBeschreibungRendererTest extends AbstractRendererTest {

    private OnlinePublikationBeschreibungRenderer renderer;
    private LocalizationEngine localizationEngine;
    private List<MenuItem> menu;
    private Beschreibung beschreibung;
    private Modul modul;

    @Before
    public void setUp() throws Exception {
        renderer = new OnlinePublikationBeschreibungRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
        localizationEngine = getLocalizationEngineDe();
        modul = modul("m1");
        beschreibung = new Beschreibung(report("report"), null);
        menu = Arrays.asList(new MenuItem(beschreibung), new MenuItem(modul));
    }

    @Test
    public void testIsResponsibleFor() {
        assertTrue(renderer.isResponsibleFor(beschreibung));
    }

    @Test
    public void testIsNotResponsibleFor() {
        assertFalse(renderer.isResponsibleFor(modul));
    }

    @Test
    public void buildBeschreibung() throws IOException {
        String x = renderModelElement(beschreibung, menu, localizationEngine, null);
        assertTrue(x, x.contains("model/de/id_report/presentationName"));
        assertTrue(x, x.contains("model/de/presentation_id_report/mainDescription"));
        checkHtmlString(wrapInBody(x));
    }
    
    private String renderModelElement(AbstractMethodenElement methodenElement, List<MenuItem> adjustedMenu,
            LocalizationEngine localizationEngine2, PublishContainer hermesWebsite) {
        return renderer.renderModelElement(methodenElement, adjustedMenu, localizationEngine2, hermesWebsite, false);
    }

}
