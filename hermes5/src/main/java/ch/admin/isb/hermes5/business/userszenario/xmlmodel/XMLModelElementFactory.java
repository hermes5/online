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
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.model.api.Module;
import ch.admin.isb.hermes5.model.api.Role;
import ch.admin.isb.hermes5.model.api.RoleRef;
import ch.admin.isb.hermes5.model.api.Task;
import ch.admin.isb.hermes5.model.api.Task.ResponsibleRole;
import ch.admin.isb.hermes5.model.api.TaskRef;
import ch.admin.isb.hermes5.model.api.TemplateType;
import ch.admin.isb.hermes5.model.api.Workproduct;
import ch.admin.isb.hermes5.model.api.Workproduct.CollaborationRoles;
import ch.admin.isb.hermes5.model.api.Workproduct.Template;
import ch.admin.isb.hermes5.model.api.WorkproductRef;

public class XMLModelElementFactory {

    public CollaborationRoles buildCollaborationRoles(LocalizationEngine localizationEngine, Rolle rolle) {
        RoleRef roleRef = buildRoleRef(localizationEngine, rolle);
        CollaborationRoles collaborationRoles = new CollaborationRoles();
        collaborationRoles.getRoleRef().add(roleRef);
        return collaborationRoles;
    }

    public ResponsibleRole buildResponsibleRole(LocalizationEngine localizationEngine, Rolle verantwortlicheRolle) {
        ResponsibleRole responsibleRole = new ResponsibleRole();
        RoleRef roleRef = buildRoleRef(localizationEngine, verantwortlicheRolle);
        responsibleRole.setRoleRef(roleRef);
        return responsibleRole;
    }

    public Role buildRole(LocalizationEngine localizationEngine, Rolle rolle) {
        Role role = new Role();
        role.setId(rolle.getId());
        role.setName(rolle.getName());
        role.setPresentationName(localizationEngine.localize(rolle.getPresentationName()));
        role.setDescription(nullForBlank(localizationEngine.localize(rolle.getBriefDescription())));
        role.setResponsibility(nullForBlank(localizationEngine.localize(rolle.getMainDescription())));
        role.setAuthority(nullForBlank(localizationEngine.localize(rolle.getAssignementApproaches())));
        role.setSkills(nullForBlank(localizationEngine.localize(rolle.getSkills())));
        return role;
    }

    public Task buildTask(LocalizationEngine localizationEngine, Aufgabe aufgabe) {
        Task task = new Task();
        task.setId(aufgabe.getId());
        task.setName(aufgabe.getName());
        task.setPresentationName(localizationEngine.localize(aufgabe.getPresentationName()));
        task.setPurpose(nullForBlank(localizationEngine.localize(aufgabe.getPurpose())));
        task.setDescription(nullForBlank(localizationEngine.localize(aufgabe.getBriefDescription())));
        task.setBasicIdea(nullForBlank(localizationEngine.localize(aufgabe.getMainDescription())));
        task.setHermesSpecific(nullForBlank(localizationEngine.localize(aufgabe.getKeyConsiderations())));
        task.setActivities(nullForBlank(localizationEngine.localize(aufgabe.getAlternatives())));
        task.setChecklist(nullForBlank(localizationEngine.localize(aufgabe.getChecklist())));
        return task;
    }

    public TaskRef buildTaskRef(LocalizationEngine localizationEngine, Aufgabe aufgabe) {
        TaskRef xmlTask = new TaskRef();
        xmlTask.setId(aufgabe.getId());
        xmlTask.setName(aufgabe.getName());
        return xmlTask;
    }

    public Template buildTemplate(String linkUrl, String linkName, TemplateType webUrl) {
        Template template = new Template();
        template.setUrl(linkUrl);
        template.setName(linkName);
        template.setType(webUrl);
        return template;
    }

    public Workproduct buildWorkProduct(LocalizationEngine localizationEngine, Ergebnis ergebnis) {
        Workproduct workproduct = new Workproduct();
        workproduct.setId(ergebnis.getId());
        workproduct.setName(ergebnis.getName());

        workproduct.setPresentationName(localizationEngine.localize(ergebnis.getPresentationName()));
        workproduct.setDescription(nullForBlank(localizationEngine.localize(ergebnis.getBriefDescription())));
        workproduct.setTopic(nullForBlank(localizationEngine.localize(ergebnis.getPurpose())));
        return workproduct;
    }

    public WorkproductRef buildWorkProductRef(LocalizationEngine localizationEngine, Ergebnis ergebnis) {
        WorkproductRef xmlWorkproduct = new WorkproductRef();
        xmlWorkproduct.setId(ergebnis.getId());
        xmlWorkproduct.setName(ergebnis.getName());
        return xmlWorkproduct;
    }

    public Module buildModule(LocalizationEngine localizationEngine, Modul modul) {
        Module module = new Module();
        module.setId(modul.getId());
        module.setName(modul.getName());
        module.setPresentationName(localizationEngine.localize(modul.getPresentationName()));
        module.setBriefDescription(nullForBlank(localizationEngine.localize(modul.getBriefDescription())));
        module.setDescription(nullForBlank(localizationEngine.localize(modul.getMainDescription())));
        return module;
    }

    private RoleRef buildRoleRef(LocalizationEngine localizationEngine, Rolle verantwortlicheRolle) {
        RoleRef roleRef = new RoleRef();
        roleRef.setId(verantwortlicheRolle.getId());
        roleRef.setName(verantwortlicheRolle.getName());
        return roleRef;
    }

}
