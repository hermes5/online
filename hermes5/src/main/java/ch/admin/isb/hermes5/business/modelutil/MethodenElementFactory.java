/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelutil;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.RollenGruppe;
import ch.admin.isb.hermes5.epf.uma.schema.ContentDescription;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.RoleSet;
import ch.admin.isb.hermes5.epf.uma.schema.Task;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProduct;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@ApplicationScoped
public class MethodenElementFactory {

    @Inject
    @SystemProperty(value="ergebnis_required_key", fallback="required")
    ConfigurationProperty ergebnisRequiredKey;
    
    public Ergebnis getOrCreateErgebnis(List<Ergebnis> ergebnisse, WorkProduct workprodukt, List<Rolle> rollen,
            Map<String, MethodElement> index) {
        for (Ergebnis e : ergebnisse) {
            if(workprodukt == null) {
                throw new IllegalArgumentException("workprodukt is null");
            }
            if(workprodukt.getId()== null) {
                throw new IllegalArgumentException("workprodukt id null "+workprodukt.getName());
            }
            if (workprodukt.getId().equals(e.getId())) {
                return e;
            }
        }
        boolean required = isRequired(workprodukt);
        Ergebnis created = new Ergebnis(workprodukt, index, required );
        for (Rolle rolle : rollen) {
            // Verantwortlich fuer Ergebnis
            if (rolle.getRole().getResponsibleFor().contains(created.getId())) {
                created.addVerantwortlicheRolle(rolle);
            }
        }
        ergebnisse.add(created);
        return created;
    }

    private boolean isRequired(WorkProduct workprodukt) {
        ContentDescription presentation = workprodukt.getPresentation();
        return presentation != null && presentation.getKeyConsiderations() != null
                && presentation.getKeyConsiderations().contains("required");
    }

    public Aufgabe getOrCreateAufgabe(List<Aufgabe> aufgaben, Task task, Map<String, MethodElement> index) {
        for (Aufgabe aufgabe : aufgaben) {
            if (task != null && task.getId() != null && task.getId().equals(aufgabe.getId())) {
                return aufgabe;
            }
        }
        Aufgabe created = new Aufgabe(task, index);
        aufgaben.add(created);
        return created;
    }

    public Rolle getOrCloneRolle(List<Rolle> rollen, Rolle rolleOriginal) {
        for (Rolle rolle : rollen) {
            if (rolle.getId() != null && rolle.getId().equals(rolleOriginal.getId())) {
                return rolle;
            }
        }
        Rolle newRolle = new Rolle(rolleOriginal.getRole(), rolleOriginal.getMethodLibraryIndex());
        
        for (RollenGruppe rollenGruppe : rolleOriginal.getRollengruppen()) {
            newRolle.addRollenGruppe(new RollenGruppe((RoleSet) rollenGruppe.getElement(), rollenGruppe.getMethodLibraryIndex()));
        }
        
        rollen.add(newRolle);
        return newRolle;
    }

    public Modul getOrCloneModul(List<Modul> module, Modul modulOriginal) {
        for (Modul modul : module) {
            if (modul.getId().equals(modulOriginal.getId())) {
                return modul;
            }
        }
        Modul newModul = new Modul(modulOriginal.getDiscipline(), modulOriginal.getMethodLibraryIndex());
        module.add(newModul);
        return newModul;
    }

    /**
     * Roles are ignored because it is only used on szenario stripping
     */
    public Aufgabe getOrCloneAufgabe(List<Aufgabe> aufgaben, Aufgabe aufgabeOriginal) {
        for (Aufgabe aufgabe : aufgaben) {
            if (aufgabe.getId().equals(aufgabeOriginal.getId())) {
                return aufgabe;
            }
        }
        Aufgabe newAufgabe = new Aufgabe((Task) aufgabeOriginal.getElement(), aufgabeOriginal.getMethodLibraryIndex());
        aufgaben.add(newAufgabe);
        return newAufgabe;
    }

    public Ergebnis getOrCloneErgebnis(List<Ergebnis> ergebnisse, Ergebnis ergebnisseOriginal) {
        for (Ergebnis ergebnis : ergebnisse) {
            if (ergebnis.getId().equals(ergebnisseOriginal.getId())) {
                return ergebnis;
            }
        }
        Ergebnis newErgebnis = new Ergebnis((WorkProduct) ergebnisseOriginal.getElement(),
                ergebnisseOriginal.getMethodLibraryIndex(), ergebnisseOriginal.isRequired());
        ergebnisse.add(newErgebnis);
        return newErgebnis;
    }

}
