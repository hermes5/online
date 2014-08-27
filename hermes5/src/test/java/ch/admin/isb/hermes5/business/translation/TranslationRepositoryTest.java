/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translation;

import static ch.admin.isb.hermes5.domain.Status.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.persistence.db.dao.DocumentDAO;
import ch.admin.isb.hermes5.persistence.db.dao.ImageDAO;
import ch.admin.isb.hermes5.persistence.db.dao.TranslateableTextDAO;
import ch.admin.isb.hermes5.util.AssertUtils;

public class TranslationRepositoryTest {

    private static final String MODEL_IDENTIFIER = "123";
    private TranslationRepository translationRepository;

    @Before
    public void setUp() {
        translationRepository = new TranslationRepository();
        translationRepository.imageDAO = mock(ImageDAO.class);
        translationRepository.documentDAO = mock(DocumentDAO.class);
        translationRepository.translateableTextDAO = mock(TranslateableTextDAO.class);
    }

    @Test
    public void testGestStati() {
        when(translationRepository.imageDAO.getImageStati(MODEL_IDENTIFIER, "fr")).thenReturn(
                Arrays.asList(UNVOLLSTAENDIG, IN_ARBEIT));
        when(translationRepository.documentDAO.getDocumentStati(MODEL_IDENTIFIER, "fr")).thenReturn(
                Arrays.asList(FREIGEGEBEN));
        when(translationRepository.translateableTextDAO.getTextElementStati(MODEL_IDENTIFIER, "fr")).thenReturn(
                Arrays.asList(IN_ARBEIT, FREIGEGEBEN));
        List<Status> stati = translationRepository.getTranslationEntityStati(MODEL_IDENTIFIER, "fr");
        assertEquals(3, stati.size());
        AssertUtils.assertContains(stati, Status.UNVOLLSTAENDIG, IN_ARBEIT, FREIGEGEBEN);
    }

