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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.customelement.RelationshipTablePreprocessor;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.util.Hardcoded;

public class OnlinePublikationAufgabeRendererTest extends AbstractRendererTest {

    private OnlinePublikationAufgabeRenderer renderer;
    private Aufgabe aufgabe;
    private Aufgabe aufgabe2;
    private Modul modul;
    private LocalizationEngine localizationEngine;
    private List<MenuItem> menu;

    @Before
    public void setUp() throws Exception {
        renderer = new OnlinePublikationAufgabeRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        renderer.relationshipTablePreprocessor = new RelationshipTablePreprocessor();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
        modul = modul("m1");
        aufgabe = aufgabe("a1", modul, ergebnis("e1"), ergebnis("e2"));
        aufgabe2 = aufgabe("a2", modul);
        localizationEngine = getLocalizationEngineDe();
        menu = Arrays.asList(new MenuItem(aufgabe), new MenuItem(modul));
    }

    @Test
    public void testIsResponsibleFor() {
        assertTrue(renderer.isResponsibleFor(aufgabe));
    }

    @Test
    public void testIsNotResponsibleFor() {
        assertFalse(renderer.isResponsibleFor(modul));
    }

    @Test
    public void render() {
        String x = renderModelElement(aufgabe, menu, localizationEngine, null);
        assertNotNull(x);
        checkHtmlString(wrapInBody(x));
    }

    @Test
    public void doNotRenderRenderEmptyRelationshiptable() {

        String x = renderModelElement(aufgabe2, menu, localizationEngine, null);
        assertFalse(x, x.contains("class=\"relationshiptable\""));
        checkHtmlString(wrapInBody(x));
    }

    @Test
    public void buildAufgabeWithRelationshipsOnlinePublikation() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Aufgabe aufgabe = aufgabe("aufgabe", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        String x = renderModelElement(aufgabe, menu, localizationEngine, null);
        assertTrue(x, x.contains("class=\"relationshiptable\""));
        assertTrue(x, x.contains("Beziehungen"));
        assertFalse(x, x.contains("Zurück zu Szenario"));
        assertTrue(x, x.contains("href=\"index.xhtml?element=modul_modul1.html\">model/de/disciplineid_modul1/presentationName"));
        checkHtmlString(wrapInBody(x));
    }

    @Test
    public void buildAufgabeWithMenuOnlinePublikation() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Aufgabe aufgabe1 = aufgabe("aufgabe1", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        Aufgabe aufgabe2 = aufgabe("aufgabe2", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        ArrayList<MenuItem> adjustedMenu = buildMenu(aufgabe1, aufgabe2);

        String x = renderModelElement(aufgabe1, adjustedMenu, localizationEngine, null);
        assertTrue(
                x,
                x.contains("<div class=\"navi-ebene1-div active\"><div class=\"navi-ebene1-text-div\"><a href=\"index.xhtml?element=kategorie_Aufgaben.html\""));
        assertTrue(
                x,
                x.contains("<div class=\"navi-ebene2-div selected\"><div class=\"navi-ebene2-text-div\"><a href=\"index.xhtml?element=aufgabe_aufgabe1.html\""));
        assertTrue(x, x.contains("Beschreibung2"));
        assertTrue(x, x.contains("aufgabe1"));
        assertTrue(x, x.contains("aufgabe2"));
        assertFalse(x, x.contains("Zurück zu Szenario"));
        checkHtmlString(wrapInBody(x));
    }

    @Test
    public void buildAufgabeWithMenuOnlinePublikationOnlySearchableContent() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Aufgabe aufgabe1 = aufgabe("aufgabe1", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        Aufgabe aufgabe2 = aufgabe("aufgabe2", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        ArrayList<MenuItem> adjustedMenu = buildMenu(aufgabe1, aufgabe2);

        String x = renderModelElementOnlySearchableContent(aufgabe1, adjustedMenu, localizationEngine, null);
        assertFalse(x, x.contains("navi-ebene1"));
        checkHtmlString(wrapInBody(x));
    }

    private ArrayList<MenuItem> buildMenu(Aufgabe aufgabe1, Aufgabe aufgabe2) {
        ArrayList<MenuItem> adjustedMenu = new ArrayList<MenuItem>();
        adjustedMenu.add(new MenuItem(new Beschreibung(report("Beschreibung1"), null)));
        adjustedMenu.add(new MenuItem(new Beschreibung(report("Beschreibung2"), null)));
        MenuItem aufgabenMenu = new MenuItem(new Kategorie(customCategory("Aufgaben"), null));
        MenuItem menuSelected = new MenuItem(aufgabe1);
        menuSelected.setSelected();
        aufgabenMenu.setActive();
        aufgabenMenu.getSubItems().add(menuSelected);
        aufgabenMenu.getSubItems().add(new MenuItem(aufgabe2));

        adjustedMenu.add(aufgabenMenu);
        return adjustedMenu;
    }

    @Test
    public void buildAufgabeWithRelationshipsOnlinePublikationOnlySearchableContent() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Aufgabe aufgabe = aufgabe("aufgabe", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        String x = renderModelElementOnlySearchableContent(aufgabe, menu, localizationEngine, null);
        assertFalse(x, x.contains("class=\"relationshiptable\""));
        assertFalse(x, x.contains("Beziehungen"));
        checkHtmlString(wrapInBody(x));
    }

    private String renderModelElement(AbstractMethodenElement methodenElement, List<MenuItem> adjustedMenu,
            LocalizationEngine localizationEngine2, PublishContainer hermesWebsite) {
        return renderer.renderModelElement(methodenElement, adjustedMenu, localizationEngine2, hermesWebsite, false);
    }

    private String renderModelElementOnlySearchableContent(AbstractMethodenElement methodenElement,
            List<MenuItem> adjustedMenu, LocalizationEngine localizationEngine2, PublishContainer hermesWebsite) {
        return renderer.renderModelElement(methodenElement, adjustedMenu, localizationEngine2, hermesWebsite, true);
    }

}
