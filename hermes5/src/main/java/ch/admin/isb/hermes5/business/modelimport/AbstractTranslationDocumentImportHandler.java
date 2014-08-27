/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.StringUtil;


public abstract class AbstractTranslationDocumentImportHandler implements ModelImportHandler {

    private static final long serialVersionUID = 1L;
    @Inject
    ModelRepository modelRepository;

    @Inject
    S3 s3;

    private EPFModel publishedModel;

    @PostConstruct
    public void init() {
        publishedModel = modelRepository.getPublishedModel();
    }

    @Override
    public ImportResult handleImport(String modelIdentifier, byte[] content, String urlDe) {
        ImportResult importResult = new ImportResult();
        TranslationDocument translationDocument = createTranslationDocument(modelIdentifier, urlDe);
        translationDocument.setStatusFr(Status.UNVOLLSTAENDIG);
        translationDocument.setStatusIt(Status.UNVOLLSTAENDIG);
        translationDocument.setStatusEn(Status.UNVOLLSTAENDIG);

        if (publishedModel != null) {
            String publishedModelIdentifier = publishedModel.getIdentifier();
            TranslationDocument publishedDocument = readTranslationDocument(urlDe, publishedModelIdentifier);
            if (publishedDocument != null && isGermanEqual(modelIdentifier, publishedModelIdentifier, urlDe)) {
                // Francais
                String urlFr = publishedDocument.getUrlFr();
                if (StringUtil.isNotBlank(urlFr)) {
                    byte[] readFile = s3.readFile(publishedModelIdentifier, "fr", urlFr);
                    s3.addFile(new ByteArrayInputStream(readFile), readFile.length, modelIdentifier, "fr", urlFr);
                    translationDocument.setUrlFr(urlFr);
                    translationDocument.setStatusFr(Status.FREIGEGEBEN);
                }

                // Italiano
                String urlIt = publishedDocument.getUrlIt();
                if (StringUtil.isNotBlank(urlIt)) {
                    byte[] readFile = s3.readFile(publishedModelIdentifier, "it", urlIt);
                    s3.addFile(new ByteArrayInputStream(readFile), readFile.length, modelIdentifier, "it", urlIt);
                    translationDocument.setUrlIt(urlIt);
                    translationDocument.setStatusIt(Status.FREIGEGEBEN);
                }

                // English
                String urlEn = publishedDocument.getUrlEn();
                if (StringUtil.isNotBlank(urlEn)) {
                    byte[] readFile = s3.readFile(publishedModelIdentifier, "en", urlEn);
                    s3.addFile(new ByteArrayInputStream(readFile), readFile.length, modelIdentifier, "en", urlEn);
                    translationDocument.setUrlEn(urlEn);
                    translationDocument.setStatusEn(Status.FREIGEGEBEN);
                }
            }
        }

        mergeTranslationDocument(translationDocument);
        importResult.addSuccessResult(urlDe);
        return importResult;
    }

    protected abstract void mergeTranslationDocument(TranslationDocument translationDocument);

    protected abstract TranslationDocument readTranslationDocument(String filename, String publishedModelIdentifier);

    protected abstract TranslationDocument createTranslationDocument(String modelIdentifier, String filename);

    private Boolean isGermanEqual(String modelIdentifier, String publishedModelIdentifier, String urlDe) {
        byte[] currentDe = s3.readFile(modelIdentifier, "de", urlDe);

        try {
            byte[] publishedDe = s3.readFile(publishedModelIdentifier, "de", urlDe);
            return Arrays.equals(currentDe, publishedDe);
        } catch (Exception e) {
            return false;
        }
    }
}
