/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.xmlmodel;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.SzenarioItemUtil;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.model.api.ModelType;
import ch.admin.isb.hermes5.model.api.ModuleRef;
import ch.admin.isb.hermes5.model.api.Modules;
import ch.admin.isb.hermes5.model.api.Phase;
import ch.admin.isb.hermes5.model.api.Roles;
import ch.admin.isb.hermes5.model.api.Scenario;
import ch.admin.isb.hermes5.model.api.Task;
import ch.admin.isb.hermes5.model.api.Task.ResponsibleRole;
import ch.admin.isb.hermes5.model.api.TaskRef;
import ch.admin.isb.hermes5.model.api.Tasks;
import ch.admin.isb.hermes5.model.api.TemplateType;
import ch.admin.isb.hermes5.model.api.Workproduct;
import ch.admin.isb.hermes5.model.api.Workproduct.CollaborationRoles;
import ch.admin.isb.hermes5.model.api.Workproduct.Template;
import ch.admin.isb.hermes5.model.api.Workproducts;
import ch.admin.isb.hermes5.util.StringUtil;

public class XMLModelAssembler {

    
    @Inject
    XMLModelMarshaller xmlModelMarshaller;
    @Inject
    SzenarioItemUtil szenarioItemUtil;
    @Inject
    XMLModelElementFactory xmlModelElementFactory;
    
   

    public String assembleXML(AnwenderloesungRenderingContainer container, LocalizationEngine localizationEngine) {
        ModelType modelType = new ModelType();
        Szenario szenario = container.getSzenario();

        modelType.setRoles(buildRoles(localizationEngine, szenario));
        modelType.setModules(buildModules(localizationEngine, szenario));
        modelType.setTasks(buildTasks(localizationEngine, szenario));
        modelType.setWorkproducts(buildWorkProducts(localizationEngine, szenario));
        modelType.setScenario(buildSzenario(container, localizationEngine, szenario));

        String xmlString = xmlModelMarshaller.marshal(modelType);
        return xmlString;
    }

    private Scenario buildSzenario(AnwenderloesungRenderingContainer container, LocalizationEngine localizationEngine,
            Szenario szenario) {
        Scenario scenario = new Scenario();
        scenario.setName(szenario.getName());
        scenario.setPresentationName(localizationEngine.localize(szenario.getPresentationName()));
        for (ch.admin.isb.hermes5.domain.Phase phase : szenario.getPhasen()) {
            Phase xmlPhase = new Phase();
            xmlPhase.setName(phase.getName());
            xmlPhase.setPresentationName(localizationEngine.localize(phase.getPresentationName()));
            List<Aufgabe> aufgabenInPhase = phase.getAufgaben();
            for (Modul modul : phase.getModule()) {
                ModuleRef xmlModule = new ModuleRef();
                xmlModule.setId(modul.getId());
                xmlModule.setName(modul.getName());
                for (Aufgabe aufgabe : modul.getAufgaben()) {
                    if (aufgabenInPhase.contains(aufgabe)) {
                        TaskRef xmlTask = xmlModelElementFactory.buildTaskRef(localizationEngine, aufgabe);

                        SzenarioItem szenarioTree = container.getSzenarioUserData().getSzenarioTree();
                        for (Ergebnis ergebnis : aufgabe.getErgebnisse()) {
                            if (szenarioTree == null
                                    || modul.isCustom()
                                    || szenarioItemUtil.isErgebnisSelected(szenarioTree, phase, modul, aufgabe,
                                            ergebnis)) {
                                xmlTask.getWorkproductRef().add(
                                        xmlModelElementFactory.buildWorkProductRef(localizationEngine, ergebnis));
                            }
                        }
                        xmlModule.getTaskRef().add(xmlTask);
                    }
                }
                xmlPhase.getModuleRef().add(xmlModule);
            }
            scenario.getPhase().add(xmlPhase);
        }
        return scenario;
    }

    private Roles buildRoles(LocalizationEngine localizationEngine, Szenario szenario) {
        Roles roles = new Roles();
        for (Rolle rolle : szenario.getRollen()) {
            roles.getRole().add(xmlModelElementFactory.buildRole(localizationEngine, rolle));
        }
        return roles;
    }

    private Modules buildModules(LocalizationEngine localizationEngine, Szenario szenario) {
        Modules modules = new Modules();
        for (Modul modul : szenario.getModule()) {
            modules.getModule().add(xmlModelElementFactory.buildModule(localizationEngine, modul));
        }
        return modules;
    }

    private Tasks buildTasks(LocalizationEngine localizationEngine, Szenario szenario) {
        Tasks tasks = new Tasks();
        for (Aufgabe aufgabe : szenario.getAufgaben()) {
            Task task = xmlModelElementFactory.buildTask(localizationEngine, aufgabe);

            Rolle verantwortlicheRolle = aufgabe.getVerantwortlicheRolle();
            if (verantwortlicheRolle != null) {
                ResponsibleRole responsibleRole = xmlModelElementFactory.buildResponsibleRole(localizationEngine,
                        verantwortlicheRolle);
                task.setResponsibleRole(responsibleRole);
            }

            tasks.getTask().add(task);
        }
        return tasks;
    }

    private Workproducts buildWorkProducts(LocalizationEngine localizationEngine, Szenario szenario) {
        final String templatePrefix = "../../templates/" + localizationEngine.getLanguage() + "/";
        Workproducts workproducts = new Workproducts();
        for (Ergebnis ergebnis : szenario.getErgebnisse()) {

            Workproduct workproduct = xmlModelElementFactory.buildWorkProduct(localizationEngine, ergebnis);

            for (String url : ergebnis.getTemplateAttachmentUrls()) {
                if (ergebnis instanceof CustomErgebnis) {
                    Template template = xmlModelElementFactory.buildTemplate(templatePrefix + "custom/"
                            + getLinkName(url), getLinkName(url), TemplateType.FILE);
                    workproduct.getTemplate().add(template);
                } else {
                    String documentUrl = localizationEngine.documentUrl(url);
                    if (StringUtil.isNotBlank(documentUrl)) {
                        Template template = xmlModelElementFactory.buildTemplate(templatePrefix
                                + getLinkName(documentUrl), getLinkName(documentUrl), TemplateType.FILE);
                        workproduct.getTemplate().add(template);
                    }

                }
            }

            for (Localizable url : ergebnis.getWebAttachmentUrls()) {
                Template template = xmlModelElementFactory.buildTemplate(getLinkUrl(localizationEngine.localize(url)),
                        getLinkName(localizationEngine.localize(url)), TemplateType.WEB_URL);
                workproduct.getTemplate().add(template);
            }

            for (Rolle rolle : ergebnis.getVerantwortlicheRollen()) {
                CollaborationRoles collaborationRoles = xmlModelElementFactory.buildCollaborationRoles(
                        localizationEngine, rolle);
                workproduct.setCollaborationRoles(collaborationRoles);
            }

            workproducts.getWorkproduct().add(workproduct);
        }
        return workproducts;
    }

    

  
}
