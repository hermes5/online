/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationexport;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.domain.TranslationEntity.TranslationEntityType;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.StringUtil;

public class TranslationExportWorkflowTranslationDocumentProcessor implements TranslationExportWorkflowProcessor {

    @Inject
    TranslationRepository translationRepository;
    @Inject
    S3 s3;

    @Override
    public String path(String modelIdentifier, TranslationEntity translationEntity, String lang) {
        TranslationDocument translationDocument = (TranslationDocument) translationEntity;
        return "MethodLibrary_" + modelIdentifier + "/" + translationDocument.getType().nameDePlural() + "/"
                +buildIdentifer(translationDocument.getUrlDe()) + "/" + lang + "/"
                + new File(getUrlOfFileToBeExported(translationDocument, lang)).getName();
    }

    private String buildIdentifer(String urlDe) {
        return urlDe.replaceAll("/", "_").replaceAll("\\.", "_");
    }

    private String getUrlOfFileToBeExported(TranslationDocument translationDocument, String lang) {
        if ("fr".equals(lang) && StringUtil.isNotBlank(translationDocument.getUrlFr())) {
            return translationDocument.getUrlFr();
        }
        if ("it".equals(lang) && StringUtil.isNotBlank(translationDocument.getUrlIt())) {
            return translationDocument.getUrlIt();
        }
        if ("en".equals(lang) && StringUtil.isNotBlank(translationDocument.getUrlEn())) {
            return translationDocument.getUrlEn();
        }
        return translationDocument.getUrlDe();
    }

    private String getLangToBeExported(TranslationDocument translationDocument, String lang) {
        if (("fr".equals(lang) && StringUtil.isNotBlank(translationDocument.getUrlFr()))
                || ("it".equals(lang) && StringUtil.isNotBlank(translationDocument.getUrlIt())) || "en".equals(lang)
                && StringUtil.isNotBlank(translationDocument.getUrlEn())) {
            return lang;
        }
        return "de";
    }


    @Override
    public byte[] processEntity(TranslationEntity translationEntity, String lang) {
        TranslationDocument translationDocument = (TranslationDocument) translationEntity;
        String urlOfFileToBeExported = getUrlOfFileToBeExported(translationDocument, lang);
        String langToBeExported = getLangToBeExported(translationDocument, lang);
        return s3.readFile(translationDocument.getModelIdentifier(), langToBeExported, urlOfFileToBeExported);
    }

    @Override
    public void markAsInArbeit(TranslationEntity translationEntity, List<String> langs) {
        TranslationDocument td = (TranslationDocument) translationEntity;
        translationRepository.markTranslationDocumentAsInArbeit(td, langs);
    }

    @Override
    public boolean isResponsible(TranslationEntity translationEntity) {
        return translationEntity.getType() == TranslationEntityType.DOCUMENT
                || translationEntity.getType() == TranslationEntityType.IMAGE;
    }

}
