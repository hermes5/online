/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.UploadedFile;

import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.CustomLocalizable;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.presentation.common.MessageProvider;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.util.Hardcoded;

public class SzenarioEigeneElementeControllerTest {

    private static final String TEST_MODUL_NAME = "testModul";
    private static final String TEST_ERGEBNIS_NAME = "testErgebnis";
    private static final String TEST_DESCRIPTION = "TestDescription";
    private static final String TEST_FILE_NAME_VALID = "TestFile.pdf";
    private static final String TEST_FILE_NAME_INVALID = "TestFile.txt";
    private static final byte[] TEST_FILE_CONTENT = "TestContent".getBytes();

    private SzenarioEigeneElementeController controller;
    private UploadedFile validTemplate;
    private UploadedFile invalidTemplate;

    @Before
    public void setUp() throws Exception {
        controller = new SzenarioEigeneElementeController();
        controller.szenarioDownloadController = mock(SzenarioDownloadController.class);
        when(controller.szenarioDownloadController.display()).thenReturn("szenario-download");
        controller.messagesUtil = mock(MessagesUtil.class);

        controller.messageProvider = mock(MessageProvider.class);
        when(controller.messageProvider.getLocalized(eq("al_eigene_elemente_modulname"))).thenReturn("modul");
        when(controller.messageProvider.getLocalized(eq("al_eigene_elemente_ergebnisname"))).thenReturn("ergebnis");

        controller.szenarioWizardContext = new SzenarioWizardContext();
        Szenario szenario = new Szenario(null);
        controller.szenarioWizardContext.setSzenario(szenario);

        SzenarioUserData userData = new SzenarioUserData();
        SzenarioItem tree = new SzenarioItem();
        tree.getChildren().add(new SzenarioItem("phase1", new CustomLocalizable("phase1", "phase1", "phase1", "phase1"), tree));
        tree.getChildren().add(new SzenarioItem("phase2", new CustomLocalizable("phase2", "phase2", "phase2", "phase2"), tree));
        tree.getChildren().add(new SzenarioItem("phase3", new CustomLocalizable("phase3", "phase3", "phase3", "phase3"), tree));
        userData.setSzenarioTree(tree);
        controller.szenarioWizardContext.setSzenarioUserData(userData);

        controller.init();
        controller.display();

        validTemplate = mock(UploadedFile.class);
        when(validTemplate.getFileName()).thenReturn(TEST_FILE_NAME_VALID);
        when(validTemplate.getContents()).thenReturn(TEST_FILE_CONTENT);

        invalidTemplate = mock(UploadedFile.class);
        when(invalidTemplate.getFileName()).thenReturn(TEST_FILE_NAME_INVALID);
        when(invalidTemplate.getContents()).thenReturn(TEST_FILE_CONTENT);

        Hardcoded.enableDefaults(controller);
    }

    @Test
    public void testAddCustomModule() {
        assertModuleCountIs(0);
        List<CustomViewModule> modules = new ArrayList<CustomViewModule>();

        modules.add(addModule());
        assertModuleCountIs(1);

        modules.add(addModule());
        assertModuleCountIs(2);

        assertTrue(controller.getCustomModules().containsAll(modules));
    }

    @Test
    public void testDeleteCustomModule() {
        assertModuleCountIs(0);
        CustomViewModule module = addModule();
        assertModuleCountIs(1);

        controller.deleteCustomModule(module);
        assertModuleCountIs(0);

        assertNull(controller.getHighlightedWorkProduct());
        assertNull(controller.getHighlightedModule());
    }

    @Test
    public void testGetCustomModules() {
        assertModuleCountIs(0);
        List<CustomViewModule> modules = new ArrayList<CustomViewModule>();
        modules.add(addModule());
        modules.add(addModule());
        assertModuleCountIs(2);

        assertEquals(2, controller.getCustomModules().size());
        assertTrue(controller.getCustomModules().containsAll(modules));
    }

    @Test
    public void testChangeHighlightedModule() {
        assertModuleCountIs(0);
        CustomViewModule module1 = addModule();
        CustomViewModule module2 = addModule();
        assertModuleCountIs(2);

        assertEquals(module2, controller.getHighlightedModule());
        controller.changeHighlightedModule(module1);
        assertEquals(module1, controller.getHighlightedModule());
        controller.changeHighlightedModule(module2);
        assertEquals(module2, controller.getHighlightedModule());
    }

