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
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.domain.TranslationEntity.TranslationEntityType;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.util.Logged;

@Named
@SessionScoped
public class ArtifactUploadController extends AbstractViewController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ArtifactUploadController.class);
    private static final long serialVersionUID = 1L;

    @Inject
    WebPublisherFacade webPublisherFacade;

    @Inject
    ArtifactOverviewController artifactOverviewController;
    @Inject
    ImageDisplayController imageDisplayController;

    @Inject
    MessagesUtil messagesUtil;

    private UploadedFile uploadedFile;
    private boolean renderSuccessDialog;

    private TranslationDocument translationEntity;
    private String lang;
    private String backIdentifier;

    private List<String> allowedFileTypes;

    public String getBackIdentifier() {
        return backIdentifier;
    }

    @Logged
    public String display(TranslationDocument translationEntity, String lang, String backIdentifier) {
        this.translationEntity = translationEntity;
        this.lang = lang;
        this.backIdentifier = backIdentifier;
        this.allowedFileTypes = translationEntity.getType() == TranslationEntityType.IMAGE ? Arrays.asList(".png",
                ".jpeg", ".jpg", ".gif") : Arrays.asList(".doc", ".docx", ".xls", ".xlsx", "dot", "dotx", "ppt", "pptx");
        renderSuccessDialog = false;
        uploadedFile = null;
        return getIdentifier();
    }

    @Logged
    public String handleFileUpload() {
        renderSuccessDialog = false;
        if (uploadedFile != null) {
            if (isAllowed(uploadedFile.getFileName())) {
                try {
                    translationEntity = webPublisherFacade.addOrUpdateArtifact(translationEntity,
                            uploadedFile.getContents(), uploadedFile.getFileName(), lang);
                    renderSuccessDialog = true;
                } catch (Exception e) {
                    logger.info("Beim Hochladen ist ein Fehler aufgetreten", e);
                    messagesUtil.addGlobalErrorMessage("Beim Hochladen ist ein unbekannter Fehler aufgetreten");
                }
            } else {
                messagesUtil.addGlobalErrorMessage("Unerlaubter Dateityp");
            }
        } else {
            messagesUtil.addGlobalErrorMessage("Bitte ein File angeben.");
        }
        return null;
    }

    private boolean isAllowed(String fileName) {
        for (String string : allowedFileTypes) {
            if (fileName.toLowerCase().endsWith(string)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRenderSuccessDialog() {
        return renderSuccessDialog;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    @Override
    public String getIdentifier() {
        return "artifact-upload";
    }

    public String confirmSuccessDialog() {
        if (backIdentifier.equals(artifactOverviewController.getIdentifier())) {
            return redirectTo(artifactOverviewController.display());
        } else if (backIdentifier.equals(imageDisplayController.getIdentifier())) {
            return imageDisplayController.display((Image) this.translationEntity);
        }
        return redirectTo(backIdentifier);
    }

}
