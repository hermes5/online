/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.onlinepublikation;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.customelement.RelationshipTablePreprocessor;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.util.Hardcoded;

public class OnlinePublikationErgebnisRendererTest extends AbstractRendererTest {

    private OnlinePublikationErgebnisRenderer renderer;
    private LocalizationEngine localizationEngine;
    private List<MenuItem> menu;
    private Ergebnis ergebnis;
    private Modul modul;

    @Before
    public void setUp() throws Exception {
        renderer = new OnlinePublikationErgebnisRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        renderer.relationshipTablePreprocessor = new RelationshipTablePreprocessor();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
        localizationEngine = getLocalizationEngineDe();
        modul = modul("m1");
        ergebnis = ergebnis("e1");
        menu = Arrays.asList(new MenuItem(ergebnis), new MenuItem(modul));
    }

    @Test
    public void testIsResponsibleFor() {
        assertTrue(renderer.isResponsibleFor(ergebnis));
    }

    @Test
    public void testIsNotResponsibleFor() {
        assertFalse(renderer.isResponsibleFor(modul));
    }

    @Test
    public void buildErgebnisOnlinePublication() throws IOException {
        Ergebnis ergebnis = ergebnis("name", "url/Vorlage1.dot|url/Vorlage2.dot");
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        String x = renderModelElement(ergebnis, menu, localizationEngine, null);
        assertFalse(x, x.contains("Zurück zu Szenario"));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"content/de_url/Vorlage1.dot\""));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"content/de_url/Vorlage2.dot\""));
        checkHtmlString(wrapInBody(x));
    }
    @Test
    public void buildErgebnisOnlinePublicationWithPurpose() throws IOException {
        Ergebnis ergebnis = ergebnis("name", "url/Vorlage1.dot|url/Vorlage2.dot");
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        String x = renderModelElement(ergebnis, menu, localizationEngine, null);
        assertTrue(x, x.contains("Inhalt"));  
        assertTrue(x, x.contains("purpose")); 
        checkHtmlString(wrapInBody(x));
    }

    @Test
    public void doNotRenderRenderEmptyRelationshiptable() {
        Ergebnis ergebnis = ergebnis("name", "url/Vorlage1.dot|url/Vorlage2.dot");
        String x = renderModelElement(ergebnis, menu, localizationEngine, null);
        assertFalse(x, x.contains("class=\"relationshiptable\""));
        checkHtmlString(wrapInBody(x));
    }

    @Test
    public void buildErgebnisWithRelationshipsOnlinePublikation() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Ergebnis ergebnis = ergebnis("ergebnis", rolle);
        aufgabe("aufgabe", modul("modul1"), rolle, ergebnis);
        String x = renderModelElement(ergebnis, menu, localizationEngine, null);
        assertTrue(x, x.contains("class=\"relationshiptable\""));
        assertTrue(x, x.contains("Beziehungen"));
        assertFalse(x, x.contains("Zurück zu Szenario"));
        assertTrue(x, x.contains("href=\"index.xhtml?element=modul_modul1.html\">model/de/disciplineid_modul1/presentationName"));
        checkHtmlString(wrapInBody(x));
    }
    private String renderModelElement(AbstractMethodenElement methodenElement, List<MenuItem> adjustedMenu,
            LocalizationEngine localizationEngine2, PublishContainer hermesWebsite) {
        return renderer.renderModelElement(methodenElement, adjustedMenu, localizationEngine2, hermesWebsite, false);
    }
}
