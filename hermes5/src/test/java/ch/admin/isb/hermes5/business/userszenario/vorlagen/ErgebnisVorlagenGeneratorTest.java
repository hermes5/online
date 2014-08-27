/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.vorlagen;

import static ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder.*;
import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
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
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungTemplateIndexPageRenderer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.userszenario.vorlagen.ErgebnisLink.Type;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.word.WordDocumentCustomizer;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioBuilder;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.AssertUtils;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ZipUtil;

public class ErgebnisVorlagenGeneratorTest {

    private ErgebnisVorlagenGenerator ergebnisVorlagenGenerator;
    private static final ZipUtil zipUtil = new ZipUtil();

    @Before
    public void setUp() throws Exception {
        ergebnisVorlagenGenerator = new ErgebnisVorlagenGenerator();
        ergebnisVorlagenGenerator.documentCustomizer = mock(WordDocumentCustomizer.class);
        ergebnisVorlagenGenerator.anwenderloesungTemplateIndexPageRenderer = mock(AnwenderloesungTemplateIndexPageRenderer.class);

        when(
                ergebnisVorlagenGenerator.documentCustomizer.adjustDocumentWithUserData(anyString(), (byte[]) any(),
                        any(SzenarioUserData.class))).thenAnswer(new Answer<byte[]>() {

            @Override
            public byte[] answer(InvocationOnMock invocation) throws Throwable {
                return (byte[]) invocation.getArguments()[1];
            }
        });
        ergebnisVorlagenGenerator.s3 = mock(S3.class);
        when(ergebnisVorlagenGenerator.s3.readFile(anyString(), anyString(), anyString())).thenReturn(
                "bytes".getBytes());
        Hardcoded.enableDefaults(ergebnisVorlagenGenerator);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddTemplateIndexPage() {
        ZipOutputBuilder zipBuilder = mock(ZipOutputBuilder.class);
        Szenario szenario = mock(Szenario.class);
        when(szenario.getErgebnisse()).thenReturn(new ArrayList<Ergebnis>());

        SzenarioUserData szenarioUserData = new SzenarioUserData();
        boolean projektstrukturplan = true;
        boolean dokumentation = false;
        boolean ergebnisvorlagen = true;

        List<String> languages = Arrays.asList(new String[] { "de", "fr", "it", "en" });
        when(
                ergebnisVorlagenGenerator.anwenderloesungTemplateIndexPageRenderer.renderTemplateIndexPage(
                        any(LocalizationEngine.class), (List<ErgebnisLink>) any(),
                        any(AnwenderloesungRenderingContainer.class))).thenReturn("content");
        addTemplateIndexPages("model_id", szenario, projektstrukturplan, dokumentation, ergebnisvorlagen, languages,
                zipBuilder, szenarioUserData);
        verify(zipBuilder).addFile(anyString(), (byte[]) any());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddTemplateIndexPagesSendsSzenario() {
        ZipOutputBuilder zipBuilder = mock(ZipOutputBuilder.class);
        Szenario szenario = mock(Szenario.class);
        when(szenario.getErgebnisse()).thenReturn(new ArrayList<Ergebnis>());

        SzenarioUserData szenarioUserData = new SzenarioUserData();
        List<String> languages = Arrays.asList(new String[] { "de" });
        when(
                ergebnisVorlagenGenerator.anwenderloesungTemplateIndexPageRenderer.renderTemplateIndexPage(
                        any(LocalizationEngine.class), (List<ErgebnisLink>) any(),
                        any(AnwenderloesungRenderingContainer.class))).thenReturn("content");
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model_id", szenario,
                szenarioUserData, languages, false, false, true, true);
        ergebnisVorlagenGenerator.addTemplateIndexPage(container, zipBuilder, getLocalizationEngineDe());
        verify(ergebnisVorlagenGenerator.anwenderloesungTemplateIndexPageRenderer).renderTemplateIndexPage(
                matchDELocalizationEngine(), anyListOf(ErgebnisLink.class), eq(container));
    }

    private LocalizationEngine matchDELocalizationEngine() {
        return argThat(new ArgumentMatcher<LocalizationEngine>() {

            @Override
            public boolean matches(Object argument) {
                return "de".equals(((LocalizationEngine) argument).getLanguage());
            }
        });
    }

    @Test
    public void testAddErgebnisVorlagen() throws Exception {
        Szenario szenario = szenario("szenario");
        szenario.getPhasen().add(phaseFull("phase"));
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        ergebnisVorlagenGenerator.addErgebnisVorlagen("model", szenario, new SzenarioUserData(),
                getLocalizationEngineDe(), zipBuilder);

        byte[] result = zipBuilder.getResult();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(result));
        ZipEntry nextEntry;
        List<String> names = new ArrayList<String>();
        while ((nextEntry = zipInputStream.getNextEntry()) != null) {
            names.add(nextEntry.getName());
            byte[] byteArray = zipUtil.readZipEntry(zipInputStream);
            assertNotNull(byteArray);
        }
        AssertUtils.assertContains(names, "templates/de/vorlage1", "templates/de/vorlage2", "templates/de/vorlage3");
    }

