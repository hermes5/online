/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.userszenario.projektstrukturplan.ProjektstrukturplanGenerator;
import ch.admin.isb.hermes5.business.userszenario.szenario.SzenarioDocumentationGenerator;
import ch.admin.isb.hermes5.business.userszenario.vorlagen.ErgebnisVorlagenGenerator;
import ch.admin.isb.hermes5.business.userszenario.xmlmodel.XMLModelGenerator;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.domain.SzenarioUserData;

public class SzenarioDownloadWorkflowTest {

    private SzenarioDownloadWorkflow downloadWorkflow;

    @Before
    public void setUp() throws Exception {
        downloadWorkflow = new SzenarioDownloadWorkflow();
        downloadWorkflow.szenarioDocumentationGenerator = mock(SzenarioDocumentationGenerator.class);
        downloadWorkflow.szenarioStripper = mock(SzenarioStripper.class);
        downloadWorkflow.ergebnisVorlagenGenerator = mock(ErgebnisVorlagenGenerator.class);
        downloadWorkflow.translationRepository = mock(TranslationRepository.class);
        downloadWorkflow.projektstrukturplanGenerator = mock(ProjektstrukturplanGenerator.class);
        downloadWorkflow.xmlModelGenerator = mock(XMLModelGenerator.class);
        downloadWorkflow.szenarioCustomizer = mock(SzenarioCustomizer.class);
        when(
                downloadWorkflow.szenarioStripper.stripSzenarioToSelectedItems(any(Szenario.class),
                        any(SzenarioItem.class))).thenAnswer(new Answer<Szenario>() {

            @Override
            public Szenario answer(InvocationOnMock invocation) throws Throwable {
                return (Szenario) invocation.getArguments()[0];
            }
        });
    }

    @Test
    public void testGenerateDownloadZipDokumentationOnlyDeAndFr() {
        final Szenario szenario = new Szenario(null);
        final SzenarioUserData szenarioUserData = new SzenarioUserData();
        final List<String> expectedLangs = Arrays.asList("de", "fr");
        downloadWorkflow.generateDownloadZip("model", szenario, szenarioUserData, false, true, false, false,
                expectedLangs);
        verifySzenarioDokumentationAdded(szenario, szenarioUserData);
        verifyTemplatesAdded(szenario, szenarioUserData);
        verifyNoTemplateStartPageAdded();
        verifyProjektstrukturplanNeverCalled();
        verifyXMLModelNeverCalled();
    }

    @Test
    public void testGenerateDownloadZipProjektstrukturplanOnlyDeAndFr() {
        final Szenario szenario = new Szenario(null);
        final SzenarioUserData szenarioUserData = new SzenarioUserData();
        final List<String> expectedLangs = Arrays.asList("de", "fr");
        downloadWorkflow.generateDownloadZip("model", szenario, szenarioUserData, true, false, false, false,
                expectedLangs);
        
        verifyProjektstrukturplanAdded(szenario, szenarioUserData);
        verifyNoTemplatesAdded();
        verifyDocumentationNeverCalled();
        verifyXMLModelNeverCalled();
    }

    @Test
    public void testGenerateDownloadZipVorlagenOnlyDeAndFr() {
        final Szenario szenario = new Szenario(null);
        final SzenarioUserData szenarioUserData = new SzenarioUserData();
        final List<String> expectedLangs = Arrays.asList("de", "fr");
        downloadWorkflow.generateDownloadZip("model", szenario, szenarioUserData, false, false, true, false,
                expectedLangs);
        verifyTemplateStartpageAdded(szenario, szenarioUserData);

        verifyTemplatesAdded(szenario, szenarioUserData);
        verifyDocumentationNeverCalled();
        verifyProjektstrukturplanNeverCalled();
        verifyXMLModelNeverCalled();
    }

