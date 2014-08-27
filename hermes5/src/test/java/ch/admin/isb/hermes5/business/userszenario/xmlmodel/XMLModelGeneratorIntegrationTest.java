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
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelrepository.SzenarioIntegrationTestSupport;
import ch.admin.isb.hermes5.business.modelutil.MethodenElementFactory;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.userszenario.SzenarioCustomizer;
import ch.admin.isb.hermes5.business.userszenario.SzenarioStripper;
import ch.admin.isb.hermes5.business.util.SzenarioItemUtil;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilderUtils;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.model.api.ModelType;
import ch.admin.isb.hermes5.model.api.ModuleRef;
import ch.admin.isb.hermes5.model.api.Phase;
import ch.admin.isb.hermes5.model.api.Scenario;
import ch.admin.isb.hermes5.model.api.TaskRef;
import ch.admin.isb.hermes5.model.api.TemplateType;
import ch.admin.isb.hermes5.model.api.Workproduct;
import ch.admin.isb.hermes5.model.api.Workproduct.Template;
import ch.admin.isb.hermes5.model.api.WorkproductRef;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ReflectionUtil;

public class XMLModelGeneratorIntegrationTest {

    // @Test
    public void listSzenarios() {
        for (int i = 0; i < 9; i++) {
            InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
            Szenario szenario = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, i);
            System.out.println(i + ": " + szenario.getName());
        }
    }

    private XMLModelGenerator xmlModelGenerator;
    private SzenarioStripper szenarioStripper;
    private SzenarioCustomizer szenarioCustomizer;
    private LocalizationEngine localizationEngine;

    @Before
    public void setUp() throws Exception {
        xmlModelGenerator = new XMLModelGenerator();
        xmlModelGenerator.xmlModelAssembler = new XMLModelAssembler();
        xmlModelGenerator.xmlModelAssembler.xmlModelMarshaller = new XMLModelMarshaller();
        xmlModelGenerator.zipOutputBuilderUtils = new ZipOutputBuilderUtils();
        xmlModelGenerator.xmlModelAssembler.xmlModelElementFactory = new XMLModelElementFactory();
        xmlModelGenerator.xmlModelAssembler.szenarioItemUtil = new SzenarioItemUtil();

        localizationEngine = getLocalizationEngineDe();
        szenarioStripper = new SzenarioStripper();
        szenarioCustomizer = new SzenarioCustomizer();
        MethodenElementFactory methodenElementFactory = new MethodenElementFactory();
        Hardcoded.enableDefaults(methodenElementFactory);
        ReflectionUtil.updateField(szenarioStripper, "methodenElementFactory", methodenElementFactory);
    }

    @Test
    public void testUserDataEmpty() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenario = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 6);
        String szenarioName = "szenario_01_IT-Individualanwendung";
        assertEquals(szenarioName, szenario.getName());
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario,
                new SzenarioUserData(), Arrays.asList("null"), true, true, true, true);
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        xmlModelGenerator.addXMLModelWithContent(container, zipBuilder, localizationEngine);
        ModelType model = new XMLModelGeneratorTestUtil().readFirstFileToModelType(zipBuilder);

        Scenario scenario = model.getScenario();
        assertEquals(szenarioName, scenario.getName());
        assertEquals(4, scenario.getPhase().size());
        Phase phase = scenario.getPhase().get(0);
        assertEquals("phase_Initialisierung", phase.getName());
        assertEquals(3, phase.getModuleRef().size());
        ModuleRef moduleRef = phase.getModuleRef().get(0);
        assertEquals("modul_projektsteuerung", moduleRef.getName());
        assertEquals(2, moduleRef.getTaskRef().size());
        TaskRef taskRef = moduleRef.getTaskRef().get(0);
        assertEquals("aufgabe_initialisierung_beauftragen_und_steuern", taskRef.getName());
        assertEquals(1, taskRef.getWorkproductRef().size());
        assertEquals("ergebnis_projektinitialisierungsauftrag", taskRef.getWorkproductRef().get(0).getName());

    }

    @Test
    public void testWithUserDataSzenario() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenarioFull = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 6);
        SzenarioItem szenarioTree = buildTree(szenarioFull);
        Szenario szenario = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);
        String szenarioName = "szenario_01_IT-Individualanwendung";
        assertEquals(szenarioName, szenario.getName());
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setSzenarioTree(szenarioTree);
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario,
                szenarioUserData, Arrays.asList("de"), true, true, true, true);
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        xmlModelGenerator.addXMLModelWithContent(container, zipBuilder, localizationEngine);
        ModelType model = new XMLModelGeneratorTestUtil().readFirstFileToModelType(zipBuilder);

        Scenario scenario = model.getScenario();
        assertEquals(szenarioName, scenario.getName());
        assertEquals(3, scenario.getPhase().size());
        Phase phase = scenario.getPhase().get(0);
        assertEquals("phase_Voranalyse", phase.getName());
        assertEquals(9, phase.getModuleRef().size());
        ModuleRef moduleRef = phase.getModuleRef().get(0);
        assertEquals("modul_projektfuehrung", moduleRef.getName());
        assertEquals(7, moduleRef.getTaskRef().size());
        TaskRef taskRef = moduleRef.getTaskRef().get(0);
        assertEquals("aufgabe_leistungen_vereinbaren_und_steuern", taskRef.getName());
        assertEquals(2, taskRef.getWorkproductRef().size());
        assertEquals("ergebnis_evaluationsbericht", taskRef.getWorkproductRef().get(0).getName());

    }

    @Test
    public void testWithUserDataSzenarioAndCustomModul() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        Szenario szenarioFull = new SzenarioIntegrationTestSupport().buildSzenario(resourceAsStream, 6);
        SzenarioItem szenarioTree = buildTree(szenarioFull);
        Szenario szenario = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull, szenarioTree);

        Rolle rolle = szenarioFull.getRollen().get(0);
        assertEquals("rolle_auftraggeber", rolle.getName());

        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setSzenarioTree(szenarioTree);
        String customModulName = "my custom module";
        List<CustomErgebnis> customErgebnisse = new ArrayList<CustomErgebnis>();
        customErgebnisse.add(new CustomErgebnis("my custom ergebnis", "briefDescription", "vorlageFilename.pdf",
                "vorlageContent".getBytes(), Arrays.asList(szenario.getPhasen().get(0).getId()), Arrays.asList(rolle)));
        szenarioUserData.getCustomModules().put(customModulName, customErgebnisse);

        szenarioCustomizer.addCustomModule(szenario, customModulName, customErgebnisse);
        String szenarioName = "szenario_01_IT-Individualanwendung";
        assertEquals(szenarioName, szenario.getName());

        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario,
                szenarioUserData, Arrays.asList("de"), true, true, true, true);
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        xmlModelGenerator.addXMLModelWithContent(container, zipBuilder, localizationEngine);
        ModelType model = new XMLModelGeneratorTestUtil().readFirstFileToModelType(zipBuilder);
        Scenario scenario = model.getScenario();
        assertEquals(szenarioName, scenario.getName());
        assertEquals(3, scenario.getPhase().size());
        Phase phase = scenario.getPhase().get(0);
        assertEquals("phase_Voranalyse", phase.getName());

        List<ModuleRef> moduleRefs = phase.getModuleRef();
        for (ModuleRef moduleRef : moduleRefs) {
            if ("custommodul_my_custom_module".equals(moduleRef.getName())) {
                assertEquals(1, moduleRef.getTaskRef().size());
                TaskRef taskRef = moduleRef.getTaskRef().get(0);
                assertEquals(1, taskRef.getWorkproductRef().size());
                WorkproductRef workproductRef = taskRef.getWorkproductRef().get(0);
                assertEquals("customergebnis_my_custom_ergebnis", workproductRef.getName());

                List<Workproduct> workproducts = model.getWorkproducts().getWorkproduct();
                boolean found = false;
                for (Workproduct workproduct : workproducts) {
                    if (workproduct.getId().equals(workproductRef.getId())) {
                        found = true;
                        assertEquals(1, workproduct.getTemplate().size());
                        Template template = workproduct.getTemplate().get(0);
                        assertEquals(TemplateType.FILE, template.getType());
                        assertEquals("vorlageFilename.pdf", template.getName());
                        assertEquals("../../templates/de/custom/vorlageFilename.pdf", template.getUrl());

                    }
                }
                assertTrue(found);

            }
        }
        assertEquals(10, moduleRefs.size());
        ModuleRef moduleRef = moduleRefs.get(0);
        assertEquals("modul_projektfuehrung", moduleRef.getName());

    }

    private SzenarioItem buildTree(Szenario szenario) {
        SzenarioItem szenarioItem = new SzenarioItem();
        szenarioItem.setSelected(true);
        List<ch.admin.isb.hermes5.domain.Phase> phasen = szenario.getPhasen();
        for (ch.admin.isb.hermes5.domain.Phase p : phasen) {
            SzenarioItem phaseItem = new SzenarioItem(p.getId(), p.getPresentationName(), szenarioItem);
            if (p.getName().equals("phase_Initialisierung")) {
                phaseItem.setSelected(false);
            }
            szenarioItem.getChildren().add(phaseItem);
            List<Modul> module = p.getModule();
            for (Modul m : module) {
                SzenarioItem modulItem = new SzenarioItem(m.getId(), m.getPresentationName(), phaseItem);
                if (m.getName().equals("modul_projektsteuerung")) {
                    modulItem.setSelected(false);
                }
                phaseItem.getChildren().add(modulItem);
                List<Aufgabe> aufgabenModul = m.getAufgaben();
                List<Aufgabe> aufgabenPhase = p.getAufgaben();
                for (Aufgabe a : aufgabenModul) {
                    if (aufgabenPhase.contains(a)) {
                        SzenarioItem aufgabeItem = new SzenarioItem(a.getId(), a.getPresentationName(), modulItem);
                        if (a.getName().equals("aufgabe_projekt_fuehren_und_kontrollieren")) {
                            aufgabeItem.setSelected(false);
                        }
                        modulItem.getChildren().add(aufgabeItem);
                        List<Ergebnis> ergebnisse = a.getErgebnisse();
                        for (Ergebnis e : ergebnisse) {
                            SzenarioItem ergebnisItem = new SzenarioItem(e.getId(), e.getPresentationName(),
                                    aufgabeItem);
                            if (e.getName().equals("ergebnis_offertanfrage")) {
                                ergebnisItem.setSelected(false);
                            }
                            aufgabeItem.getChildren().add(ergebnisItem);
                        }
                    }
                }

            }
        }
        return szenarioItem;
    }

}
