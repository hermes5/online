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
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.presentation.common.SelectableRow;

@SessionScoped
public class VorlagenControllerSession implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<SelectableRow<Modul>> modules;
    private Map<Modul, List<SelectableRow<Document>>> documents;
    private Map<Modul, List<Localizable>> webLinks;
    private byte[] zipFile;
    private boolean renderDownloadDialog;
    private String modelIdentifier;

    public void init(String modelIdentifier, List<SelectableRow<Modul>> modules,
            Map<Modul, List<SelectableRow<Document>>> documents, Map<Modul, List<Localizable>> webLinks) {
        zipFile = null;
        renderDownloadDialog = false;
        this.documents = documents;
        this.modelIdentifier = modelIdentifier;
        this.modules = modules;
        this.webLinks = webLinks;
    }

    public String getModelIdentifier() {
        return modelIdentifier;
    }

    public boolean isRenderDownloadDialog() {
        return renderDownloadDialog;
    }

    public void setRenderDownloadDialog() {
        this.renderDownloadDialog = true;
    }

    public void setZipFile(byte[] zipFile) {
        this.zipFile = zipFile;
    }

    public List<SelectableRow<Modul>> getModules() {
        return modules;
    }

    public List<SelectableRow<Document>> getDocuments(Modul modul) {
        return documents.get(modul);
    }

    public Map<Modul, List<SelectableRow<Document>>> getDocuments() {
        return documents;
    }

    public Map<Modul, List<Localizable>> getWebLinks() {
        return webLinks;
    }

    public byte[] getZipFile() {
        return zipFile;
    }

    public void reset() {
        zipFile = null;
        renderDownloadDialog = false;
    }
}