    @Test
    public void testChangeHighlightedModuleWithChangedName() {
        assertModuleCountIs(0);
        CustomViewModule module1 = addModule();
        String module1Name = module1.getName();
        CustomViewModule module2 = addModule();
        String module2Name = module2.getName();
        assertModuleCountIs(2);

        assertEquals(module2, controller.getHighlightedModule());

        // Rename MODUL1 to MODUL3
        controller.getHighlightedModule().setName(TEST_MODUL_NAME);

        controller.changeHighlightedModule(module1);
        assertEquals(module1, controller.getHighlightedModule());

        List<String> moduleNames = getModuleNames();
        assertTrue(moduleNames.contains(module1Name));
        assertTrue(moduleNames.contains(TEST_MODUL_NAME));
        assertFalse(moduleNames.contains(module2Name));

        // Rename modul1 to test_modul_name -> Duplicate
        controller.getHighlightedModule().setName(TEST_MODUL_NAME);
        controller.changeHighlightedModule(module2);
        verify(controller.messagesUtil).addGlobalError(eq("al_custom_modul_name_eindeutig"));
        assertEquals(module1, controller.getHighlightedModule());

        moduleNames = getModuleNames();
        assertFalse(moduleNames.contains(module2Name));
        assertFalse(moduleNames.contains(module1Name));
        assertTrue(moduleNames.contains(TEST_MODUL_NAME));
    }
    
    @Test
    public void testAddCustomViewWorkProduct() {
        addModule();
        assertWorkProductCountIs(1);
        
        CustomViewWorkProduct workProduct = addWorkProduct();
        assertWorkProductCountIs(2);

        assertNull(workProduct.getTemplateFileName());
        assertNull(workProduct.getTemplateContent());
        assertNull(workProduct.getBriefDescription());
    }
    
    @Test
    public void testDeleteCustomViewWorkProduct() {
        addModule();
        assertWorkProductCountIs(1);

        CustomViewWorkProduct workProduct = addWorkProduct();
        assertWorkProductCountIs(2);

        controller.deleteCustomViewWorkProduct(workProduct);
        assertWorkProductCountIs(1);
    }

    @Test
    public void testChangeHighlightedWorkProduct() {
        addModule();

        assertWorkProductCountIs(1);
        CustomViewWorkProduct workProduct1 = addWorkProduct();
        CustomViewWorkProduct workProduct2 = addWorkProduct();
        assertWorkProductCountIs(3);

        assertEquals(workProduct2, controller.getHighlightedWorkProduct());

        controller.changeHighlightedWorkProduct(workProduct1);
        assertEquals(workProduct1, controller.getHighlightedWorkProduct());
    }

    @Test
    public void testChangeHighlightedWorkProductWithChangedName() {
        addModule();

        assertWorkProductCountIs(1);
        CustomViewWorkProduct workProduct1 = addWorkProduct();
        String workProductName1 = workProduct1.getPresentationName();
        CustomViewWorkProduct workProduct2 = addWorkProduct();
        String workProductName2 = workProduct2.getPresentationName();
        assertWorkProductCountIs(3);

        assertEquals(workProduct2, controller.getHighlightedWorkProduct());

        // Change presentationName of workProduct2 to valid name
        workProduct2.setPresentationName(TEST_ERGEBNIS_NAME);
        controller.changeHighlightedWorkProduct(workProduct1);
        assertEquals(workProduct1, controller.getHighlightedWorkProduct());

        List<String> workProductNames = getWorkProductNames();
        assertTrue(workProductNames.contains(TEST_ERGEBNIS_NAME));
        assertTrue(workProductNames.contains(workProductName1));
        assertFalse(workProductNames.contains(workProductName2));

        // Change presentationName of workProduct1 to invalid (duplicate) name
        workProduct1.setPresentationName(TEST_ERGEBNIS_NAME);
        controller.changeHighlightedWorkProduct(workProduct2);
        verify(controller.messagesUtil).addGlobalError(eq("al_custom_ergebnis_name_eindeutig"));
        assertEquals(workProduct1, controller.getHighlightedWorkProduct());

        workProductNames = getWorkProductNames();
        assertTrue(workProductNames.contains(TEST_ERGEBNIS_NAME));
        assertFalse(workProductNames.contains(workProductName1));
        assertFalse(workProductNames.contains(workProductName2));
    }

    @Test
    public void testVorlageUpload() {
        addModule();
        CustomViewWorkProduct workProduct = addWorkProduct();

        controller.setCustomTemplate(validTemplate);
        controller.vorlageUpload();

        assertEquals(TEST_FILE_NAME_VALID, workProduct.getTemplateFileName());
        assertEquals(TEST_FILE_CONTENT, workProduct.getTemplateContent());
        assertNull(controller.getCustomTemplate());
    }

