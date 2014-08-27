/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelutil.MethodenElementFactory;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.util.AssertUtils;

public class SzenarioStripperTest {

    private SzenarioStripper szenarioStripper;
    private Phase phase1;
    private Phase phase2;
    private Modul modul1;
    private Modul modul2;
    private Modul modul3;
    private Ergebnis ergebnis1;
    private Ergebnis ergebnis2;
    private Ergebnis ergebnis3;
    private Ergebnis ergebnis5;
    private Ergebnis ergebnis4;
    private Ergebnis ergebnis6;
    private Aufgabe aufgabe1;
    private Aufgabe aufgabe2;
    private Aufgabe aufgabe3;
    private Aufgabe aufgabe4;
    private Aufgabe aufgabe5;
    private Rolle rolle1;
    private Rolle rolle2;

    @Before
    public void setUp() throws Exception {
        szenarioStripper = new SzenarioStripper();
        szenarioStripper.methodenElementFactory = new MethodenElementFactory();
        rolle1 = rolle("rolle1");
        rolle2 = rolle("rolle2");
        phase1 = phase("phase1");
        phase2 = phase("phase2");
        modul1 = modul("modul1");
        modul2 = modul("modul2");
        modul3 = modul("modul3");
        ergebnis1 = ergebnis("ergebnis1");
        ergebnis2 = ergebnis("ergebnis2");
        ergebnis3 = ergebnis("ergebnis3");
        ergebnis5 = ergebnis("ergebnis5");
        ergebnis4 = ergebnis("ergebnis4");
        ergebnis6 = ergebnis("ergebnis6");
        aufgabe1 = aufgabe("aufgabe1", modul1, ergebnis1, ergebnis2);
        aufgabe2 = aufgabe("aufgabe2", modul1, ergebnis3);
        aufgabe3 = aufgabe("aufgabe3", modul2, ergebnis4);
        aufgabe4 = aufgabe("aufgabe4", modul2, ergebnis5);
        aufgabe5 = aufgabe("aufgabe5", modul3, ergebnis6);
    }

