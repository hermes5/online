/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.util.Logged;
import ch.admin.isb.hermes5.validation.ListSize;

@Named
@SessionScoped
public class TranslationZipDownloadController extends AbstractViewController implements Serializable {

    private static final long serialVersionUID = 1L;
    private String modelIdentifier;
    private String backIdentifier;
    @ListSize(min = 1)
    private List<String> selectedLanguages;
    @ListSize(min = 1)
    private List<String> selectedStatus;

    @Inject
    WebPublisherFacade webPublisherFacade;

    @Inject
    ArtifactOverviewController artifactOverviewController;

    @Inject
    ModelOverviewController modelOverviewController;
    
    @Inject
    MessagesUtil messageUtil;
    private byte[] translationZip;
    private boolean renderDownloadDialog;

    public boolean isRenderDownloadDialog() {
        return renderDownloadDialog;
    }

    @Logged
    public String display(String modelIdentifier, String backIdentifier) {
        this.modelIdentifier = modelIdentifier;
        selectedLanguages = new ArrayList<String>();
        selectedLanguages.add("fr");
        selectedStatus = new ArrayList<String>();
        selectedStatus.add("UNVOLLSTAENDIG");
        renderDownloadDialog = false;
        translationZip = null;
        this.backIdentifier = backIdentifier;
        return getIdentifier();
    }

    @Logged
    @Override
    public String getIdentifier() {
        return "translation-zip-download";
    }

    public void setSelectedLanguages(List<String> languages) {
        selectedLanguages = languages;
    }

    public List<String> getSelectedLanguages() {
        return selectedLanguages;
    }

    public List<String> getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(List<String> selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public String triggerExport() {
        translationZip = webPublisherFacade.getTranslationZip(modelIdentifier, selectedLanguages,
                Status.valueOfStrings(selectedStatus));
        if (translationZip == null) {
            messageUtil.addGlobalInfoMessage("Es wurden keine Elemente zum Übersetzen gefunden.");
        } else {
            renderDownloadDialog = true;
        }
        return null;
    }

    public String downloadDialogClose() {
        this.translationZip = null;
        if(backIdentifier.equals("artifact-overview")){
            return artifactOverviewController.display(modelIdentifier);
        }
        else if(backIdentifier.equals("index")){
            return modelOverviewController.display();
        }
        return null;
    }

    public StreamedContent getFile() {
        ByteArrayInputStream is = new ByteArrayInputStream(translationZip);
        return new DefaultStreamedContent(is, "zip", getFilename());
    }

    public String getFilename() {
        return "Translation_ML_" + modelIdentifier + ".zip";
    }

    public String getBackIdentifier() {
        return backIdentifier;
    }
}
