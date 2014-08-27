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
public class WelcomeController {
    @Inject
    @SystemProperty(value="welcomepage.url.de")
    ConfigurationProperty welcomePageUrlDe;
    @Inject
    @SystemProperty(value="welcomepage.url.fr")
    ConfigurationProperty welcomePageUrlFr;
    @Inject
    @SystemProperty(value="welcomepage.url.it")
    ConfigurationProperty welcomePageUrlIt;
    @Inject
    @SystemProperty(value="welcomepage.url.en")
    ConfigurationProperty welcomePageUrlEn;
    @Inject
    @SystemProperty(value="welcomepage.height", fallback="1400px")
    ConfigurationProperty welcomePageHeight;   
    @Inject
    LocaleController localeController;

    public String getWelcomePageUrl() {
        String lang = localeController.getLanguage();
        if ("fr".equals(lang)) {
            return welcomePageUrlFr.getStringValue();
        }
        if ("it".equals(lang)) {
            return welcomePageUrlIt.getStringValue();
        }
        if ("en".equals(lang)) {
            return welcomePageUrlEn.getStringValue();
        }
        return welcomePageUrlDe.getStringValue();
    }
    
    public String getWelcomePageHeight() {
        return welcomePageHeight.getStringValue();
    }
    
}
