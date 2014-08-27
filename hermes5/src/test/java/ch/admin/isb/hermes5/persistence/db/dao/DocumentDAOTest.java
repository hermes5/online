/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.db.dao;

import static ch.admin.isb.hermes5.domain.Status.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.util.AssertUtils;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;

public class DocumentDAOTest extends AbstractDAOTest {

    private DocumentDAO dao;
    private ReflectionTestHelper helper = new ReflectionTestHelper();
    private Document d1 = new Document();
    private Document d2 = new Document();
    private Document d3 = new Document();

    @Before
    public void setUp() throws Exception {
        dao = new DocumentDAO();
        dao.em = getEntityManager();
        beginTransaction();
        helper.fillInSetter(d1);
        helper.fillInSetter(d2);
        helper.fillInSetter(d3);
        d1.setId(null);
        d2.setId(null);
        d3.setId(null);
        d1.setUrlDe("urlDe1");
        d1.setUrlFr("urlFr1");
        d1.setModelIdentifier("m1");
        d1.setStatusFr(UNVOLLSTAENDIG);
        d1.setStatusIt(UNVOLLSTAENDIG);
        d1.setStatusEn(UNVOLLSTAENDIG);
        d2.setModelIdentifier("m2");
        d3.setModelIdentifier("m1");
        d3.setUrlDe("urlDe3");
        d3.setStatusFr(UNVOLLSTAENDIG);
        d3.setStatusIt(IN_ARBEIT);
        d3.setStatusEn(FREIGEGEBEN);
        persist(d1, d2, d3);
        commitTransaction();
    }

    @Test
    public void testGetDocument() {
        Document d = dao.getDocument("m1", "urlDe1");
        assertEquals("urlFr1", d.getUrlFr());
    }

    @Test
    public void getStatusFr() {
        List<Status> textElementStati = dao.getDocumentStati("m1", "fr");
        assertEquals(textElementStati + "", 1, textElementStati.size());
        assertEquals(UNVOLLSTAENDIG, textElementStati.get(0));
    }

    @Test
    public void getStatusIt() {
        List<Status> textElementStati = dao.getDocumentStati("m1", "it");
        assertEquals(textElementStati + "", 2, textElementStati.size());
        AssertUtils.assertContains(textElementStati, UNVOLLSTAENDIG, IN_ARBEIT);
    }

    @Test
    public void getStatusEn() {
        List<Status> textElementStati = dao.getDocumentStati("m1", "en");
        assertEquals(2, textElementStati.size());
        AssertUtils.assertContains(textElementStati, UNVOLLSTAENDIG, FREIGEGEBEN);
    }

    @Test
    public void testMerge() {
        beginTransaction();
        Document document = new Document();
        helper.fillInSetter(document);
        document.setId(null);
        Document merge = dao.merge(document);
        assertNotNull(merge);
        assertNotSame(merge, document);
        assertNotNull(merge.getId());
        getEntityManager().flush();
        commitTransaction();
        Document find = getEntityManager().find(Document.class, merge.getId());
        helper.assertGettersResultEqual(document, find, "id", "url", "name");
    }

    @Test
    public void testGetDocuments() {
        beginTransaction();
        List<Document> documents = dao.getDocuments("m1");
        assertEquals(2, documents.size());
        helper.assertGettersResultEqual(d1, documents.get(0), "url", "name");
        helper.assertGettersResultEqual(d3, documents.get(1), "url", "name");
        commitTransaction();
    }

    @Test
    public void testGetDocumentById() {
        Document document = dao.getDocument(d1.getId());
        helper.assertGettersResultEqual(document, d1, "url", "name");
    }

}
