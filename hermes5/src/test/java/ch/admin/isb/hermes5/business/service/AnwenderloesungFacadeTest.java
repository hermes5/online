/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.service;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.userszenario.SzenarioDownloadWorkflow;
import ch.admin.isb.hermes5.business.word.WordDocumentCustomizer;
import ch.admin.isb.hermes5.domain.DefaultLocalizable;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.ZipUtil;

public class AnwenderloesungFacadeTest {

    private AnwenderloesungFacade anwenderloesungFacade;

    @Before
    public void setUp() throws Exception {
        anwenderloesungFacade = new AnwenderloesungFacade();
        anwenderloesungFacade.modelRepository = mock(ModelRepository.class);
        anwenderloesungFacade.szenarioDownloadWorkflow = mock(SzenarioDownloadWorkflow.class);
        anwenderloesungFacade.translationRepository = mock(TranslationRepository.class);
        anwenderloesungFacade.docx4jWordDocumentCustomizer = mock(WordDocumentCustomizer.class);
        anwenderloesungFacade.s3 = mock(S3.class);
    }

    @Test
    public void testGetSzenarien() {
        List<Szenario> expected = Arrays.asList(szenario("szenario"));
        when(anwenderloesungFacade.modelRepository.getSzenarien("model")).thenReturn(expected);
        assertEquals(expected, anwenderloesungFacade.getSzenarien("model"));
    }

    @Test
    public void testGetPublishedModel() {
        EPFModel expected = new EPFModel();
        when(anwenderloesungFacade.modelRepository.getPublishedModel()).thenReturn(expected);
        assertEquals(expected, anwenderloesungFacade.getPublishedModel());
    }

    @Test
    public void testGetPublishedSzenarioFile() {
        byte[] expected = "content".getBytes();
        when(anwenderloesungFacade.s3.readSampelszenarioFile("modelidentifier", "/url")).thenReturn(expected);
        assertEquals(expected, anwenderloesungFacade.getPublishedSzenarioFile("modelidentifier", "/url"));

    }

    @Test
    public void testGenerateDownloadZip() {
        Szenario szenario = szenario("szenario");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        List<String> languages = Arrays.asList("de", "fr");
        byte[] expected = "bytes".getBytes();
        when(
                anwenderloesungFacade.szenarioDownloadWorkflow.generateDownloadZip("model", szenario, szenarioUserData,
                        true, true, true, true, languages)).thenReturn(expected);
        assertEquals(expected, anwenderloesungFacade.generateDownloadZip("model", szenario, szenarioUserData, true,
                true, true, true, languages));

    }

    @Test
    public void testLocalize() {
        Localizable localizable = new DefaultLocalizable(task("task"), "text");
        when(anwenderloesungFacade.translationRepository.getLocalizedText("model", "de", "taskid_task", "text"))
                .thenReturn("translated");
        assertEquals("translated", anwenderloesungFacade.localize("model", localizable, "de"));
    }

    @Test
    public void testGetModules() {
        List<Modul> expected = Arrays.asList(modul("aa"));
        when(anwenderloesungFacade.modelRepository.getModulesWithErgebnisse("model")).thenReturn(expected);
        assertEquals(expected, anwenderloesungFacade.getModulesWithErgebnisse("model"));
    }

    @Test
    public void readDocument() {
        byte[] expected = "abc".getBytes();
        byte[] file = "bcd".getBytes();
        when(anwenderloesungFacade.s3.readFile("model", "fr", "url")).thenReturn(file);
        when(
                anwenderloesungFacade.docx4jWordDocumentCustomizer.adjustDocumentWithUserData(eq("url"), eq(file),
                        any(SzenarioUserData.class))).thenReturn(expected);
        byte[] readDocument = anwenderloesungFacade.readDocument("model", "fr", "url");
        assertEquals(expected, readDocument);
    }

    @Test
    public void getVorlagenZip() throws IOException {
        byte[] expected1 = "abc1".getBytes();
        byte[] expected2 = "abc2".getBytes();
        byte[] expected3 = "abc3".getBytes();
        byte[] file1 = "bcd1".getBytes();
        byte[] file2 = "bcd2".getBytes();
        byte[] file3 = "bcd3".getBytes();
        when(anwenderloesungFacade.s3.readFile("model", "fr", "url1")).thenReturn(file1);
        when(anwenderloesungFacade.s3.readFile("model", "fr", "url2")).thenReturn(file2);
        when(anwenderloesungFacade.s3.readFile("model", "fr", "url3")).thenReturn(file3);
        when(
                anwenderloesungFacade.docx4jWordDocumentCustomizer.adjustDocumentWithUserData(eq("url1"), eq(file1),
                        any(SzenarioUserData.class))).thenReturn(expected1);
        when(
                anwenderloesungFacade.docx4jWordDocumentCustomizer.adjustDocumentWithUserData(eq("url2"), eq(file2),
                        any(SzenarioUserData.class))).thenReturn(expected2);
        when(
                anwenderloesungFacade.docx4jWordDocumentCustomizer.adjustDocumentWithUserData(eq("url3"), eq(file3),
                        any(SzenarioUserData.class))).thenReturn(expected3);
        List<Document> selectedDocument = new ArrayList<Document>();
        selectedDocument.add(document("url1"));
        selectedDocument.add(document("url2"));
        selectedDocument.add(document("url3"));
        byte[] zip = anwenderloesungFacade.generateVorlagenZip(selectedDocument, "fr");
        assertNotNull(zip);
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zip));
        ZipUtil zipUtil = new ZipUtil();
        zipInputStream.getNextEntry();
        assertTrue(Arrays.equals(expected1, zipUtil.readZipEntry(zipInputStream)));
        zipInputStream.getNextEntry();
        assertTrue(Arrays.equals(expected2, zipUtil.readZipEntry(zipInputStream)));
        zipInputStream.getNextEntry();
        assertTrue(Arrays.equals(expected3, zipUtil.readZipEntry(zipInputStream)));
    }

    private Document document(String string) {
        Document document = new Document();
        document.setUrlFr(string);
        document.setModelIdentifier("model");
        return document;
    }

    @Test
    public void getDocumentsOfModule() {
        when(anwenderloesungFacade.translationRepository.getDocument(eq("model"), anyString())).thenAnswer(
                new Answer<Document>() {

                    @Override
                    public Document answer(InvocationOnMock invocation) throws Throwable {
                        Document document = new Document();
                        document.setUrlDe(String.valueOf(invocation.getArguments()[1]));
                        return document;
                    }
                });
        Modul modul = modul("modul");

        Ergebnis ergebnis1 = ergebnis("e1", "vorlage1|vorlage2");
        Ergebnis ergebnis2 = ergebnis("e2", "vorlage3");
        aufgabe("a1", modul, rolle("r1"), ergebnis1, ergebnis2);
        Ergebnis ergebnis3 = ergebnis("e3", "vorlage4");
        aufgabe("a2", modul, rolle("r2"), ergebnis3);
        List<Document> documentsOfModule = anwenderloesungFacade.getDocumentsOfModule("model", modul);
        assertEquals(4, documentsOfModule.size());
        assertEquals("vorlage1", documentsOfModule.get(0).getUrlDe());
        assertEquals("vorlage2", documentsOfModule.get(1).getUrlDe());
        assertEquals("vorlage3", documentsOfModule.get(2).getUrlDe());
        assertEquals("vorlage4", documentsOfModule.get(3).getUrlDe());
    }
}
