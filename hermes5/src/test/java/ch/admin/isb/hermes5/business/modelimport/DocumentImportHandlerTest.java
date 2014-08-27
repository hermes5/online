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
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.persistence.db.dao.DocumentDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.Hardcoded;

public class DocumentImportHandlerTest {

    private static final String URL_DE = "url/filename_de.doc";
    private static final String URL_FR = "url/filename_fr.doc";
    private static final String URL_IT = "url/filename_it.doc";
    private static final String URL_EN = "url/filename_en.doc";
    private static final String PUBLISHED = "PUBLISHED";
    private static final String MODEL_IDENTIFIER = "modelIdentifier";
    private static final byte[] BYTES = "content".getBytes();
    private static final byte[] BYTES2 = "content2".getBytes();
    private DocumentImportHandler handler;

    @Before
    public void setUp() throws Exception {
        handler = new DocumentImportHandler();
        handler.documentDAO = mock(DocumentDAO.class);
        handler.modelRepository = mock(ModelRepository.class);
        handler.s3 = mock(S3.class);
        EPFModel published = new EPFModel();
        published.setIdentifier(PUBLISHED);
        when(handler.modelRepository.getPublishedModel()).thenReturn(published);
        Hardcoded.enableDefaults(handler);
        handler.init();
    }

