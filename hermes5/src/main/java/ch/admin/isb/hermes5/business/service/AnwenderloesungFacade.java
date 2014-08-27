/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.publish.UserSzenarioPublisher;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.userszenario.SzenarioDownloadWorkflow;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.word.WordDocumentCustomizer;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.PerformanceLogged;
import ch.admin.isb.hermes5.util.StringUtil;

@Stateless
public class AnwenderloesungFacade implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(AnwenderloesungFacade.class);

    private static final long serialVersionUID = 1L;

    @Inject
    ModelRepository modelRepository;
    
    @Inject
    S3 s3;
    @Inject
    TranslationRepository translationRepository;

    @Inject
    WordDocumentCustomizer docx4jWordDocumentCustomizer;

    @Inject
    SzenarioDownloadWorkflow szenarioDownloadWorkflow;
    @Inject
    UserSzenarioPublisher onlinePublisher;

    @PerformanceLogged
    public List<Szenario> getSzenarien(String modelIdentifier) {
        return modelRepository.getSzenarien(modelIdentifier);
    }

    public EPFModel getPublishedModel() {
        return modelRepository.getPublishedModel();
    }

    @PerformanceLogged
    public byte[] generateDownloadZip(String modelIdentifiers, Szenario szenario, SzenarioUserData szenarioUserData,
            boolean projektstrukturplan, boolean dokumentation, boolean ergebnisvorlagen, boolean xmlModel,
            List<String> languages) {
        return szenarioDownloadWorkflow.generateDownloadZip(modelIdentifiers, szenario, szenarioUserData,
                projektstrukturplan, dokumentation, ergebnisvorlagen, xmlModel, languages);
    }

    public String localize(String modelIdentifier, Localizable localizable, String language) {
        return new LocalizationEngine(translationRepository, modelIdentifier, language).localize(localizable);
    }

    public List<Modul> getModulesWithErgebnisse(String modelIdentifier) {
        return modelRepository.getModulesWithErgebnisse(modelIdentifier);
    }

    public List<Document> getDocumentsOfModule(String modelIdentifier, Modul modul) {
        List<Document> documents = new ArrayList<Document>();
        for (Ergebnis ergebnis : modul.getErgebnisse()) {
            for (String urlDe : ergebnis.getTemplateAttachmentUrls()) {
                Document document = translationRepository.getDocument(modelIdentifier, urlDe);
                if (document == null) {
                    logger.warn("unable to find document " + urlDe + " in model " + modelIdentifier);
                } else {
                    documents.add(document);
                }
            }
        }
        return documents;
    }

    public List<Localizable> getWebLinksOfModule(String modelIdentifier, Modul modul) {
        List<Localizable> webLinks = new ArrayList<Localizable>();
        for (Ergebnis ergebnis : modul.getErgebnisse()) {
            webLinks.addAll(ergebnis.getWebAttachmentUrls());
        }

        return webLinks;
    }

    public byte[] readDocument(String modelIdentifier, String lang, String url) {
        byte[] readFile = s3.readFile(modelIdentifier, lang, url);
        return docx4jWordDocumentCustomizer.adjustDocumentWithUserData(url, readFile, new SzenarioUserData());
    }

    @PerformanceLogged
    public byte[] generateVorlagenZip(List<Document> selectedDocument, String language) {
        ZipOutputBuilder zipOutputBuilder = new ZipOutputBuilder();
        for (Document document : selectedDocument) {
            String url = document.getUrl(language);
            String filename = StringUtil.replaceSpecialChars(StringUtil.getLinkName(url));
            if (!zipOutputBuilder.containsFile(filename)) {
                byte[] readFile = readDocument(document.getModelIdentifier(), language, url);
                if (readFile != null) {
                    zipOutputBuilder.addFile(filename, readFile);
                } else {
                    logger.error("no file found " + document.getModelIdentifier() + ", " + language + ", " + url);
                }
            }
        }
        if (zipOutputBuilder.isEmpty()) {
            return null;
        }
        return zipOutputBuilder.getResult();
    }

    @PerformanceLogged
    public String publishOnline(byte[] zipFile, String mainLanguage) {
        return onlinePublisher.publishOnline(zipFile, mainLanguage);
    }

    public byte[] getPublishedUserFile(String url) {
        return s3.readUserszenarioFile(url);
    }

    public byte[] getPublishedSzenarioFile(String modelIdentifier, String url) {
        return s3.readSampelszenarioFile(modelIdentifier, url);
    }

}
