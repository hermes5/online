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
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.admin.isb.hermes5.business.modelimport.EPFModelImporter;
import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.publish.OnlinePublikationPublishWorkflow;
import ch.admin.isb.hermes5.business.publish.PrintXmlDownloadWorkflow;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.translation.TranslationRepositoryWrapper;
import ch.admin.isb.hermes5.business.translationexport.TranslationExportWorkflow;
import ch.admin.isb.hermes5.business.translationimport.TranslationImporter;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.persistence.db.dao.TranslateableTextDAO;
import ch.admin.isb.hermes5.util.PerformanceLogged;

@Stateless
public class WebPublisherFacade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    EPFModelImporter epfModelImporter;
    @Inject
    TranslationRepository translationRepository;
    @Inject
    OnlinePublikationPublishWorkflow publishWorkflow;
    @Inject
    ModelRepository modelRepository;
    @Inject
    TranslationExportWorkflow translationExportWorkflow;
    @Inject
    TranslateableTextDAO translateableTextDAO;
    @Inject
    TranslationImporter translationImporter;
    @Inject
    PrintXmlDownloadWorkflow printXmlDownloadWorkflow;

    public List<EPFModel> getModels() {
        return modelRepository.getEPFModels();
    }

    public void deleteModel(String modelIdentifier) {
        modelRepository.deleteModel(modelIdentifier);
    }

    @PerformanceLogged
    public ImportResult importEPFModelFromZipFile(byte[] contents, String fileName, String title, String version) {
        return epfModelImporter.importEPFModelFromZipFile(contents, fileName, title, version);
    }

    public List<TranslationEntity> getTranslationEntities(String modelIdentifier) {
        return translationRepository.getTranslationEntities(modelIdentifier);
    }

    public TranslationDocument addOrUpdateArtifact(TranslationDocument translationEntity, byte[] contents,
            String filename, String lang) {
        String translationUrl = buildUrl(translationEntity.getUrlDe(), filename);
        if (contents != null) {
            epfModelImporter.addFile(translationEntity.getModelIdentifier(), lang, translationUrl, contents);
        }
        TranslationDocument updateTranslationEntity = translationRepository.updateTranslationEntity(translationEntity,
                translationUrl, lang);
        modelRepository.updateStatusAndUpdateDate(translationEntity.getModelIdentifier());
        return updateTranslationEntity;
    }

    private String buildUrl(String urlDe, String filename) {
        if (filename == null) {
            return null;
        }
        int lastIndexOf = urlDe.lastIndexOf("/");
        if (lastIndexOf > 0) {
            return urlDe.substring(0, lastIndexOf) + "/" + filename;
        }
        return filename;
    }

    @PerformanceLogged
    public byte[]
            getTranslationZip(String modelIdentifier, List<String> selectedLanguages, List<Status> selectedStatus) {
        return translationExportWorkflow.export(modelIdentifier, selectedLanguages, selectedStatus);
    }
    
    @PerformanceLogged
    public EPFModel publish(String modelIdentifier) {
        return publishWorkflow.publish(modelIdentifier);

    }

    public Image getImage(long id) {
        return translationRepository.getImage(id);
    }

    public TranslationDocument getDocument(long id) {
        return translationRepository.getDocument(id);
    }

    public EPFModel getModel(String modelIdentifier) {
        return modelRepository.getModelByIdentifier(modelIdentifier);
    }

    public ModelElement getModelElement(String modelIdentifier, String rootElementIdentifier) {
        return translationRepository.getModelElement(modelIdentifier, rootElementIdentifier);
    }

    public TranslateableText deleteTranslateableText(String modelIdentifier, String rootElementIdentifier,
            String textIdentifier, String lang) {
        TranslateableText deleteTranslateableText = translateableTextDAO.deleteTranslateableText(modelIdentifier,
                rootElementIdentifier, textIdentifier, lang);
        modelRepository.updateStatusAndUpdateDate(modelIdentifier);
        return deleteTranslateableText;
    }
    
    @PerformanceLogged
    public ImportResult importTranslationZip(String modelIdentifier, byte[] contents, List<String> languages) {
        ImportResult importTranslations = translationImporter.importTranslations(modelIdentifier, contents, languages);
        modelRepository.updateStatusAndUpdateDate(modelIdentifier);
        return importTranslations;
    }

    @PerformanceLogged
    public String getPrintXml(String modelIdentifier, String lang) {
        TranslationRepositoryWrapper translationRepositoryWrapper = new TranslationRepositoryWrapper(modelIdentifier, translationRepository, Arrays.asList(lang));
        LocalizationEngine localizationEngine = new LocalizationEngine(translationRepositoryWrapper, modelIdentifier, lang);
        return printXmlDownloadWorkflow.generatePrintXml(modelIdentifier, localizationEngine);
    }

    public EPFModel getPublishedModel() {
        return modelRepository.getPublishedModel();
    }

}
