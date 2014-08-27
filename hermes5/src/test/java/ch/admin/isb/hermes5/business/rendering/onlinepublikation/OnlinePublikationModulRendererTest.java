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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.customelement.ModulAufgabeTableBuilder;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.util.Hardcoded;

public class OnlinePublikationModulRendererTest extends AbstractRendererTest {

    private OnlinePublikationModulRenderer renderer;
    private Aufgabe a1;
    private Aufgabe a2;
    private Aufgabe a3;
    private Aufgabe a4;
    private Modul m1;
    private LocalizationEngine localizationEngine;
    private List<MenuItem> menu;

    @Before
    public void setUp() throws Exception {
        renderer = new OnlinePublikationModulRenderer();
        renderer.modulAufgabeTableBuilder = new ModulAufgabeTableBuilder();
        renderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
        m1 = modul("m1");
        a1 = aufgabe("a1", m1 );
        a2 = aufgabe("a2", m1 );
        a3 = aufgabe("a3", m1 );
        Modul m2 = modul("m2");
        a4 = aufgabe("a4", m2 );
        localizationEngine=  getLocalizationEngineDe();
        MenuItem cat = new MenuItem(new Kategorie(customCategory("aufgaben"), null));
        MenuItem m1Menu = new MenuItem(m1);
        m1Menu.getSubItems().add(new MenuItem(a1));
        m1Menu.getSubItems().add(new MenuItem(a2));
        m1Menu.getSubItems().add(new MenuItem(a3));
        MenuItem m2Menu = new MenuItem(m2);
        cat.getSubItems().add(m1Menu);
        cat.getSubItems().add(m2Menu);
        menu = Arrays.asList(cat);
    }

    @Test
    public void testIsNotResponsibleFor() {
        assertFalse(renderer.isResponsibleFor(a1));
    }

    @Test
    public void testIsResponsibleFor() {
        assertTrue(renderer.isResponsibleFor(m1));
    }

    @Test
    public void render() {
        Phase phase1 = phase("phase1");
        phase1.addAufgabe(a1);
        phase1.addAufgabe(a2);
        phase1.addAufgabe(a4);
        Phase phase2 = phase("phase2");
        phase2.addAufgabe(a3);
        List<Phase> phasen = Arrays.asList(phase1, phase2);
        String x = renderModelElement(m1, menu, localizationEngine,
                new PublishContainer(null, null, phasen, new ArrayList<Szenario>()));
        assertTrue(x, x.contains(phase1.getId()));
        assertTrue(x, x.contains(phase2.getId()));
        assertTrue(x, x.contains(a1.getName()));
        assertTrue(x, x.contains(a2.getName()));
        assertTrue(x, x.contains(a3.getName()));
        assertFalse(x, x.contains(a4.getName()));
        assertTrue(x, x.contains("navi-ebene3"));
        
        assertFalse(x, x.contains("milestone"));
        checkHtmlString(wrapInBody(x));
    }
    
    @Test
    public void renderWithMilestones() {
        Phase phase1 = phase("phase1");
        phase1.addAufgabe(a1);
        phase1.addAufgabe(a2);
        phase1.addAufgabe(a4);
        Phase phase2 = phase("phase2");
        phase2.addAufgabe(a3);
        Aufgabe a5=aufgabe("milestone_a5",m1);
        phase2.addAufgabe(a5);
        List<Phase> phasen = Arrays.asList(phase1, phase2);
        String x = renderModelElement(m1, menu, localizationEngine,
                new PublishContainer(null, null, phasen, new ArrayList<Szenario>()));
        assertTrue(x, x.contains(phase1.getId()));
        assertTrue(x, x.contains(phase2.getId()));
        assertTrue(x, x.contains(a1.getName()));
        assertTrue(x, x.contains(a2.getName()));
        assertTrue(x, x.contains(a3.getName()));
        assertFalse(x, x.contains(a4.getName()));
        assertTrue(x, x.contains("navi-ebene3"));
        assertTrue(x, x.contains("<span class=\"cell-elements-box milestone\"><a href=\"index.xhtml?element=aufgabe_milestone_a5.html\">"));
        checkHtmlString(wrapInBody(x));
    }
    
    @Test
    public void dontRenderTableIfNotNecessary() {
        Phase phase1 = phase("phase1");
        Phase phase2 = phase("phase2");
        List<Phase> phasen = Arrays.asList(phase1, phase2);
        String x = renderModelElement(m1, menu, localizationEngine,
                new PublishContainer(null, null, phasen, new ArrayList<Szenario>()));
        System.out.println(x);
        assertFalse(x, x.contains(phase1.getId()));
        checkHtmlString(wrapInBody(x));
    }
    private String renderModelElement(AbstractMethodenElement methodenElement, List<MenuItem> adjustedMenu,
            LocalizationEngine localizationEngine2, PublishContainer hermesWebsite) {
        return renderer.renderModelElement(methodenElement, adjustedMenu, localizationEngine2, hermesWebsite, false);
    }
}
