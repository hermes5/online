/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import ch.admin.isb.hermes5.business.modelutil.MethodenElementFactory;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.epf.uma.schema.Discipline;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.Task;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProduct;
import ch.admin.isb.hermes5.util.JAXBUtils;

public class ModulAssembler implements Serializable {

    private static final Logger logger = Logger.getLogger(ModulAssembler.class);

    private static final long serialVersionUID = 1l;

    @Inject
    MethodenElementFactory methodenElementFactory;

    private Modul assembleModul(Map<String, MethodElement> index, Discipline discipline, List<Rolle> rollen,
            List<Aufgabe> aufgaben, List<Ergebnis> ergebnisse) {
        Modul e = new Modul(discipline, index);
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<JAXBElement<String>> taskOrSubDisciplineOrReferenceWorkflow = (List) discipline
                .getTaskOrSubDisciplineOrReferenceWorkflow();
        List<String> taskRefs = JAXBUtils.getValuesWithName(taskOrSubDisciplineOrReferenceWorkflow, "Task");
        for (String taskRef : taskRefs) {
            Task task = (Task) index.get(taskRef);
            if (task == null) {
                logger.error("Unable to find task with id " + taskRef + "while assembling modul "
                        + (discipline != null ? discipline.getId() : null) + ". This task will be ignored");
            } else {
                Aufgabe aufgabe = methodenElementFactory.getOrCreateAufgabe(aufgaben, task, index);
                for (String roleRef : task.getPerformedBy()) {
                    Role role = (Role) index.get(roleRef);
                    Rolle rolle = getRolle(rollen, role.getId());
                    if (rolle != null) {
                        aufgabe.setVerantwortlicheRolle(rolle);
                    }
                }
                for (String outputRef : JAXBUtils.getValuesWithName(
                        task.getMandatoryInputOrOutputOrAdditionallyPerformedBy(), "Output")) {
                    MethodElement methodElement = index.get(outputRef);
                    if (methodElement instanceof WorkProduct) {
                        WorkProduct workproduct = (WorkProduct) methodElement;
                        Ergebnis ergebnis = methodenElementFactory.getOrCreateErgebnis(ergebnisse, workproduct, rollen,
                                index);
                        aufgabe.addErgebnis(ergebnis);
                    }
                }
                aufgabe.addModul(e); // adds a two-way relationship
            }
        }
        return e;
    }

    private Rolle getRolle(List<Rolle> rollen, String roleId) {
        for (Rolle rolle : rollen) {
            if (rolle.getId().equals(roleId)) {
                return rolle;
            }
        }
        return null;
    }

    public List<Modul> assembleModules(Map<String, MethodElement> index, List<Discipline> disciplines,
            List<Rolle> rollen) {
        List<Modul> result = new ArrayList<Modul>();
        List<Aufgabe> aufgaben = new ArrayList<Aufgabe>();
        List<Ergebnis> ergebnisse = new ArrayList<Ergebnis>();
        for (Discipline discipline : disciplines) {
            result.add(assembleModul(index, discipline, rollen, aufgaben, ergebnisse));
        }

        return result;
    }

}
