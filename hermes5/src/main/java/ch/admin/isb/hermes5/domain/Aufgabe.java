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

import ch.admin.isb.hermes5.epf.uma.schema.Checklist;
import ch.admin.isb.hermes5.epf.uma.schema.DescribableElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.Task;
import ch.admin.isb.hermes5.util.JAXBUtils;
import ch.admin.isb.hermes5.util.StringUtil;

public class Aufgabe extends AbstractMethodenElement {

    private List<Ergebnis> ergebnisse;

    private List<Modul> module;

    private Task task;

    private Rolle verantwortlicheRolle;

    public Aufgabe(Task task, Map<String, MethodElement> index) {
        super(index);
        this.task = task;
        ergebnisse = new ArrayList<Ergebnis>();
        module = new ArrayList<Modul>();
    }

    public boolean isHidden() {
        return false;
    }

    public void addModule(List<Modul> list) {
        for (Modul modul : list) {
            addModul(modul);
        }
    }

    public void addModul(Modul modul) {
        modul.addAufgabe(this);
        if (!getModule().contains(modul)) {
            getModule().add(modul);
        }
    }

    public boolean isMilestone() {
        return task.getName().startsWith("milestone_");
    }

    public Localizable getChecklist() {
        List<String> elementIds = JAXBUtils.getValuesWithName(task.getChecklistOrConceptOrExample(), "Checklist");
        if (!elementIds.isEmpty()) {
            Checklist checklist = (Checklist) resolveMethodLibraryReference(elementIds.get(0));
            DefaultLocalizable defLoc = new DefaultLocalizable(checklist.getPresentation(), "mainDescription");
            return defLoc;
        }
        return null;
    }

    public List<Ergebnis> getErgebnisse() {
        return ergebnisse;
    }

    public List<Modul> getModule() {
        return module;
    }

    public Rolle getVerantwortlicheRolle() {
        return verantwortlicheRolle;
    }

    public void setVerantwortlicheRolle(Rolle verantwortlicheRolle) {
        this.verantwortlicheRolle = verantwortlicheRolle;
        if (!verantwortlicheRolle.getVerantwortlichFuerAufgabe().contains(this)) {
            verantwortlicheRolle.addVerantwortlichFuerAufgabe(this);
        }
    }

    public String getName() {
        return StringUtil.replaceSpecialChars("aufgabe_" + task.getName());
    }

    public List<RelationshipTableRecord> getRelationshipTableRecords() {
        List<RelationshipTableRecord> result = new ArrayList<RelationshipTableRecord>();
        for (Modul modul : getModule()) {
            for (Ergebnis ergebnis : getErgebnisse()) {
                addIfNotContained(result, new RelationshipTableRecord(modul, this, this.getVerantwortlicheRolle(),
                        ergebnis, ergebnis.getVerantwortlicheRollen()));
            }
        }
        return result;
    }

    public Localizable getPurpose() {
        if (task.getPresentation() == null) {
            return null;
        }
        return new DefaultLocalizable(task.getPresentation(), "purpose");
    }

    public Localizable getKeyConsiderations() {
        if (task.getPresentation() == null) {
            return null;
        }
        return new DefaultLocalizable(task.getPresentation(), "keyConsiderations");
    }

    public Localizable getAlternatives() {
        if (task.getPresentation() == null) {
            return null;
        }
        return new DefaultLocalizable(task.getPresentation(), "alternatives");
    }

    @Override
    public DescribableElement getElement() {
        return task;
    }

    public void addErgebnis(Ergebnis ergebnis) {
        if (!getErgebnisse().contains(ergebnis)) {
            getErgebnisse().add(ergebnis);
        }
        if (!ergebnis.getAufgaben().contains(this)) {
            ergebnis.getAufgaben().add(this);
        }

    }

}
