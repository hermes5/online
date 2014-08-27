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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.persistence.db.dao.ImageDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.Hardcoded;

public class ImageImportHandlerTest {

    private static final String PUBLISHED = "PUBLISHED";
    private static final String MODEL_IDENTIFIER = "modelIdentifier";
    private static final byte[] BYTES = "content".getBytes();
    private static final byte[] BYTES2 = "content2".getBytes();
    private ImageImportHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new ImageImportHandler();
        handler.imageDAO = mock(ImageDAO.class);
        handler.modelRepository = mock(ModelRepository.class);
        handler.s3 = mock(S3.class);
        EPFModel published = new EPFModel();
        published.setIdentifier(PUBLISHED);
        when(handler.modelRepository.getPublishedModel()).thenReturn(published);
        Hardcoded.enableDefaults(handler);
        handler.init();
    }

    @Test
    public void testIsResponsiblePng() {
        assertTrue(handler.isResponsible("aaa.png"));
    }

    @Test
    public void testIsResponsibleJPG() {
        assertTrue(handler.isResponsible("aaa.JPG"));
    }

    @Test
    public void testIsResponsibledoc() {
        assertFalse(handler.isResponsible("aaa.doc"));
    }

    @Test
    public void testHandleImportFrNoPublished() {
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, "url/filename.png");
        assertTrue(handleImport.isSuccess());
        final String urlDe = "url/filename.png";
        final String urlFr = null;
        final Status statusFr = Status.UNVOLLSTAENDIG;
        verifyFr(urlDe, urlFr, statusFr);
    }

    @Test
    public void testHandleImportWithExistingDeAndFrImage() {
        Image publishedImage = new Image();
        publishedImage.setUrlDe("url/filename.png");
        publishedImage.setUrlFr("url/filename.png");
        when(handler.imageDAO.getImage(PUBLISHED, "url/filename.png")).thenReturn(publishedImage);
        when(handler.s3.readFile(PUBLISHED, "de", "url/filename.png")).thenReturn(BYTES);
        when(handler.s3.readFile(PUBLISHED, "fr", "url/filename.png")).thenReturn(BYTES2);
        when(handler.s3.readFile(MODEL_IDENTIFIER, "de", "url/filename.png")).thenReturn(BYTES);
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, "url/filename.png");
        assertTrue(handleImport.isSuccess());
        verifyFr("url/filename.png", "url/filename.png", Status.FREIGEGEBEN);
        verify(handler.s3).addFile(any(InputStream.class), anyLong(), eq(MODEL_IDENTIFIER), eq("fr"),
                eq("url/filename.png"));
    }

    private void verifyFr(final String urlDe, final String urlFr, final Status statusFr) {
        verify(handler.imageDAO).merge(argThat(new ArgumentMatcher<Image>() {

            @Override
            public boolean matches(Object argument) {
                Image image = (Image) argument;
                assertEquals(image.getUrlDe(), urlDe);
                assertEquals(image.getUrlFr(), urlFr);
                assertEquals(image.getStatusFr(), statusFr);
                return true;
            }
        }));
    }

    @Test
    public void testHandleImportItNoPublished() {
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, "url/filename.png");
        assertTrue(handleImport.isSuccess());
        final String urlDe = "url/filename.png";
        final String urlIt = null;
        final Status statusIt = Status.UNVOLLSTAENDIG;
        verifyIt(urlDe, urlIt, statusIt);
    }

    @Test
    public void testHandleImportWithExistingDeAndItImage() {
        Image publishedImage = new Image();
        publishedImage.setUrlDe("url/filename.png");
        publishedImage.setUrlIt("url/filename.png");
        when(handler.imageDAO.getImage(PUBLISHED, "url/filename.png")).thenReturn(publishedImage);
        when(handler.s3.readFile(PUBLISHED, "de", "url/filename.png")).thenReturn(BYTES);
        when(handler.s3.readFile(PUBLISHED, "it", "url/filename.png")).thenReturn(BYTES2);
        when(handler.s3.readFile(MODEL_IDENTIFIER, "de", "url/filename.png")).thenReturn(BYTES);
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, "url/filename.png");
        assertTrue(handleImport.isSuccess());
        verifyIt("url/filename.png", "url/filename.png", Status.FREIGEGEBEN);
        verify(handler.s3).addFile(any(InputStream.class), anyLong(), eq(MODEL_IDENTIFIER), eq("it"),
                eq("url/filename.png"));
    }

    private void verifyIt(final String urlDe, final String urlIt, final Status statusIt) {
        verify(handler.imageDAO).merge(argThat(new ArgumentMatcher<Image>() {

            @Override
            public boolean matches(Object argument) {
                Image image = (Image) argument;
                assertEquals(image.getUrlDe(), urlDe);
                assertEquals(image.getUrlIt(), urlIt);
                assertEquals(image.getStatusIt(), statusIt);
                return true;
            }
        }));
    }
    @Test
    public void testHandleImportEnNoPublished() {
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, "url/filename.png");
        assertTrue(handleImport.isSuccess());
        final String urlDe = "url/filename.png";
        final String urlEn = null;
        final Status statusEn = Status.UNVOLLSTAENDIG;
        verifyEn(urlDe, urlEn, statusEn);
    }
    
    @Test
    public void testHandleImportWEnhExistingDeAndEnImage() {
        Image publishedImage = new Image();
        publishedImage.setUrlDe("url/filename.png");
        publishedImage.setUrlEn("url/filename.png");
        when(handler.imageDAO.getImage(PUBLISHED, "url/filename.png")).thenReturn(publishedImage);
        when(handler.s3.readFile(PUBLISHED, "de", "url/filename.png")).thenReturn(BYTES);
        when(handler.s3.readFile(PUBLISHED, "en", "url/filename.png")).thenReturn(BYTES2);
        when(handler.s3.readFile(MODEL_IDENTIFIER, "de", "url/filename.png")).thenReturn(BYTES);
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, "url/filename.png");
        assertTrue(handleImport.isSuccess());
        verifyEn("url/filename.png", "url/filename.png", Status.FREIGEGEBEN);
        verify(handler.s3).addFile(any(InputStream.class), anyLong(), eq(MODEL_IDENTIFIER), eq("en"),
                eq("url/filename.png"));
    }
    
    private void verifyEn(final String urlDe, final String urlEn, final Status statusEn) {
        verify(handler.imageDAO).merge(argThat(new ArgumentMatcher<Image>() {
            
            @Override
            public boolean matches(Object argument) {
                Image image = (Image) argument;
                assertEquals(image.getUrlDe(), urlDe);
                assertEquals(image.getUrlEn(), urlEn);
                assertEquals(image.getStatusEn(), statusEn);
                return true;
            }
        }));
    }

}
