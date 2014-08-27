/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.common.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.presentation.anwenderloesung.controller.LocaleController;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@Named
@RequestScoped
public class TopMenuController {

    @Inject
    @SystemProperty(value = "ueberhermes.url.de",
            fallback = "http://www.isb.admin.ch/themen/methoden/01661/index.html?lang=de")
    ConfigurationProperty ueberHermesUrlDe;
    @Inject
    @SystemProperty(value = "ueberhermes.url.fr",
            fallback = "http://www.isb.admin.ch/themen/methoden/01661/index.html?lang=fr")
    ConfigurationProperty ueberHermesUrlFr;
    @Inject
    @SystemProperty(value = "ueberhermes.url.it",
            fallback = "http://www.isb.admin.ch/themen/methoden/01661/index.html?lang=it")
    ConfigurationProperty ueberHermesUrlIt;
    @Inject
    @SystemProperty(value = "ueberhermes.url.en",
            fallback = "http://www.isb.admin.ch/themen/methoden/01661/index.html?lang=en")
    ConfigurationProperty ueberHermesUrlEn;
    @Inject
    LocaleController localeController;

    public String getUeberHermesUrl() {
        String lang = localeController.getLanguage();
        if ("fr".equals(lang)) {
            return ueberHermesUrlFr.getStringValue();
        }
        if ("it".equals(lang)) {
            return ueberHermesUrlIt.getStringValue();
        }
        if ("en".equals(lang)) {
            return ueberHermesUrlEn.getStringValue();
        }
        return ueberHermesUrlDe.getStringValue();
    }

}
