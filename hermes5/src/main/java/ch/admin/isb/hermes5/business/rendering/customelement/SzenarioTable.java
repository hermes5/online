/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;

public class SzenarioTable {

    private static final String HEADER_ROW = "header-row";
    private static final String BODY_ROW_EVEN = "body-row even";
    private static final String BODY_ROW_ODD = "body-row odd";
    private static final String CELL_PHASE = "cell-phase";
    private static final String CELL_ELEMENTS = "cell-elements";
    private static final String CELL_ELEMENTS_LAST = "cell-elements last";
    private static final String EMPTY_CELL = "cell-empty";
    private static final String CELL_MODUL = "cell-modul";

    public static class SzenarioCell {

        private final LinkedHashSet<AbstractMethodenElement> elements = new LinkedHashSet<AbstractMethodenElement>();
        private boolean emptyCell = false;
        private boolean isLast = false;

        public SzenarioCell() {
        }

        public SzenarioCell markEmptyCell() {
            emptyCell = true;
            return this;
        }

        public SzenarioCell(AbstractMethodenElement element) {
            this();
            getElements().add(element);
        }

        public boolean isElementCell() {
            return (CELL_ELEMENTS.equals(getStyleClass()) || CELL_ELEMENTS_LAST.equals(getStyleClass()));
        }

        public Collection<AbstractMethodenElement> getElements() {
            return elements;
        }

        public String getStyleClass() {
            if (emptyCell) {
                return EMPTY_CELL;
            }
            if (getElements().size() == 1 && getElements().iterator().next() instanceof Phase) {
                return CELL_PHASE;
            }
            if (getElements().size() == 1 && getElements().iterator().next() instanceof Modul) {
                return CELL_MODUL;
            }
            if (isLast) {
                return CELL_ELEMENTS_LAST;
            }
            return CELL_ELEMENTS;
        }
    }

    public static class SzenarioRow {

        private final List<SzenarioCell> cells = new ArrayList<SzenarioTable.SzenarioCell>();
        private String styleClass = BODY_ROW_ODD;

        public List<SzenarioCell> getCells() {
            return cells;
        }

        public String getStyleClass() {
            return styleClass;
        }

        public void setHeader() {
            styleClass = HEADER_ROW;
        }

        public boolean isHeaderRow() {
            return HEADER_ROW.equals(styleClass);
        }
    }

    private List<SzenarioRow> rows = new ArrayList<SzenarioTable.SzenarioRow>();

    public List<SzenarioRow> getRows() {
        assignStyles();
        return rows;
    }

    public void assignStyles() {
        for (int i = 0; i < rows.size(); i++) {
            if (!rows.get(i).isHeaderRow()) {
                if (i % 2 != 0 && rows.size() > 6) {
                    rows.get(i).styleClass = BODY_ROW_ODD;
                } else {
                    rows.get(i).styleClass = BODY_ROW_EVEN;
                }
            }
            List<SzenarioCell> cells = rows.get(i).getCells();
            if (cells.size() > 0 && cells.get(cells.size() - 1).isElementCell()) {
                cells.get(cells.size() - 1).isLast = true;
            }
        }
    }

    public boolean isContainsCellsInBody() {
        for (SzenarioRow row : getRows()) {
            if (!row.isHeaderRow()) {
                for (SzenarioCell szenarioCell : row.getCells()) {
                    if (szenarioCell.isElementCell()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
