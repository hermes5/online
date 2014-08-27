/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.onlinepublikation.controller;

import java.util.ArrayList;
import java.util.List;

import ch.admin.isb.hermes5.business.search.SearchResult;

public class SearchPaginator {

    private static final int DEFAULT_PAGE_INDEX = 1;

    private int records;
    private int recordsTotal;
    private int pageIndex;
    private int pages;
    private List<SearchResult> origModel;
    private List<SearchResult> model;

    public SearchPaginator(List<SearchResult> model, int numberOfRecords) {
        this.origModel = model;
        this.records = numberOfRecords;
        this.pageIndex = DEFAULT_PAGE_INDEX;
        this.recordsTotal = model.size();

        if (records > 0) {
            pages = records <= 0 ? 1 : recordsTotal / records;

            if (recordsTotal % records > 0) {
                pages++;
            }

            if (pages == 0) {
                pages = 1;
            }
        } else {
            records = 1;
            pages = 1;
        }

        updateModel();
    }

    public void updateModel() {
        int fromIndex = getFirst();
        int toIndex = getToIndex();

        this.model = origModel.subList(fromIndex, toIndex);
    }

    public int getToIndex() {
        int toIndex = getFirst() + records;

        if (toIndex > this.recordsTotal) {
            toIndex = this.recordsTotal;
        }
        return toIndex;
    }
    public int getFromIndex() {
        return getFirst() + 1;
    }
    

    public void next() {
        if (this.pageIndex < pages) {
            this.pageIndex++;
        }

        updateModel();
    }

    public void toFirstPage() {
        setPageIndex(1);
        updateModel();
    }

    public void toLastPage() {
        setPageIndex(pages);
        updateModel();
    }

    public void toPage(int index) {
        if (index > 0 && index <= pages) {
            this.pageIndex = index;
        }
        updateModel();
    }

    public void prev() {
        if (this.pageIndex > 1) {
            this.pageIndex--;
        }

        updateModel();
    }

    public int getRecords() {
        return records;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPages() {
        return pages;
    }

    public int getFirst() {
        return (pageIndex * records) - records;
    }
    

    public List<SearchResult> getModel() {
        return model;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public List<Integer> getNavigationPages() {
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 1; i <= pages; i++) {
            result.add(i);
        }
        return result;
    }

}
