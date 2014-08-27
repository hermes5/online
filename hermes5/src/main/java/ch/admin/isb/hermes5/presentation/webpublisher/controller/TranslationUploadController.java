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
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.util.Logged;

@Named
@SessionScoped
public class TranslationUploadController extends AbstractZipUploadViewController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    WebPublisherFacade webPublisherFacade;

    @Inject
    ArtifactOverviewController artifactOverviewController;

    private List<String> languages;

    private String modelIdentifier;

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @Logged
    public String display(String modelIdentifier) {
        this.modelIdentifier = modelIdentifier;
        super.reset();
        return getIdentifier();
    }

    protected ImportResult runImport() {
        return webPublisherFacade.importTranslationZip(modelIdentifier, getUploadedFile().getContents(), languages);
    }

    public String confirmSuccessDialog() {
        return redirectTo(artifactOverviewController.display(modelIdentifier));
    }

    @Override
    public String getIdentifier() {
        return "translation-upload";
    }

}