    @Test
    public void testHandleImportEnNoPublished() {
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, URL_DE);
        assertTrue(handleImport.isSuccess());
        final String urlDe = URL_DE;
        final String urlEn = null;
        final Status statusEn = Status.UNVOLLSTAENDIG;
        verifyEn(urlDe, urlEn, statusEn);
    }

    @Test
    public void testHandleImportFrNoPublished() {
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, URL_DE);
        assertTrue(handleImport.isSuccess());
        final String urlDe = URL_DE;
        final String urlFr = null;
        final Status statusFr = Status.UNVOLLSTAENDIG;
        verifyFr(urlDe, urlFr, statusFr);
    }

    @Test
    public void testHandleImportItNoPublished() {
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, URL_DE);
        assertTrue(handleImport.isSuccess());
        final String urlDe = URL_DE;
        final String urlIt = null;
        final Status statusIt = Status.UNVOLLSTAENDIG;
        verifyIt(urlDe, urlIt, statusIt);
    }

    @Test
    public void testHandleImportWithChangedDeDocument() {
        Document publishedDocument = new Document();
        publishedDocument.setUrlDe(URL_DE);
        when(handler.documentDAO.getDocument(PUBLISHED, URL_DE)).thenReturn(publishedDocument);
        when(handler.s3.readFile(PUBLISHED, "de", URL_DE)).thenReturn(BYTES);
        when(handler.s3.readFile(MODEL_IDENTIFIER, "de", URL_DE)).thenReturn(BYTES2);
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, URL_DE);
        assertTrue(handleImport.isSuccess());
        verifyEn(URL_DE, null, Status.UNVOLLSTAENDIG);
        verifyFr(URL_DE, null, Status.UNVOLLSTAENDIG);
        verifyIt(URL_DE, null, Status.UNVOLLSTAENDIG);
        verify(handler.s3, never()).addFile(any(InputStream.class), anyLong(), anyString(), anyString(), anyString());
    }

    @Test
    public void testHandleImportWithExistingDeAndEnDocument() {
        Document publishedDocument = new Document();
        publishedDocument.setUrlDe(URL_DE);
        publishedDocument.setUrlEn(URL_EN);
        when(handler.documentDAO.getDocument(PUBLISHED, URL_DE)).thenReturn(publishedDocument);
        when(handler.s3.readFile(PUBLISHED, "de", URL_DE)).thenReturn(BYTES);
        when(handler.s3.readFile(PUBLISHED, "en", URL_EN)).thenReturn(BYTES2);
        when(handler.s3.readFile(MODEL_IDENTIFIER, "de", URL_DE)).thenReturn(BYTES);
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, URL_DE);
        assertTrue(handleImport.isSuccess());
        verifyEn(URL_DE, URL_EN, Status.FREIGEGEBEN);
        verify(handler.s3).addFile(any(InputStream.class), anyLong(), eq(MODEL_IDENTIFIER), eq("en"), eq(URL_EN));
    }

    @Test
    public void testHandleImportWithExistingDeAndFrDocument() {
        Document publishedDocument = new Document();
        publishedDocument.setUrlDe(URL_DE);
        publishedDocument.setUrlFr(URL_FR);
        when(handler.documentDAO.getDocument(PUBLISHED, URL_DE)).thenReturn(publishedDocument);
        when(handler.s3.readFile(PUBLISHED, "de", URL_DE)).thenReturn(BYTES);
        when(handler.s3.readFile(PUBLISHED, "fr", URL_FR)).thenReturn(BYTES2);
        when(handler.s3.readFile(MODEL_IDENTIFIER, "de", URL_DE)).thenReturn(BYTES);
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, URL_DE);
        assertTrue(handleImport.isSuccess());
        verifyFr(URL_DE, URL_FR, Status.FREIGEGEBEN);
        verify(handler.s3).addFile(any(InputStream.class), anyLong(), eq(MODEL_IDENTIFIER), eq("fr"), eq(URL_FR));
    }

    @Test
    public void testHandleImportWithExistingDeAndItDocument() {
        Document publishedDocument = new Document();
        publishedDocument.setUrlDe(URL_DE);
        publishedDocument.setUrlIt(URL_IT);
        when(handler.documentDAO.getDocument(PUBLISHED, URL_DE)).thenReturn(publishedDocument);
        when(handler.s3.readFile(PUBLISHED, "de", URL_DE)).thenReturn(BYTES);
        when(handler.s3.readFile(PUBLISHED, "it", URL_IT)).thenReturn(BYTES2);
        when(handler.s3.readFile(MODEL_IDENTIFIER, "de", URL_DE)).thenReturn(BYTES);
        ImportResult handleImport = handler.handleImport(MODEL_IDENTIFIER, BYTES, URL_DE);
        assertTrue(handleImport.isSuccess());
        verifyIt(URL_DE, URL_IT, Status.FREIGEGEBEN);
        verify(handler.s3).addFile(any(InputStream.class), anyLong(), eq(MODEL_IDENTIFIER), eq("it"), eq(URL_IT));
    }

    @Test
    public void testIsResponsibledoc() {
        assertFalse(handler.isResponsible("aaa.png"));
    }

    @Test
    public void testIsResponsibleJPG() {
        assertTrue(handler.isResponsible("aaa.DOCX"));
    }

    @Test
    public void testIsResponsiblePng() {
        assertTrue(handler.isResponsible("aaa.doc"));
    }

    private void verifyEn(final String urlDe, final String urlEn, final Status statusEn) {
        verify(handler.documentDAO).merge(argThat(new ArgumentMatcher<Document>() {

            @Override
            public boolean matches(Object argument) {
                Document d = (Document) argument;
                assertEquals(d.getUrlDe(), urlDe);
                assertEquals(d.getUrlEn(), urlEn);
                assertEquals(d.getStatusEn(), statusEn);
                return true;
            }
        }));
    }

    private void verifyFr(final String urlDe, final String urlFr, final Status statusFr) {
        verify(handler.documentDAO).merge(argThat(new ArgumentMatcher<Document>() {

            @Override
            public boolean matches(Object argument) {
                Document d = (Document) argument;
                assertEquals(d.getUrlDe(), urlDe);
                assertEquals(d.getUrlFr(), urlFr);
                assertEquals(d.getStatusFr(), statusFr);
                return true;
            }
        }));
    }

    private void verifyIt(final String urlDe, final String urlIt, final Status statusIt) {
        verify(handler.documentDAO).merge(argThat(new ArgumentMatcher<Document>() {

            @Override
            public boolean matches(Object argument) {
                Document d = (Document) argument;
                assertEquals(d.getUrlDe(), urlDe);
                assertEquals(d.getUrlIt(), urlIt);
                assertEquals(d.getStatusIt(), statusIt);
                return true;
            }
        }));
    }
}
