/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import java.util.Comparator;
import java.util.List;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.RelationshipTableRecord;

public class RelationshipTableRecordComparator implements Comparator<RelationshipTableRecord> {

    private final LocalizationEngine localizationEngine;

    public RelationshipTableRecordComparator(LocalizationEngine localizationEngine) {
        this.localizationEngine = localizationEngine;
    }

    @Override
    public int compare(RelationshipTableRecord o1, RelationshipTableRecord o2) {
        int modulCompare = singleCompare(o1.getModul(), o2.getModul());
        if (modulCompare != 0) {
            return modulCompare;
        }
        int aufgabeCompare = singleCompare(o1.getAufgabe(), o2.getAufgabe());
        if (aufgabeCompare != 0) {
            return aufgabeCompare;
        }
        int aufgabeRolleCompare = singleCompare(o1.getVerantwortlichFuerAufgabe(), o2.getVerantwortlichFuerAufgabe());
        if (aufgabeRolleCompare != 0) {
            return aufgabeRolleCompare;
        }
        int ergebnisCompare = singleCompare(o1.getErgebnis(), o2.getErgebnis());
        if (ergebnisCompare != 0) {
            return ergebnisCompare;
        }
        return compareList(o1.getVerantwortlichFuerErgebnis(), o2.getVerantwortlichFuerErgebnis());
    }

    private int singleCompare(AbstractMethodenElement o1, AbstractMethodenElement o2) {
        if(o1 == null && o2 == null) {
            return 0;
        }
        if(o1 != null && o2 == null) {
            return 1;
        }
        if(o1 == null && o2 != null) {
            return -1;
        }
        return localizationEngine.localize(o1.getPresentationName()).compareTo(
                localizationEngine.localize(o2.getPresentationName()));
    }
    
    private int compareList(List<? extends AbstractMethodenElement> o1, List<? extends AbstractMethodenElement> o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }

        return toString(o1).compareTo(toString(o2));
        
    }

    private String toString(List<? extends AbstractMethodenElement> list) {
        StringBuilder sb = new StringBuilder();
        for (AbstractMethodenElement abstractMethodenElement : list) {
            sb.append(localizationEngine.localize(abstractMethodenElement.getPresentationName())).append(" ");
        }
        return sb .toString();
    }
}
