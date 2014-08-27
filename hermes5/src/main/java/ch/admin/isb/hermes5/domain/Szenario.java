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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ch.admin.isb.hermes5.epf.uma.schema.DescribableElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.util.StringUtil;

public class Szenario extends AbstractMethodenElement {

    private final MethodConfiguration methodConfiguration;

    private final List<Phase> phasen = new ArrayList<Phase>();

    public Szenario(MethodConfiguration methodConfiguration) {
        super(null);
        this.methodConfiguration = methodConfiguration;
    }

    @Override
    public DescribableElement getElement() {
        return null;
    }

    @Override
    public String getName() {
        return StringUtil.replaceSpecialChars("szenario_" + getMethodConfiguration().getName());
    }

    public MethodConfiguration getMethodConfiguration() {
        return methodConfiguration;
    }

    public List<Phase> getPhasen() {
        return phasen;
    }

    public List<Modul> getModule() {
        Set<Modul> result = new LinkedHashSet<Modul>();
        for (Phase phase : getPhasen()) {
            result.addAll(phase.getModule());
        }
        return new ArrayList<Modul>(result);
    }

    public List<Rolle> getRollen() {
        Set<Rolle> result = new LinkedHashSet<Rolle>();
        for (Aufgabe aufgabe : getAufgaben()) {
            Rolle verantwortlicheRolle = aufgabe.getVerantwortlicheRolle();
            if (verantwortlicheRolle != null) {
                result.add(verantwortlicheRolle);
            }
        }
        List<Ergebnis> ergebnisse = getErgebnisse();
        for (Ergebnis ergebnis : ergebnisse) {
            result.addAll(ergebnis.getVerantwortlicheRollen());
        }
        return new ArrayList<Rolle>(result);
    }

    public List<Aufgabe> getAufgaben() {
        Set<Aufgabe> result = new LinkedHashSet<Aufgabe>();
        for (Modul modul : getModule()) {
            result.addAll(modul.getAufgaben());
        }
        return new ArrayList<Aufgabe>(result);
    }

    public List<Ergebnis> getErgebnisse() {
        Set<Ergebnis> result = new LinkedHashSet<Ergebnis>();
        for (Aufgabe aufgabe : getAufgaben()) {
            result.addAll(aufgabe.getErgebnisse());
        }
        return new ArrayList<Ergebnis>(result);
    }

    @Override
    public Localizable getPresentationName() {
        return new DefaultLocalizable(methodConfiguration, "presentationName");
    }

    @Override
    public Localizable getBriefDescription() {
        return new DefaultLocalizable(methodConfiguration, "briefDescription");
    }

    @Override
    public String getId() {
        return methodConfiguration.getId();
    }

    public boolean containsRolleWithId(String id) {
        for (Rolle rolle : getRollen()) {
            if (rolle.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public Rolle getRolleWithId(String id) {
        for (Rolle rolle : getRollen()) {
            if (rolle.getId().equals(id)) {
                return rolle;
            }
        }
        return null;
    }

}