    @Test
    public void testGenerateDownloadZipXMLOnlyDeAndFr() {
        final Szenario szenario = new Szenario(null);
        final SzenarioUserData szenarioUserData = new SzenarioUserData();
        final List<String> expectedLangs = Arrays.asList("de", "fr");
        downloadWorkflow.generateDownloadZip("model", szenario, szenarioUserData, false, false, false, true,
                expectedLangs);

        verifyXMLModelAdded(szenario, szenarioUserData);
        verifyTemplatesAdded(szenario, szenarioUserData);
        verifyNoTemplateStartPageAdded();
        verifyDocumentationNeverCalled();
        verifyProjektstrukturplanNeverCalled();

    }
    
    @Test
    public void testGenerateDownloadZipDeAndFr() {
        final Szenario szenario = new Szenario(null);
        final SzenarioUserData szenarioUserData = new SzenarioUserData();
        final List<String> expectedLangs = Arrays.asList("de", "fr");
        downloadWorkflow.generateDownloadZip("model", szenario, szenarioUserData, true, true, true, true,
                expectedLangs);

        verifyXMLModelAdded(szenario, szenarioUserData);
        verifyTemplatesAdded(szenario, szenarioUserData);
        verifyTemplateStartpageAdded(szenario, szenarioUserData);
        verifySzenarioDokumentationAdded(szenario, szenarioUserData);
        verifyProjektstrukturplanAdded(szenario, szenarioUserData);

    }
    
    private ArgumentMatcher<AnwenderloesungRenderingContainer> containerMatcher(final Szenario szenario,
            final SzenarioUserData szenarioUserData) {
        return new ArgumentMatcher<AnwenderloesungRenderingContainer>() {

            @Override
            public boolean matches(Object argument) {
                AnwenderloesungRenderingContainer container = (AnwenderloesungRenderingContainer) argument;
                assertEquals("model", container.getModelIdentifier());
                assertEquals(szenarioUserData, container.getSzenarioUserData());
                assertEquals(szenario, container.getSzenario());
                return true;
            }
        };
    }

    private ArgumentMatcher<LocalizationEngine> localizationEngineMatcher(final String lang) {
        return new ArgumentMatcher<LocalizationEngine>() {

            @Override
            public boolean matches(Object argument) {
                LocalizationEngine localizationEngine = (LocalizationEngine) argument;
                return lang.equals(localizationEngine.getLanguage());
            }
        };
    }


    private void verifyDocumentationNeverCalled() {
        verify(downloadWorkflow.szenarioDocumentationGenerator, never()).addDocumentation(
                any(AnwenderloesungRenderingContainer.class), any(ZipOutputBuilder.class),
                any(LocalizationEngine.class));
    }

    private void verifyTemplateStartpageAdded(final Szenario szenario, final SzenarioUserData szenarioUserData) {
        verify(downloadWorkflow.ergebnisVorlagenGenerator).addTemplateIndexPage(
                argThat(containerMatcher(szenario, szenarioUserData)), any(ZipOutputBuilder.class),
                argThat(localizationEngineMatcher("de")));
        verify(downloadWorkflow.ergebnisVorlagenGenerator).addTemplateIndexPage(
                argThat(containerMatcher(szenario, szenarioUserData)), any(ZipOutputBuilder.class),
                argThat(localizationEngineMatcher("fr")));
    }

    private void verifyNoTemplatesAdded() {
        verify(downloadWorkflow.ergebnisVorlagenGenerator, never()).addErgebnisVorlagen(anyString(), any(Szenario.class),
                any(SzenarioUserData.class), any(LocalizationEngine.class), any(ZipOutputBuilder.class));
        verifyNoTemplateStartPageAdded();
    }

    private void verifyNoTemplateStartPageAdded() {
        verify(downloadWorkflow.ergebnisVorlagenGenerator, never()).addTemplateIndexPage(
                any(AnwenderloesungRenderingContainer.class), any(ZipOutputBuilder.class),
                any(LocalizationEngine.class));
    }

