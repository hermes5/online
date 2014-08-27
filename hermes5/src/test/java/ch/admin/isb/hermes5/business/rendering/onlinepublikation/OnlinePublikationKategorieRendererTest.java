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
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.Hardcoded;

public class OnlinePublikationKategorieRendererTest extends AbstractRendererTest {

    private OnlinePublikationKategorieRenderer renderer;
    private Kategorie kategorie;
    private Modul modul;
    private LocalizationEngine localizationEngine;
    private List<MenuItem> menu;

    @Before
    public void setUp() throws Exception {
        renderer = new OnlinePublikationKategorieRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
        modul = modul("m1");
        kategorie = new Kategorie(customCategory("cat"), null);
        localizationEngine=  getLocalizationEngineDe();
        menu = Arrays.asList(new MenuItem(kategorie), new MenuItem(modul));
    }

    @Test
    public void testIsResponsibleFor() {
        assertTrue(renderer.isResponsibleFor(kategorie));
    }

    @Test
    public void testIsNotResponsibleFor() {
        assertFalse(renderer.isResponsibleFor(modul));
    }
    
    @Test
    public void render() {
        String x = renderModelElement(kategorie, menu, localizationEngine, null);
        assertNotNull(x);
        checkHtmlString(wrapInBody(x));
    }
    @Test
    public void buildKategorieWithEinleitungBeschreibung() throws IOException {
        Beschreibung beschreibung =new Beschreibung( report("report"), null);
        Kategorie k = new Kategorie(customCategory("customCategory"),null);
        k.setEinleitung(beschreibung);
        String x =renderModelElement(k, menu, localizationEngine, null);
        assertTrue(x, x.contains("model/de/id_report/presentationName"));
        assertTrue(x, x.contains("model/de/presentation_id_report/mainDescription"));
        checkHtmlString(wrapInBody(x));
    }
    
    private String renderModelElement(AbstractMethodenElement methodenElement, List<MenuItem> adjustedMenu,
            LocalizationEngine localizationEngine2, PublishContainer hermesWebsite) {
        return renderer.renderModelElement(methodenElement, adjustedMenu, localizationEngine2, hermesWebsite, false);
    }
}
