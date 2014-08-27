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

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.util.Logged;
import ch.admin.isb.hermes5.validation.UserInput;

@Named
@SessionScoped
public class ModelUploadController extends AbstractZipUploadViewController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    WebPublisherFacade webPublisherFacade;

    @Inject
    ModelOverviewController modelOverviewController;

    @UserInput(pattern = UserInput.CHARS_NUMBERS_SPACES_PATTERN)
    private String uploadTitle;
    @UserInput(pattern = UserInput.CHARS_NUMBERS_SPACES_PATTERN)
    private String uploadVersion;

    @Logged
    public String display() {
        super.reset();
        uploadTitle = null;
        uploadVersion = null;
        return getIdentifier();
    }

    @Override
    protected ImportResult runImport() {
        return webPublisherFacade.importEPFModelFromZipFile(getUploadedFile().getContents(), getUploadedFile()
                .getFileName(), getUploadTitle(), getUploadVersion());
    }

    public String getUploadTitle() {
        return uploadTitle;
    }

    public void setUploadTitle(String uploadTitle) {
        this.uploadTitle = uploadTitle;
    }

    public String getUploadVersion() {
        return uploadVersion;
    }

    public void setUploadVersion(String uploadVersion) {
        this.uploadVersion = uploadVersion;
    }

    public String confirmSuccessDialog() {
        return redirectTo(modelOverviewController.display());
    }

    @Override
    public String getIdentifier() {
        return "model-upload";
    }

}