    @Test
    public void testVorlageUploadWithInvalidFileExtension() {
        addModule();
        CustomViewWorkProduct workProduct = addWorkProduct();

        controller.setCustomTemplate(invalidTemplate);
        controller.vorlageUpload();

        verify(controller.messagesUtil).addGlobalError(eq("al_vorlageupload_message_error_nodoc"));
        assertEquals(invalidTemplate, controller.getCustomTemplate());
        assertNull(workProduct.getTemplateFileName());
        assertNull(workProduct.getTemplateContent());
    }

    @Test
    public void testVorlageDelete() {
        addModule();
        CustomViewWorkProduct workProduct = addWorkProduct();

        controller.setCustomTemplate(validTemplate);
        controller.vorlageUpload();
        controller.deleteVorlage();

        assertNull(controller.getCustomTemplate());
        assertNull(workProduct.getTemplateFileName());
        assertNull(workProduct.getTemplateContent());
    }

    @Test
    public void testSaveDataValidateAtLeastOnePhaseSelected() {
         addModule();
        assertNull(controller.gotoDownload());
        verify(controller.messagesUtil).addGlobalError("al_custom_modul_workproduct_without_phase");
    }
    
    @Test
    public void testSaveDataValidateAtLeastOneWorkproductInModule() {
         addModule();
        controller.deleteCustomViewWorkProduct(controller.getHighlightedWorkProduct());
        String result = controller.gotoDownload();
        assertNull(result, result);
        verify(controller.messagesUtil).addGlobalError("al_custom_modul_module_without_workproduct");
    }
    
    @Test
    public void testSaveData() {
        CustomViewModule module = addModule();
        CustomViewWorkProduct workProduct = controller.getHighlightedWorkProduct();
        controller.getHighlightedWorkProduct().setBriefDescription(TEST_DESCRIPTION);
        controller.getHighlightedWorkProduct().getPhases().get(0).setSelected(true);
        
        controller.setCustomTemplate(validTemplate);
        controller.vorlageUpload();
        String gotoDownload = controller.gotoDownload();
        
        verify(controller.messagesUtil, never()).addGlobalError(anyString());
        verify(controller.messagesUtil, never()).addGlobalErrorMessage(anyString());
        
        assertEquals("szenario-download?faces-redirect=true", gotoDownload);
        
        Map<String, List<CustomErgebnis>> customModules = controller.szenarioWizardContext.getSzenarioUserData().getCustomModules();
        assertTrue(customModules.containsKey(module.getName()));
        
        CustomErgebnis customErgebnis = customModules.get(module.getName()).get(0);
        assertEquals(workProduct.getPresentationName(), getDeText(customErgebnis.getPresentationName()));
        assertEquals(workProduct.getPresentationName(), customErgebnis.getCustomName());
        assertEquals(TEST_DESCRIPTION, getDeText(customErgebnis.getBriefDescription()));
        assertEquals(TEST_FILE_NAME_VALID, customErgebnis.getVorlageFilename());
        assertEquals(TEST_FILE_CONTENT, customErgebnis.getVorlageContent());
    }

    @Test
    public void testIsDisabledAddModuleButton() {
        controller.maxOwnModules = Hardcoded.configuration(1);

        assertModuleCountIs(0);
        assertFalse(controller.isDisabledAddModuleButton());
        
        addModule();
        assertModuleCountIs(1);
        assertTrue(controller.isDisabledAddModuleButton());
    }

    @Test
    public void testIsDisabledAddWorkProductButtonButton() {
        controller.maxOwnWorkproducts = Hardcoded.configuration(2);

        addModule();
        assertWorkProductCountIs(1);
        assertFalse(controller.isDisabledAddWorkProductButton());

        addWorkProduct();
        assertWorkProductCountIs(2);
        assertTrue(controller.isDisabledAddWorkProductButton());
    }


    private CustomViewModule addModule() {
        controller.addCustomModule();
        return controller.getHighlightedModule();
    }

    private void assertModuleCountIs(int count) {
        assertEquals(count, controller.getCustomModules().size());
    }
    
    private CustomViewWorkProduct addWorkProduct() {
        controller.addCustomViewWorkProduct();
        return controller.getHighlightedWorkProduct();
    }

    private void assertWorkProductCountIs(int count) {
        assertEquals(count, controller.getCustomViewWorkProducts().size());
    }

    private List<String> getModuleNames() {
        List<String> names = new ArrayList<String>();
        for (CustomViewModule module : controller.getCustomModules()) {
            names.add(module.getName());
        }

        return names;
    }

    private List<String> getWorkProductNames() {
        List<String> names = new ArrayList<String>();
        for (CustomViewWorkProduct cvwp : controller.getCustomViewWorkProducts()) {
            names.add(cvwp.getPresentationName());
        }

        return names;
    }

    private String getDeText(Localizable loc) {
        return ((CustomLocalizable) loc).getText("de");
    }
}
