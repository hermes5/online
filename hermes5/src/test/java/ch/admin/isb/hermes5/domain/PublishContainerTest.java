/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import static ch.admin.isb.hermes5.domain.MethodElementBuilder.*;
import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class PublishContainerTest {
    private PublishContainer publishContainer;
    private List<AbstractMethodenElement> elementsToPublish;
    @Before
    public void setUp() throws Exception {
        elementsToPublish = new ArrayList<AbstractMethodenElement>();
        List<Phase> phasen = new ArrayList<Phase>();
        publishContainer = new PublishContainer(kategorie("root"), elementsToPublish, phasen, new ArrayList<Szenario>());
        
    }

    @Test
    public void testGetAufgaben() {
        Modul modul1 = modul("m1");
        Modul modul2 = modul("m2");
        elementsToPublish.addAll(Arrays.asList(modul1, aufgabe("a1", modul2), aufgabe("a1", modul2)));
        List<Aufgabe> aufgaben = publishContainer.getAufgaben();
        assertEquals(2, aufgaben.size());
        
    }
    @Test
    public void testGetErgebnisse() {
        Modul modul1 = modul("m1");
        Modul modul2 = modul("m2");
        elementsToPublish.addAll(Arrays.asList(modul1, aufgabe("a1", modul2), aufgabe("a1", modul2), ergebnis("abc"),ergebnis("abc2")));
        List<Ergebnis> ergebnisse = publishContainer.getErgebnisse();
        assertEquals(2, ergebnisse.size());
        
    }
    @Test
    public void testGetModule() {
        Modul modul1 = modul("m1");
        Modul modul2 = modul("m2");
        elementsToPublish.addAll(Arrays.asList(modul1, modul2, aufgabe("a1", modul2), aufgabe("a1", modul2), ergebnis("abc"),ergebnis("abc2")));
        List<Modul>  module = publishContainer.getModule();
        assertEquals(2, module.size());
        assertTrue(module.contains(modul1));
        assertTrue(module.contains(modul2));
    }

}
