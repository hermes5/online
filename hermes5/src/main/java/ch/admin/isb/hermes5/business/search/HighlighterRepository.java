/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import javax.inject.Inject;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;


public class HighlighterRepository {
    
    @Inject
    @SystemProperty(value = "search.numberoffragments", fallback = "3")
    ConfigurationProperty numberOfFragments;
    @Inject
    @SystemProperty(value = "search.trimstrings", fallback = ".,!:;)")
    ConfigurationProperty trimstrings;

    public HighlighterWrapper getHighlighter(Analyzer analyzer, IndexSearcher isearcher, Query query) {
        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter();
        Highlighter highlighter = new Highlighter(htmlFormatter, new QueryScorer(query));
        return new HighlighterWrapper(highlighter, numberOfFragments.getIntegerValue(), isearcher, analyzer, trimstringsList());
    }

    private String[] trimstringsList() {
       String stringValue = trimstrings.getStringValue();
       return stringValue.split("");
    }


}
