/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.presentation.common.SelectableRow;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.WebFont;

@Named
@RequestScoped
public class VorlagenController {

    @Inject
    PresentationNameComparator presentationNameComparator;

    @Inject
    AnwenderloesungFacade anwenderloesungFacade;

    @Inject
    LocaleController localeController;

    @Inject
    VorlagenControllerSession vorlagenControllerSession;

    @Inject
    Localizer localizer;

    @PostConstruct
    public void init() {
        String modelIdentifier = anwenderloesungFacade.getPublishedModel().getIdentifier();
        if (!modelIdentifier.equals(vorlagenControllerSession.getModelIdentifier())) {
            initialLoad();
        }
    }

    public void initialLoad() {
        ArrayList<SelectableRow<Modul>> modules = new ArrayList<SelectableRow<Modul>>();
        HashMap<Modul, List<SelectableRow<Document>>> documents = new HashMap<Modul, List<SelectableRow<Document>>>();
        HashMap<Modul, List<Localizable>> webLinks = new HashMap<Modul, List<Localizable>>();

        String modelIdentifier = anwenderloesungFacade.getPublishedModel().getIdentifier();
        List<Modul> modulesWithErgebnisse = anwenderloesungFacade.getModulesWithErgebnisse(modelIdentifier);
        Collections.sort(modulesWithErgebnisse, presentationNameComparator);
        for (Modul modul : modulesWithErgebnisse) {
            List<SelectableRow<Document>> documentsOfModul = new ArrayList<SelectableRow<Document>>();
            for (Document document : anwenderloesungFacade.getDocumentsOfModule(modelIdentifier, modul)) {
                documentsOfModul.add(new SelectableRow<Document>(document));
            }

            List<Localizable> webLinksOfModule = anwenderloesungFacade.getWebLinksOfModule(modelIdentifier, modul);

            if (!(documentsOfModul.isEmpty() && webLinksOfModule.isEmpty())) {
                modules.add(new SelectableRow<Modul>(modul));
            }

            if (!documentsOfModul.isEmpty()) {
                documents.put(modul, documentsOfModul);
            }

            if (!webLinksOfModule.isEmpty()) {
                webLinks.put(modul, webLinksOfModule);
            }
        }

        vorlagenControllerSession.init(modelIdentifier, modules, documents, webLinks);
    }

    public StreamedContent getFile() {
        ByteArrayInputStream is = new ByteArrayInputStream(vorlagenControllerSession.getZipFile());
        return new DefaultStreamedContent(is, "application/zip", getFilename());
    }

    public String downloadSelected() {
        List<Document> selectedDocument = new ArrayList<Document>();
        for (Entry<Modul, List<SelectableRow<Document>>> entry : vorlagenControllerSession.getDocuments().entrySet()) {
            List<SelectableRow<Document>> value = entry.getValue();
            for (SelectableRow<Document> selectableRow : value) {
                if (selectableRow.isSelected()) {
                    Document data = selectableRow.getData();
                    selectedDocument.add(data);
                }
            }
        }

        byte[] zipFile = anwenderloesungFacade.generateVorlagenZip(selectedDocument, localeController.getLanguage());
        if (zipFile != null) {
            vorlagenControllerSession.setZipFile(zipFile);
            vorlagenControllerSession.setRenderDownloadDialog();
        }

        return null;
    }

    public List<SelectableRow<Modul>> getModules() {
        return vorlagenControllerSession.getModules();
    }

    public boolean isRenderDownloadDialog() {
        return vorlagenControllerSession.isRenderDownloadDialog();
    }

    public List<SelectableRow<Document>> getDocuments(Modul modul) {
        return vorlagenControllerSession.getDocuments(modul);
    }

    public List<String> getWebLinks(Modul modul) {
        List<String> localizedWebLinks = new ArrayList<String>();

        List<Localizable> webLinksOfModule = vorlagenControllerSession.getWebLinks().get(modul);
        if (webLinksOfModule != null) {
            for (Localizable webLink : webLinksOfModule) {
                localizedWebLinks.add(StringUtil.addTargetToHtmlLink(localizer.localize(webLink)));
            }
        }

        return localizedWebLinks;
    }

    public String downloadDialogClose() {
        vorlagenControllerSession.reset();
        return null;
    }

    public String getFilename() {
        return "Templates.zip";
    }

    public String getDocumentUrl(Document document) {
        String language = localeController.getLanguage();
        return "/anwenderloesung/vorlagen/" + document.getModelIdentifier() + "/" + language + "/"
                + document.getUrl(language);
    }

    public String getDocumentName(Document document) {
        String language = localeController.getLanguage();
        String url = document.getUrl(language);
        return StringUtil.getLinkName(url);
    }

    public WebFont getWebFontFor(String filename) {
        return WebFont.forFilename(filename);
    }
}
