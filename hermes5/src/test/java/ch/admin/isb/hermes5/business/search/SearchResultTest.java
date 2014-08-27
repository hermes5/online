/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import static org.junit.Assert.*;

import org.junit.Test;


public class SearchResultTest {

    @Test
    public void testPageUrl() {
        SearchResult searchResult = new SearchResult("presentationName", "name", "content", SearchResultType.PAGE);
        assertEquals("index.xhtml?element=name.html", searchResult.getUrl());
    }
    @Test
    public void testPageDocumentWORD() {
        SearchResult searchResult = new SearchResult("presentationName", "name", "content", SearchResultType.WORD);
        assertEquals("content/name", searchResult.getUrl());
    }
    @Test
    public void testPageDocumentExcel() {
        SearchResult searchResult = new SearchResult("presentationName", "name", "content", SearchResultType.EXCEL);
        assertEquals("content/name", searchResult.getUrl());
    }
    @Test
    public void testPageDocumentPowerpoint() {
        SearchResult searchResult = new SearchResult("presentationName", "name", "content", SearchResultType.POWERPOINT);
        assertEquals("content/name", searchResult.getUrl());
    }

}
