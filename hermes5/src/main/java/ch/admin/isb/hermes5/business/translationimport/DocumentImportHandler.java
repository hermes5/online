/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationimport;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslationEntity.TranslationEntityType;
import ch.admin.isb.hermes5.persistence.db.dao.DocumentDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;

public class DocumentImportHandler implements TranslationImportHandler {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ImageImportHandler.class);

    @Inject
    DocumentDAO documentDAO;
    @Inject
    S3 s3;

    @Override
    public boolean isResponsible(String filename) {
        if (filename.contains(TranslationEntityType.DOCUMENT.nameDePlural())) {
            return true;
        }
        return false;
    }

    @Override
    public ImportResult handleImport(String modelIdentifier, byte[] content, List<String> languages,
            String translationZipurl) {
        ImportResult result = new ImportResult();
        String[] urlParts = translationZipurl.split("\\/");
        String docLang = urlParts[urlParts.length - 2];
        String docFolder = urlParts[urlParts.length - 3];
        String docFilename = urlParts[urlParts.length - 1];
        if (languages.contains(docLang)) {
            Document document = getDocByUrl(docFolder, modelIdentifier);
            if (document == null) {
                result.addError("Unable to find image " + docFolder + " in model.");
            } else {
                String url = "";
                try {
                    url = replaceFilenameInUrl(document.getUrlDe(), docFilename);
                    if (docLang.equals("fr")) {
                        document.setUrlFr(url);
                        document.setStatusFr(Status.FREIGEGEBEN);
                    } else if (docLang.equals("it")) {
                        document.setUrlIt(url);
                        document.setStatusIt(Status.FREIGEGEBEN);
                    } else if (docLang.equals("en")) {
                        document.setUrlEn(url);
                        document.setStatusEn(Status.FREIGEGEBEN);
                    } else {
                        logger.warn("Error: " + docLang + "is not a valid language");
                        result.addError("Unknown language: " + docLang + " in " + url);
                    }
                } catch (Exception e) {
                    result.addError("Unable to import: " + modelIdentifier + " -> " + url + ": " + e.getClass() + " "
                            + e.getMessage());
                }
                documentDAO.merge(document);
                s3.addFile(new ByteArrayInputStream(content), content.length, modelIdentifier, docLang, url);
                result.addSuccessResult(url + " -> " + docLang);
            }
        }
        return result;
    }

    private String replaceFilenameInUrl(String urlDe, String newFilename) {
        String result = "";
        String[] urlPartsDe = urlDe.split("\\/");
        for (int c = 0; c < (urlPartsDe.length - 1); c++) {
            result += (urlPartsDe[c] + "/");
        }
        result += newFilename;
        return result;
    }

    private Document getDocByUrl(String docFolder, String modelIdentifier) {
        List<Document> docs = documentDAO.getDocuments(modelIdentifier);
        for (Document doc : docs) {
            String folder = doc.getUrlDe().replaceAll("\\/", "_").replaceAll("\\.", "_");
            if (folder.equals(docFolder)) {
                return doc;
            }
        }
        return null;
    }

}
