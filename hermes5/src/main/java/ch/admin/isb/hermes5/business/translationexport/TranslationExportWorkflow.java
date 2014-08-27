/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationexport;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.Logged;

public class TranslationExportWorkflow implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TranslationExportWorkflow.class);

    private static final long serialVersionUID = 1L;

    @Inject
    TranslationRepository translationRepository;

    @Inject
    @Any
    Instance<TranslationExportWorkflowProcessor> translationExportProcessors;

    @Logged
    public byte[] export(String modelIdentifier, List<String> selectedLanguages, List<Status> selectedStatus) {
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        List<TranslationEntity> translationEntities = translationRepository.getTranslationEntities(modelIdentifier);
        for (TranslationEntity translationEntity : translationEntities) {
            List<String> langsGenerated = new ArrayList<String>();
            TranslationExportWorkflowProcessor processor = getResposibleProcessor(translationEntity);
            for (String lang : getLangsToGenerate(translationEntity, selectedLanguages, selectedStatus)) {
                byte[] file = processor.processEntity(translationEntity, lang);
                if (file != null) {
                    String path = processor.path(modelIdentifier, translationEntity, lang);
                    zipBuilder.addFile(path, file);
                    langsGenerated.add(lang);
                } else {
                    logger.warn("unable to process " + translationEntity + " in lang " + lang);
                }
            }
            if (!langsGenerated.isEmpty()) {
                processor.markAsInArbeit(translationEntity, langsGenerated);
            }
        }
        if (zipBuilder.isEmpty()) {
            zipBuilder.close();
            return null;
        }
        InputStream anleitung = getClass().getResourceAsStream("Anleitung.txt");
        zipBuilder.addFile("Anleitung.txt", IOUtil.readToByteArray(anleitung));
        return zipBuilder.getResult();
    }

    private TranslationExportWorkflowProcessor getResposibleProcessor(TranslationEntity translationEntity) {
        for (TranslationExportWorkflowProcessor processor : translationExportProcessors) {
            if (processor.isResponsible(translationEntity)) {
                return processor;
            }
        }
        throw new IllegalStateException("No processor found for " + translationEntity + " in "
                + translationExportProcessors);
    }

    private List<String> getLangsToGenerate(TranslationEntity translationEntity, List<String> selectedLanguages,
            List<Status> selectedStatus) {
        List<String> result = new ArrayList<String>();
        if (selectedLanguages.contains("fr") && selectedStatus.contains(translationEntity.getStatusFr())) {
            result.add("fr");
        }
        if (selectedLanguages.contains("it") && selectedStatus.contains(translationEntity.getStatusIt())) {
            result.add("it");
        }
        if (selectedLanguages.contains("en") && selectedStatus.contains(translationEntity.getStatusEn())) {
            result.add("en");
        }
        if (!result.isEmpty()) {
            result.add("de");
        }
        return result;
    }

}
