/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.MethodElementBuilder;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.RollenGruppe;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.util.Hardcoded;

public class CustomElementErgebnisTabelleRendererTest extends AbstractRendererTest {

    private CustomElementErgebnisTabelleRenderer renderer;
    private PublishContainer publishContainer;
    private List<AbstractMethodenElement> elementsToPublish = new ArrayList<AbstractMethodenElement>();
    List<Phase> phasen = new ArrayList<Phase>();

    @Before
    public void setUp() throws Exception {
        renderer = new CustomElementErgebnisTabelleRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        renderer.customElementRendererUtil = new CustomElementRendererUtil();
        Hardcoded.enableDefaults(renderer, renderer.velocityAdapter);
    }

    @Test
    public void testGetCustomElementKey() {
        assertEquals("${h5_table_ergebnis_uebersicht}", renderer.getCustomElementKey());
    }

    @Test
    public void renderCustomElement() {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        Ergebnis ergebnis = ergebnis("e21");
        Ergebnis ergebnis2 = ergebnis("e22");
        aufgabe("a21", modul("m1"), ergebnis);
        aufgabe("a21", modul("m2"), ergebnis2);
        elementsToPublish.add(ergebnis);
        elementsToPublish.add(ergebnis2);
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        assertTrue(result, result.contains("<table"));
        assertTrue(result, result.contains("e21"));
        assertFalse(result, result.contains("a21"));
        assertTrue(result, result.contains("even"));
        assertTrue(result, result.contains("odd"));
        assertTrue(result, result.contains("<a href=\"index.xhtml?element=modul_m1.html\">"));
        assertTrue(result, result.contains("<a href=\"index.xhtml?element=ergebnis_e21.html\">"));
        assertTrue(result, result.contains("<a href=\"index.xhtml?element=ergebnis_e22.html\">"));
        

        checkPartHtmlString(result);

    }

    @Test
    public void renderCustomElementSameModulShouldNotOccurTwice() {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        Ergebnis ergebnis = ergebnis("e21");
        Ergebnis ergebnis2 = ergebnis("e22");
        Modul modul = modul("m1");
        aufgabe("a21", modul, ergebnis, ergebnis2);
        aufgabe("a22", modul, ergebnis);
        elementsToPublish.add(ergebnis);
        elementsToPublish.add(ergebnis2);
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        assertTrue(result, result.contains("<table"));
        assertTrue(result, result.contains("e21"));
        assertTrue(result, result.contains("even"));
        assertFalse(result, result.contains("odd"));
        String modulSequence = "<a href=\"index.xhtml?element=modul_m1.html\">";
        assertTrue(result, result.contains(modulSequence));
        assertFalse(result,
                result.substring(result.indexOf(modulSequence) + modulSequence.length()).contains(modulSequence));

        checkPartHtmlString(result);
    }

    @Test
    public void renderCustomElementTwoDifferentModuls() {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        Ergebnis ergebnis = ergebnis("e21");
        Ergebnis ergebnis2 = ergebnis("e22");
        Modul modul1 = modul("m1");
        Modul modul2 = modul("m2");
        aufgabe("a21", modul1, ergebnis, ergebnis2);
        aufgabe("a22", modul2, ergebnis);
        elementsToPublish.add(ergebnis);
        elementsToPublish.add(ergebnis2);
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String renderCustomElement = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        String result = removeWhiteSpaces(renderCustomElement);
        assertNotNull(result);
        assertTrue(result, result.contains("<table"));
        assertTrue(result, result.contains("e21"));
        assertTrue(result, result.contains("even"));
        assertTrue(result, result.contains("odd"));
        assertTrue(result,
                result.contains("<td> <a href=\"index.xhtml?element=modul_m1.html\"> model/de/disciplineid_m1/presentationName </a> </td>"));
        assertTrue(result,
                result.contains("<td> <a href=\"index.xhtml?element=modul_m2.html\"> model/de/disciplineid_m2/presentationName </a> </td>"));
        assertFalse(result,
                result.contains("<td> <a href=\"index.xhtml?element=modul_m1.html\"> model/de/disciplineid_m1/presentationName "
                        + "</a> <a href=\"index.xhtml?element=modul_m2.html\"> model/de/disciplineid_m2/presentationName </a> </td>"));

        checkPartHtmlString(result);
        System.out.println(result);
    }

