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

import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.util.AssertUtils;

public class TranslateableTextDAOTest extends AbstractDAOTest {

    private TranslateableTextDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new TranslateableTextDAO();
        dao.em = getEntityManager();
        beginTransaction();
        persist(new TranslateableText("model1", "type", "rootid", "filetype", "filename1", "elementId", "textId",
                "textde"));
        TranslateableText translateableText = new TranslateableText("model1", "type", "rootid", "filetype2", "filename1", "elementId", "textId",
                "textde");
        translateableText.setStatusFr(UNVOLLSTAENDIG);
        translateableText.setStatusIt(IN_ARBEIT);
        translateableText.setStatusEn(FREIGEGEBEN);
        persist(translateableText);
        persist(new TranslateableText("model2", "type", "rootid", "filetype2", "filename1", "elementId", "textId",
                "textde"));
        commitTransaction();
    }

    @Test
    public void testGetTexts() {
        beginTransaction();
        List<TranslateableText> texts = dao.getTexts("model1");
        assertEquals(2, texts.size());
        commitTransaction();
    }

    @Test
    public void getStatusFr() {
        List<Status> textElementStati = dao.getTextElementStati("model1", "fr");
        assertEquals(textElementStati+"", 1, textElementStati.size());
        assertEquals(UNVOLLSTAENDIG, textElementStati.get(0));
    }
    @Test
    public void getStatusIt() {
        List<Status> textElementStati = dao.getTextElementStati("model1", "it");
        assertEquals(textElementStati+"", 2, textElementStati.size());
        AssertUtils.assertContains(textElementStati, UNVOLLSTAENDIG, IN_ARBEIT);
    }
    @Test
    public void getStatusEn() {
        List<Status> textElementStati = dao.getTextElementStati("model1", "en");
        assertEquals(2, textElementStati.size());
        AssertUtils.assertContains(textElementStati, UNVOLLSTAENDIG, FREIGEGEBEN);
    }

    @Test
    public void testMerge() {
        beginTransaction();
        TranslateableText translateableText = new TranslateableText("model5", "type", "rootid", "filetype",
                "filename3333", "elementId", "textId", "textde");
        TranslateableText merge = dao.merge(translateableText);
        assertNotSame(merge, translateableText);
        assertNotNull(merge.getId());
        assertEquals("model5", merge.getModelIdentifier());
        getEntityManager().flush();
        commitTransaction();
        beginTransaction();
        TranslateableText find = getEntityManager().find(TranslateableText.class, merge.getId());
        assertEquals("filename3333", find.getFileName());

    }

}
