/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.MessageProvider;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.presentation.common.SelectableRow;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;
import ch.admin.isb.hermes5.util.WebFont;

@Named
@SessionScoped
public class SzenarioEigeneElementeController extends AbstractViewController implements Serializable {

    private static final int MAX_FILENAME_LENGTH_IN_GUI = 30;
    private static final long serialVersionUID = 1L;
    private static final String IDENTIFIER = "szenario-eigene-elemente";

    private static final Logger logger = LoggerFactory.getLogger(SzenarioEigeneElementeController.class);

    private CustomViewModule highlightedModule;
    private CustomViewWorkProduct highlightedWorkProduct;

    private int moduleCounter;
    private int workPorductCounter;

    private Map<CustomViewModule, List<CustomViewWorkProduct>> customModules;

    private boolean visibleUploadVorlageDialog;
    private UploadedFile customTemplate;

    @Inject
    SzenarioWizardContext szenarioWizardContext;

    @Inject
    SzenarioElementeController szenarioElementeController;

    @Inject
    SzenarioDownloadController szenarioDownloadController;

    @Inject
    Localizer localizer;

    @Inject
    MessagesUtil messagesUtil;

    @Inject
    MessageProvider messageProvider;

    @Inject
    @SystemProperty(value = "anwenderloesung.templateTypes", fallback = "dotx, docx, xlsx, pptx, pdf")
    ConfigurationProperty templateTypes;

    @Inject
    @SystemProperty(value = "anwenderloesung.maxOwnModules", fallback = "4")
    ConfigurationProperty maxOwnModules;

    @Inject
    @SystemProperty(value = "anwenderloesung.maxOwnWorkproducts", fallback = "10")
    ConfigurationProperty maxOwnWorkproducts;

    @PostConstruct
    public void init() {
        resetData();
    }

