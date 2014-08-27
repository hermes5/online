/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.anwenderloesung;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.customelement.RelationshipTablePreprocessor;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.util.HtmlChecker;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.MockInstance;

public class AnwenderloesungModelElementDocumentationRendererTest {

    private static final String MODEL = "model";
    private final HtmlChecker htmlChecker = new HtmlChecker();
    private AnwenderloesungModelElementDocumentationRenderer renderer;
    private TranslationRepository translationRepository;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungModelElementDocumentationRenderer();
        
        renderer.anwenderloesungSzenarioRenderer = mock(AnwenderloesungSzenarioRenderer.class);
        
        VelocityAdapter velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(velocityAdapter);

        RelationshipTablePreprocessor relationshipTablePreprocessor = new RelationshipTablePreprocessor();

        AnwenderloesungModulRenderer anwenderloesungModulRenderer = new AnwenderloesungModulRenderer();
        anwenderloesungModulRenderer.velocityAdapter = velocityAdapter;

        AnwenderloesungPhaseRenderer anwenderloesungPhaseRenderer = new AnwenderloesungPhaseRenderer();
        anwenderloesungPhaseRenderer.velocityAdapter = velocityAdapter;

        AnwenderloesungBeschreibungRenderer anwenderloesungBeschreibungRenderer = new AnwenderloesungBeschreibungRenderer();
        anwenderloesungBeschreibungRenderer.velocityAdapter = velocityAdapter;

        AnwenderloesungErgebnisRenderer anwenderloesungErgebnisRenderer = new AnwenderloesungErgebnisRenderer();
        anwenderloesungErgebnisRenderer.velocityAdapter = velocityAdapter;
        anwenderloesungErgebnisRenderer.relationshipTablePreprocessor = relationshipTablePreprocessor;

        AnwenderloesungRolleRenderer anwenderloesungRolleRenderer = new AnwenderloesungRolleRenderer();
        anwenderloesungRolleRenderer.velocityAdapter = velocityAdapter;
        anwenderloesungRolleRenderer.relationshipTablePreprocessor = relationshipTablePreprocessor;

        AnwenderloesungAufgabeRenderer anwenderloesungAufgabeRenderer = new AnwenderloesungAufgabeRenderer();
        anwenderloesungAufgabeRenderer.velocityAdapter = velocityAdapter;
        anwenderloesungAufgabeRenderer.relationshipTablePreprocessor = relationshipTablePreprocessor;

        renderer.anwenderloesungModelElementRenderers = new MockInstance<AnwenderloesungModelElementRenderer>(
                anwenderloesungRolleRenderer, anwenderloesungModulRenderer, anwenderloesungErgebnisRenderer,
                anwenderloesungAufgabeRenderer, anwenderloesungPhaseRenderer, anwenderloesungBeschreibungRenderer);

