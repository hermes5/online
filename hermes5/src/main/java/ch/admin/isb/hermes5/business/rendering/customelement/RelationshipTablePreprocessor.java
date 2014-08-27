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
import java.util.Collections;
import java.util.List;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.RelationshipTableRecord;

public class RelationshipTablePreprocessor {

    public List<RelationshipTableRecord> preprocess(List<RelationshipTableRecord> list,
            LocalizationEngine localizationEngine) {
        ArrayList<RelationshipTableRecord> result = new ArrayList<RelationshipTableRecord>(list);
        Collections.sort(result, new RelationshipTableRecordComparator(localizationEngine));
        return setOddAndEvenAndRenderFirstThreeColumns(result);

    }

    private List<RelationshipTableRecord> setOddAndEvenAndRenderFirstThreeColumns(List<RelationshipTableRecord> result) {
        for (int i = 0; i < result.size(); i++) {
            RelationshipTableRecord currentRow = result.get(i);
            if (i == 0) {
                currentRow.setEvenRow(true);
                currentRow.setRenderModulAndAufgabe(true);
            } else {
                RelationshipTableRecord lastRow = result.get(i - 1);
                if (equalModulAufgabeAndVerantwortlichFuerAufgabe(currentRow, lastRow)) {
                    currentRow.setRenderModulAndAufgabe(false);
                    currentRow.setEvenRow(lastRow.isEvenRow());
                } else {
                    currentRow.setRenderModulAndAufgabe(true);
                    currentRow.setEvenRow(!lastRow.isEvenRow());
                }
            }
        }
        return result;
    }

    private boolean equalModulAufgabeAndVerantwortlichFuerAufgabe(RelationshipTableRecord current,
            RelationshipTableRecord last) {
        return equalId(current.getModul(), last.getModul()) && equalId(current.getAufgabe(), last.getAufgabe())
                && equalId(current.getVerantwortlichFuerAufgabe(), last.getVerantwortlichFuerAufgabe());
    }

    private boolean equalId(AbstractMethodenElement me1, AbstractMethodenElement me2) {
        return me1 != null && me2 != null && me1.getId() != null && me1.getId().equals(me2.getId());
    }
}
