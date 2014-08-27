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
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.util.Hardcoded;

public class CustomElementSzenarioTabelleRendererTest extends AbstractRendererTest {

    private CustomElementSzenarioTabelleRenderer renderer;
    private Aufgabe a1;
    private Aufgabe a2;
    private Aufgabe a3;
    private Aufgabe a4;
    private Modul m1;
    private LocalizationEngine localizationEngine;
    private Szenario szenario;
    private Modul m2;
    private Phase phase1;
    private Phase phase2;
    private PublishContainer publishContainer;

    @Before
    public void setUp() throws Exception {
        renderer = new CustomElementSzenarioTabelleRenderer();
        Hardcoded.enableDefaults(renderer);
        renderer.szenarioAufgabeTableBuilder = new SzenarioAufgabeTableBuilder();
        renderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
        m1 = modul("m1");
        a1 = aufgabe("a1", m1);
        a2 = aufgabe("a2", m1);
        a3 = aufgabe("a3", m1);
        m2 = modul("m2");
        a4 = aufgabe("a4", m2);
        phase1 = phase("phase1");
        phase1.addAufgabe(a1);
        phase1.addAufgabe(a2);
        phase1.addAufgabe(a4);
        phase2 = phase("phase2");
        phase2.addAufgabe(a3);
        szenario = szenario("ABC");
        szenario.getPhasen().add(phase1);
        szenario.getPhasen().add(phase2);

        List<AbstractMethodenElement> elementsToPublish = new ArrayList<AbstractMethodenElement>();
        publishContainer = new PublishContainer(null, elementsToPublish  , Arrays.asList(phase1, phase2),  Arrays.asList(szenario));
        
        localizationEngine = getLocalizationEngineDe();
    }

    @Test
    public void testMatch() {
        assertTrue(renderer.matches("before ${h5_table_szenario_ABC} after"));
    }

    @Test
    public void testNotCloseMatch() {
        assertFalse(renderer.matches("before ${h5_table_szenario_ABC after"));
    }

    @Test
    public void testNotMatch() {
        assertFalse(renderer.matches("before ${h5_table_sze_ABC} after"));
    }

    @Test
    public void applyCustomElementRenderer() {
       String html = "<div>before</div> ${h5_table_szenario_ABC} <div>after</div>";
        String x = renderer.applyCustomElementRenderer(html, publishContainer, localizationEngine);
        assertNotNull(x);
        assertEquals(x, 2, x.split("phase1").length);
        assertTrue(x, x.contains("overviewtable"));
        assertTrue(x, x.contains("cell-empty"));
        assertTrue(x, x.contains("before"));
        assertTrue(x, x.contains("after"));
        assertTrue(x, x.contains(m1.getId()));
        assertTrue(x, x.contains(m1.getName()));
        checkPartHtmlString(x);
        System.out.println(x);
    }

}
