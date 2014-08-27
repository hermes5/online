/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.xmlmodel;

import static ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder.*;
import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static ch.admin.isb.hermes5.util.StringUtil.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.model.api.ModelType;
import ch.admin.isb.hermes5.model.api.ModuleRef;
import ch.admin.isb.hermes5.model.api.Phase;
import ch.admin.isb.hermes5.model.api.Role;
import ch.admin.isb.hermes5.model.api.Scenario;
import ch.admin.isb.hermes5.model.api.Task;
import ch.admin.isb.hermes5.model.api.TaskRef;
import ch.admin.isb.hermes5.model.api.TemplateType;
import ch.admin.isb.hermes5.model.api.Workproduct;
import ch.admin.isb.hermes5.model.api.Workproduct.Template;
import ch.admin.isb.hermes5.util.StringUtil;

public class XMLModelAssemblerTest {

    private XMLModelAssembler xmlModelGenerator;
    private Szenario szenario;
    private ModelType model;
    private LocalizationEngine localizationEngine;

    @Before
    public void setUp() throws Exception {
        xmlModelGenerator = new XMLModelAssembler();
        xmlModelGenerator.xmlModelMarshaller = new XMLModelMarshaller();
        xmlModelGenerator.xmlModelElementFactory = new XMLModelElementFactory();

        localizationEngine = getLocalizationEngineDe();

        // Generate example xml model
        szenario = szenario("test");
        szenario.getPhasen().add(phaseFull("init"));

    }

