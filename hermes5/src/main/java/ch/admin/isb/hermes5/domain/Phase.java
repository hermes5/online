/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.admin.isb.hermes5.epf.uma.schema.DescribableElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.util.StringUtil;

public class Phase extends AbstractMethodenElement {

    private ch.admin.isb.hermes5.epf.uma.schema.Phase phase;
    private List<Aufgabe> aufgaben;

    public Phase(ch.admin.isb.hermes5.epf.uma.schema.Phase phase, Map<String, MethodElement> index) {
        super(index);
        this.phase = phase;
        aufgaben = new ArrayList<Aufgabe>();
    }

    public List<Aufgabe> getAufgaben() {
        return aufgaben;
    }

    public List<Modul> getModule() {
        List<Modul> result = new ArrayList<Modul>();
        for (Aufgabe aufgabe : aufgaben) {
            for (Modul modul : aufgabe.getModule()) {
                if (!result.contains(modul)) {
                    result.add(modul);
                }
            }
        }
        return result;
    }

    public String getName() {
        return "phase_" + StringUtil.replaceSpecialChars(phase.getName());
    }

    @Override
    public DescribableElement getElement() {
        return phase;
    }

    public void addAufgabe(Aufgabe aufgabe) {
        getAufgaben().add(aufgabe);
    }

}
