/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.util.Hardcoded;

public class SearchEngineTest {

    private static final String TARGET_TMP_SEARCH = "target/tmp/search";
    private SearchEngine searchEngine;

    @Before
    public void setUp() {
        searchEngine = new SearchEngine();
        searchEngine.searchIndexManager = mock(SearchIndexManager.class);
        searchEngine.analyserRepository = new AnalyserRepository();
        searchEngine.highlighterRepository = new HighlighterRepository();
        
        Hardcoded.enableDefaults(searchEngine);
        Hardcoded.enableDefaults(searchEngine.highlighterRepository);
        when(searchEngine.searchIndexManager.getSearchIndexPath("identifier", "de")).thenReturn(TARGET_TMP_SEARCH);
        deleteExistingFiles();
    }

    private void deleteExistingFiles() {
        File[] listFiles = new File(TARGET_TMP_SEARCH).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                file.delete();
            }
        }
    }

    @Test
    public void testSearch() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        indexWriter
                .addDocumentFromModelElement(rolle("projektleiter"),
                        "Der Projektleiter führt das Projekt im Auftrag des Auftraggebers. Er wird vom Auftraggeber ernannt und geführt.");
        Aufgabe aufgabe = aufgabe("projektsteuern", modul("modul1"));
        indexWriter
                .addDocumentFromModelElement(aufgabe,
                        "Die Steuerung des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Steuerung", "identifier", "de");
        assertEquals(1, search.size());
        assertEquals(aufgabe.getName(), search.get(0).getName());
        assertEquals(
                "Die <B>Steuerung</B> des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.",
                search.get(0).getContent());
    }
    
    @Test
    public void testSearchIssueHERMES164Projektfreiga() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        Aufgabe aufgabe = aufgabe("projektsteuern", modul("modul1"));
        indexWriter
                .addDocumentFromModelElement(aufgabe,
                        "Der Entscheid zur Projektfreigabe schafft die Voraussetzung für die Arbeiten in der Phase Konzept.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Projektfreiga", "identifier", "de");
        assertEquals(1, search.size());
        assertEquals(aufgabe.getName(), search.get(0).getName());
    }
    
    @Test
    public void testSearchIssueHERMES164Projektfreigab() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        Aufgabe aufgabe = aufgabe("projektsteuern", modul("modul1"));
        indexWriter
                .addDocumentFromModelElement(aufgabe,
                        "Der Entscheid zur Projektfreigabe schafft die Voraussetzung für die Arbeiten in der Phase Konzept.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Projektfreigab", "identifier", "de");
        assertEquals(1, search.size());
        assertEquals(aufgabe.getName(), search.get(0).getName());
    }
    
    @Test
    public void testSearchIssueHERMES164Projektfreigabe() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        Aufgabe aufgabe = aufgabe("projektsteuern", modul("modul1"));
        indexWriter
                .addDocumentFromModelElement(aufgabe,
                        "Der Entscheid zur Projektfreigabe schafft die Voraussetzung für die Arbeiten in der Phase Konzept.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Projektfreigabe", "identifier", "de");
        assertEquals(1, search.size());
        assertEquals(aufgabe.getName(), search.get(0).getName());
    }

    @Test
    public void testSearchExactMatch() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        Aufgabe aufgabe = aufgabe("projektsteuern", modul("modul1"));
        indexWriter.addDocumentFromModelElement(aufgabe,
                "Der Entscheid zur Projektfreigabe schafft die Voraussetzung für die Arbeiten in der Phase Konzept.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("\"Phase Konzept\"", "identifier", "de");
        assertEquals(1, search.size());
        assertEquals(aufgabe.getName(), search.get(0).getName());
    }

    @Test
    public void testSearchExactMatchEmpty() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        Aufgabe aufgabe = aufgabe("projektsteuern", modul("modul1"));
        indexWriter.addDocumentFromModelElement(aufgabe,
                "Der Entscheid zur Projektfreigabe schafft die Voraussetzung für die Arbeiten in der Phase Konzept.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("\"\"", "identifier", "de");
        assertEquals(0, search.size());
    }

    @Test
    public void testSearchEmpty() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        Aufgabe aufgabe = aufgabe("projektsteuern", modul("modul1"));
        indexWriter.addDocumentFromModelElement(aufgabe,
                "Der Entscheid zur Projektfreigabe schafft die Voraussetzung für die Arbeiten in der Phase Konzept.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("", "identifier", "de");
        assertEquals(0, search.size());
    }

    @Test
    public void testSearchPrefix() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        indexWriter
                .addDocumentFromModelElement(rolle("projektleiter"),
                        "Der Projektleiter führt das Projekt im Auftrag des Auftraggebers. Er wird vom Auftraggeber ernannt und geführt.");
        Aufgabe aufgabe = aufgabe("projektsteuern", modul("modul1"));
        indexWriter
                .addDocumentFromModelElement(aufgabe,
                        "Die Steuerung des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Steuer", "identifier", "de");
        assertEquals(1, search.size());
        assertEquals(aufgabe.getName(), search.get(0).getName());
        assertEquals(
                "Die <B>Steuerung</B> des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.",
                search.get(0).getContent());
    }

    @Test
    public void testSearchTrimComma() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        indexWriter
                .addDocumentFromModelElement(aufgabe("projektsteuern", modul("modul1")),
                        ", Die Steuerung des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Steuerung", "identifier", "de");
        assertEquals(
                "Die <B>Steuerung</B> des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.",
                search.get(0).getContent());
    }
    @Test
    public void testSearchTrimDot() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        indexWriter
        .addDocumentFromModelElement(aufgabe("projektsteuern", modul("modul1")),
                ". Die Steuerung des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Steuerung", "identifier", "de");
        assertEquals(
                "Die <B>Steuerung</B> des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.",
                search.get(0).getContent());
    }
    @Test
    public void testSearchTrimWhitespace() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        indexWriter
        .addDocumentFromModelElement(aufgabe("projektsteuern", modul("modul1")),
                "       Die Steuerung des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Steuerung", "identifier", "de");
        assertEquals(
                "Die <B>Steuerung</B> des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.",
                search.get(0).getContent());
    }
    @Test
    public void testSearchTrimNewline() {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        indexWriter
        .addDocumentFromModelElement(aufgabe("projektsteuern", modul("modul1")),
                "   \n    Die Steuerung des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.");
        searchEngine.finishIndexing(indexWriter);
        List<SearchResult> search = searchEngine.search("Steuerung", "identifier", "de");
        assertEquals(
                "Die <B>Steuerung</B> des Projekts sowie das Treffen von Entscheidungen bilden eine Voraussetzung für den Projekterfolg.",
                search.get(0).getContent());
    }

    @Test
    public void testSearchWithoutIndexRestoresFromS3() {
        try {
            searchEngine.search("Steuerung", "identifier", "de");
        } finally {
            verify(searchEngine.searchIndexManager).restoreIndexFilesFromS3("identifier", "de");
        }
    }

    @Test
    public void testStartIndexingCleansUpOldIndexfile() throws Exception {
        IndexWriterWrapper indexWriter = searchEngine.startIndexing("identifier",
                LocalizationEngineBuilder.getLocalizationEngineDe());
        verify(searchEngine.searchIndexManager).cleanOldIndices("identifier", "de");
        searchEngine.finishIndexing(indexWriter);
    }

    @Test
    public void testFinishIndexingStoresIndicesToS3() throws Exception {
        LocalizationEngine localizationEngineDe = LocalizationEngineBuilder.getLocalizationEngineDe();
        String modelIdentifier = localizationEngineDe.getModelIdentifier();
        when(searchEngine.searchIndexManager.getSearchIndexPath(modelIdentifier, "de")).thenReturn(TARGET_TMP_SEARCH);
        IndexWriterWrapper indexWriter = searchEngine.startIndexing(modelIdentifier, localizationEngineDe);
        searchEngine.finishIndexing(indexWriter);
        verify(searchEngine.searchIndexManager).storeSearchIndexInS3(modelIdentifier, "de");
    }

}