    @Test
    public void testAddTemplateIndexPagesWithCustomErgebnis() throws IOException {
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        Szenario szenario = mock(Szenario.class);
        when(szenario.getErgebnisse()).thenReturn(
                Arrays.asList(SzenarioBuilder.ergebnis("abc", "vorlage1.docx|vorlage2.pptx")));

        SzenarioUserData szenarioUserData = new SzenarioUserData();
        List<CustomErgebnis> customErgebnisse = szenarioUserData.addCustomModule("testModul");
        customErgebnisse.add(new CustomErgebnis("name", "mainDescription", "vorlageFilename.docx", "vorlageContent"
                .getBytes(), new ArrayList<String>(), new ArrayList<Rolle>()));
        boolean projektstrukturplan = true;
        boolean dokumentation = false;
        boolean ergebnisvorlagen = true;

        List<String> languages = Arrays.asList(new String[] { "de", "fr", "it", "en" });
        when(
                ergebnisVorlagenGenerator.anwenderloesungTemplateIndexPageRenderer.renderTemplateIndexPage(
                        any(LocalizationEngine.class), anyListOf(ErgebnisLink.class),
                        any(AnwenderloesungRenderingContainer.class))).thenReturn("content");
        addTemplateIndexPages("model_id", szenario, projektstrukturplan, dokumentation, ergebnisvorlagen, languages,
                zipBuilder, szenarioUserData);
        verify(ergebnisVorlagenGenerator.anwenderloesungTemplateIndexPageRenderer).renderTemplateIndexPage(
                matchDELocalizationEngine(), argThat(new ArgumentMatcher<List<ErgebnisLink>>() {

                    @SuppressWarnings("rawtypes")
                    @Override
                    public boolean matches(Object argument) {
                        List list = (List) argument;
                        assertEquals(3, list.size());
                        assertTrue(String.valueOf(list), list.contains(new ErgebnisLink("vorlageFilename.docx","custom/vorlageFilename.docx", Type.TEMPLATE)));
                        assertTrue(String.valueOf(list), list.contains(new ErgebnisLink("vorlage1.docx","vorlage1.docx", Type.TEMPLATE)));
                        assertTrue(String.valueOf(list), list.contains(new ErgebnisLink("vorlage2.pptx","vorlage2.pptx", Type.TEMPLATE)));
                        return true;
                    }
                }), any(AnwenderloesungRenderingContainer.class));

    }
    
    @Test
    public void testAddTemplateIndexPagesWithURL() throws IOException {
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        Szenario szenario = mock(Szenario.class);
        when(szenario.getErgebnisse()).thenReturn(
                Arrays.asList(SzenarioBuilder.ergebnis("abc", "<a href=\"http://www.hermes.admin.ch\">HERMES</a>")));

        SzenarioUserData szenarioUserData = new SzenarioUserData();
        boolean projektstrukturplan = true;
        boolean dokumentation = false;
        boolean ergebnisvorlagen = true;

        List<String> languages = Arrays.asList(new String[] { "de", "fr", "it", "en" });
        when(
                ergebnisVorlagenGenerator.anwenderloesungTemplateIndexPageRenderer.renderTemplateIndexPage(
                        any(LocalizationEngine.class), anyListOf(ErgebnisLink.class),
                        any(AnwenderloesungRenderingContainer.class))).thenReturn("content");
        LocalizationEngine localizationEngineMock = mock(LocalizationEngine.class);
        when(localizationEngineMock.localize(any(Localizable.class))).thenReturn("<a href=\"http://www.hermes.admin.ch\">HERMES</a>");
        addTemplateIndexPages("model_id", szenario, projektstrukturplan, dokumentation, ergebnisvorlagen, languages,
                zipBuilder, szenarioUserData, localizationEngineMock);
        verify(ergebnisVorlagenGenerator.anwenderloesungTemplateIndexPageRenderer).renderTemplateIndexPage(
                eq(localizationEngineMock), argThat(new ArgumentMatcher<List<ErgebnisLink>>() {

                    @SuppressWarnings("rawtypes")
                    @Override
                    public boolean matches(Object argument) {
                        List list = (List) argument;
                        assertEquals(1, list.size());
                        assertTrue(String.valueOf(list), list.contains(new ErgebnisLink("HERMES","http://www.hermes.admin.ch", Type.URL)));
                        return true;
                    }
                }), any(AnwenderloesungRenderingContainer.class));

    }


    private void addTemplateIndexPages(String string, Szenario szenario, boolean projektstrukturplan,
            boolean dokumentation, boolean ergebnisvorlagen, List<String> languages, ZipOutputBuilder zipBuilder,
            SzenarioUserData szenarioUserData) {
        LocalizationEngine localizationEngineDe = getLocalizationEngineDe();
        addTemplateIndexPages(string, szenario, projektstrukturplan, dokumentation, ergebnisvorlagen, languages,
                zipBuilder, szenarioUserData, localizationEngineDe);
    }

    private void addTemplateIndexPages(String string, Szenario szenario, boolean projektstrukturplan,
            boolean dokumentation, boolean ergebnisvorlagen, List<String> languages, ZipOutputBuilder zipBuilder,
            SzenarioUserData szenarioUserData, LocalizationEngine localizationEngineDe) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(string, szenario,
                szenarioUserData, languages, dokumentation, projektstrukturplan, ergebnisvorlagen,false);
        ergebnisVorlagenGenerator.addTemplateIndexPage(container, zipBuilder, localizationEngineDe);
    }

}
