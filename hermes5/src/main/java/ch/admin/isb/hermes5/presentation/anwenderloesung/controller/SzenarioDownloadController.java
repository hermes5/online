/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.util.StringUtil;

@Named
@SessionScoped
public class SzenarioDownloadController extends AbstractViewController implements Serializable {

    private static final String PROJEKTSTRUKTURPLAN = "projektstrukturplan";
    private static final String DOKUMENTATION = "dokumentation";
    private static final String ERGEBNISVORLAGEN = "ergebnisvorlagen";
    private static final String XML_MODELL = "xmlmodel";
    private static final long serialVersionUID = 1L;

    private String[] languages;
    private String[] components;

    @Inject
    SzenarioWizardContext szenarioWizardContext;

    @Inject
    SzenarioEigeneElementeController szenarioEigeneElementeController;
    @Inject
    AnwenderloesungFacade anwenderloesungFacade;
    @Inject
    LocaleController localeController;
    @Inject
    MessagesUtil messageUtil;

    @Inject
    FacesContext facesContext;
    private byte[] zipFile;
    private boolean renderDownloadDialog;
    private boolean renderPublishedOnlineDialog;
    private String publishedUrl;

    public boolean isRenderDownloadDialog() {
        return renderDownloadDialog;
    }

    public boolean isRenderPublishedOnlineDialog() {
        return renderPublishedOnlineDialog;
    }

    @PostConstruct
    public void init() {
        components = new String[] { PROJEKTSTRUKTURPLAN, DOKUMENTATION, ERGEBNISVORLAGEN };
        languages = new String[] { localeController.getLanguage() };
    }

    public String display() {
        reset();
        return getIdentifier();
    }

    public void reset() {
        zipFile = null;
        renderDownloadDialog = false;
        renderPublishedOnlineDialog = false;
    }

    public String goBack() {
        return redirectTo(szenarioEigeneElementeController.display());
    }

    @Override
    public String getIdentifier() {
        return "szenario-download";
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getComponents() {
        return components;
    }

    public void setComponents(String[] components) {
        this.components = components;
    }

    public String download() {
        if (validateForDownload()) {
            zipFile = anwenderloesungFacade.generateDownloadZip(anwenderloesungFacade.getPublishedModel()
                    .getIdentifier(), szenarioWizardContext.getSzenario(), szenarioWizardContext.getSzenarioUserData(),
                    isSelected(PROJEKTSTRUKTURPLAN), isSelected(DOKUMENTATION), isSelected(ERGEBNISVORLAGEN),
                    isSelected(XML_MODELL), Arrays.asList(languages));
            renderDownloadDialog = true;
        }
        return null;
    }

    private boolean validateForDownload() {
        if (languages.length < 1) {
            messageUtil.addGlobalError("al_no_language_selected");
            return false;
        } else if (components.length < 1) {
            messageUtil.addGlobalError("al_no_component_selected");
            return false;
        }
        return true;
    }

    public String publishOnline() {
        if (validateForDownload()) {
            zipFile = anwenderloesungFacade.generateDownloadZip(anwenderloesungFacade.getPublishedModel()
                    .getIdentifier(), szenarioWizardContext.getSzenario(), szenarioWizardContext.getSzenarioUserData(),
                    isSelected(PROJEKTSTRUKTURPLAN), isSelected(DOKUMENTATION), isSelected(ERGEBNISVORLAGEN),
                    isSelected(XML_MODELL), Arrays.asList(languages));
            publishedUrl = "http://" + facesContext.getExternalContext().getRequestHeaderMap().get("host") + "/"
                    + anwenderloesungFacade.publishOnline(zipFile, languages[0]);
            renderPublishedOnlineDialog = true;
        }
        return null;
    }

    public String downloadDialogClose() {
        this.zipFile = null;
        renderDownloadDialog = false;
        return null;
    }

    public String publishOnlineDialogClose() {
        this.zipFile = null;
        renderPublishedOnlineDialog = false;
        publishedUrl = null;
        return null;
    }

    public String getPublishedUrl() {
        return publishedUrl;
    }

    private String getFilename() {
        return projektname() + ".zip";
    }

    private String projektname() {
        String projektname = szenarioWizardContext.getSzenarioUserData().getProjektname();
        return StringUtil.isNotBlank(projektname) ? projektname : "Hermes";
    }

    public StreamedContent getFile() {
        if (zipFile == null) {
            return null;
        }

        ByteArrayInputStream is = new ByteArrayInputStream(zipFile);
        return new DefaultStreamedContent(is, "application/zip", getFilename());
    }

    private boolean isSelected(String string) {
        for (String component : components) {
            if (string.equals(component)) {
                return true;
            }
        }

        return false;
    }
}
