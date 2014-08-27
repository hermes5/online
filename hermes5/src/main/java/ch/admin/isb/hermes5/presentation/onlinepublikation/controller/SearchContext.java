/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.onlinepublikation.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import ch.admin.isb.hermes5.business.search.SearchResult;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@SessionScoped
public class SearchContext implements Serializable {

    @Inject
    @SystemProperty(value = "search_results_per_page", fallback = "20")
    ConfigurationProperty numberOfRecords;

    private static final long serialVersionUID = 1L;

    private String searchString;

    private SearchPaginator searchPaginator;

    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchPaginator = new SearchPaginator(searchResults, numberOfRecords.getIntegerValue());
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public SearchPaginator getSearchPaginator() {
        return searchPaginator != null ? searchPaginator : new SearchPaginator(new ArrayList<SearchResult>(),
                numberOfRecords.getIntegerValue());
    }

}
