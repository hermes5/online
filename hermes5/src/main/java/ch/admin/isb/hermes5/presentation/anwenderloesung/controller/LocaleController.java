/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import static ch.admin.isb.hermes5.domain.Status.*;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.IllegalProductException;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Status;

@Named
@SessionScoped
public class LocaleController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    FacesContext context;
    private Locale locale;

    @Inject
    AnwenderloesungFacade anwenderloesungFacade;

    @PostConstruct
    public void init() {
        try {
            locale = context.getViewRoot().getLocale();
            if (!isEnabled(locale.getLanguage())) {
                setLanguage("de");
            }
        } catch (IllegalProductException e) {
            locale = new Locale("de");
        }
    }

    public boolean isEnabled(String lang) {
        EPFModel model = anwenderloesungFacade.getPublishedModel();
        if (model == null) {
            return false;
        }
        if ("fr".equals(lang)) {
            return model.getStatusFr() == Status.FREIGEGEBEN;
        }
        if ("it".equals(lang)) {
            return model.getStatusIt() == Status.FREIGEGEBEN;
        }
        if ("en".equals(lang)) {
            return model.getStatusEn() == Status.FREIGEGEBEN;
        }
        return true;
    }

    public boolean isLast(String lang) {
        EPFModel model = anwenderloesungFacade.getPublishedModel();
        if (model == null) {
            return true;
        }
        if ("de".equals(lang)) {
            return model.getStatusFr() != FREIGEGEBEN && model.getStatusIt() != FREIGEGEBEN
                    && model.getStatusEn() != FREIGEGEBEN;
        }
        if ("fr".equals(lang)) {
            return model.getStatusFr() == FREIGEGEBEN && model.getStatusIt() != FREIGEGEBEN
                    && model.getStatusEn() != FREIGEGEBEN;
        }
        if ("it".equals(lang)) {
            return model.getStatusIt() == FREIGEGEBEN && model.getStatusEn() != FREIGEGEBEN;
        }
        if ("en".equals(lang)) {
            return model.getStatusEn() == Status.FREIGEGEBEN;
        }
        return false;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        context.getViewRoot().setLocale(locale);
    }
}
