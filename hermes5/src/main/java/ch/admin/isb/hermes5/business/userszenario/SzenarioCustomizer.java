/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario;

import java.util.List;

import ch.admin.isb.hermes5.domain.CustomAufgabe;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.CustomModul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Szenario;

public class SzenarioCustomizer {

    public void addCustomModule(Szenario szenario, String customModulName, List<CustomErgebnis> customErgebnisse) {
        CustomModul modul = new CustomModul(customModulName);
        for (CustomErgebnis customErgebnis : customErgebnisse) {
            CustomAufgabe customAufgabe = new CustomAufgabe(customErgebnis.getCustomName());
            customAufgabe.addErgebnis(customErgebnis);
            customAufgabe.addModul(modul);
            for (Phase phase : szenario.getPhasen()) {
                if (customErgebnis.getPhasenIds().contains(phase.getId())) {
                    phase.addAufgabe(customAufgabe);
                }
            }
        }
    }
    
    

}