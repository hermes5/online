/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import java.text.Collator;
import java.util.Locale;

public abstract class AbstractCustomElementModulBasedTabelleRow {

    private final ModulCellElement modul;

    private String rowClass;

    private final Collator collator;

    public AbstractCustomElementModulBasedTabelleRow(ModulCellElement modul, String language) {
        this.modul = modul;
        this.collator = Collator.getInstance(new Locale(language));
    }

    public Collator getCollator() {
        return collator;
    }

    public String getRowClass() {
        return rowClass;
    }

    public void setRowClass(String rowClass) {
        this.rowClass = rowClass;
    }

    public static class ModulCellElement implements Comparable<ModulCellElement> {

        private final String modul;
        private final String modulLink;
        private boolean cleared = false;

        public boolean isCleared() {
            return cleared;
        }

        public ModulCellElement(String modul, String modulLink) {
            this.modul = modul;
            this.modulLink = modulLink;
        }

        public String getModul() {
            return modul != null ? modul : "";
        }

        public String getModulLink() {
            return modulLink;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((modul == null) ? 0 : modul.hashCode());
            result = prime * result + ((modulLink == null) ? 0 : modulLink.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ModulCellElement other = (ModulCellElement) obj;
            if (modul == null) {
                if (other.modul != null)
                    return false;
            } else if (!modul.equals(other.modul))
                return false;
            if (modulLink == null) {
                if (other.modulLink != null)
                    return false;
            } else if (!modulLink.equals(other.modulLink))
                return false;
            return true;
        }

        @Override
        public int compareTo(ModulCellElement o) {
            return getModul().compareTo(o.getModul());
        }

        public void setCleared() {
            cleared = true;
        }

    }

    public String getModulStringToCompare() {
        return modul.getModul();
    }

    public ModulCellElement getModul() {
        return modul;
    }

    public void clearModul() {
        this.modul.setCleared();
    }

    public int compareTo(AbstractCustomElementModulBasedTabelleRow o2) {
        return getCollator().compare(this.getModulStringToCompare(), o2.getModulStringToCompare());
    }
}
