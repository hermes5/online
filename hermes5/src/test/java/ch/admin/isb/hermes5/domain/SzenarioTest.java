/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;



public class SzenarioTest {

    private Szenario szenario;

    @Before
    public void setUp() {
        szenario = szenario("testSzenario");
        szenario.getPhasen().add(phaseFull("init"));
    }

    @Test
    public void testGetAufgaben() {
        List<Aufgabe> aufgaben = szenario.getPhasen().get(0).getModule().get(0).getAufgaben();
        assertEquals(3, aufgaben.size());
    }

    @Test
    public void testGetErgebnisse() {
        List<Aufgabe> aufgaben = szenario.getPhasen().get(0).getModule().get(0).getAufgaben();

        List<Ergebnis> ergebnisse = aufgaben.get(1).getErgebnisse();
        assertEquals(1, ergebnisse.size());
    }
}
