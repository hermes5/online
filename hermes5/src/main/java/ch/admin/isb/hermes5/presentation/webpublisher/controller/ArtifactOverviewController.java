/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.domain.TranslationEntity.TranslationEntityType;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.Logged;
import ch.admin.isb.hermes5.util.SystemProperty;

@Named
@SessionScoped
public class ArtifactOverviewController extends AbstractViewController implements Serializable {

    private static final long serialVersionUID = 1L;

    // "http://localhost:8080/s3mock?url="
    // http://development.hermes5.s3.amazonaws.com
    @Inject
    @SystemProperty(value = "s3.publicBucketUrl")
    ConfigurationProperty s3PublicBucketUrl;

    @Inject
    WebPublisherFacade webPublisherFacade;

    @Inject
    MessagesUtil messagesUtil;

    @Inject
    TranslationZipDownloadController translationZipDownloadController;
    @Inject
    TranslationUploadController translationUploadController;

    private List<TranslationEntity> translationEntities;
    private SelectItem[] filterOptionsType;

    private SelectItem[] filterOptionsStatus;

    private EPFModel model;

    @Override
    public String getIdentifier() {
        return "artifact-overview";
    }

    public List<TranslationEntity> getTranslationEntities() {
        return translationEntities;
    }

    public String getLinkDeForModelElement(ModelElement e) {
        return "modelelement/" + model.getIdentifier() + "/" + e.getRootElementIdentifier();
    }

    public String getLinkDeForDocument(TranslationDocument e) {
        if (e.getType() == TranslationEntityType.IMAGE) {
            return "imagedisplay/" + ((Image) e).getId();
        }
        return s3PublicBucketUrl.getStringValue() + "/" + e.getModelIdentifier() + "/de/" + e.getUrlDe();
    }

    public String getLinkFr(Document e) {
        return s3PublicBucketUrl.getStringValue() + "/" + e.getModelIdentifier() + "/fr/" + e.getUrlFr();
    }

    public String getLinkNameFr(Document e) {
        return getLinkName(e.getUrlFr());
    }

    public String getLinkEn(Document e) {
        return s3PublicBucketUrl.getStringValue() + "/" + e.getModelIdentifier() + "/en/" + e.getUrlEn();
    }
    
    public String getLinkNameEn(Document e) {
        return getLinkName(e.getUrlEn());
    }

    public String getLinkIt(Document e) {
        return s3PublicBucketUrl.getStringValue() + "/" + e.getModelIdentifier() + "/it/" + e.getUrlIt();
    }
    
    public String getLinkNameIt(Document e) {
        return getLinkName(e.getUrlIt());
    }

    public boolean renderUploadArtifactLink(TranslationEntity e) {
        return e.getType() == TranslationEntityType.IMAGE || e.getType() == TranslationEntityType.DOCUMENT;
    }

    public String uploadArtifactLink(TranslationDocument translationDocument, String lang) {
        return "artifactupload/" + lang + "/" + translationDocument.getType().name().toLowerCase() + "/"
                + translationDocument.getId();
    }

    @Logged
    public String gotoTranslationZipExport() {
        return redirectTo(translationZipDownloadController.display(model.getIdentifier(), "artifact-overview"));
    }

    @Logged
    public String gotoTranslationImport() {
        if (model.isPublished()) {
            messagesUtil.addGlobalErrorMessage("Beim publizierten Modell können keine Übersetzungen importiert werden");
            return null;
        }
        return redirectTo(translationUploadController.display(model.getIdentifier()));
    }

    public EPFModel getModel() {
        return model;
    }

    @Logged
    public String display(String modelIdentifier) {
        model = webPublisherFacade.getModel(modelIdentifier);
        return display();
    }

    public String display() {
        translationEntities = webPublisherFacade.getTranslationEntities(model.getIdentifier());
        filterOptionsType = createFilterOptions(extractTypes());
        filterOptionsStatus = createFilterOptions(extractStatus());
        return getIdentifier();
    }

    private Collection<String> extractTypes() {
        Set<String> set = new HashSet<String>();
        for (TranslationEntity te : translationEntities) {
            set.add(te.getTypeName());
        }
        return set;
    }

    private Collection<String> extractStatus() {
        Set<String> set = new HashSet<String>();
        for (Status status : Status.values()) {
            set.add(status.getNameDe());
        }
        return set;
    }

    @Logged
    public SelectItem[] getFilterOptionsType() {
        return filterOptionsType;
    }

    @Logged
    public SelectItem[] getFilterOptionsStatus() {
        return filterOptionsStatus;
    }

    private SelectItem[] createFilterOptions(Collection<String> src) {
        List<String> data = new ArrayList<String>(src);
        Collections.sort(data);
        SelectItem[] options = new SelectItem[data.size() + 1];

        options[0] = new SelectItem("", "Select");

        for (int i = 0; i < data.size(); i++) {
            options[i + 1] = new SelectItem(data.get(i), data.get(i));
        }

        return options;
    }

    public String publishModel() {
        if (model.isPublished()) {
            messagesUtil.addGlobalErrorMessage("Modell ist bereits publiziert");
        } else {
            webPublisherFacade.publish(model.getIdentifier());
            messagesUtil.addGlobalInfoMessage("Modell wurde erfolgreich publiziert");
        }
        return null;
    }
}
