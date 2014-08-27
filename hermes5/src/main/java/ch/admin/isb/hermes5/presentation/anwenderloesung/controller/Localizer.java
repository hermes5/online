/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.Localizable;

@Named
@ApplicationScoped
public class Localizer {

    @Inject
    AnwenderloesungFacade anwenderloesungFacade;

    @Inject
    LocaleController localeController;

    public String getActualLanguage() {
        return localeController.getLanguage();
    }

    public String localize(Localizable localizable) {
        return localize(localizable, getActualLanguage());
    }

    public String localize(Localizable localizable, String language) {
        return anwenderloesungFacade.localize(anwenderloesungFacade.getPublishedModel().getIdentifier(), localizable, language);
    }
}
