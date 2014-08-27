/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.TranslateableText;

/**
 * Wrapper used to cache all texts in a model
 *
 */
public class TranslationRepositoryWrapper implements LocalizationEngineSupport {
    private static final Logger logger = LoggerFactory.getLogger(TranslationRepositoryWrapper.class);

    private final TranslationRepository translationRepository;
    private final Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
    private final String modelIdentifier;

    public TranslationRepositoryWrapper(String modelIdentifier, TranslationRepository translationRepository,
            List<String> langs) {
        this.modelIdentifier = modelIdentifier;
        this.translationRepository = translationRepository;
        for (String lang : langs) {
            map.put(lang, new HashMap<String, String>());
        }
        for (TranslateableText translateableText : translationRepository.getTexts(modelIdentifier)) {
            String key = key(translateableText);
            for (String lang : langs) {
                if ("de".equals(lang)) {
                    map.get(lang).put(key, translateableText.getTextDe());
                }
                if ("fr".equals(lang)) {
                    map.get(lang).put(key, translateableText.getTextFr());
                }
                if ("it".equals(lang)) {
                    map.get(lang).put(key, translateableText.getTextIt());
                }
                if ("en".equals(lang)) {
                    map.get(lang).put(key, translateableText.getTextEn());
                }
            }
        }
    }

    private String key(TranslateableText translateableText) {
        return key(translateableText.getElementIdentifier(), translateableText.getTextIdentifier());
    }

    private String key(String elementIdentifier, String textIdentifier) {
        return elementIdentifier + "_" + textIdentifier;
    }

    @Override
    public String getDocumentUrl(String modelIdentifier, String urlDe, String language) {
        return translationRepository.getDocumentUrl(modelIdentifier, urlDe, language);
    }


    @Override
    public String
            getLocalizedText(String modelIdentifier, String lang, String elementIdentifier, String textIdentifier) {
        if(! this.modelIdentifier.equals(modelIdentifier)) {
            logger.error("Inconsistent model identifier");
            translationRepository.getLocalizedText(modelIdentifier, lang, elementIdentifier, textIdentifier);
        }
        return map.get(lang).get(key(elementIdentifier, textIdentifier));
    }

}