    @Test
    public void renderCustomElementSameErgebnisTwoAufgaben() {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        Ergebnis ergebnis = ergebnis("e21");
        Modul modul1 = modul("m1");
        aufgabe("a21", modul1, ergebnis);
        aufgabe("a22", modul1, ergebnis);
        elementsToPublish.add(ergebnis);
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String renderCustomElement = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        String result = removeWhiteSpaces(renderCustomElement);
        assertNotNull(result);
        assertTrue(result, result.contains("<table"));
        assertTrue(result, result.contains("e21"));
        assertEquals(2, result.split("ergebnis_e21.html").length);
        assertTrue(result, result.contains("even"));
        assertFalse(result, result.contains("odd"));
        assertTrue(result,
                result.contains("<td> <a href=\"index.xhtml?element=modul_m1.html\"> model/de/disciplineid_m1/presentationName </a> </td>"));
        checkPartHtmlString(result);
    }
    
    private String removeWhiteSpaces(String s) {
        return s.replaceAll("\\s\\s+", " ");
    }

    @Test
    public void renderCustomElementWithRollenGruppeAnwender() {
        customElementRollenGruppe("anwender");
    }

    @Test
    public void renderCustomElementWithRollenGruppeBetreiber() {
        customElementRollenGruppe("betreiber");
    }

    @Test
    public void renderCustomElementWithRollenGruppeErsteller() {
        customElementRollenGruppe("ersteller");
    }

    private void customElementRollenGruppe(String name) {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        RollenGruppe rollenGruppe = rollenGruppe(name);
        Ergebnis ergebnis = ergebnis("a21");
        Rolle verantwortlicheRolle = rolle("rolle");
        verantwortlicheRolle.addRollenGruppe(rollenGruppe);
        ergebnis.addVerantwortlicheRolle(verantwortlicheRolle);
        aufgabe("dummy", modul("m1"), ergebnis); // Link modul to ergebnis
        elementsToPublish.add(ergebnis);
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        assertTrue(result, result.contains("<table"));
        assertTrue(result, result.contains("X"));
        checkPartHtmlString(result);
    }

    @Test
    public void renderCustomElementWithRollenGruppeAnwenderRequired() {
        customElementRollenGruppeRequired("anwender");
    }

    @Test
    public void renderCustomElementWithRollenGruppeBetreiberRequired() {
        customElementRollenGruppeRequired("betreiber");
    }

    @Test
    public void renderCustomElementWithRollenGruppeErstellerRequired() {
        customElementRollenGruppeRequired("ersteller");
    }

    private void customElementRollenGruppeRequired(String name) {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        RollenGruppe rollenGruppe = rollenGruppe(name);
        Ergebnis ergebnis = ergebnisRequired("a21");
        assertTrue(ergebnis.isRequired());
        Rolle verantwortlicheRolle = rolle("rolle");
        aufgabe("dummy", modul("m1"), ergebnis); // Link modul to ergebnis
        verantwortlicheRolle.addRollenGruppe(rollenGruppe);
        ergebnis.addVerantwortlicheRolle(verantwortlicheRolle);
        elementsToPublish.add(ergebnis);
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        assertTrue(result, result.contains("<table"));
        assertTrue(result, result.contains("X*"));
        checkPartHtmlString(result);
    }

    @Test
    public void testApplyEmptyErgebnis() {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        PublishContainer publishContainer = new PublishContainer(root, elementsToPublish, phasen,
                new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        assertEquals("", result);

    }

}
