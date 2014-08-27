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
import ch.admin.isb.hermes5.business.word.TranslationWordAdapter;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.ModelElementBuilder;

public class TranslationExportWorkflowTextElementProcessorTest {

    private static final byte[] BYTES = "bytes".getBytes();
    private TranslationExportWorkflowTextElementProcessor processor;
    private static final String MODEL_IDENTIFIER = "123";

    @Before
    public void setUp() throws Exception {
        processor = new TranslationExportWorkflowTextElementProcessor();
        processor.wordAdapter = mock(TranslationWordAdapter.class);
        processor.translationRepository = mock(TranslationRepository.class);

    }

    @Test
    public void testPath() {
        ModelElement modelElement1 = ModelElementBuilder.modelElementName1FrInArbeit();
        ModelElement modelElement2 = ModelElementBuilder.modelElementName2Unvollstaendig();
        assertEquals("MethodLibrary_123/Textelemente/typ1/name1_de.docx",
                processor.path(MODEL_IDENTIFIER, modelElement1, "de"));
        assertEquals("MethodLibrary_123/Textelemente/typ1/name1_it.docx",
                processor.path(MODEL_IDENTIFIER, modelElement1, "it"));
        assertEquals("MethodLibrary_123/Textelemente/typ2/name2_de.docx",
                processor.path(MODEL_IDENTIFIER, modelElement2, "de"));
    }

    @Test
    public void testProcessEntityFrInArbeit() {
        ModelElement modelElement1 = ModelElementBuilder.modelElementName1FrInArbeit();
        when(processor.wordAdapter.write(modelElement1.getTexts(), "fr")).thenReturn(BYTES);
        byte[] processEntity = processor.processEntity(modelElement1, "fr");
        assertArrayEquals(BYTES, processEntity);
    }

    @Test
    public void testMarkAsInArbeit() {
        ModelElement modelElementName1FrInArbeit = ModelElementBuilder.modelElementName1FrInArbeit();
        processor.markAsInArbeit(modelElementName1FrInArbeit, Arrays.asList("fr", "de"));
        verify(processor.translationRepository).markModelElementAsInArbeit(modelElementName1FrInArbeit,
                Arrays.asList("fr", "de"));
    }

    @Test
    public void testIsResponsibleTextElement() {
        assertTrue(processor.isResponsible(ModelElementBuilder.modelElementName1FrInArbeit()));
    }
    @Test
    public void testIsResponsibleImage() {
        assertFalse(processor.isResponsible(new Image()));
    }
    @Test
    public void testIsResponsibleDocument() {
        assertFalse(processor.isResponsible(new Document()));
    }
}
