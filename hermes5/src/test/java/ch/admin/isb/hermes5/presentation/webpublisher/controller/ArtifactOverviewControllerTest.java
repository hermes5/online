/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.domain.TranslationEntity.TranslationEntityType;
import ch.admin.isb.hermes5.util.Hardcoded;

public class ArtifactOverviewControllerTest {

    private static final String MODEL_IDENTIFIER = "modelIdentifier";
    private ArtifactOverviewController artifactOverviewController;

    @Before
    public void setUp() {
        artifactOverviewController = new ArtifactOverviewController();
        artifactOverviewController.webPublisherFacade = mock(WebPublisherFacade.class);
        artifactOverviewController.s3PublicBucketUrl = Hardcoded.configuration("s3");
    }

    @Test
    public void testGetIdentifier() {
        assertEquals("artifact-overview", artifactOverviewController.getIdentifier());
    }

    @Test
    public void testGetTranslationEntities() {
        List<TranslationEntity> entities = Arrays.asList(mock(TranslationEntity.class), mock(TranslationEntity.class));
        EPFModel model = new EPFModel();
        model.setIdentifier(MODEL_IDENTIFIER);
        when(artifactOverviewController.webPublisherFacade.getModel(MODEL_IDENTIFIER)).thenReturn(
                model );
        when(artifactOverviewController.webPublisherFacade.getTranslationEntities(MODEL_IDENTIFIER)).thenReturn(
                entities);
        artifactOverviewController.display(MODEL_IDENTIFIER);
        assertEquals(entities, artifactOverviewController.getTranslationEntities());
    }

    @Test
    public void testGetLinkDeDocument() {
        Document entity = new Document();
        entity.setUrlDe("urlDE");
        entity.setModelIdentifier("model");
        assertEquals("s3/model/de/urlDE", artifactOverviewController.getLinkDeForDocument(entity));
    }
    @Test
    public void testGetLinkDeImage() {
        Image entity = new Image();
        entity.setId(33l);
        assertEquals("imagedisplay/33", artifactOverviewController.getLinkDeForDocument(entity));
    }


    
    @Test
    public void testRenderUploadButtonImageIMAGE() {
        TranslationDocument entity = mock(TranslationDocument.class);
        when(entity.getType()).thenReturn(TranslationEntityType.IMAGE);
        assertTrue(artifactOverviewController.renderUploadArtifactLink(entity));
    }

    @Test
    public void testRenderUploadButtonImageDOCUMENT() {
        TranslationDocument entity = mock(TranslationDocument.class);
        when(entity.getType()).thenReturn(TranslationEntityType.DOCUMENT);
        assertTrue(artifactOverviewController.renderUploadArtifactLink(entity));
    }

    @Test
    public void testRenderUploadButtonImageTEXT_ELEMENT() {
        TranslationDocument entity = mock(TranslationDocument.class);
        when(entity.getType()).thenReturn(TranslationEntityType.TEXT_ELEMENT);
        assertFalse(artifactOverviewController.renderUploadArtifactLink(entity));
    }

   

}
