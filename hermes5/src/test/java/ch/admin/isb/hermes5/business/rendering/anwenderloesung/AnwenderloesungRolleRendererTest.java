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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

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
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungRolleRendererTest extends AbstractRendererTest {

    private static final String MODEL = "model";

    private AnwenderloesungRolleRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungRolleRenderer();
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
    public void testResponsibleRolle() {
        assertTrue(renderer.isResponsible(rolle("role")));
    }
    @Test
    public void testNotResponsilbeAufgabe() {
        assertFalse(renderer.isResponsible(aufgabe("a1", modul("m1t"))));
    }
    
    @Test
    public void buildRolle() throws IOException {
        Rolle rolle = rolle("projektleiter");
        String x = renderModelElement(MODEL, rolle,  new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("model/de/roleid_projektleiter/briefDescription"));
        assertTrue(x.contains("<p>model/de/id_projektleiter_presentation/mainDescription</p>"));
        assertTrue(x.contains("<p>model/de/id_projektleiter_presentation/skills</p>"));
        assertTrue(x.contains("<p>model/de/id_projektleiter_presentation/assignmentApproaches</p>"));
        assertTrue(x, x.contains("Zurück zu Szenario"));
        assertTrue(x, x.contains("s1"));
        assertTrue(x, x.contains("Rolle:"));
        checkHtmlString(x);
    }

    @Test
    public void buildRolleWithRelationships() throws IOException {
        Rolle rolle = rolle("projektleiter");
        aufgabe("aufgabe", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        String x = renderModelElement(MODEL, rolle,  new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("Beziehungen"));
        assertTrue(x.contains("href=\"modul_modul1.html\">model/de/disciplineid_modul1/presentationName"));
        checkHtmlString(x);
    }

    @Test
    public void buildRolleWithoutMainDescription() throws IOException {
       
        Rolle rolle = rolle("projektleiter");
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(MODEL, szenario("s1"),
        new SzenarioUserData(), Arrays.asList("de"), true, true, true,true);
        LocalizationEngine localizationEngineDe = getLocalizationEngineDe();
        when(
                translationRepository.getLocalizedText(anyString(), anyString(), anyString(),
                        eq("mainDescription"))).thenReturn(null);
        
        String x = renderer.renderModelElement(rolle, localizationEngineDe, container);
        assertFalse(x, x.contains("<p>model/de/id_projektleiter_presentation/mainDescription</p>"));
        assertTrue(x, x.contains("<p>model/de/id_projektleiter_presentation/skills</p>"));
        assertTrue(x, x.contains("<p>model/de/id_projektleiter_presentation/assignmentApproaches</p>"));
        checkHtmlString(x);
    }

    
    private String renderModelElement(String identifier, AbstractMethodenElement modelElement,
            SzenarioUserData szenarioUserData, List<String> languages, Szenario szenario) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(identifier, szenario,
                szenarioUserData, languages, true, true, true,true);

        return renderer.renderModelElement(modelElement, super.getLocalizationEngineDe(), container);
    }
}
