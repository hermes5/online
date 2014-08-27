/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.onlinepublikation.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.search.SearchResult;
import ch.admin.isb.hermes5.business.search.SearchResultType;
import ch.admin.isb.hermes5.business.service.OnlinePublikationFacade;
import ch.admin.isb.hermes5.presentation.anwenderloesung.controller.LocaleController;
import ch.admin.isb.hermes5.util.Hardcoded;

public class SearchControllerTest {

    private SearchController searchController;

    @Before
    public void setUp() throws Exception {
        searchController = new SearchController();
        searchController.localeController = mock(LocaleController.class);
        searchController.onlinePublikationFacade = mock(OnlinePublikationFacade.class);
        searchController.searchContext = new SearchContext();
        searchController.searchContext.numberOfRecords = Hardcoded.configuration(10);
    }

    @Test
    public void testSearchFirstPage() {
        executeSearch();
        SearchPaginator searchPaginator = searchController.getSearchPaginator();
        List<SearchResult> model = searchPaginator.getModel();
        assertEquals("presentationName0", model.get(0).getPresentationName());
        assertEquals("presentationName9", model.get(9).getPresentationName());
        assertEquals(10, model.size());
        assertEquals(10, searchPaginator.getRecords());
        assertEquals(200, searchPaginator.getRecordsTotal());
        assertEquals(1, searchPaginator.getPageIndex());
    }

    @Test
    public void testSearchPaginationNext() {
        executeSearch();
        SearchPaginator searchPaginator = searchController.getSearchPaginator();
        searchPaginator.next();
        List<SearchResult> model = searchPaginator.getModel();
        assertEquals("presentationName10", model.get(0).getPresentationName());
        assertEquals("presentationName19", model.get(9).getPresentationName());
        assertEquals(10, model.size());
        assertEquals(2, searchPaginator.getPageIndex());
    }
    
    @Test
    public void testSearchPaginationNextPrev() {
        executeSearch();
        SearchPaginator searchPaginator = searchController.getSearchPaginator();
        searchPaginator.next();
        searchPaginator.prev();
        List<SearchResult> model = searchPaginator.getModel();
        assertEquals(10, model.size());
        assertEquals(1, searchPaginator.getPageIndex());
        assertEquals("presentationName0", model.get(0).getPresentationName());
        assertEquals("presentationName9", model.get(9).getPresentationName());
    }
    
    @Test
    public void testSearchPaginationPageIndex() {
        executeSearch();
        SearchPaginator searchPaginator = searchController.getSearchPaginator();
        searchPaginator.setPageIndex(5);
        searchPaginator.updateModel();
        List<SearchResult> model = searchPaginator.getModel();
        assertEquals("presentationName40", model.get(0).getPresentationName());
        assertEquals(10, model.size());
        assertEquals("presentationName49", model.get(9).getPresentationName());
    }

    private void executeSearch() {
        List<SearchResult> searchResults = searchResults();
        searchController.setSearchString("searchInput");
        when(searchController.localeController.getLanguage()).thenReturn("de");
        when(searchController.onlinePublikationFacade.search("searchInput", "de")).thenReturn(searchResults);
        searchController.search();
    }

    private List<SearchResult> searchResults() {
        List<SearchResult> arrayList = new ArrayList<SearchResult>();
        for (int i = 0; i < 200; i++) {
            arrayList.add(new SearchResult("presentationName" + i, "name" + i, "content" + i,
                    SearchResultType.values()[i % SearchResultType.values().length]));
        }
        return arrayList;
    }

}
