/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslateableText;

public class TranslationMergeTest {

    private static final String MODEL_IDENTIFIER = "modelIdentifier";
    private TranslationMerge translationMerge;

    @Before
    public void setUp() throws Exception {
        translationMerge = new TranslationMerge();
        translationMerge.modelRepository = mock(ModelRepository.class);
        translationMerge.translationRepository = mock(TranslationRepository.class);
        EPFModel model = new EPFModel();
        model.setIdentifier(MODEL_IDENTIFIER);
        when(translationMerge.modelRepository.getPublishedModel()).thenReturn(model);
    }

    @Test
    public void testMergeWithPublishedDeUnchangedFrAvailable() {
        translationMerge.init();
        helperFr(Status.FREIGEGEBEN, "textFr", "textFr", "textDe");
    }
    @Test
    public void testMergeWithPublishedDeChangedFrAvailable() {
        translationMerge.init();
        helperFr(Status.UNVOLLSTAENDIG, "textFr", "textFr", "textDe1");
    }
    @Test
    public void testMergeWithPublishedDeChangedFrNotAvailable() {
        translationMerge.init();
        helperFr(Status.UNVOLLSTAENDIG, null, null, "textDe1");
    }
    @Test
    public void testMergeWithPublishedDeUnchangedFrNotAvailable() {
        translationMerge.init();
        helperFr(Status.UNVOLLSTAENDIG, null, null, "textDe");
    }

    private void helperFr(Status status, String expected, String publishedTextFr, String publishedTextFrTextDe) {
        TranslateableText publishedTranslateableText = new TranslateableText(MODEL_IDENTIFIER, "elementType",
                "rootElementIdentifier", "fileType", "fileName", "elementIdentifier", "textIdentifier", publishedTextFrTextDe);
        publishedTranslateableText.setTextFr(publishedTextFr);
        publishedTranslateableText.setStatusFr(publishedTextFr != null ? Status.FREIGEGEBEN: Status.UNVOLLSTAENDIG);
        when(translationMerge.translationRepository.getTranslateableText(MODEL_IDENTIFIER, "elementIdentifier", "textIdentifier")).thenReturn(publishedTranslateableText);
        TranslateableText translateableText = new TranslateableText(MODEL_IDENTIFIER, "elementType",
                "rootElementIdentifier", "fileType", "fileName", "elementIdentifier", "textIdentifier", "textDe");
        TranslateableText mergeWithPublished = translationMerge.mergeWithPublished(translateableText);
        assertEquals(status, mergeWithPublished.getStatusFr());
        assertEquals(expected, mergeWithPublished.getTextFr());
    }
    
    @Test
    public void testMergeWithPublishedDeUnchangedItAvailable() {
        translationMerge.init();
        helperIt(Status.FREIGEGEBEN, "textIt", "textIt", "textDe");
    }
    @Test
    public void testMergeWithPublishedDeChangedItAvailable() {
        translationMerge.init();
        helperIt(Status.UNVOLLSTAENDIG, "textIt", "textIt", "textDe1");
    }
    @Test
    public void testMergeWithPublishedDeChangedItNotAvailable() {
        translationMerge.init();
        helperIt(Status.UNVOLLSTAENDIG, null, null, "textDe1");
    }
    @Test
    public void testMergeWithPublishedDeUnchangedItNotAvailable() {
        translationMerge.init();
        helperIt(Status.UNVOLLSTAENDIG, null, null, "textDe");
    }

    private void helperIt(Status status, String expected, String publishedTextIt, String publishedTextItTextDe) {
        TranslateableText publishedTranslateableText = new TranslateableText(MODEL_IDENTIFIER, "elementType",
                "rootElementIdentifier", "fileType", "fileName", "elementIdentifier", "textIdentifier", publishedTextItTextDe);
        publishedTranslateableText.setTextIt(publishedTextIt);
        publishedTranslateableText.setStatusIt(publishedTextIt != null ? Status.FREIGEGEBEN: Status.UNVOLLSTAENDIG);
        when(translationMerge.translationRepository.getTranslateableText(MODEL_IDENTIFIER, "elementIdentifier", "textIdentifier")).thenReturn(publishedTranslateableText);
        TranslateableText translateableText = new TranslateableText(MODEL_IDENTIFIER, "elementType",
                "rootElementIdentifier", "fileType", "fileName", "elementIdentifier", "textIdentifier", "textDe");
        TranslateableText mergeWithPublished = translationMerge.mergeWithPublished(translateableText);
        assertEquals(status, mergeWithPublished.getStatusIt());
        assertEquals(expected, mergeWithPublished.getTextIt());
    }

    @Test
    public void testMergeWEnhPublishedDeUnchangedEnAvailable() {
        translationMerge.init();
        helperEn(Status.FREIGEGEBEN, "textEn", "textEn", "textDe");
    }
    @Test
    public void testMergeWEnhPublishedDeChangedEnAvailable() {
        translationMerge.init();
        helperEn(Status.UNVOLLSTAENDIG, "textEn", "textEn", "textDe1");
    }
    @Test
    public void testMergeWEnhPublishedDeChangedEnNotAvailable() {
        translationMerge.init();
        helperEn(Status.UNVOLLSTAENDIG, null, null, "textDe1");
    }
    @Test
    public void testMergeWEnhPublishedDeUnchangedEnNotAvailable() {
        translationMerge.init();
        helperEn(Status.UNVOLLSTAENDIG, null, null, "textDe");
    }

    private void helperEn(Status status, String expected, String publishedTextEn, String publishedTextEnTextDe) {
        TranslateableText publishedTranslateableText = new TranslateableText(MODEL_IDENTIFIER, "elementType",
                "rootElementIdentifier", "fileType", "fileName", "elementIdentifier", "textIdentifier", publishedTextEnTextDe);
        publishedTranslateableText.setTextEn(publishedTextEn);
        publishedTranslateableText.setStatusEn(publishedTextEn != null ? Status.FREIGEGEBEN: Status.UNVOLLSTAENDIG);
        when(translationMerge.translationRepository.getTranslateableText(MODEL_IDENTIFIER, "elementIdentifier", "textIdentifier")).thenReturn(publishedTranslateableText);
        TranslateableText translateableText = new TranslateableText(MODEL_IDENTIFIER, "elementType",
                "rootElementIdentifier", "fileType", "fileName", "elementIdentifier", "textIdentifier", "textDe");
        TranslateableText mergeWEnhPublished = translationMerge.mergeWithPublished(translateableText);
        assertEquals(status, mergeWEnhPublished.getStatusEn());
        assertEquals(expected, mergeWEnhPublished.getTextEn());
    }

}
