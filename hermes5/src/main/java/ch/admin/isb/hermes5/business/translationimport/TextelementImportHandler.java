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

import ch.admin.isb.hermes5.business.word.TranslationWordAdapter;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.domain.TranslationEntity.TranslationEntityType;
import ch.admin.isb.hermes5.persistence.db.dao.TranslateableTextDAO;

public class TextelementImportHandler implements TranslationImportHandler {

    private static final Logger logger = LoggerFactory.getLogger(TextelementImportHandler.class);
    private static final long serialVersionUID = 1L;

    @Inject
    TranslationWordAdapter wordAdapter;

    @Inject
    TranslateableTextDAO translateableTextDAO;

    @Override
    public boolean isResponsible(String filename) {
        if (filename.contains(TranslationEntityType.TEXT_ELEMENT.nameDePlural())) {
            return true;
        }
        return false;
    }

    @Override
    public ImportResult handleImport(String modelIdentifier, byte[] content, List<String> languages, String docUrl) {
        ImportResult result = new ImportResult();
        String docLang = docUrl.substring(docUrl.lastIndexOf('.') - 2, docUrl.lastIndexOf('.'));
        if (languages.contains(docLang)) {
            try {
                List<TranslateableText> texts = wordAdapter.read(new ByteArrayInputStream(content), docLang);
                for (TranslateableText translatedText : texts) {
                    try {
                        TranslateableText actualText = translateableTextDAO.getSpecificText(modelIdentifier,
                                translatedText.getElementIdentifier(), translatedText.getTextIdentifier());
                        if (actualText != null) {
                            if (languages.contains("fr") && docLang.equals("fr")) {
                                actualText.setTextFr(translatedText.getTextFr());
                                actualText.setStatusFr(Status.FREIGEGEBEN);
                            }
                            if (languages.contains("it") && docLang.equals("it")) {
                                actualText.setTextIt(translatedText.getTextIt());
                                actualText.setStatusIt(Status.FREIGEGEBEN);
                            }
                            if (languages.contains("en") && docLang.equals("en")) {
                                actualText.setTextEn(translatedText.getTextEn());
                                actualText.setStatusEn(Status.FREIGEGEBEN);
                            }
                            translateableTextDAO.merge(actualText);
                            result.addSuccessResult(docUrl + " -> " + docLang);
                        } else {
                            result.addError("Unable to import: " + modelIdentifier + " "
                                    + translatedText.getElementIdentifier() + "/" + translatedText.getTextIdentifier());
                        }
                    } catch (Exception e) {
                        logger.warn("Error on " + docUrl + " -> " + docLang, e);
                        result.addError("Error on " + docUrl + " -> " + docLang + ": " + e.getClass() + " "
                                + e.getMessage());
                    }
                }
            } catch (Exception e) {
                logger.warn("Error on " + docUrl + " -> " + docLang, e);
                result.addError("Error on " + docUrl + " -> " + docLang + ": " + e.getClass() + " " + e.getMessage());
            }
        }
        return result;
    }
}