    public void resetData() {
        customModules = new LinkedHashMap<CustomViewModule, List<CustomViewWorkProduct>>();
        highlightedModule = null;
        highlightedWorkProduct = null;
        moduleCounter = 1;
        workPorductCounter = 1;
        visibleUploadVorlageDialog = false;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    public String display() {
        return getIdentifier();
    }

    public String goBack() {
        return redirectTo(szenarioElementeController.display());
    }

    public String gotoDownload() {
        if (validate()) {
            saveStatus();
            return redirectTo(szenarioDownloadController.display());
        }
        return null;
    }

    private boolean validate() {
        for (List<CustomViewWorkProduct> viewProducts : customModules.values()) {
            if(viewProducts.isEmpty()) {
                messagesUtil.addGlobalError("al_custom_modul_module_without_workproduct");
                return false;
            }
            for (CustomViewWorkProduct customViewWorkProduct : viewProducts) {
                List<SelectableRow<SzenarioItem>> phases = customViewWorkProduct.getPhases();
                boolean noPhaseSelected = true;
                for (SelectableRow<SzenarioItem> selectableRow : phases) {
                    if (selectableRow.isSelected()) {
                        noPhaseSelected = false;
                    }
                }
                if (noPhaseSelected) {
                    messagesUtil.addGlobalError("al_custom_modul_workproduct_without_phase");
                    return false;
                }
            }
        }

        
        return true;
    }

    public void changeHighlightedModule(CustomViewModule module) {
        if (this.highlightedModule != null && isDuplicateCustomViewModuleName(highlightedModule)) {
            messagesUtil.addGlobalError("al_custom_modul_name_eindeutig");
            return;
        }

        this.highlightedModule = module;
        this.highlightedWorkProduct = null;
    }

    public void changeHighlightedWorkProduct(CustomViewWorkProduct cvwp) {
        if (highlightedWorkProduct != null && isDuplicateCustomViewErgebnisPresentationName(highlightedWorkProduct)) {
            messagesUtil.addGlobalError("al_custom_ergebnis_name_eindeutig");
            return;
        }

        this.highlightedWorkProduct = cvwp;
    }

    public List<CustomViewModule> getCustomModules() {
        return new ArrayList<CustomViewModule>(customModules.keySet());
    }

    public void addCustomModule() {
        CustomViewModule module = new CustomViewModule(getNextDefaultModuleName());
        customModules.put(module, new ArrayList<CustomViewWorkProduct>());
        changeHighlightedModule(module);
        addCustomViewWorkProduct();
    }

    public void deleteCustomModule(CustomViewModule module) {
        customModules.remove(module);
        if (module.equals(highlightedModule)) {
            changeHighlightedModule(null);
        }
    }

    public List<CustomViewWorkProduct> getCustomViewWorkProducts() {
        List<CustomViewWorkProduct> ergebnisse = customModules.get(highlightedModule);
        return ergebnisse;
    }

    public void addCustomViewWorkProduct() {
        CustomViewWorkProduct cvwp = new CustomViewWorkProduct(getNextDefaultWorkProductName());
        initCustomViewWorkProduct(cvwp);
        getCustomViewWorkProducts().add(cvwp);
        changeHighlightedWorkProduct(cvwp);
    }

    public void deleteCustomViewWorkProduct(CustomViewWorkProduct toDelete) {
        Iterator<CustomViewWorkProduct> it = getCustomViewWorkProducts().iterator();
        while (it.hasNext()) {
            CustomViewWorkProduct candidate = it.next();
            if (candidate.equals(toDelete)) {
                it.remove();
                break;
            }
        }
        if (toDelete.equals(highlightedWorkProduct)) {
            changeHighlightedWorkProduct(null);
        }
    }

    public String cancelVorlageUpload() {
        customTemplate = null;
        visibleUploadVorlageDialog = false;
        return null;
    }

    public String showVorlageUploadDialog() {
        visibleUploadVorlageDialog = true;
        return null;
    }

    public String deleteVorlage() {
        customTemplate = null;
        highlightedWorkProduct.setTemplateContent(null);
        highlightedWorkProduct.setTemplateFileName(null);
        return null;
    }

    public String vorlageUpload() {
        if (customTemplate == null) {
            logger.warn("No file uploaded. customTemplate is null!");
        } else {
            String[] split = customTemplate.getFileName().split("\\.");
            if (!templateTypes.getStringValue().contains(split[split.length - 1])) {
                messagesUtil.addGlobalError("al_vorlageupload_message_error_nodoc");
                return null;
            }

            highlightedWorkProduct.setTemplateContent(customTemplate.getContents());
            highlightedWorkProduct.setTemplateFileName(StringUtil.getLinkName(customTemplate.getFileName()));
        }

        customTemplate = null;
        visibleUploadVorlageDialog = false;
        return null;
    }

    public boolean isVisibleUploadVorlageDialog() {
        return visibleUploadVorlageDialog;
    }

    public void setVisibleUploadVorlageDialog(boolean visibleUploadVorlageDialog) {
        this.visibleUploadVorlageDialog = visibleUploadVorlageDialog;
    }

    public UploadedFile getCustomTemplate() {
        return customTemplate;
    }

    public void setCustomTemplate(UploadedFile customTemplate) {
        this.customTemplate = customTemplate;
    }

    public boolean workProductHasTemplate() {
        return highlightedWorkProduct != null && StringUtil.isNotBlank(highlightedWorkProduct.getTemplateFileName());
    }

    public WebFont getWebFontForTemplate() {
        return WebFont.forFilename(highlightedWorkProduct.getTemplateFileName());
    }

    public String getShortTemplateFilename() {
        if (workProductHasTemplate()) {
            return StringUtil.shortFilename(highlightedWorkProduct.getTemplateFileName(), MAX_FILENAME_LENGTH_IN_GUI);
        } else {
            return null;
        }
    }

    public CustomViewModule getHighlightedModule() {
        return highlightedModule;
    }

    public void setHighlightedModuleName(CustomViewModule highlightedModule) {
        this.highlightedModule = highlightedModule;
    }

    public CustomViewWorkProduct getHighlightedWorkProduct() {
        return highlightedWorkProduct;
    }

    public void setHighlightedWorkProduct(CustomViewWorkProduct highlightedWorkProduct) {
        this.highlightedWorkProduct = highlightedWorkProduct;
    }

    public boolean isDisabledAddModuleButton() {
        return customModules.size() >= maxOwnModules.getIntegerValue();
    }

    public boolean isDisabledAddWorkProductButton() {
        List<CustomViewWorkProduct> workProducts = customModules.get(highlightedModule);
        return workProducts == null || workProducts.size() >= maxOwnWorkproducts.getIntegerValue();
    }

    private void saveStatus() {
        szenarioWizardContext.getSzenarioUserData().getCustomModules().clear();
        for (CustomViewModule module : customModules.keySet()) {
            szenarioWizardContext.getSzenarioUserData().addCustomModule(module.getName());
            for (CustomViewWorkProduct cvwp : customModules.get(module)) {
                List<Rolle> roles = new ArrayList<Rolle>();
                for (SelectableRow<Rolle> selectableRolle : cvwp.getRoles()) {
                    if (selectableRolle.isSelected()) {
                        roles.add(selectableRolle.getData());
                    }
                }

                List<String> phases = new ArrayList<String>();
                for (SelectableRow<SzenarioItem> selectablePhase : cvwp.getPhases()) {
                    if (selectablePhase.isSelected()) {
                        phases.add(selectablePhase.getData().getId());
                    }
                }

                CustomErgebnis ergebnis = new CustomErgebnis(cvwp.getPresentationName(), cvwp.getBriefDescription(),
                        cvwp.getTemplateFileName(), cvwp.getTemplateContent(), phases, roles);

                szenarioWizardContext.getSzenarioUserData().getCustomErgebnisse(module.getName()).add(ergebnis);
            }
        }
    }

    private boolean isDuplicateCustomViewModuleName(CustomViewModule module) {
        for (CustomViewModule candidate : customModules.keySet()) {
            if (!candidate.getId().equals(module.getId()) && candidate.getName().equals(module.getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isDuplicateCustomViewErgebnisPresentationName(CustomViewWorkProduct cvwp) {
        for (CustomViewWorkProduct candidate : getCustomViewWorkProducts()) {
            if (!candidate.getId().equals(cvwp.getId())
                    && candidate.getPresentationName().equals(cvwp.getPresentationName())) {
                return true;
            }
        }
        return false;
    }

    private String getNextDefaultModuleName() {
        return messageProvider.getLocalized("al_eigene_elemente_modulname") + moduleCounter++;
    }

    private String getNextDefaultWorkProductName() {
        return messageProvider.getLocalized("al_eigene_elemente_ergebnisname") + workPorductCounter++;
    }

    private void initCustomViewWorkProduct(CustomViewWorkProduct ergebnis) {
        List<SzenarioItem> p = szenarioWizardContext.getSzenarioUserData().getSzenarioTree().getChildren();
        for (SzenarioItem phase : p) {
            ergebnis.getPhases().add(new SelectableRow<SzenarioItem>(phase));
        }

        List<Rolle> r = szenarioWizardContext.getSzenario().getRollen();
        for (Rolle rolle : r) {
            ergebnis.getRoles().add(new SelectableRow<Rolle>(rolle));
        }
    }
}
