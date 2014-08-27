/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.TokenSources;

public class HighlighterWrapper {

    private final Highlighter highlighter;
    private final Integer numberOfFragments;
    private final IndexSearcher isearcher;
    private final Analyzer analyzer;
    private final List<String> trimStart;

    public HighlighterWrapper(Highlighter highlighter, Integer numberOfFragments, IndexSearcher isearcher,
            Analyzer analyzer, String[] strings) {
        this.highlighter = highlighter;
        this.numberOfFragments = numberOfFragments;
        this.isearcher = isearcher;
        this.analyzer = analyzer;
        this.trimStart = Arrays.asList(strings);

    }

    public String getHighlightedText(int id, String contentField) throws IOException {
        try {
            Document hitDoc = isearcher.doc(id);
            String text = hitDoc.get(contentField);

            TokenStream tokenStream = TokenSources.getAnyTokenStream(isearcher.getIndexReader(), id, contentField,
                    analyzer);
            String fragment = highlighter.getBestFragments(tokenStream, text, numberOfFragments, "...");
            return trimFragment(fragment);
        } catch (InvalidTokenOffsetsException e) {
            throw new RuntimeException(e);
        }
    }

    private String trimFragment(String fragment) {
        String trimmedString = fragment.trim();
        // nbsp (unicode 160) are not handled correctly by trim
        if (!trimmedString.isEmpty() && (trimStart.contains(String.valueOf(trimmedString.charAt(0))) || 160 == (int) trimmedString.charAt(0))) {
            return trimFragment(trimmedString.substring(1));
        }
        return trimmedString;
    }

}
