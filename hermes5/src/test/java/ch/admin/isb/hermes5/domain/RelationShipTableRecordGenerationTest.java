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

public class RelationShipTableRecordGenerationTest {

    private Ergebnis ergebnis1;
    private Ergebnis ergebnis2;
    private Modul modul1;
    private Rolle rolle1;
    private Rolle rolle2;

    @Before
    public void setUp() {
        ergebnis1 = ergebnis("ergebnis1");
        ergebnis2 = ergebnis("ergebnis2");
        modul1 = modul("modul1");
        rolle1 = rolle("rolle1");
        rolle2 = rolle("rolle2");
    }

    @Test
    public void testAufgabeRelationshipTableRecords() {
        ergebnis1.addVerantwortlicheRolle(rolle2);
        ergebnis2.addVerantwortlicheRolle(rolle1);
        Aufgabe aufgabe = aufgabe("aufgabe", modul1, ergebnis1, ergebnis2);
        aufgabe.setVerantwortlicheRolle(rolle1);
        List<RelationshipTableRecord> relationshipTableRecords = aufgabe.getRelationshipTableRecords();
        assertEquals(2, relationshipTableRecords.size());
        helper(aufgabe, ergebnis1, modul1, rolle1, rolle2, relationshipTableRecords.get(0));
        helper(aufgabe, ergebnis2, modul1, rolle1, rolle1, relationshipTableRecords.get(1));
    }

    @Test
    public void testAufgabeRelationshipTableRecordsTwoErgebnis() {
        Aufgabe aufgabe = aufgabe("aufgabe", modul1, ergebnis1);
        aufgabe.setVerantwortlicheRolle(rolle1);
        ergebnis1.addVerantwortlicheRolle(rolle2);
        List<RelationshipTableRecord> relationshipTableRecords = aufgabe.getRelationshipTableRecords();
        assertEquals(1, relationshipTableRecords.size());
        helper(aufgabe, ergebnis1, modul1, rolle1, rolle2, relationshipTableRecords.get(0));
    }
    @Test
    public void testErgebnisRelationshipTableRecords() {
        Aufgabe aufgabe = aufgabe("aufgabe", modul1, ergebnis1);
        aufgabe.setVerantwortlicheRolle(rolle1);
        ergebnis1.addVerantwortlicheRolle(rolle2);
        List<RelationshipTableRecord> relationshipTableRecords = ergebnis1.getRelationshipTableRecords();
        assertEquals(1, relationshipTableRecords.size());
        helper(aufgabe, ergebnis1, modul1, rolle1, rolle2, relationshipTableRecords.get(0));
    }
    @Test
    public void testRolleRelationshipTableRecordsVerantwortlichFuerAufgabe() {
        Aufgabe aufgabe = aufgabe("aufgabe", modul1, ergebnis1);
        aufgabe.setVerantwortlicheRolle(rolle1);
        ergebnis1.addVerantwortlicheRolle(rolle2);
        List<RelationshipTableRecord> relationshipTableRecords = rolle1.getRelationshipTableRecords();
        assertEquals(1, relationshipTableRecords.size());
        helper(aufgabe, ergebnis1, modul1, rolle1, rolle2, relationshipTableRecords.get(0));
    }
    
    @Test
    public void testRolleRelationshipTableRecordsVerantwortlichFuerErgebnis() {
        Aufgabe aufgabe = aufgabe("aufgabe", modul1, ergebnis1);
        aufgabe.setVerantwortlicheRolle(rolle1);
        ergebnis1.addVerantwortlicheRolle(rolle2);
        List<RelationshipTableRecord> relationshipTableRecords = rolle2.getRelationshipTableRecords();
        assertEquals(1, relationshipTableRecords.size());
        helper(aufgabe, ergebnis1, modul1, rolle1, rolle2, relationshipTableRecords.get(0));
    }
    
    @Test
    public void testRolleRelationshipTableRecordsVerantwortlichFuerErgebnis2() {
        Aufgabe aufgabe = aufgabe("aufgabe", modul1, ergebnis1);
        Aufgabe aufgabe2 = aufgabe("aufgabe", modul1, ergebnis1);
        aufgabe.setVerantwortlicheRolle(rolle1);
        aufgabe2.setVerantwortlicheRolle(rolle1);
        ergebnis1.addVerantwortlicheRolle(rolle2);
        List<RelationshipTableRecord> relationshipTableRecords = rolle1.getRelationshipTableRecords();
        assertEquals(1, relationshipTableRecords.size());
        helper(aufgabe, ergebnis1, modul1, rolle1, rolle2, relationshipTableRecords.get(0));
    }
    
    @Test
    public void testRolleRelationshipTableRecordsVerantwortlichFuerAufgabeUndErgebnis() {
        Aufgabe aufgabe = aufgabe("aufgabe", modul1, ergebnis1);
        aufgabe.setVerantwortlicheRolle(rolle1);
        ergebnis1.addVerantwortlicheRolle(rolle1);
        List<RelationshipTableRecord> relationshipTableRecords = rolle1.getRelationshipTableRecords();
        assertEquals(1, relationshipTableRecords.size());
        helper(aufgabe, ergebnis1, modul1, rolle1, rolle1, relationshipTableRecords.get(0));
    }
    
    @Test
    public void testErgebnisRelationshipTableRecordsTwoAufgaben() {
        Aufgabe aufgabe1 = aufgabe("aufgabe1", modul1, ergebnis1);
        Aufgabe aufgabe2 = aufgabe("aufgabe2", modul1, ergebnis1);
        aufgabe1.setVerantwortlicheRolle(rolle1);
        aufgabe2.setVerantwortlicheRolle(rolle2);
        ergebnis1.addVerantwortlicheRolle(rolle2);
        List<RelationshipTableRecord> relationshipTableRecords = ergebnis1.getRelationshipTableRecords();
        assertEquals(2, relationshipTableRecords.size());
        helper(aufgabe1, ergebnis1, modul1, rolle1, rolle2, relationshipTableRecords.get(0));
        helper(aufgabe2, ergebnis1, modul1, rolle2, rolle2, relationshipTableRecords.get(1));
    }

    private void helper(Aufgabe aufgabe, Ergebnis ergebnis, Modul modul, Rolle rolleAufgabe, Rolle rolleErgebnis,
            RelationshipTableRecord record) {
        assertEqualsHelper(aufgabe, record.getAufgabe().getPresentationName());
        assertEqualsHelper(ergebnis, record.getErgebnis().getPresentationName());
        assertEqualsHelper(modul, record.getModul().getPresentationName());
        assertEqualsHelper(rolleAufgabe, record.getVerantwortlichFuerAufgabe().getPresentationName());
        assertEqualsHelper(rolleErgebnis, record.getVerantwortlichFuerErgebnis().get(0).getPresentationName());
    }

    private void assertEqualsHelper(AbstractMethodenElement aufgabe, Localizable aufgabe2) {
        assertEquals(aufgabe.getId(), ((DefaultLocalizable)aufgabe2).getElementIdentifier());
    }

}