    @Test
    public void testGetModelElements() {
        List<TranslateableText> texts = new ArrayList<TranslateableText>();
        texts.add(new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType1", "fileName1",
                "textIdentifier1", "presentationName", "textDe"));
        texts.add(new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType1", "fileName1",
                "textIdentifier1", "mainDescription", "textDe"));
        texts.add(new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType1", "fileName2",
                "textIdentifier2", "briefDescription", "textDe"));
        texts.add(new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType2", "fileName1",
                "textIdentifier3", "briefDescription", "textDe"));
        texts.add(new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType2", "fileName1",
                "textIdentifier3", "briefDescription", "textDe"));
        when(translationRepository.translateableTextDAO.getTexts(MODEL_IDENTIFIER)).thenReturn(texts);
        List<TranslationEntity> modelElements = translationRepository.getTranslationEntities(MODEL_IDENTIFIER);
        assertNotNull(modelElements);
        assertEquals(3, modelElements.size());
        for (TranslationEntity modelElement : modelElements) {
            assertEquals(Status.UNVOLLSTAENDIG, modelElement.getStatusFr());
            assertEquals(Status.UNVOLLSTAENDIG, modelElement.getStatusEn());
            assertEquals(Status.UNVOLLSTAENDIG, modelElement.getStatusIt());
        }
        ModelElement first = (ModelElement) modelElements.get(0);
        assertEquals("fileType1", first.getTyp());
        assertEquals("fileName1", first.getName());
        assertEquals(2, first.getTexts().size());
    }

    @Test
    public void testGetModelElementsStatusTest() {
        List<TranslateableText> texts = new ArrayList<TranslateableText>();
        TranslateableText t1 = new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType1", "fileName1",
                "textIdentifier1", "presentationName", "textDe");
        t1.setStatusFr(Status.IN_ARBEIT);
        texts.add(t1);
        TranslateableText t2 = new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType1", "fileName1",
                "textIdentifier1", "mainDescription", "textDe");
        texts.add(t2);
        TranslateableText t3_1 = new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType1", "fileName2",
                "textIdentifier2", "briefDescription", "textDe");
        t3_1.setStatusEn(Status.FREIGEGEBEN);
        TranslateableText t3 = new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType1", "fileName2",
                "textIdentifier2", "briefDescription", "textDe");
        texts.add(t3_1);
        texts.add(t3);
        TranslateableText t4 = new TranslateableText(MODEL_IDENTIFIER, "type", "rootid", "fileType2", "fileName1",
                "textIdentifier3", "briefDescription", "textDe");
        t4.setStatusIt(Status.FREIGEGEBEN);
        texts.add(t4);
        when(translationRepository.translateableTextDAO.getTexts(MODEL_IDENTIFIER)).thenReturn(texts);
        List<TranslationEntity> modelElements = translationRepository.getTranslationEntities(MODEL_IDENTIFIER);
        assertEquals(Status.IN_ARBEIT, modelElements.get(0).getStatusFr());
        assertEquals(Status.UNVOLLSTAENDIG, modelElements.get(1).getStatusEn());
        assertEquals(Status.FREIGEGEBEN, modelElements.get(2).getStatusIt());
    }

    @Test
    public void testMarkInArbeit() {
        List<TranslateableText> texts = new ArrayList<TranslateableText>();
        TranslateableText e = new TranslateableText("modelIdentifier", "type", "rootid", "fileType", "fileName",
                "elementIdentifier", "textIdentifier", "textDe");
        texts.add(e);
        ModelElement modelElement = new ModelElement("typ", "name", UNVOLLSTAENDIG, UNVOLLSTAENDIG, UNVOLLSTAENDIG,
                texts);
        translationRepository.markModelElementAsInArbeit(modelElement, Arrays.asList("fr", "it"));
        assertEquals(IN_ARBEIT, e.getStatusFr());
        assertEquals(IN_ARBEIT, e.getStatusIt());
        assertEquals(UNVOLLSTAENDIG, e.getStatusEn());
        verify(translationRepository.translateableTextDAO).merge(e);
    }

    @Test
    public void testUpdateImageFr() {
        Image entry = new Image();
        entry.setId(12l);
        Image readEntry = new Image();
        when(translationRepository.imageDAO.getImage(12l)).thenReturn(readEntry);

        translationRepository.updateTranslationEntity(entry, "urlFR", "fr");
        verify(translationRepository.imageDAO).merge(readEntry);
        assertEquals("urlFR", readEntry.getUrlFr());
        assertEquals(Status.FREIGEGEBEN, readEntry.getStatusFr());
    }

    @Test
    public void testUpdateImageFrNull() {
        Image entry = new Image();
        entry.setId(12l);
        Image readEntry = new Image();
        when(translationRepository.imageDAO.getImage(12l)).thenReturn(readEntry);

        translationRepository.updateTranslationEntity(entry, null, "fr");
        verify(translationRepository.imageDAO).merge(readEntry);
        assertNull(readEntry.getUrlFr());
        assertEquals(Status.UNVOLLSTAENDIG, readEntry.getStatusFr());
    }

    @Test
    public void testUpdateImageIt() {
        Image entry = new Image();
        entry.setId(12l);
        Image readEntry = new Image();
        when(translationRepository.imageDAO.getImage(12l)).thenReturn(readEntry);

        translationRepository.updateTranslationEntity(entry, "urlIt", "it");
        verify(translationRepository.imageDAO).merge(readEntry);
        assertEquals("urlIt", readEntry.getUrlIt());
        assertEquals(Status.FREIGEGEBEN, readEntry.getStatusIt());
    }

    @Test
    public void testUpdateImageEn() {
        Image entry = new Image();
        entry.setId(12l);
        Image readEntry = new Image();
        when(translationRepository.imageDAO.getImage(12l)).thenReturn(readEntry);

        translationRepository.updateTranslationEntity(entry, "urlEn", "en");
        verify(translationRepository.imageDAO).merge(readEntry);
        assertEquals("urlEn", readEntry.getUrlEn());
        assertEquals(Status.FREIGEGEBEN, readEntry.getStatusEn());
    }

    @Test
    public void testUpdateDocument() {
        Document entry = new Document();
        entry.setId(12l);
        Document readEntry = new Document();
        when(translationRepository.documentDAO.getDocument(12l)).thenReturn(readEntry);

        translationRepository.updateTranslationEntity(entry, "urlFR", "fr");
        verify(translationRepository.documentDAO).merge(readEntry);
        assertEquals("urlFR", readEntry.getUrlFr());
    }
    
    @Test
    public void testLocalizeFr() {
        prepareLocalize();
        assertEquals("textFr", translationRepository.getLocalizedText(MODEL_IDENTIFIER, "fr", "elementIdentifier", "textIdentifier"));
    }
    @Test
    public void testLocalizeDe() {
        prepareLocalize();
        assertEquals("textDe", translationRepository.getLocalizedText(MODEL_IDENTIFIER, "de", "elementIdentifier", "textIdentifier"));
    }
    @Test
    public void testLocalizeIt() {
        prepareLocalize();
        assertEquals("textIt", translationRepository.getLocalizedText(MODEL_IDENTIFIER, "it", "elementIdentifier", "textIdentifier"));
    }
    @Test
    public void testLocalizeEn() {
        prepareLocalize();
        assertEquals("textEn", translationRepository.getLocalizedText(MODEL_IDENTIFIER, "en", "elementIdentifier", "textIdentifier"));
    }

    private void prepareLocalize() {
        TranslateableText text = new TranslateableText("model", "type", "root", "file", "name", "element", "text", "textDe");
        text.setTextFr("textFr");
        text.setTextIt("textIt");
        text.setTextEn("textEn");
        when(translationRepository.translateableTextDAO.getSpecificText(MODEL_IDENTIFIER,"elementIdentifier", "textIdentifier")).thenReturn(text);
    }
}
