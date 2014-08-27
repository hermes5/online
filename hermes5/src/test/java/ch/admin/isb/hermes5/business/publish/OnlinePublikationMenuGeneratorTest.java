/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import static ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder.*;
import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.onlinepublikation.MenuItem;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.MethodElementBuilder;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.util.Hardcoded;

public class OnlinePublikationMenuGeneratorTest {

    private final Map<String, MethodElement> index = new HashMap<String, MethodElement>();

    private OnlinePublikationMenuGenerator generator;
    private Beschreibung anotherReport1;
    private Beschreibung anotherReport2;
    private Kategorie kategorie;

    private Beschreibung phasenmodell;

    @Before
    public void setUp() throws Exception {
        generator = new OnlinePublikationMenuGenerator();
        Hardcoded.enableDefaults(generator);
    }

    @Test
    public void testGenerateMenu() {
        List<MenuItem> generateMenu = generator.generateMenu(publishContainer());
        assertEquals(2, generateMenu.size());
        MenuItem phasenmodellMenu = generateMenu.get(0);
        assertTrue(phasenmodellMenu.getSubItems().isEmpty());
        MenuItem reporteMenu = generateMenu.get(1);
        assertEquals(2, reporteMenu.getSubItems().size());
        assertFalse(reporteMenu.isSubElementsSorted());
    }

    @Test
    public void testGenerateMenuAufgabeSorted() {
        Aufgabe aufgabe1 = aufgabe("aufgabe1", modul("m1"));
        Aufgabe aufgabe2 = aufgabe("aufgabe2", modul("m1"));

        assertTrue(addKategorieAndGenerate(aufgabe1, aufgabe2).isSubElementsSorted());
    }

    @Test
    public void testGenerateMenuModulSorted() {
        Modul modul1 = modul("m1");
        Modul modul2 = modul("m2");

        assertTrue(addKategorieAndGenerate(modul1, modul2).isSubElementsSorted());
    }

    @Test
    public void testGenerateMenuErgebnisSorted() {
        Ergebnis ergebnis1 = ergebnis("e1");
        Ergebnis ergebnis2 = ergebnis("e2");
        assertTrue(addKategorieAndGenerate(ergebnis1, ergebnis2).isSubElementsSorted());
    }

    @Test
    public void testGenerateMenuRolleSorted() {
        Rolle rolle1 = rolle("r1");
        Rolle rolle2 = rolle("r2");
        assertTrue(addKategorieAndGenerate(rolle1, rolle2).isSubElementsSorted());
    }

    @Test
    public void testGenerateMenuBeschreibungNotSorted() {
        Beschreibung beschreibung1 = beschreibung("r1");
        Beschreibung beschreibung2 = beschreibung("r1");
        assertFalse(addKategorieAndGenerate(beschreibung1, beschreibung2).isSubElementsSorted());
    }

    @Test
    public void testGenerateMenuModulAufgabeMixedNotSorted() {
        Modul modul1 = modul("m1");
        Aufgabe aufgabe2 = aufgabe("aufgabe2", modul("m1"));
        assertFalse(addKategorieAndGenerate(modul1, aufgabe2).isSubElementsSorted());
    }
    
    @Test
    public void testTrimToTopMenu() {
        List<MenuItem> generateMenu = generator.generateMenu(publishContainer());
        List<MenuItem> trimToTopMenu = generator.trimToTopMenu(generateMenu);
        assertEquals(2, trimToTopMenu.size());
        for (MenuItem menuItem : trimToTopMenu) {
            assertTrue(menuItem.getSubItems().isEmpty());
        }
       
        
    }

    private MenuItem addKategorieAndGenerate(AbstractMethodenElement e1, AbstractMethodenElement e2) {
        PublishContainer publishContainer = publishContainer();
        CustomCategory customCat = new CustomCategory();
        customCat.setId("customCat");
        Kategorie kategorieModul = new Kategorie(customCat, index);
        kategorieModul.getChildren().add(e1);
        kategorieModul.getChildren().add(e2);
        publishContainer.getPublishingRoot().getChildren().add(kategorieModul);
        List<MenuItem> generateMenu = generator.generateMenu(publishContainer);
        assertEquals(3, generateMenu.size());
        MenuItem addedMenu = generateMenu.get(2);
        assertEquals(2, addedMenu.getSubItems().size());
        return addedMenu;
    }

    @Test
    public void testGenerateMenuWithModul() {
        PublishContainer publishContainer = publishContainer();
        Modul modul = modul("m1");
        publishContainer.getPublishingRoot().getChildren().add(modul);

        List<MenuItem> generateMenu = generator.generateMenu(publishContainer);
        assertEquals(3, generateMenu.size());
        MenuItem modulMenu = generateMenu.get(2);
        assertEquals(modul, modulMenu.getMethodenElement());
        assertEquals(0, modulMenu.getSubItems().size());
    }

   

    @Test
    public void testAdustTopSelected() {
        List<MenuItem> generateMenu = generator.generateMenu(publishContainer());
        List<MenuItem> adjustMenuForItem = generator.adjustMenuForItem(generateMenu, phasenmodell,
                getLocalizationEngineDe());
        assertEquals(2, adjustMenuForItem.size());
        assertEquals("report_phasenmodell.html", adjustMenuForItem.get(0).getLink());
        assertTrue(adjustMenuForItem.get(0).isSelected());
        assertFalse(adjustMenuForItem.get(0).isActive());
        assertEquals("kategorie_reporte.html", adjustMenuForItem.get(1).getLink());
        assertTrue(adjustMenuForItem.get(0).getSubItems().isEmpty());
        assertTrue(adjustMenuForItem.get(1).getSubItems().isEmpty());
    }

    @Test
    public void testAdustSubSelected() {
        List<MenuItem> generateMenu = generator.generateMenu(publishContainer());
        List<MenuItem> adjustMenuForItem = generator.adjustMenuForItem(generateMenu, anotherReport2,
                getLocalizationEngineDe());
        assertEquals(2, adjustMenuForItem.size());
        assertTrue(adjustMenuForItem.get(0).getSubItems().isEmpty());
        assertFalse(adjustMenuForItem.get(1).getSubItems().isEmpty());
        assertEquals(2, adjustMenuForItem.get(1).getSubItems().size());
    }

    private PublishContainer publishContainer() {
        CustomCategory root = new CustomCategory();
        root.setId("HERMES_Methode");
        anotherReport1 = new Beschreibung(MethodElementBuilder.report("anotherReport1"), index);
        anotherReport2 = new Beschreibung(MethodElementBuilder.report("anotherReport2"), index);
        Kategorie reports = new Kategorie(MethodElementBuilder.customCategory("reporte"), index);
        reports.getChildren().add(anotherReport1);
        reports.getChildren().add(anotherReport2);
        kategorie = new Kategorie(root, index);

        phasenmodell = new Beschreibung(MethodElementBuilder.report("phasenmodell"), index);
        kategorie.getChildren().add(phasenmodell);
        kategorie.getChildren().add(reports);
        return new PublishContainer(kategorie, null, null, new ArrayList<Szenario>());

    }

}