    private void verifyProjektstrukturplanAdded(final Szenario szenario, final SzenarioUserData szenarioUserData) {
        verify(downloadWorkflow.projektstrukturplanGenerator).generateProjektStrukturplan(
                any(ZipOutputBuilder.class),  argThat(containerMatcher(szenario, szenarioUserData)),
                argThat(localizationEngineMatcher("de")));
        verify(downloadWorkflow.projektstrukturplanGenerator).generateProjektStrukturplan(
                any(ZipOutputBuilder.class),  argThat(containerMatcher(szenario, szenarioUserData)),
                argThat(localizationEngineMatcher("fr")));
    }

    
    private void verifyProjektstrukturplanNeverCalled() {
        verify(downloadWorkflow.projektstrukturplanGenerator, never()).generateProjektStrukturplan(
                any(ZipOutputBuilder.class), any(AnwenderloesungRenderingContainer.class),
                any(LocalizationEngine.class));
    }

    private void verifySzenarioDokumentationAdded(final Szenario szenario, final SzenarioUserData szenarioUserData) {
        verify(downloadWorkflow.szenarioDocumentationGenerator).addDocumentation(
                argThat(containerMatcher(szenario, szenarioUserData)), any(ZipOutputBuilder.class),
                argThat(localizationEngineMatcher("de")));
        verify(downloadWorkflow.szenarioDocumentationGenerator).addDocumentation(
                argThat(containerMatcher(szenario, szenarioUserData)), any(ZipOutputBuilder.class),
                argThat(localizationEngineMatcher("fr")));
    }

    private void verifyTemplatesAdded(final Szenario szenario, final SzenarioUserData szenarioUserData) {
        verify(downloadWorkflow.ergebnisVorlagenGenerator).addErgebnisVorlagen(eq("model"), eq(szenario),
                eq(szenarioUserData), argThat(localizationEngineMatcher("de")), any(ZipOutputBuilder.class));
        verify(downloadWorkflow.ergebnisVorlagenGenerator).addErgebnisVorlagen(eq("model"), eq(szenario),
                eq(szenarioUserData), argThat(localizationEngineMatcher("fr")), any(ZipOutputBuilder.class));
    }

    private void verifyXMLModelAdded(final Szenario szenario, final SzenarioUserData szenarioUserData) {
        verify(downloadWorkflow.xmlModelGenerator).addXMLModelWithContent(
                argThat(containerMatcher(szenario, szenarioUserData)), any(ZipOutputBuilder.class),
                argThat(localizationEngineMatcher("de")));
        verify(downloadWorkflow.xmlModelGenerator).addXMLModelWithContent(
                argThat(containerMatcher(szenario, szenarioUserData)), any(ZipOutputBuilder.class),
                argThat(localizationEngineMatcher("fr")));
        
        verify(downloadWorkflow.xmlModelGenerator).addIndexPage(argThat(containerMatcher(szenario, szenarioUserData)),
                any(ZipOutputBuilder.class), argThat(localizationEngineMatcher("de")));
        verify(downloadWorkflow.xmlModelGenerator).addIndexPage(argThat(containerMatcher(szenario, szenarioUserData)),
                any(ZipOutputBuilder.class), argThat(localizationEngineMatcher("fr")));

        verify(downloadWorkflow.xmlModelGenerator, times(1)).addXSDToZip(any(ZipOutputBuilder.class));
    }

    private void verifyXMLModelNeverCalled() {
        verify(downloadWorkflow.xmlModelGenerator, never()).addXMLModelWithContent(
                any(AnwenderloesungRenderingContainer.class), any(ZipOutputBuilder.class),
                any(LocalizationEngine.class));
        verify(downloadWorkflow.xmlModelGenerator, never()).addIndexPage(any(AnwenderloesungRenderingContainer.class),
                any(ZipOutputBuilder.class), any(LocalizationEngine.class));
        verify(downloadWorkflow.xmlModelGenerator, never()).addXSDToZip(any(ZipOutputBuilder.class));
    }

}
