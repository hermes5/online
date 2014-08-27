/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.util.Logged;

@Named
@SessionScoped
public class PrintXmlDownloadController extends AbstractViewController implements Serializable {

    private static final long serialVersionUID = 1L;
    private String modelIdentifier;
    private String selectedLanguage;

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    @Inject
    WebPublisherFacade webPublisherFacade;

    @Inject
    ModelOverviewController modelOverviewController;

    private String printXml;
    private boolean renderDownloadDialog;

    public boolean isRenderDownloadDialog() {
        return renderDownloadDialog;
    }

    @Logged
    public String display(String modelIdentifier) {
        this.modelIdentifier = modelIdentifier;
        selectedLanguage = "de";
        renderDownloadDialog = false;
        printXml = null;
        return getIdentifier();
    }

    @Logged
    @Override
    public String getIdentifier() {
        return "print-xml-download";
    }

    public String triggerExport() {
        printXml = webPublisherFacade.getPrintXml(modelIdentifier, selectedLanguage);
        renderDownloadDialog = true;
        return null;
    }

    public String downloadDialogClose() {
        printXml = null;
        renderDownloadDialog = false;
        return null;
    }

    public String getBackIdentifier() {
        return modelOverviewController.display();
    }

    public StreamedContent getSchemaFile() {
        return new DefaultStreamedContent(getClass().getResourceAsStream("/print_schema.xsd"), "text/xml", "print_schema.xsd");
    }
    
    public StreamedContent getFile() {
        ByteArrayInputStream is;
        try {
            is = new ByteArrayInputStream(printXml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unable to load charset UTF-8", e);
        }
        return new DefaultStreamedContent(is, "zip", getFilename());
    }

    public String getFilename() {   
        return "Print_XML_" + modelIdentifier + "_" + selectedLanguage + ".xml";
    }

}
