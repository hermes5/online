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
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.customelement.RelationshipTablePreprocessor;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungAufgabeRendererTest extends AbstractRendererTest {

    private static final String MODEL = "model";

    private AnwenderloesungAufgabeRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungAufgabeRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        renderer.relationshipTablePreprocessor = new RelationshipTablePreprocessor();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
    }

    @Test
    public void testNotResponsibleModul() {
        assertFalse(renderer.isResponsible(modul("m1")));
    }
    @Test
    public void testNotResponsibleErgebnis() {
        assertFalse(renderer.isResponsible(ergebnis("e1")));
    }
    public void testNotResponsibleRolle() {
        assertFalse(renderer.isResponsible(rolle("role")));
    }
    @Test
    public void testResponsilbeAufgabe() {
        assertTrue(renderer.isResponsible(aufgabe("a1", modul("m1t"))));
    }
    
    @Test
    public void buildAufgabe() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Aufgabe aufgabe = aufgabe("aufgabe1", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        String x = renderModelElement(MODEL, aufgabe, new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("model/de/taskid_aufgabe1/briefDescription"));
        assertTrue(x
                .contains("<a href=\"rolle_projektleiter.html\">model/de/roleid_projektleiter/presentationName</a>"));
        assertTrue(x.contains("<p>model/de/presentation_id_aufgabe1/alternatives</p>")); // Aktivitäten
        assertTrue(x, x.contains("Aufgabe:")); 
        checkHtmlString(x);
    }
    
    @Test
    public void buildAufgabeWithRelationships() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Aufgabe aufgabe = aufgabe("aufgabe", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        String x = renderModelElement(MODEL, aufgabe, new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("Beziehungen"));
        assertTrue(x, x.contains("Zurück zu Szenario"));
        assertTrue(x.contains("model/de/taskid_aufgabe/briefDescription"));
        assertTrue(x.contains("href=\"modul_modul1.html\">model/de/disciplineid_modul1/presentationName"));
        checkHtmlString(x);
    }
    
    private String renderModelElement(String identifier, AbstractMethodenElement modelElement,
            SzenarioUserData szenarioUserData, List<String> languages, Szenario szenario) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(identifier, szenario,
                szenarioUserData, languages, true, true, true,true);

        return renderer.renderModelElement(modelElement, super.getLocalizationEngineDe(), container);
    }
}