    @Test
    public void testStripSzenarioToSelectedItemsNullSzenarioSelectedItem() {
        Szenario szenarioFull = new Szenario(null);
        Szenario szenarioToSelectedItems = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, null);
        assertEquals(szenarioFull, szenarioToSelectedItems);
    }

    @Test
    public void testStripSzenarioToSelectedItemsSzenario() {
        Szenario szenarioFull = szenario("szenario");
        SzenarioItem szenarioTree = new SzenarioItem();
        Szenario szenarioToSelectedItems = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        assertFalse(szenarioFull.equals(szenarioToSelectedItems));
        assertEquals(szenarioFull.getMethodConfiguration(), szenarioToSelectedItems.getMethodConfiguration());
    }

    @Test
    public void testAufgabeObjectGeneration() {
        Szenario szenarioFull = szenario("szenario");
        SzenarioItem szenarioTree = new SzenarioItem();
        Szenario szenario = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        Map<String, Aufgabe> map = new HashMap<String, Aufgabe>();
        assertNotNull(szenario);
        List<Modul> module = szenario.getModule();
        for (Modul modul : module) {
            List<Aufgabe> aufgaben = modul.getAufgaben();
            for (Aufgabe aufgabe : aufgaben) {
                if (map.containsKey(aufgabe.getId())) {
                    assertSame(aufgabe, map.get(aufgabe.getId()));
                } else {
                    map.put(aufgabe.getId(), aufgabe);
                }
            }
        }

    }

    @Test
    public void testErgebnisObjectGeneration() {
        Szenario szenarioFull = szenario("szenario");
        SzenarioItem szenarioTree = new SzenarioItem();
        Szenario szenario = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        Map<String, Ergebnis> map = new HashMap<String, Ergebnis>();
        assertNotNull(szenario);
        List<Modul> module = szenario.getModule();
        for (Modul modul : module) {
            List<Aufgabe> aufgaben = modul.getAufgaben();
            for (Aufgabe aufgabe : aufgaben) {
                List<Ergebnis> ergebnisse = aufgabe.getErgebnisse();
                for (Ergebnis ergebnis : ergebnisse) {
                    if (map.containsKey(ergebnis.getId())) {
                        assertSame(ergebnis, map.get(ergebnis.getId()));
                    } else {
                        map.put(ergebnis.getId(), ergebnis);
                    }
                }
            }
        }

    }

    @Test
    public void testStripSzenarioToSelectedItemsPhaseSelected() {
        Szenario szenarioFull = buildSzenario(phase1, phase2);
        SzenarioItem szenarioTree = new SzenarioItem();
        szenarioTree.getChildren().add(new SzenarioItem(phase1.getId(), phase1.getPresentationName(), szenarioTree));
        szenarioTree.getChildren().add(new SzenarioItem(phase2.getId(), phase2.getPresentationName(), szenarioTree));
        Szenario szenarioToSelectedItems = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        assertFalse(szenarioFull.equals(szenarioToSelectedItems));
        List<Phase> phasen = szenarioToSelectedItems.getPhasen();
        assertEquals(2, phasen.size());
        assertFalse(phasen.contains(phase1));
        assertFalse(phasen.contains(phase2));
        assertEquals(phase1.getElement(), phasen.get(0).getElement());
        assertEquals(phase2.getElement(), phasen.get(1).getElement());
    }

    @Test
    public void testStripSzenarioToSelectedItemsPhasenModuleAufgabenErgebnissSelected() {
        Szenario szenarioFull = buildModel();
        SzenarioItem szenarioTree = buildScenarioTree();

        Szenario szenarioToSelectedItems = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        assertFalse(szenarioFull.equals(szenarioToSelectedItems));

        // PHASEN

        List<Phase> phasen = szenarioToSelectedItems.getPhasen();
        assertEquals(2, phasen.size());
        assertFalse(phasen.contains(phase1));
        assertFalse(phasen.contains(phase2));

        assertEquals(phase1.getElement(), phasen.get(0).getElement());
        List<Aufgabe> aufgabenPhase1 = phasen.get(0).getAufgaben();
        assertEquals(3, aufgabenPhase1.size());

        assertEquals(phase2.getElement(), phasen.get(1).getElement());
        List<Aufgabe> aufgabenPhase2 = phasen.get(1).getAufgaben();
        assertEquals(2, aufgabenPhase2.size());

        // AUFGABEN

        Aufgabe aufg1 = aufgabenPhase1.get(0);
        assertEquals(aufgabe1.getElement(), aufg1.getElement());
        Modul mod1 = aufg1.getModule().get(0);
        assertEquals(modul1.getElement(), mod1.getElement());

        Aufgabe aufg2 = aufgabenPhase1.get(1);
        assertEquals(aufgabe2.getElement(), aufg2.getElement());
        assertEquals(mod1, aufg2.getModule().get(0));

        Aufgabe aufg3 = aufgabenPhase1.get(2);
        assertEquals(aufgabe3.getElement(), aufg3.getElement());
        Modul mod2 = aufg3.getModule().get(0);
        assertEquals(modul2.getElement(), mod2.getElement());

        Aufgabe aufg4 = aufgabenPhase2.get(0);
        assertEquals(aufgabe4.getElement(), aufg4.getElement());
        assertEquals(mod2, aufg4.getModule().get(0));

        Aufgabe aufg5 = aufgabenPhase2.get(1);
        assertEquals(aufgabe5.getElement(), aufg5.getElement());
        Modul mod3 = aufg5.getModule().get(0);
        assertEquals(modul3.getElement(), mod3.getElement());

        // MODULE

        assertEquals(2, mod1.getAufgaben().size());
        AssertUtils.assertContains(mod1.getAufgaben(), aufg1, aufg2);

        assertEquals(2, mod2.getAufgaben().size());
        AssertUtils.assertContains(mod2.getAufgaben(), aufg3, aufg4);

        assertEquals(1, mod3.getAufgaben().size());
        AssertUtils.assertContains(mod3.getAufgaben(), aufg5);

        // ERGEBNISSE
        assertEquals(2, aufg1.getErgebnisse().size());
        Ergebnis erg1 = aufg1.getErgebnisse().get(0);
        assertEquals(ergebnis1.getElement(), erg1.getElement());
        Ergebnis erg2 = aufg1.getErgebnisse().get(1);
        assertEquals(ergebnis2.getElement(), erg2.getElement());

        // ROLLEN
        assertEquals(rolle1.getElement(), aufg1.getVerantwortlicheRolle().getElement());
        assertEquals(rolle2.getElement(), aufg2.getVerantwortlicheRolle().getElement());
        assertEquals(rolle1.getElement(), aufg3.getVerantwortlicheRolle().getElement());
        assertEquals(rolle1.getElement(), aufg4.getVerantwortlicheRolle().getElement());
        assertEquals(rolle1.getElement(), aufg5.getVerantwortlicheRolle().getElement());

        assertEquals(rolle1.getElement(), erg1.getVerantwortlicheRollen().get(0).getElement());
        assertEquals(rolle2.getElement(), erg2.getVerantwortlicheRollen().get(0).getElement());

    }

    @Test
    public void testStripSzenarioToSelectedItemsDeselectPhase1() {
        Szenario szenarioFull = buildModel();
        SzenarioItem szenarioTree = buildScenarioTree();
        deselect(szenarioTree, phase1);
        Szenario szenarioToSelectedItems = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        // PHASEN

        List<Phase> phasen = szenarioToSelectedItems.getPhasen();
        assertEquals(1, phasen.size());
        assertFalse(phasen.contains(phase2));

        assertEquals(phase2.getElement(), phasen.get(0).getElement());
        List<Aufgabe> aufgabenPhase2 = phasen.get(0).getAufgaben();
        assertEquals(2, aufgabenPhase2.size());
        // AUFGABEN
        Aufgabe aufg4 = aufgabenPhase2.get(0);
        assertEquals(aufgabe4.getElement(), aufg4.getElement());
        Modul mod2 = aufg4.getModule().get(0);
        assertEquals(modul2.getElement(), mod2.getElement());

        Aufgabe aufg5 = aufgabenPhase2.get(1);
        assertEquals(aufgabe5.getElement(), aufg5.getElement());
        Modul mod3 = aufg5.getModule().get(0);
        assertEquals(modul3.getElement(), mod3.getElement());
        // ERGEBNISSE
        assertEquals(1, aufg4.getErgebnisse().size());
        Ergebnis erg5 = aufg4.getErgebnisse().get(0);
        assertEquals(ergebnis5.getElement(), erg5.getElement());
        Ergebnis erg6 = aufg5.getErgebnisse().get(0);
        assertEquals(ergebnis6.getElement(), erg6.getElement());

        // ROLLEN
        assertEquals(rolle1.getElement(), aufg4.getVerantwortlicheRolle().getElement());
        assertEquals(rolle1.getElement(), aufg5.getVerantwortlicheRolle().getElement());

        assertEquals(rolle1.getElement(), erg5.getVerantwortlicheRollen().get(0).getElement());
        assertEquals(rolle1.getElement(), erg6.getVerantwortlicheRollen().get(0).getElement());
    }


    @Test
    public void testStripSzenarioToSelectedItemsDeselectErgebnis2AndErgebnis6() {
        Szenario szenarioFull = buildModel();
        SzenarioItem szenarioTree = buildScenarioTree();
        deselect(szenarioTree, ergebnis2);
        deselect(szenarioTree, ergebnis6);
        Szenario szenarioToSelectedItems = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        // PHASEN

        List<Phase> phasen = szenarioToSelectedItems.getPhasen();
        assertEquals(2, phasen.size());
        assertFalse(phasen.contains(phase1));
        assertFalse(phasen.contains(phase2));

        assertEquals(phase1.getElement(), phasen.get(0).getElement());
        List<Aufgabe> aufgabenPhase1 = phasen.get(0).getAufgaben();
        assertEquals(3, aufgabenPhase1.size());

        assertEquals(phase2.getElement(), phasen.get(1).getElement());
        List<Aufgabe> aufgabenPhase2 = phasen.get(1).getAufgaben();
        assertEquals(2, aufgabenPhase2.size());
        
        // AUFGABEN

        Aufgabe aufg1 = aufgabenPhase1.get(0);
        assertEquals(aufgabe1.getElement(), aufg1.getElement());
        Modul mod1 = aufg1.getModule().get(0);
        assertEquals(modul1.getElement(), mod1.getElement());
        
        assertEquals(1, aufg1.getErgebnisse().size());
        assertEquals(ergebnis1.getElement(), aufg1.getErgebnisse().get(0).getElement());
        
        Aufgabe aufg5 = aufgabenPhase2.get(1);
        assertEquals(aufgabe5.getElement(), aufg5.getElement());
        Modul mod3 = aufg5.getModule().get(0);
        assertEquals(modul3.getElement(), mod3.getElement());
        
        assertEquals(0, aufg5.getErgebnisse().size());
        
    }
    @Test
    public void testStripSzenarioToSelectedItemsDeselectAufgabe2() {
        Szenario szenarioFull = buildModel();
        SzenarioItem szenarioTree = buildScenarioTree();
        deselect(szenarioTree, aufgabe2);
        Szenario szenarioToSelectedItems = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        // PHASEN

        List<Phase> phasen = szenarioToSelectedItems.getPhasen();
        assertEquals(2, phasen.size());
        assertFalse(phasen.contains(phase1));
        assertFalse(phasen.contains(phase2));

        assertEquals(phase1.getElement(), phasen.get(0).getElement());
        List<Aufgabe> aufgabenPhase1 = phasen.get(0).getAufgaben();
        assertEquals(2, aufgabenPhase1.size());

        assertEquals(phase2.getElement(), phasen.get(1).getElement());
        List<Aufgabe> aufgabenPhase2 = phasen.get(1).getAufgaben();
        assertEquals(2, aufgabenPhase2.size());
        
        // AUFGABEN

        Aufgabe aufg1 = aufgabenPhase1.get(0);
        assertEquals(aufgabe1.getElement(), aufg1.getElement());
        Modul mod1 = aufg1.getModule().get(0);
        assertEquals(modul1.getElement(), mod1.getElement());

        Aufgabe aufg3 = aufgabenPhase1.get(1);
        assertEquals(aufgabe3.getElement(), aufg3.getElement());
        Modul mod2 = aufg3.getModule().get(0);
        assertEquals(modul2.getElement(), mod2.getElement());

    }
    private Szenario buildModel() {
        setVerantwortlicheRolle(rolle1, aufgabe1, aufgabe3, aufgabe4, aufgabe5);
        setVerantwortlicheRolle(rolle2, aufgabe2);
        setVerantwortlicheRolle(rolle1, ergebnis1, ergebnis4, ergebnis5, ergebnis6);
        setVerantwortlicheRolle(rolle2, ergebnis2, ergebnis3);
        phase1.addAufgabe(aufgabe1);
        phase1.addAufgabe(aufgabe2);
        phase1.addAufgabe(aufgabe3);
        phase2.addAufgabe(aufgabe4);
        phase2.addAufgabe(aufgabe5);

        Szenario szenarioFull = buildSzenario(phase1, phase2);
        return szenarioFull;
    }

    @Test
    public void testStripSzenarioToSelectedItemsDeselectModul2() {
        Szenario szenarioFull = buildModel();
        SzenarioItem szenarioTree = buildScenarioTree();
        deselect(szenarioTree, modul2);
        Szenario szenarioToSelectedItems = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        
        // PHASEN

        List<Phase> phasen = szenarioToSelectedItems.getPhasen();
        assertEquals(2, phasen.size());
        assertFalse(phasen.contains(phase1));
        assertFalse(phasen.contains(phase2));

        assertEquals(phase1.getElement(), phasen.get(0).getElement());
        List<Aufgabe> aufgabenPhase1 = phasen.get(0).getAufgaben();
        assertEquals(2, aufgabenPhase1.size());

        assertEquals(phase2.getElement(), phasen.get(1).getElement());
        List<Aufgabe> aufgabenPhase2 = phasen.get(1).getAufgaben();
        assertEquals(1, aufgabenPhase2.size());
        
        // AUFGABEN

        Aufgabe aufg1 = aufgabenPhase1.get(0);
        assertEquals(aufgabe1.getElement(), aufg1.getElement());
        Modul mod1 = aufg1.getModule().get(0);
        assertEquals(modul1.getElement(), mod1.getElement());

        Aufgabe aufg2 = aufgabenPhase1.get(1);
        assertEquals(aufgabe2.getElement(), aufg2.getElement());
        assertEquals(mod1, aufg2.getModule().get(0));

        Aufgabe aufg5 = aufgabenPhase2.get(0);
        assertEquals(aufgabe5.getElement(), aufg5.getElement());
        Modul mod3 = aufg5.getModule().get(0);
        assertEquals(modul3.getElement(), mod3.getElement());
    }
    
    private void deselect(SzenarioItem szenarioTree, AbstractMethodenElement el) {
        if (el.getId().equals(szenarioTree.getId())) {
            szenarioTree.setSelected(false);
        } else {
            for (SzenarioItem item : szenarioTree.getChildren()) {
                deselect(item, el);
            }
        }

    }

    private void setVerantwortlicheRolle(Rolle rolle, Aufgabe... aufgaben) {
        for (Aufgabe aufgabe : aufgaben) {
            aufgabe.setVerantwortlicheRolle(rolle);
        }
    }

    private void setVerantwortlicheRolle(Rolle rolle, Ergebnis... ergebnisse) {
        for (Ergebnis ergebnis : ergebnisse) {
            ergebnis.addVerantwortlicheRolle(rolle);
        }
    }

    private SzenarioItem buildScenarioTree() {
        SzenarioItem szenarioTree = new SzenarioItem();
        SzenarioItem siPhase1 = addChild(szenarioTree, phase1);
        SzenarioItem siModul1_1 = addChild(siPhase1, modul1);
        SzenarioItem a1 = addChild(siModul1_1, aufgabe1);
        addChild(a1, ergebnis1);
        addChild(a1, ergebnis2);
        SzenarioItem a2 = addChild(siModul1_1, aufgabe2);
        addChild(a2, ergebnis3);
        SzenarioItem siModul1_2 = addChild(siPhase1, modul2);
        SzenarioItem a3 = addChild(siModul1_2, aufgabe3);
        addChild(a3, ergebnis4);
        SzenarioItem siPhase2 = addChild(szenarioTree, phase2);
        SzenarioItem siModul2_2 = addChild(siPhase2, modul2);
        SzenarioItem a4 = addChild(siModul2_2, aufgabe4);
        addChild(a4, ergebnis5);
        SzenarioItem siModul2_3 = addChild(siPhase2, modul3);
        SzenarioItem a5 = addChild(siModul2_3, aufgabe5);
        addChild(a5, ergebnis6);
        return szenarioTree;
    }

    private SzenarioItem addChild(SzenarioItem szenarioTree, AbstractMethodenElement me) {
        SzenarioItem newItem = new SzenarioItem(me.getId(), me.getPresentationName(), szenarioTree);
        szenarioTree.getChildren().add(newItem);
        return newItem;
    }

    private Szenario buildSzenario(Phase phase1, Phase phase2) {
        Szenario szenarioFull = szenario("szenario");
        szenarioFull.getPhasen().add(phase1);
        szenarioFull.getPhasen().add(phase2);
        return szenarioFull;
    }

}
