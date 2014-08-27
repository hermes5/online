/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.onlinepublikation.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.service.OnlinePublikationFacade;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.presentation.anwenderloesung.controller.LocaleController;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

@RequestScoped
@Named
public class OnlinePublikationController {

    private static final Logger logger = LoggerFactory.getLogger(OnlinePublikationController.class);

    @Inject
    @SystemProperty(value = "onlinepublikation_start_html", fallback = "kategorie_methode_aufbau.html")
    ConfigurationProperty onlinePublikationStart;

    @Inject
    LocaleController localeController;
    @Inject
    OnlinePublikationFacade onlinePublikationFacade;

    public String getOnlinePublikationStart() {
        return onlinePublikationStart.getStringValue();
    }

    public String getPageContent(String element) {
        if (StringUtil.isBlank(element)) {
            return loadContent(getOnlinePublikationStart());
        }
        return loadContent(element);
    }

    private String loadContent(String element) {
        EPFModel publishedModel = onlinePublikationFacade.getPublishedModel();
        String language = localeController.getLanguage();
        byte[] content = onlinePublikationFacade.getPublishedFile(publishedModel.getIdentifier(), language, "/"
                + element);
        if (content != null) {
            return StringUtil.fromBytes(content);
        } else if (getOnlinePublikationStart().equals(element)) {
            throw new RuntimeException("error on loading start page");
        }
        logger.warn("Error on loading onlinepublikation element: " + element + " will try to load start page instead");
        return loadContent(getOnlinePublikationStart());
    }
}
