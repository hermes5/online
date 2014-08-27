/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.anwenderloesung;

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
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.CustomAufgabe;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.CustomModul;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungErgebnisRendererTest extends AbstractRendererTest {

    private static final String MODEL = "model";

    private AnwenderloesungErgebnisRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungErgebnisRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        renderer.relationshipTablePreprocessor = new RelationshipTablePreprocessor();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
    }

    @Test
    public void testNotResponsibleModul() {
        assertFalse(renderer.isResponsible(modul("m1")));
    }

    @Test
    public void testResponsibleErgebnis() {
        assertTrue(renderer.isResponsible(ergebnis("e1")));
    }

    public void testResponsibleCustomErgebnis() {
        assertTrue(renderer.isResponsible(new CustomErgebnis("e1", "main", null, null, null, null)));
    }

    @Test
    public void testNotResponsilbeAufgabe() {
        assertFalse(renderer.isResponsible(aufgabe("a1", modul("m1t"))));
    }

    @Test
    public void buildErgebnisWithBeziehungen() throws IOException {
        Ergebnis ergebnis = ergebnis("name", "url/Vorlage1.dot|url/Vorlage2.dot");
        aufgabe("aufgabe", modul("modul1"), ergebnis).setVerantwortlicheRolle(rolle("rolle2"));
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        String x = renderModelElement(MODEL, ergebnis, new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        x = removeWhiteSpaces(x);
        assertTrue(x, x.contains("model/de/wpid_name/briefDescription"));
        assertTrue(x, x.contains("<a href=\"rolle_rolle1.html\"> model/de/roleid_rolle1/presentationName</a>"));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"../../templates/de/Vorlage1.dot\""));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"../../templates/de/Vorlage2.dot\""));
        checkHtmlString(x);
    }

    private String removeWhiteSpaces(String s) {
        s = s.replaceAll("\\n+", " ");
        s = s.replaceAll("\\s\\s+", " ");
        return s;
    }

    @Test
    public void buildErgebnisWithBeziehungenMultipleResponsible() throws IOException {
        Ergebnis ergebnis = ergebnis("name", "url/Vorlage1.dot|url/Vorlage2.dot");
        aufgabe("aufgabe", modul("modul1"), ergebnis).setVerantwortlicheRolle(rolle("rolle2"));
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        ergebnis.addVerantwortlicheRolle(rolle("rolle2"));
        String x = renderModelElement(MODEL, ergebnis, new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        x = removeWhiteSpaces(x);
        assertTrue(x.contains("model/de/wpid_name/briefDescription"));
        assertTrue(
                x,
                x.contains("<a href=\"rolle_rolle1.html\"> model/de/roleid_rolle1/presentationName</a>, <a href=\"rolle_rolle2.html\"> model/de/roleid_rolle2/presentationName</a>"));
        assertFalse(
                x,
                x.contains("<a href=\"rolle_rolle1.html\"> model/de/roleid_rolle1/presentationName</a>, <a href=\"rolle_rolle2.html\"> model/de/roleid_rolle2/presentationName</a>,"));
        checkHtmlString(x);
    }

    @Test
    public void buildCustomErgebnis() throws IOException {
        Ergebnis ergebnis = customErgebnis("customName", "Vorlage1.docx");
        aufgabe("aufgabe", modul("modul1"), ergebnis).setVerantwortlicheRolle(rolle("rolle2"));
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        String x = renderModelElement(MODEL, ergebnis, new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(
                x,
                x.contains("<a class=\"custom-artifact-link\" href=\"../../templates/de/custom/Vorlage1.docx\" target=\"_blank\">Vorlage1.docx</a>"));
        assertTrue(x, x.contains("beschreibung_customName"));
        checkHtmlString(x);
    }

    @Test
    public void buildCustomErgebnisWithBeziehungenMultipleResposible() throws IOException {
        CustomErgebnis ergebnis = new CustomErgebnis("customName", "beschreibung_" + "customName", "Vorlage1.docx",
                "name".getBytes(), new ArrayList<String>(), Arrays.asList(rolle("rolle1"), rolle("rolle2")));

        CustomModul modul = new CustomModul("cm");
        CustomAufgabe customAufgabe = new CustomAufgabe(ergebnis.getCustomName());
        customAufgabe.addErgebnis(ergebnis);
        customAufgabe.addModul(modul);

        String x = renderModelElement(MODEL, ergebnis, new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(
                x,
                x.contains("<a class=\"custom-artifact-link\" href=\"../../templates/de/custom/Vorlage1.docx\" target=\"_blank\">Vorlage1.docx</a>"));
        assertTrue(x, x.contains("beschreibung_customName"));
        assertTrue(x, x.contains("model/de/roleid_rolle1/presentationName"));
        assertTrue(x, x.contains("model/de/roleid_rolle2/presentationName"));
        checkHtmlString(x);
    }

    @Test
    public void buildErgebnisAnwenderloesung() throws IOException {
        Ergebnis ergebnis = ergebnis("name", "url/Vorlage1.dot|url/Vorlage2.dot");
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        String x = renderModelElement(MODEL, ergebnis, new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x, x.contains("Zurück zu Szenario"));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"../../templates/de/Vorlage1.dot\""));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"../../templates/de/Vorlage2.dot\""));
        assertTrue(x, x.contains("Ergebnis: "));
        checkHtmlString(x);
    }

    private String renderModelElement(String identifier, AbstractMethodenElement modelElement,
            SzenarioUserData szenarioUserData, List<String> languages, Szenario szenario) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(identifier, szenario,
                szenarioUserData, languages, true, true, true,true);

        return renderer.renderModelElement(modelElement, super.getLocalizationEngineDe(), container);
    }
}
