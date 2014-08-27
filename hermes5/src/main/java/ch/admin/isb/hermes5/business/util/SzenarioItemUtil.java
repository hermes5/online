/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.util;

import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.SzenarioItem;

public class SzenarioItemUtil {

    public boolean isErgebnisSelected(SzenarioItem item, Phase phase, Modul modul, Aufgabe aufgabe, Ergebnis ergebnis) {
        for (SzenarioItem phaseItem : item.getChildren()) {
            if (phaseItem.getId().equals(phase.getId()) && phaseItem.isSelected()) {
                for (SzenarioItem modulItem : phaseItem.getChildren()) {
                    if (modulItem.getId().equals(modul.getId()) && modulItem.isSelected()) {
                        for (SzenarioItem aufgabeItem : modulItem.getChildren()) {
                            if (aufgabeItem.getId().equals(aufgabe.getId()) && aufgabeItem.isSelected()) {
                                for (SzenarioItem ergebnisItem : aufgabeItem.getChildren()) {
                                    if (ergebnisItem.getId().equals(ergebnis.getId()) && ergebnisItem.isSelected()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