        translationRepository = mock(TranslationRepository.class);
        when(translationRepository.getLocalizedText(anyString(), anyString(), anyString(), anyString()))
                .thenAnswer(new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        String s = "";
                        Object[] arguments = invocation.getArguments();
                        for (Object object : arguments) {
                            s += "/" + object;
                        }
                        return s;
                    }
                });
        when(translationRepository.getDocumentUrl(anyString(), anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return invocation.getArguments()[2] + "_" + invocation.getArguments()[1];
                    }
                });
    }

    @Test
    public void buildModulIntegrationTest() {
        Modul modul = modul("my_module");
        String x = renderModelElement(MODEL, modul, "de", new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("/model/de/id_my_module/mainDescription"));
        assertTrue(x.contains("/model/de/disciplineid_my_module/briefDescription"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildModulWithCustomProjektnameIntegrationTest() {
        Modul modul = modul("my_module");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setProjektname("My new Project");
        String x = renderModelElement(MODEL, modul, "de", szenarioUserData, Arrays.asList("de"), szenario("s1"));
        assertTrue(x, x.contains("My new Project"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildPhaseIntegrationTest() {
        Phase phase = phaseFull("abc");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setProjektname("My new Project");
        String x = renderModelElement(MODEL, phase, "de", szenarioUserData, Arrays.asList("de"), szenario("s1"));
        assertTrue(x, x.contains("abc"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildBeschreibungIntegrationTest() {
        Beschreibung beschreibung = beschreibung("abc");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setProjektname("My new Project");
        String x = renderModelElement(MODEL, beschreibung, "de", szenarioUserData, Arrays.asList("de"), szenario("s1"));
        assertTrue(x, x.contains("abc"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildModulWithoutCustomProjektnameIntegrationTest() {
        Modul modul = modul("my_module");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        Szenario szenario = szenario("s1");
        String x = renderModelElement(MODEL, modul, "de", szenarioUserData, Arrays.asList("de"), szenario);
        assertTrue(x, x.contains(szenario.getId()));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildWithLanguageHeaderIntegrationTest() {
        Modul modul = modul("my_module");
        String x = renderModelElement(MODEL, modul, "de", new SzenarioUserData(), Arrays.asList("de", "fr"),
                szenario("s1"));
        assertTrue(x, x.contains("/model/de/id_my_module/mainDescription"));
        assertTrue(x, x.contains("/model/de/disciplineid_my_module/briefDescription"));
        assertTrue(x, x.contains("Deutsch"));
        assertTrue(x, x.contains("Français"));
        assertTrue(x, x.contains("last"));
        htmlChecker.checkHtmlString(x);
    }

    private String renderModelElement(String identifier, AbstractMethodenElement modelElement, String lang,
            SzenarioUserData szenarioUserData, List<String> languages, Szenario szenario) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(identifier, szenario,
                szenarioUserData, languages, true, true, true,true);
        return renderer.renderModelElement(modelElement, new LocalizationEngine(translationRepository, MODEL, lang),
                container);
    }

    @Test
    public void buildErgebnisWithBeziehungenIntegrationTest() throws IOException {
        Ergebnis ergebnis = ergebnis("name", "url/Vorlage1.dot|url/Vorlage2.dot");
        aufgabe("aufgabe", modul("modul1"), ergebnis).setVerantwortlicheRolle(rolle("rolle2"));
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        String x = renderModelElement(MODEL, ergebnis, "de", new SzenarioUserData(), Arrays.asList("de"),
                szenario("s1"));
        x= removeWhiteSpaces(x);
        assertTrue(x.contains("/model/de/wpid_name/briefDescription"));
        assertTrue(x.contains("<a href=\"rolle_rolle1.html\"> /model/de/roleid_rolle1/presentationName</a>"));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"../../templates/de/Vorlage1.dot\""));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"../../templates/de/Vorlage2.dot\""));
        htmlChecker.checkHtmlString(x);
    }
    private String removeWhiteSpaces(String s) {
        s = s.replaceAll("\\n+", " ");
        s = s.replaceAll("\\s\\s+", " ");
        return s;
    }
    @Test
    public void buildCustomErgebnisIntegrationTest() throws IOException {
        Ergebnis ergebnis = customErgebnis("customName", "Vorlage1.docx");
        aufgabe("aufgabe", modul("modul1"), ergebnis).setVerantwortlicheRolle(rolle("rolle2"));
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        String x = renderModelElement(MODEL, ergebnis, "de", new SzenarioUserData(), Arrays.asList("de"),
                szenario("s1"));
        assertTrue(
                x,
                x.contains("<a class=\"custom-artifact-link\" href=\"../../templates/de/custom/Vorlage1.docx\" target=\"_blank\">Vorlage1.docx</a>"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildRolleIntegrationTest() throws IOException {
        Rolle rolle = rolle("projektleiter");
        String x = renderModelElement(MODEL, rolle, "de", new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("/model/de/roleid_projektleiter/briefDescription"));
        assertTrue(x.contains("<p>/model/de/id_projektleiter_presentation/mainDescription</p>"));
        assertTrue(x.contains("<p>/model/de/id_projektleiter_presentation/skills</p>"));
        assertTrue(x.contains("<p>/model/de/id_projektleiter_presentation/assignmentApproaches</p>"));
        assertTrue(x, x.contains("Zurück zu Szenario"));
        assertTrue(x, x.contains("s1"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildRolleWithRelationshipsIntegrationTest() throws IOException {
        Rolle rolle = rolle("projektleiter");
        aufgabe("aufgabe", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        String x = renderModelElement(MODEL, rolle, "de", new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("Beziehungen"));
        assertTrue(x.contains("href=\"modul_modul1.html\">/model/de/disciplineid_modul1/presentationName"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildRolleWithoutMainDescriptionIntegrationTest() throws IOException {
        when(
                translationRepository.getLocalizedText(anyString(), anyString(), anyString(),
                        eq("mainDescription"))).thenReturn(null);
        Rolle rolle = rolle("projektleiter");
        String x = renderModelElement(MODEL, rolle, "de", new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(!x.contains("<p>/model/de/id_projektleiter_presentation/mainDescription</p>"));
        assertTrue(x.contains("<p>/model/de/id_projektleiter_presentation/skills</p>"));
        assertTrue(x.contains("<p>/model/de/id_projektleiter_presentation/assignmentApproaches</p>"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildAufgabeIntegrationTest() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Aufgabe aufgabe = aufgabe("aufgabe1", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        String x = renderModelElement(MODEL, aufgabe, "de", new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("/model/de/taskid_aufgabe1/briefDescription"));
        assertTrue(x
                .contains("<a href=\"rolle_projektleiter.html\">/model/de/roleid_projektleiter/presentationName</a>"));
        assertTrue(x.contains("<p>/model/de/presentation_id_aufgabe1/alternatives</p>")); // Aktivitäten
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildAufgabeWithRelationshipsIntegrationTest() throws IOException {
        Rolle rolle = rolle("projektleiter");
        Aufgabe aufgabe = aufgabe("aufgabe", modul("modul1"), rolle, ergebnis("ergebnis", rolle));
        String x = renderModelElement(MODEL, aufgabe, "de", new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x.contains("Beziehungen"));
        assertTrue(x, x.contains("Zurück zu Szenario"));
        assertTrue(x.contains("/model/de/taskid_aufgabe/briefDescription"));
        assertTrue(x.contains("href=\"modul_modul1.html\">/model/de/disciplineid_modul1/presentationName"));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void buildErgebnisAnwenderloesungIntegrationTest() throws IOException {
        Ergebnis ergebnis = ergebnis("name", "url/Vorlage1.dot|url/Vorlage2.dot");
        ergebnis.addVerantwortlicheRolle(rolle("rolle1"));
        String x = renderModelElement(MODEL, ergebnis, "de", new SzenarioUserData(), Arrays.asList("de"),
                szenario("s1"));
        assertTrue(x, x.contains("Zurück zu Szenario"));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"../../templates/de/Vorlage1.dot\""));
        assertTrue(x, x.contains("<a class=\"artifact-link\" href=\"../../templates/de/Vorlage2.dot\""));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void testRenderSzenario() {
        AnwenderloesungRenderingContainer container = mock(AnwenderloesungRenderingContainer.class);
        when(renderer.anwenderloesungSzenarioRenderer.renderSzenario(argThat(new ArgumentMatcher<LocalizationEngine>() {

            @Override
            public boolean matches(Object argument) {
                return argument instanceof LocalizationEngine && argument != null
                        && "fr".equals(((LocalizationEngine) argument).getLanguage());
            }
        }), eq(container))).thenReturn("success");

        assertEquals("success", renderer.renderSzenario(new LocalizationEngine(translationRepository, MODEL, "fr"), container));
    }
}
