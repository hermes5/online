/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationexport;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ImageBuilder;
import ch.admin.isb.hermes5.domain.ModelElementBuilder;
import ch.admin.isb.hermes5.persistence.s3.S3;

public class TranslationExportWorkflowTranslationDocumentProcessorTest {

    private static final String MODELIDENTIFIER = "123";
    private TranslationExportWorkflowTranslationDocumentProcessor processor;
    private byte[] BYTES = "bytes".getBytes();

    @Before
    public void setUp() throws Exception {
        processor = new TranslationExportWorkflowTranslationDocumentProcessor();
        processor.s3 = mock(S3.class);
        processor.translationRepository = mock(TranslationRepository.class);
    }

    @Test
    public void testPathWhenNoFrenchImage() {
        assertEquals("MethodLibrary_123/Bilder/urlDe_image_png/fr/image.png",
                processor.path(MODELIDENTIFIER, ImageBuilder.imageUnvollstaendig(22, "urlDe/image.png"), "fr"));
    }

    @Test
    public void testPathWhenWithFrenchImage() {
        Image imageUnvollstaendig = ImageBuilder.imageUnvollstaendig(22, "urlDe/image.png");
        imageUnvollstaendig.setUrlFr("urlOther/imageOther.jpg");
        assertEquals("MethodLibrary_123/Bilder/urlDe_image_png/fr/imageOther.jpg",
                processor.path(MODELIDENTIFIER, imageUnvollstaendig, "fr"));
    }

    @Test
    public void testPathWhenWithItalianImage() {
        Image imageUnvollstaendig = ImageBuilder.imageUnvollstaendig(22, "urlDe/image.png");
        imageUnvollstaendig.setUrlIt("urlOther/imageOther.jpg");
        assertEquals("MethodLibrary_123/Bilder/urlDe_image_png/it/imageOther.jpg",
                processor.path(MODELIDENTIFIER, imageUnvollstaendig, "it"));
    }

    @Test
    public void testPathWhenWithEnglishImage() {
        Image imageUnvollstaendig = ImageBuilder.imageUnvollstaendig(22, "urlDe/image.png");
        imageUnvollstaendig.setUrlEn("urlOther/imageOther.jpg");
        assertEquals("MethodLibrary_123/Bilder/urlDe_image_png/en/imageOther.jpg",
                processor.path(MODELIDENTIFIER, imageUnvollstaendig, "en"));
    }

    @Test
    public void testProcessEntityNoFrenchImage() {
        when(processor.s3.readFile(MODELIDENTIFIER, "de", "urlDe/image.png")).thenReturn(BYTES);
        Image imageUnvollstaendig = ImageBuilder.imageUnvollstaendig(22, "urlDe/image.png");
        imageUnvollstaendig.setModelIdentifier(MODELIDENTIFIER);
        byte[] processEntity = processor.processEntity(imageUnvollstaendig, "fr");
        assertArrayEquals(BYTES, processEntity);
    }
    @Test
    public void testProcessEntityWithFrenchImage() {
        when(processor.s3.readFile(MODELIDENTIFIER, "fr", "urlOther/imageOther.jpg")).thenReturn(BYTES);
        Image imageUnvollstaendig = ImageBuilder.imageUnvollstaendig(22, "urlDe/image.png");
        imageUnvollstaendig.setModelIdentifier(MODELIDENTIFIER);
        imageUnvollstaendig.setUrlFr("urlOther/imageOther.jpg");
        byte[] processEntity = processor.processEntity(imageUnvollstaendig, "fr");
        assertArrayEquals(BYTES, processEntity);
    }

    @Test
    public void testMarkAsInArbeit() {
        Image imageUnvollstaendig = ImageBuilder.imageUnvollstaendig(22, "urlDe/image.png");
        processor.markAsInArbeit(imageUnvollstaendig, Arrays.asList("fr", "it"));
        verify(processor.translationRepository).markTranslationDocumentAsInArbeit(imageUnvollstaendig, Arrays.asList("fr", "it"));
    }

    @Test
    public void testIsResponsibleImage() {
      assertTrue(processor.isResponsible(new Image()));
    }
    @Test
    public void testIsResponsibleDocument() {
        assertTrue(processor.isResponsible(new Document()));
    }
    @Test
    public void testIsResponsibleTextElement() {
        assertFalse(processor.isResponsible(ModelElementBuilder.modelElementName1FrInArbeit()));
    }

}
