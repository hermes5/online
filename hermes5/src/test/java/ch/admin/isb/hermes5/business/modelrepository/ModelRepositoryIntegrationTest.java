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
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.RelationshipTableRecord;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.util.ResourceUtils;
import ch.admin.isb.hermes5.util.StringUtil;

public class ModelRepositoryIntegrationTest   {

   
   
   private final ModelRepositoryIntegrationTestSupport support = new ModelRepositoryIntegrationTestSupport();
    private byte[] xmlFileContents;

    @Before
    public void setup() {
        InputStream resourceAsStream = getClass().getResourceAsStream("20121219_export.xml");
        xmlFileContents = ResourceUtils.loadResource(resourceAsStream);
    }

   
    @Test
    public void testGetHermesWebsite() {
        PublishContainer hermesWebsite = support.getHermesWebsite(xmlFileContents);
        assertNotNull(hermesWebsite);
        List<Aufgabe> aufgaben = hermesWebsite.getAufgaben();
        assertTrue(aufgaben.size() > 10);
    }

    @Test
    public void testProjektleiterStudieRelationship() {
        PublishContainer hermesWebsite = support.getHermesWebsite(xmlFileContents);
        List<AbstractMethodenElement> elementsToPublish = hermesWebsite.getElementsToPublish();
        Rolle projektleiter = (Rolle) getElementWithName(elementsToPublish, "projektleiter");
        List<RelationshipTableRecord> projektleiterTable = projektleiter.getRelationshipTableRecords();
        RelationshipTableRecord record = getRecordWithAufgabeAndErgebnis(projektleiterTable,
                "projektgrundlagen_erarbeiten", "studie");
        assertEquals("projektleiter", record.getVerantwortlichFuerAufgabe().getElement().getName());
    }
    @Test
    public void testProjektgrundlagenStudieRelationship() {
        PublishContainer hermesWebsite = support.getHermesWebsite(xmlFileContents);
        List<AbstractMethodenElement> elementsToPublish = hermesWebsite.getElementsToPublish();
         
        Aufgabe projektgrundlagenErarbeiten = (Aufgabe) getElementWithName(elementsToPublish,
                "projektgrundlagen_erarbeiten");
        List<RelationshipTableRecord> projektgrundlagenErarbeitenTable = projektgrundlagenErarbeiten
                .getRelationshipTableRecords();
        RelationshipTableRecord record = getRecordWithAufgabeAndErgebnis(projektgrundlagenErarbeitenTable,
                "projektgrundlagen_erarbeiten", "studie");
        assertEquals("projektleiter", record.getVerantwortlichFuerAufgabe().getElement().getName());
    }

    private RelationshipTableRecord getRecordWithAufgabeAndErgebnis(List<RelationshipTableRecord> table,
            String aufgabe, String ergebnis) {
        RelationshipTableRecord record = null;
        for (RelationshipTableRecord relationshipTableRecord : table) {
            if (relationshipTableRecord.getAufgabe().getElement().getName().equals(aufgabe)
                    && relationshipTableRecord.getErgebnis().getElement().getName().equals(ergebnis)) {
                record = relationshipTableRecord;
            }
        }
        assertNotNull("record not found aufgabe " + aufgabe + " ergebnis " + ergebnis + " "
                + listAufgabeErgebnis(table), record);
        return record;
    }

    private String listAufgabeErgebnis(List<RelationshipTableRecord> table) {
        List<String> strings = new ArrayList<String>();
        for (RelationshipTableRecord relationshipTableRecord : table) {
            strings.add(relationshipTableRecord.getAufgabe().getElement().getName() + " - "
                    + relationshipTableRecord.getErgebnis().getElement().getName());
        }
        Collections.sort(strings);
        return StringUtil.join(strings, "\n");
    }

    private AbstractMethodenElement getElementWithName(List<AbstractMethodenElement> elementsToPublish,
            String elementName) {
        AbstractMethodenElement element = null;
        for (AbstractMethodenElement abstractMethodenElement : elementsToPublish) {
            if (abstractMethodenElement.getElement().getName().equalsIgnoreCase(elementName)) {
                element = abstractMethodenElement;
            }
        }
        assertNotNull("element with name " + elementName + " not found", element);
        return element;
    }

}
