/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Szenario;

public class SzenarioAssemblerIntegrationTest {

    @Test
    public void testBuildScenario() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenario = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 0);
        assertNotNull(szenario);
        assertFalse(szenario.getPhasen().isEmpty());
        assertFalse(szenario.getPhasen().get(0).getAufgaben().isEmpty());
        Aufgabe aufgabe = szenario.getPhasen().get(1).getAufgaben().get(0);
        assertFalse(aufgabe.getErgebnisse().isEmpty());
        assertNotNull(aufgabe.getVerantwortlicheRolle());
        assertFalse(aufgabe.getModule().isEmpty());
    }

    @Test
    public void testAufgabeObjectGeneration() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenario = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 0);
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
    public void testAufgaben() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenario = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 0);
        List<Aufgabe> aufgaben = szenario.getAufgaben();
        assertEquals(getAufgaben(szenario.getModule()), aufgaben);
    }

    @Test
    public void testErgebnisse() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenario = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 0);
        List<Ergebnis> ergebnisse = szenario.getErgebnisse();
        assertEquals(getErgebnisse(szenario.getModule()), ergebnisse);
    }

    private List<Aufgabe> getAufgaben(List<Modul> module) {
        ArrayList<Aufgabe> result = new ArrayList<Aufgabe>();
        Set<String> alreadyAddedIds = new HashSet<String>();
        for (Modul modul : module) {
            for (Aufgabe aufgabe : modul.getAufgaben()) {
                if (!alreadyAddedIds.contains(aufgabe.getId())) {
                    alreadyAddedIds.add(aufgabe.getId());
                    result.add(aufgabe);
                }
            }
        }
        return result;
    }

    private List<Ergebnis> getErgebnisse(List<Modul> module) {
        List<Ergebnis> result = new ArrayList<Ergebnis>();
        Set<String> alreadyAddedIds = new HashSet<String>();
        for (Aufgabe aufgabe : getAufgaben(module)) {
            for (Ergebnis ergebnis : aufgabe.getErgebnisse()) {
                if (!alreadyAddedIds.contains(ergebnis.getId())) {
                    alreadyAddedIds.add(ergebnis.getId());
                    result.add(ergebnis);
                }
            }
        }
        return result;
    }

    @Test
    public void testErgebnisObjectGeneration() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenario = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 0);
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
    public void testVerantwortlicheFuerAuftrag() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenario = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 0);
        List<Aufgabe> aufgaben = szenario.getAufgaben();
        boolean found = false;
        for (Aufgabe aufgabe : aufgaben) {
            if ("initialisierung_beauftragen_und_steuern".equals(aufgabe.getElement().getName())) {
                found = true;
                assertNotNull(aufgabe.getVerantwortlicheRolle());
                assertEquals("auftraggeber", aufgabe.getVerantwortlicheRolle().getElement().getName());
            }
        }
        assertTrue(found);
    }

}
