/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.inject.Inject;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;

public class SearchEngine {

    @Inject
    AnalyserRepository analyserRepository;
    @Inject
    HighlighterRepository highlighterRepository;

    @Inject
    SearchIndexManager searchIndexManager;

    private final static Logger searchLogger = LoggerFactory.getLogger("SearchLog");
    private static final Logger logger = LoggerFactory.getLogger(SearchEngine.class);

    public List<SearchResult> search(String searchInput, String modelIdentifier, String lang) {
        DirectoryReader directoryReader = null;

        try {
            List<SearchResult> results = new ArrayList<SearchResult>();
            String queryString = buildQueryString(searchInput);
            if (queryString != null) {

                String searchIndexPath = searchIndexManager.getSearchIndexPath(modelIdentifier, lang);
                try {
                    directoryReader = DirectoryReader.open(FSDirectory.open(new File(searchIndexPath)));
                } catch (Exception e) {
                    logger.warn("No index files found at " + searchIndexPath + ". Will try to restore from S3");
                    searchIndexManager.restoreIndexFilesFromS3(modelIdentifier, lang);
                    directoryReader = DirectoryReader.open(FSDirectory.open(new File(searchIndexPath)));
                }
                Analyzer analyzer = analyserRepository.getAnalyzer(lang);
                IndexSearcher isearcher = new IndexSearcher(directoryReader);

                Query query = new QueryParser(Version.LUCENE_47, "presentationName", analyzer).parse(queryString);
                ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
                HighlighterWrapper highlighter = highlighterRepository.getHighlighter(analyzer, isearcher, query);
                for (int i = 0; i < hits.length; i++) {
                    results.add(buildSearchResult(isearcher, highlighter, hits[i].doc));
                }
            }

            searchLogger.info("<{}> returned {} results", searchInput, results.size());

            return results;
        } catch (Exception e) {
            logger.warn("An exception occurred during search, empty result will be returned", e);
            return new ArrayList<SearchResult>();
        } finally {
            try {
                if (directoryReader != null) {
                    directoryReader.close();
                }
            } catch (IOException e) {
                logger.debug("unable to close directory reader", e);
            }
        }
    }

    private String buildQueryString(String searchInput) {
        String searchInputTrimmed = prepare(searchInput);
        String queryString = "";
        if (searchInputTrimmed.isEmpty()) {
            return null;
        }
        if (searchInput.startsWith("\"") && searchInput.endsWith("\"")) {
            // Exact search -> Do not append star
            if (searchInput.length() == 2) {
                return null;
            }
            queryString = "(presentationName: " + searchInput + ")^1.5 (content: " + searchInput + ")";

        } else {
            String searchInputStared = searchInputTrimmed + "*";
            queryString = "(presentationName: " + searchInputStared + ")^1.5 (content: " + searchInputStared + ")"
                    + "(presentationName: " + searchInputTrimmed + ")^1.5 (content: " + searchInputTrimmed + ")";
        }
        return queryString;
    }

    private SearchResult buildSearchResult(IndexSearcher isearcher, HighlighterWrapper highlighter, int id)
            throws IOException, InvalidTokenOffsetsException {
        Document hitDoc = isearcher.doc(id);
        String presentationName = hitDoc.getField("presentationName").stringValue();
        SearchResultType type = SearchResultType.valueOf(hitDoc.getField("type").stringValue());
        String name = hitDoc.getField("name").stringValue();
        String fragment = highlighter.getHighlightedText(id, "content");
        return new SearchResult(presentationName, name, fragment, type);
    }

    private String prepare(String searchInput) {
        String trim = searchInput.trim();
        if (trim.startsWith("*") || trim.startsWith("?")) {
            return prepare(trim.substring(1));
        }
        if (trim.endsWith("*") || trim.endsWith("?")) {
            return prepare(trim.substring(0, trim.length() - 1));
        }
        return trim;
    }

    public IndexWriterWrapper startIndexing(String modelIdentifier, LocalizationEngine localizationEngine) {
        try {
            String language = localizationEngine.getLanguage();
            Analyzer analyzer = analyserRepository.getAnalyzer(language);
            String path = searchIndexManager.getSearchIndexPath(modelIdentifier, language);

            searchIndexManager.cleanOldIndices(modelIdentifier, language);

            Directory directory = FSDirectory.open(new File(path));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);
            IndexWriter iwriter = new IndexWriter(directory, config);
            return new IndexWriterWrapper(iwriter, localizationEngine);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Asynchronous
    public Future<Void> finishIndexing(IndexWriterWrapper iwriter) {
        iwriter.close();
        String modelIdentifier = iwriter.getLocalizationEngine().getModelIdentifier();
        String language = iwriter.getLocalizationEngine().getLanguage();
        searchIndexManager.storeSearchIndexInS3(modelIdentifier, language);
        return new AsyncResult<Void>(null);
    }

}
