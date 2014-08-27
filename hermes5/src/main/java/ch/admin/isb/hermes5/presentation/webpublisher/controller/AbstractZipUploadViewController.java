/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import javax.inject.Inject;

import org.primefaces.model.UploadedFile;

import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.util.Logged;

public abstract class AbstractZipUploadViewController extends AbstractViewController {


    @Inject
    MessagesUtil messagesUtil;

    private UploadedFile uploadedFile;
    private boolean renderSuccessDialog;
    private ImportResult importResult;

    public void reset() {
        uploadedFile = null;
        renderSuccessDialog = false;
    }

    @Logged
    public String handleFileUpload() {
        renderSuccessDialog = false;
        if (uploadedFile != null) {
            if (!uploadedFile.getFileName().toLowerCase().endsWith(".zip")) {
                messagesUtil.addGlobalErrorMessage("Nur Zip Files sind erlaubt.");
            } else {
                try {
                    importResult = runImport();
                    renderSuccessDialog = true;
                } catch (Exception e) {
                    messagesUtil.addGlobalErrorMessage("Beim Importieren ist ein unerwarteter Fehler aufgetreten");
                }
            }
        } else {
            messagesUtil.addGlobalErrorMessage("Bitte ein File angeben.");
        }
        return null;
    }

    public ImportResult getImportResult() {
        return importResult;
    }

    protected abstract ImportResult runImport();

    public boolean isRenderSuccessDialog() {
        return renderSuccessDialog;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

}
