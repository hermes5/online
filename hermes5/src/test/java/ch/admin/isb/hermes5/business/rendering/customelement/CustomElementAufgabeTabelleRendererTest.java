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
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.MethodElementBuilder;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.RollenGruppe;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.util.Hardcoded;

public class CustomElementAufgabeTabelleRendererTest extends AbstractRendererTest {

    private CustomElementAufgabeTabelleRenderer renderer;
    private PublishContainer publishContainer;
    private List<AbstractMethodenElement> elementsToPublish = new ArrayList<AbstractMethodenElement>();
    List<Phase> phasen = new ArrayList<Phase>();

    @Before
    public void setUp() throws Exception {
        renderer = new CustomElementAufgabeTabelleRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        renderer.customElementRendererUtil = new CustomElementRendererUtil();
        Hardcoded.enableDefaults(renderer, renderer.velocityAdapter);
    }

    @Test
    public void testGetCustomElementKey() {
        assertEquals("${h5_table_aufgabe_uebersicht}", renderer.getCustomElementKey());
    }

    @Test
    public void renderCustomElement() {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        elementsToPublish.add(aufgabe("a21", modul("m1")));
        elementsToPublish.add(aufgabe("a22", modul("m2")));
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        assertTrue(result, result.contains("<table"));
        assertTrue(result, result.contains("m1"));
        assertTrue(result, result.contains("m2"));
        assertTrue(result, result.contains("a21"));
        assertTrue(result, result.contains("even"));
        assertTrue(result, result.contains("odd"));
        checkPartHtmlString(result);

    }
    @Test
    public void renderCustomElementOrder() {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        elementsToPublish.add(aufgabe("cde", modul("bmodul")));
        elementsToPublish.add(aufgabe("abc", modul("bmodul")));
        elementsToPublish.add(aufgabe("efg", modul("amodul")));
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        checkPartHtmlString(result);
        assertTrue(result, result.indexOf("efg")<result.indexOf("abc"));
        assertTrue(result, result.indexOf("abc")<result.indexOf("cde"));
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
        Aufgabe aufgabe = aufgabe("a21", modul("m1"));
        Rolle verantwortlicheRolle = rolle("rolle");
        verantwortlicheRolle.addRollenGruppe(rollenGruppe);
        aufgabe.setVerantwortlicheRolle(verantwortlicheRolle);
        elementsToPublish.add(aufgabe);
        publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        assertTrue(result, result.contains("<table"));
        assertTrue(result, result.contains("X"));
        checkPartHtmlString(result);
    }

    @Test
    public void testApplyEmptyAufgaben() {
        Map<String, MethodElement> methodLibraryIndex = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(MethodElementBuilder.customCategory("root"), methodLibraryIndex);
        PublishContainer publishContainer = new PublishContainer(root, elementsToPublish, phasen, new ArrayList<Szenario>());
        String result = renderer.renderCustomElement(publishContainer, getLocalizationEngineDe());
        assertNotNull(result);
        assertEquals("", result);

    }

}
