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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.util.Hardcoded;

public class ImageDisplayControllerTest {

    private ImageDisplayController imageDisplayController;

    @Before
    public void setUp() {
        imageDisplayController = new ImageDisplayController();
        imageDisplayController.s3PublicBucketUrl = Hardcoded.configuration("http://s3");
        imageDisplayController.webPublisherFacade = mock(WebPublisherFacade.class);
        imageDisplayController.artifactUploadController = mock(ArtifactUploadController.class);

    }

    @Test
    public void deleteIt() {
        Image image = new Image();
        imageDisplayController.display(image);
        assertEquals("image-display", imageDisplayController.deleteIt());
        verify(imageDisplayController.webPublisherFacade).addOrUpdateArtifact(image, null, null, "it");
    }

    @Test
    public void testGetIdentifer() {
        assertEquals("image-display", imageDisplayController.getIdentifier());
    }

    @Test
    public void getImageTitle() {
        Image image = new Image();
        image.setUrlDe("url/Img.jpg");
        imageDisplayController.display(image);
        assertEquals("Img.jpg", imageDisplayController.getImageTitle());
    }

    @Test
    public void uploadFr() {
        Image image = image();
        imageDisplayController.display(image);
        when(imageDisplayController.artifactUploadController.display(eq(image), eq("fr"), eq("image-display")))
                .thenReturn("artifact-upload");
        assertEquals("artifact-upload", imageDisplayController.uploadFr());
    }

    @Test
    public void uploadIt() {
        Image image = image();
        imageDisplayController.display(image);
        when(imageDisplayController.artifactUploadController.display(eq(image), eq("it"), eq("image-display")))
                .thenReturn("artifact-upload");
        assertEquals("artifact-upload", imageDisplayController.uploadIt());
    }

    @Test
    public void uploadEn() {
        Image image = image();
        imageDisplayController.display(image);
        when(imageDisplayController.artifactUploadController.display(eq(image), eq("en"), eq("image-display")))
                .thenReturn("artifact-upload");
        assertEquals("artifact-upload", imageDisplayController.uploadEn());
    }

    @Test
    public void getUrlDe() {
        imageDisplayController.display(image());
        assertEquals("http://s3/modelIdentifier/de/url/ImgDe.jpg", imageDisplayController.getUrlDe());
    }

    @Test
    public void getUrlFr() {
        imageDisplayController.display(image());
        assertEquals("http://s3/modelIdentifier/fr/url/ImgFr.jpg", imageDisplayController.getUrlFr());
    }

    @Test
    public void getUrlIt() {
        imageDisplayController.display(image());
        assertEquals("http://s3/modelIdentifier/it/url/ImgIt.jpg", imageDisplayController.getUrlIt());
    }

    @Test
    public void getUrlEn() {
        imageDisplayController.display(image());
        assertEquals("http://s3/modelIdentifier/en/url/ImgEn.jpg", imageDisplayController.getUrlEn());
    }

    private Image image() {
        Image image = new Image();
        image.setUrlDe("url/ImgDe.jpg");
        image.setUrlFr("url/ImgFr.jpg");
        image.setUrlIt("url/ImgIt.jpg");
        image.setUrlEn("url/ImgEn.jpg");
        image.setModelIdentifier("modelIdentifier");
        return image;
    }

}
