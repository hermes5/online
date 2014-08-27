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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.modelutil.MethodenElementFactory;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.DeliveryProcess;
import ch.admin.isb.hermes5.epf.uma.schema.Discipline;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.Phase;
import ch.admin.isb.hermes5.epf.uma.schema.RoleDescriptor;
import ch.admin.isb.hermes5.epf.uma.schema.Task;
import ch.admin.isb.hermes5.epf.uma.schema.TaskDescriptor;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProduct;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProductDescriptor;

public class SzenarioAssembler implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(SzenarioAssembler.class);
    private static final long serialVersionUID = 1l;
    @Inject
    MethodenElementFactory methodenElementFactory;

    public Szenario buildScenario(DeliveryProcess deliveryProcess, MethodConfiguration methodConfiguration,
            Map<String, MethodElement> index, List<Discipline> disciplines, List<Rolle> rollen) {
        Szenario szenario = new Szenario(methodConfiguration);
        List<String> subtractedModules = methodConfiguration.getSubtractedCategory();

        List<Aufgabe> aufgaben = new ArrayList<Aufgabe>();
        List<Ergebnis> ergebnisse = new ArrayList<Ergebnis>();

        List<Modul> module = new ArrayList<Modul>();
        for (Discipline discipline : disciplines) {
            if (!subtractedModules.contains(discipline.getId())) {
                module.add(new Modul(discipline, index));
            }
        }
        for (Object object : deliveryProcess.getBreakdownElementOrRoadmap()) {
            if (object instanceof Phase) {
                Phase originalPhase = (Phase) object;
                szenario.getPhasen().add(assemblePhase(originalPhase, index, module, rollen, aufgaben, ergebnisse));
            } else {
                logger.info("unexpected type " + object);
            }
        }
        return szenario;
    }

    private ch.admin.isb.hermes5.domain.Phase assemblePhase(Phase originalPhase, Map<String, MethodElement> index,
            List<Modul> module, List<Rolle> rollen, List<Aufgabe> aufgaben, List<Ergebnis> ergebnisse) {
        ch.admin.isb.hermes5.domain.Phase phase = new ch.admin.isb.hermes5.domain.Phase(originalPhase, index);

        for (Object object : originalPhase.getBreakdownElementOrRoadmap()) {
            if (object instanceof TaskDescriptor) {
                TaskDescriptor taskDescriptor = (TaskDescriptor) object;
                String task = taskDescriptor.getTask();
                Task originalTask = (Task) index.get(task);
                if (originalTask == null) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Unable to find task with id " + task + "while assembling phase "
                                + (originalPhase != null ? originalPhase.getId() : null)
                                + ". This task will be ignored");
                    }
                } else {
                    Aufgabe aufgabe = methodenElementFactory.getOrCreateAufgabe(aufgaben, originalTask, index);
                    aufgabe.addModule(findModule(task, module));
                    if (aufgabe.getModule().size() > 0) {
                        List<JAXBElement<String>> checklistOrConceptOrExample = taskDescriptor
                                .getPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy();
                        for (JAXBElement<String> jaxbElement : checklistOrConceptOrExample) {
                            if (jaxbElement.getName().getLocalPart().equals("Output")) {
                                String value = jaxbElement.getValue();
                                WorkProductDescriptor workProductDescriptor = (WorkProductDescriptor) index.get(value);
                                WorkProduct workProduct = (WorkProduct) index.get(workProductDescriptor
                                        .getWorkProduct());
                                if (workProduct == null) {
                                    if (logger.isDebugEnabled()) {
                                        logger.warn("no workproduct (" + workProductDescriptor.getWorkProduct()
                                                + ") found for descriptor with id " + value);
                                    }
                                } else {
                                    aufgabe.addErgebnis(methodenElementFactory.getOrCreateErgebnis(ergebnisse,
                                            workProduct, rollen, index));
                                }
                            } else if (jaxbElement.getName().getLocalPart().equals("PerformedPrimarilyBy")) {
                                String value = jaxbElement.getValue();
                                RoleDescriptor roleProductDescriptor = (RoleDescriptor) index.get(value);
                                aufgabe.setVerantwortlicheRolle(getRolle(rollen, roleProductDescriptor.getRole()));
                            } else {
                                logger.info("unexpected element " + jaxbElement.getName());
                            }
                        }
                        phase.addAufgabe(aufgabe);
                    }
                }
            }
        }
        return phase;
    }

    private Rolle getRolle(List<Rolle> rollen, String roleId) {
        for (Rolle rolle : rollen) {
            if (roleId.equals(rolle.getId())) {
                return rolle;
            }
        }
        return null;
    }

    private List<Modul> findModule(String task, List<Modul> module) {
        List<Modul> result = new ArrayList<Modul>();
        for (Modul modul : module) {
            List<JAXBElement<?>> taskOrSubDisciplineOrReferenceWorkflow = modul.getDiscipline()
                    .getTaskOrSubDisciplineOrReferenceWorkflow();
            for (JAXBElement<?> jaxbElement : taskOrSubDisciplineOrReferenceWorkflow) {
                if (jaxbElement.getName().getLocalPart().equals("Task")) {
                    if (task.equals(jaxbElement.getValue())) {
                        result.add(modul);
                    }
                } else {
                    logger.info("unexpected element " + jaxbElement.getName());
                }
            }
        }
        return result;
    }

}
