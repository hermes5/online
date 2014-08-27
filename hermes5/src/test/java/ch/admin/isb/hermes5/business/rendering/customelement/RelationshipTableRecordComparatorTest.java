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

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.RelationshipTableRecord;
import ch.admin.isb.hermes5.domain.Rolle;

public class RelationshipTableRecordComparatorTest extends AbstractRendererTest{

    private RelationshipTableRecordComparator comparator;

    private Modul modul_a = modul("m_a");
    private Modul modul_b = modul("m_b");
    private Aufgabe aufgabe_a = aufgabe("a_a", modul_a);
    private Aufgabe aufgabe_b = aufgabe("a_b", modul_a);
    private Rolle rolle_a = rolle("r_a");
    private Rolle rolle_b = rolle("r_b");
    private Ergebnis ergebnis_a = ergebnis("e_a");
    private Ergebnis ergebnis_b = ergebnis("e_b");
    private List<Rolle> verantwortlichFuerErgebnisA = Arrays.asList(rolle_a);
    private List<Rolle> verantwortlichFuerErgebnisB = Arrays.asList(rolle_b);

    @Before
    public void setup() {
        comparator = new RelationshipTableRecordComparator(getLocalizationEngineDe());
    }
    
    @Test
    public void testCompareEqual() {
        RelationshipTableRecord r1 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        RelationshipTableRecord r2 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        assertEquals(0,comparator.compare(r1, r2));
    }
    
    @Test
    public void testCompareModul() {
        RelationshipTableRecord r1 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        RelationshipTableRecord r2 = new RelationshipTableRecord(modul_b, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        assertTrue(comparator.compare(r1, r2) < 0);
    }

    @Test
    public void testCompareAufgabe() {
        RelationshipTableRecord r1 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        RelationshipTableRecord r2 = new RelationshipTableRecord(modul_a, aufgabe_b, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        assertTrue(comparator.compare(r1, r2) < 0);
    }
    @Test
    public void testCompareVerantwortlichAufgabe() {
        RelationshipTableRecord r1 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        RelationshipTableRecord r2 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_b, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        assertTrue(comparator.compare(r1, r2) < 0);
    }
    @Test
    public void testCompareErgebnis() {
        RelationshipTableRecord r1 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        RelationshipTableRecord r2 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_b, 
                verantwortlichFuerErgebnisA);
        assertTrue(comparator.compare(r1, r2) < 0);
    }
    
    @Test
    public void testCompareErgebnisWithNullPresentation() {
        RelationshipTableRecord r1 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        RelationshipTableRecord r2 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_b, 
                verantwortlichFuerErgebnisA);
        assertTrue(comparator.compare(r1, r2) < 0);
    }
    
    @Test
    public void testCompareVerantwortlichErgebnis() {
        RelationshipTableRecord r1 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisA);
        RelationshipTableRecord r2 = new RelationshipTableRecord(modul_a, aufgabe_a, rolle_a, ergebnis_a, 
                verantwortlichFuerErgebnisB);
        assertTrue(comparator.compare(r1, r2) < 0);
    }
}
