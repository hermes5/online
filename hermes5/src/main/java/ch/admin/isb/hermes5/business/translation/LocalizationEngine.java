/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.CustomLocalizable;
import ch.admin.isb.hermes5.domain.DefaultLocalizable;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.i18n.Hermes5ResourceBundle;

public class LocalizationEngine {

    private static final Logger logger = LoggerFactory.getLogger(LocalizationEngine.class);
    private final LocalizationEngineSupport translationRepository;
    private final String modelIdentifier;

    private final Hermes5ResourceBundle hermes5ResourceBundle;
    private final String lang;

    public LocalizationEngine(LocalizationEngineSupport translationRepository, String modelIdentifier, String lang) {
        this.translationRepository = translationRepository;
        this.modelIdentifier = modelIdentifier;
        this.lang = lang;
        hermes5ResourceBundle = new Hermes5ResourceBundle(lang);
    }


    public String documentUrl(String urlDe) {
        String documentUrl = translationRepository.getDocumentUrl(modelIdentifier, urlDe, lang);
        if (documentUrl == null) {
            logger.warn("Unable to locate Url \"" + urlDe + "\" for lang " + lang, new Exception());
        }
        return documentUrl;
    }

    public String getModelIdentifier() {
        return modelIdentifier;
    }


    public String localize(Localizable localizable) {
        if (localizable == null) {
            return "";
        }
        if (localizable instanceof DefaultLocalizable) {
            DefaultLocalizable defaultLocalizable = (DefaultLocalizable) localizable;
            String localizedText = translationRepository.getLocalizedText(modelIdentifier, lang,
                    defaultLocalizable.getElementIdentifier(), defaultLocalizable.getTextIdentifier());
            return localizedText != null ? localizedText : "";

        }
        if (localizable instanceof CustomLocalizable) {
            CustomLocalizable customLocalizable = (CustomLocalizable) localizable;
            String localizedText = customLocalizable.getText(lang);
            return localizedText != null ? localizedText : "";
        }
        return "";
    }

    public String text(String key) {
        if (hermes5ResourceBundle.containsKey(key)) {
            return hermes5ResourceBundle.getString(key);
        }
        return "??? " + key + " ???";
    }

    public String getLanguage() {
        return lang;
    }
}
