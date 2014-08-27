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

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungPhaseRendererTest extends AbstractRendererTest {

    private static final String MODEL = "model";

    private AnwenderloesungPhaseRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungPhaseRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
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

    @Test
    public void testResponsiblePhase() {
        assertTrue(renderer.isResponsible(phaseFull("p1")));
    }

    public void testResponsibleCustomErgebnis() {
        assertTrue(renderer.isResponsible(new CustomErgebnis("e1", "main", null, null, null, null)));
    }

    @Test
    public void testNotResponsilbeAufgabe() {
        assertFalse(renderer.isResponsible(aufgabe("a1", modul("m1t"))));
    }

    
    @Test
    public void buildPhase() {
        Phase phase = phaseFull("abc");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        String x = renderModelElement(MODEL, phase, szenarioUserData, Arrays.asList("de"), szenario("s1"));
        assertTrue(x, x.contains("abc"));
        assertTrue(x, x.contains("Phase:"));
        checkHtmlString(x);
    }

    private String renderModelElement(String identifier, AbstractMethodenElement modelElement,
            SzenarioUserData szenarioUserData, List<String> languages, Szenario szenario) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(identifier, szenario,
                szenarioUserData, languages, true, true, true,true);

        return renderer.renderModelElement(modelElement, super.getLocalizationEngineDe(), container);
    }
}