    private void buildModelWithContent() {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario,
                new SzenarioUserData(), Arrays.asList("de"), true, true, true, true);
        String xml =  xmlModelGenerator.assembleXML(container, localizationEngine);
        model = new XMLModelGeneratorTestUtil().unmarshall(StringUtil.getBytes(xml));
    }

    
    @Test
    public void testXMLHeader() {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario,
                new SzenarioUserData(), Arrays.asList("de"), true, true, true, true);
        String xml = xmlModelGenerator.assembleXML(container, localizationEngine);
        assertTrue(xml.contains("http://www.w3.org/2001/XMLSchema-instance"));
        assertTrue(xml.contains("schemaLocation=\"http://www.hermes.admin.ch/model/hermes5/v1/model_schema.xsd"));
    }
    @Test
    public void testAddXMLModelRolesId() {
        buildModelWithContent();
        assertEquals(szenario.getRollen().size(), model.getRoles().getRole().size());
        int i = 0;
        for (Role role : model.getRoles().getRole()) {
            assertEquals(szenario.getRollen().get(i++).getId(), role.getId());
        }
    }

    @Test
    public void testAddXMLModelRolesContent() {
        buildModelWithContent();
        int i = 0;
        for (Role role : model.getRoles().getRole()) {
            Rolle rolle = szenario.getRollen().get(i++);
            assertEquals(nullForBlank(localizationEngine.localize(rolle.getBriefDescription())), role.getDescription());
            assertEquals(nullForBlank(localizationEngine.localize(rolle.getMainDescription())),
                    role.getResponsibility());
            assertEquals(nullForBlank(localizationEngine.localize(rolle.getAssignementApproaches())),
                    role.getAuthority());
            assertEquals(nullForBlank(localizationEngine.localize(rolle.getSkills())), role.getSkills());
        }
    }

    @Test
    public void testAddXMLModelTasksId() {
        buildModelWithContent();
        assertEquals(szenario.getAufgaben().size(), model.getTasks().getTask().size());
        int i = 0;
        for (Task task : model.getTasks().getTask()) {
            Aufgabe aufgabe = szenario.getAufgaben().get(i++);

            assertEquals(aufgabe.getId(), task.getId());
        }
    }

    @Test
    public void testAddXMLModelTasksContent() {
        buildModelWithContent();
        int i = 0;
        for (Task task : model.getTasks().getTask()) {
            Aufgabe aufgabe = szenario.getAufgaben().get(i++);

            assertEquals(nullForBlank(localizationEngine.localize(aufgabe.getPurpose())), task.getPurpose());
            assertEquals(nullForBlank(localizationEngine.localize(aufgabe.getBriefDescription())),
                    task.getDescription());
            assertEquals(nullForBlank(localizationEngine.localize(aufgabe.getMainDescription())), task.getBasicIdea());
            assertEquals(nullForBlank(localizationEngine.localize(aufgabe.getKeyConsiderations())),
                    task.getHermesSpecific());
            assertEquals(nullForBlank(localizationEngine.localize(aufgabe.getAlternatives())), task.getActivities());
            assertEquals(nullForBlank(localizationEngine.localize(aufgabe.getChecklist())), task.getChecklist());
        }
    }

    @Test
    public void testAddXMLModelResponsibleRolesForTask() {
        buildModelWithContent();
        for (int i = 0; i < model.getTasks().getTask().size(); i++) {
            Rolle verantwortlicheRolle = szenario.getAufgaben().get(i).getVerantwortlicheRolle();
            if (verantwortlicheRolle == null)
                continue;

            assertEquals(verantwortlicheRolle.getId(), model.getTasks().getTask().get(i).getResponsibleRole()
                    .getRoleRef().getId());
        }
    }

    @Test
    public void testAddXMLModelWorkproductsId() {
        buildModelWithContent();
        assertEquals(szenario.getErgebnisse().size(), model.getWorkproducts().getWorkproduct().size());
        int i = 0;
        for (Workproduct workproduct : model.getWorkproducts().getWorkproduct()) {
            Ergebnis ergebnis = szenario.getErgebnisse().get(i++);

            assertEquals(ergebnis.getId(), workproduct.getId());
        }
    }

    @Test
    public void testAddXMLModelWorkproductsTemplates() {
        buildModelWithContent();
        int i = 0;
        for (Workproduct workproduct : model.getWorkproducts().getWorkproduct()) {
            Ergebnis ergebnis = szenario.getErgebnisse().get(i++);

            List<String> templateUrls = getTemplateUrlsFrom(ergebnis);
            int j = 0;
            for (Template template : workproduct.getTemplate()) {
                String url = templateUrls.get(j++);

                assertEquals(getLinkName(url), template.getName());

                if (getLinkName(url).startsWith("attachment_")) {
                    assertEquals(TemplateType.WEB_URL, template.getType());
                    assertEquals(url, template.getUrl());
                } else {
                    assertEquals(TemplateType.FILE, template.getType());
                    assertEquals("../../templates/de/" + getLinkName(url), template.getUrl());
                }
            }
        }
    }

    @Test
    public void testAddXMLModelWorkproductsContent() {
        buildModelWithContent();
        int i = 0;
        for (Workproduct workproduct : model.getWorkproducts().getWorkproduct()) {
            Ergebnis ergebnis = szenario.getErgebnisse().get(i++);

            assertEquals(nullForBlank(localizationEngine.localize(ergebnis.getBriefDescription())),
                    workproduct.getDescription());
            assertEquals(nullForBlank(localizationEngine.localize(ergebnis.getPurpose())), workproduct.getTopic());
        }
    }

    @Test
    public void testAddXMLModelScenarioTree() {
        buildModelWithContent();
        // Scenario
        Scenario scenario = model.getScenario();
        assertEquals("model/de/dpid_test/presentationName", scenario.getPresentationName());

        // Phase
        assertEquals(1, scenario.getPhase().size());
        Phase phase = scenario.getPhase().get(0);
        assertEquals("model/de/phaseid_init/presentationName", phase.getPresentationName());
        assertEquals("phase_init", phase.getName());

        // Module
        assertEquals(1, phase.getModuleRef().size());
        ModuleRef module = phase.getModuleRef().get(0);
        assertEquals("modul_modul1", module.getName());

        // Task
        assertEquals(2, module.getTaskRef().size());
        int i = 1;
        for (TaskRef task : module.getTaskRef()) {
            assertEquals("taskid_task" + i++, task.getId());
        }
    }

    

    private List<String> getTemplateUrlsFrom(Ergebnis ergebnis) {
        List<String> templateUrls = ergebnis.getTemplateAttachmentUrls();
        for (Localizable localizable : ergebnis.getWebAttachmentUrls()) {
            templateUrls.add(localizationEngine.localize(localizable));
        }
        return templateUrls;
    }
}
