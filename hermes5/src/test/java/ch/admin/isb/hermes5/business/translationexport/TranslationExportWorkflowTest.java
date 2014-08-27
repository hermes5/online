/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationexport;

import static ch.admin.isb.hermes5.domain.BuilderUtils.*;
import static ch.admin.isb.hermes5.domain.Status.*;
import static ch.admin.isb.hermes5.util.AssertUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ImageBuilder;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.ModelElementBuilder;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.util.MockInstance;


public class TranslationExportWorkflowTest {

    private static final String MODEL_IDENTIFIER = "123";
    private static final byte[] BYTES = "bytes".getBytes();
    private TranslationExportWorkflow translationExportWorkflow;
    private static TranslationExportWorkflowProcessor processor1;
    private TranslationExportWorkflowProcessor processor2;

    @Before
    public void init() {
        translationExportWorkflow = new TranslationExportWorkflow();
        translationExportWorkflow.translationRepository = mock(TranslationRepository.class);
        processor1 = mock(TranslationExportWorkflowProcessor.class);
        processor2 = mock(TranslationExportWorkflowProcessor.class);
        translationExportWorkflow.translationExportProcessors = new MockInstance<TranslationExportWorkflowProcessor>(
                processor1, processor2);
    }

    @Test
    public void testExport() throws IOException {
        ModelElement modelElement1 = ModelElementBuilder.modelElementName1FrInArbeit();
        ModelElement modelElement2 = ModelElementBuilder.modelElementName2Unvollstaendig();
        ModelElement modelElement3 = ModelElementBuilder.modelElementName3FrInArbeitItFreigegeben();
        when(processor1.isResponsible(modelElement1)).thenReturn(true);
        when(processor1.isResponsible(modelElement2)).thenReturn(true);
        when(processor1.isResponsible(modelElement3)).thenReturn(true);
        Image image = ImageBuilder.imageUnvollstaendig(22, "path/name.jpg");
        when(processor2.isResponsible(image)).thenReturn(true);

        List<TranslationEntity> entities = list(modelElement1, modelElement2, modelElement3, image);
        when(translationExportWorkflow.translationRepository.getTranslationEntities(MODEL_IDENTIFIER)).thenReturn(
                entities);
        when(processor1.path(MODEL_IDENTIFIER, modelElement1, "de")).thenReturn(
                "MethodLibrary_123/Textelemente/typ1/name1_de.docx");
        when(processor1.path(MODEL_IDENTIFIER, modelElement1, "it")).thenReturn(
                "MethodLibrary_123/Textelemente/typ1/name1_it.docx");
        when(processor1.path(MODEL_IDENTIFIER, modelElement2, "de")).thenReturn(
                "MethodLibrary_123/Textelemente/typ2/name2_de.docx");
        when(processor1.path(MODEL_IDENTIFIER, modelElement2, "fr")).thenReturn(
                "MethodLibrary_123/Textelemente/typ2/name2_fr.docx");
        when(processor1.path(MODEL_IDENTIFIER, modelElement2, "it")).thenReturn(
                "MethodLibrary_123/Textelemente/typ2/name2_it.docx");
        when(processor2.path(MODEL_IDENTIFIER, image, "de")).thenReturn("MethodLibrary_123/Bilder/22_name/de/name.jpg");
        when(processor2.path(MODEL_IDENTIFIER, image, "fr")).thenReturn("MethodLibrary_123/Bilder/22_name/fr/name.jpg");
        when(processor2.path(MODEL_IDENTIFIER, image, "it")).thenReturn("MethodLibrary_123/Bilder/22_name/it/name.jpg");

        when(processor1.processEntity(any(TranslationEntity.class), anyString())).thenReturn(BYTES);
        when(processor2.processEntity(any(TranslationEntity.class), anyString())).thenReturn(BYTES);
        byte[] export = translationExportWorkflow.export(MODEL_IDENTIFIER, Arrays.asList("fr", "it"),
                Arrays.asList(UNVOLLSTAENDIG));

        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(export));
        ZipEntry nextEntry;
        List<String> names = new ArrayList<String>();
        while ((nextEntry = zipInputStream.getNextEntry()) != null) {
            names.add(nextEntry.getName());
        }
        assertEquals(9, names.size());
        assertContains(names, "Anleitung.txt", "MethodLibrary_123/Textelemente/typ1/name1_de.docx",
                "MethodLibrary_123/Textelemente/typ1/name1_it.docx",
                "MethodLibrary_123/Textelemente/typ2/name2_de.docx",
                "MethodLibrary_123/Textelemente/typ2/name2_fr.docx",
                "MethodLibrary_123/Textelemente/typ2/name2_it.docx", "MethodLibrary_123/Bilder/22_name/de/name.jpg",
                "MethodLibrary_123/Bilder/22_name/fr/name.jpg", "MethodLibrary_123/Bilder/22_name/it/name.jpg");
    }
}
