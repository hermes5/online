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
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.util.StringUtil;

public class Rolle extends AbstractMethodenElement {

    private Role role;

    private final List<Aufgabe> verantwortlichFuerAufgabe = new ArrayList<Aufgabe>();
    private final List<Ergebnis> verantwortlichFuerErgebnis = new ArrayList<Ergebnis>();
    private final List<Ergebnis> mitarbeitendAnErgebnis = new ArrayList<Ergebnis>();

    private final List<RollenGruppe> rollengruppen = new ArrayList<RollenGruppe>();

    public Rolle(Role role, Map<String, MethodElement> index) {
        super(index);
        this.role = role;
    }

    public List<RelationshipTableRecord> getRelationshipTableRecords() {
        List<RelationshipTableRecord> result = new ArrayList<RelationshipTableRecord>();
        for (Aufgabe aufgabe : verantwortlichFuerAufgabe) {
            for (Modul modul : aufgabe.getModule()) {
                for (Ergebnis ergebnis : aufgabe.getErgebnisse()) {
                    RelationshipTableRecord record = new RelationshipTableRecord(modul, aufgabe, this, ergebnis,
                            ergebnis.getVerantwortlicheRollen());
                    addIfNotContained(result, record);
                }
            }
        }

        for (Ergebnis ergebnis : verantwortlichFuerErgebnis) {
            for (Aufgabe aufgabe1 : ergebnis.getAufgaben()) {
                for (Modul modul : aufgabe1.getModule()) {
                    addIfNotContained(result,
                            new RelationshipTableRecord(modul, aufgabe1, aufgabe1.getVerantwortlicheRolle(), ergebnis,
                                    ergebnis.getVerantwortlicheRollen()));
                }
            }
        }
        for (Ergebnis ergebnis : mitarbeitendAnErgebnis) {

            for (Aufgabe aufgabe1 : ergebnis.getAufgaben()) {
                for (Modul modul : aufgabe1.getModule()) {
                    addIfNotContained(result,
                            new RelationshipTableRecord(modul, aufgabe1, aufgabe1.getVerantwortlicheRolle(), ergebnis,
                                    ergebnis.getVerantwortlicheRollen()));

                }
            }
        }
        return result;
    }

    public void addVerantwortlichFuerAufgabe(Aufgabe aufgabe) {
        verantwortlichFuerAufgabe.add(aufgabe);
    }

    public void addVerantwortlichFuerErgebnis(Ergebnis ergebnis) {
        verantwortlichFuerErgebnis.add(ergebnis);
    }

    public void addMitarbeitendAnErgebnis(Ergebnis ergebnis) {
        mitarbeitendAnErgebnis.add(ergebnis);
    }

    @Override
    public DescribableElement getElement() {
        return role;
    }

    public String getName() {
        return "rolle_" + StringUtil.replaceSpecialChars(role.getName());
    }

    public Role getRole() {
        return role;
    }

    public Localizable getSkills() {
        return new DefaultLocalizable(role.getPresentation(), "skills");
    }

    public Localizable getAssignementApproaches() {
        return new DefaultLocalizable(role.getPresentation(), "assignmentApproaches");
    }

    public List<Aufgabe> getVerantwortlichFuerAufgabe() {
        return verantwortlichFuerAufgabe;
    }

    public List<Ergebnis> getVerantwortlichFuerErgebnis() {
        return verantwortlichFuerErgebnis;
    }

    public List<RollenGruppe> getRollengruppen() {
        return rollengruppen;
    }

    public List<Ergebnis> getMitarbeitendAnErgebnis() {
        return mitarbeitendAnErgebnis;
    }

    public void addRollenGruppe(RollenGruppe rollenGruppe) {
        rollengruppen.add(rollenGruppe);
    }

}
