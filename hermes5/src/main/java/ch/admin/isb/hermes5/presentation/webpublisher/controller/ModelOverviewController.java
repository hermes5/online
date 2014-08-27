/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.presentation.common.SelectableRow;
import ch.admin.isb.hermes5.util.Logged;

@Named
@RequestScoped
public class ModelOverviewController extends AbstractViewController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    WebPublisherFacade webPublisherFacade;
    @Inject
    ModelUploadController modelUploadController;
    @Inject
    ArtifactOverviewController artifactOverviewController;
    @Inject
    MessagesUtil messagesUtil;
    @Inject
    TranslationZipDownloadController translationZipDownloadController;
    @Inject
    TranslationUploadController translationUploadController;
    @Inject
    PrintXmlDownloadController printXmlDownloadController;

    private List<SelectableRow<EPFModel>> models;

    public List<SelectableRow<EPFModel>> getModels() {
        return models;
    }

    @PostConstruct
    public void init() {
        display();
    }

    public String display() {
        this.models = new ArrayList<SelectableRow<EPFModel>>();
        List<EPFModel> epfModels = webPublisherFacade.getModels();
        for (EPFModel epfModel : epfModels) {
            models.add(new SelectableRow<EPFModel>(epfModel));
        }
        return getIdentifier();
    }

    @Logged
    public String deleteModel() {
        List<EPFModel> selectedModels = getSelectedModels();
        if (selectedModels.isEmpty()) {
            messagesUtil.addGlobalErrorMessage("Bitte mindestens ein Modell auswählen");
        }
        for (EPFModel model : selectedModels) {
            if (model.isPublished()) {
                messagesUtil.addGlobalErrorMessage("Das publizierte Modell kann nicht gelöscht werden");
                return null;
            }
        }
        for (EPFModel model : selectedModels) {
            webPublisherFacade.deleteModel(model.getIdentifier());
        }
        return display();
    }

    @Logged
    public String uploadButtonClicked() {
        return redirectTo(modelUploadController.display());
    }

    @Logged
    public String showModelDetailsClicked(String modelIdentifier) {
        return redirectTo(artifactOverviewController.display(modelIdentifier));
    }

    @Override
    public String getIdentifier() {
        return "index";
    }

    public String gotoTranslationZipExport() {
        if (notExactlyOneModelSelected()) {
            messagesUtil.addGlobalErrorMessage("Es muss genau ein Modell ausgewählt werden");
            return null;
        }
        return redirectTo(translationZipDownloadController.display(getFirstSelected().getIdentifier(), getIdentifier()));
    }

    public String gotoPrintXmlDownload() {
        if (notExactlyOneModelSelected()) {
            messagesUtil.addGlobalErrorMessage("Es muss genau ein Modell ausgewählt werden");
            return null;
        }
        return redirectTo(printXmlDownloadController.display(getFirstSelected().getIdentifier()));
    }

    public String gotoTranslationImport() {
        if (notExactlyOneModelSelected()) {
            messagesUtil.addGlobalErrorMessage("Es muss genau ein Modell ausgewählt werden");
            return null;
        } else if (webPublisherFacade.getPublishedModel() != null
                && getSelectedModels().get(0).getIdentifier()
                        .equals(webPublisherFacade.getPublishedModel().getIdentifier())) {
            messagesUtil.addGlobalErrorMessage("Beim publizierten Modell können keine Übersetzungen importiert werden");
            return null;
        }
        return redirectTo(translationUploadController.display(getFirstSelected().getIdentifier()));
    }

    private List<EPFModel> getSelectedModels() {
        List<EPFModel> selected = new ArrayList<EPFModel>();
        for (SelectableRow<EPFModel> model : models) {
            if (model.isSelected()) {
                selected.add(model.getData());
            }
        }
        return selected;
    }

    public String publishModel() {
        if (notExactlyOneModelSelected()) {
            messagesUtil.addGlobalErrorMessage("Es muss genau ein Modell ausgewählt werden");
        } else {
            EPFModel epfModel = getFirstSelected();
            if (epfModel.isPublished()) {
                messagesUtil.addGlobalErrorMessage("Modell ist bereits publiziert");
            } else {
                webPublisherFacade.publish(epfModel.getIdentifier());
                messagesUtil.addGlobalInfoMessage("Modell wurde erfolgreich publiziert");
                return display();
            }
        }
        return null;
    }

    private EPFModel getFirstSelected() {
        return getSelectedModels().get(0);
    }

    private boolean notExactlyOneModelSelected() {
        return getSelectedModels().size() != 1;
    }

}
