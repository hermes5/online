/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

@Named
@SessionScoped
public class SzenarioProjektdatenController extends AbstractViewController implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(SzenarioProjektdatenController.class);
    @Inject
    SzenarioWizardContext szenarioWizardContext;
    @Inject
    SzenarioElementeController szenarioElementeController;
    @Inject
    SzenarienOverviewController szenarienOverviewController;
    @Inject
    MessagesUtil messagesUtil;
    @Inject
    @SystemProperty(value = "imageTypes", fallback = "png, jpg, jpeg")
    ConfigurationProperty imageTypes;
    
    
    private UploadedFile logo;
    
    private boolean visibleUploadLogoDialog;

    
    public String display(Szenario szenario) {
        szenarioWizardContext.setSzenario(szenario);
        visibleUploadLogoDialog= false;
        return getIdentifier();
    }

    public String display() {
        return getIdentifier();
    }

    public SzenarioUserData getSzenarioUserData() {
        return szenarioWizardContext.getSzenarioUserData();
    }

    public void setSzenarioUserData(SzenarioUserData data) {
        szenarioWizardContext.setSzenarioUserData(data);
    }

    public String getLogoUrl() {
        return "logo/"+getSzenarioUserData().getLogoFilename();
    }
    
    @Override
    public String getIdentifier() {
        return "szenario-projektdaten";
    }

    public String cancelLogoUpload() {
        logo = null;
        visibleUploadLogoDialog = false;
        return null;
    }
    public String showLogoUploadDialog() {
        visibleUploadLogoDialog = true;
        return null;
    }
    
    public boolean isRenderedLogo() {
        return getSzenarioUserData().getLogo() != null;
    }
    public String deleteLogo() {
        logo = null;
        getSzenarioUserData().setLogo(null);
        getSzenarioUserData().setLogoFilename(null);
        return null;
    }
    public String logoUpload() {
        if (logo == null) {
            logger.warn("logo is null");
        } else {
            String[] split = logo.getFileName().toLowerCase().split("\\.");
			if (!imageTypes.getStringValue().contains(split[split.length-1])) {
                messagesUtil.addGlobalError("al_logoupload_message_error_noimg");
                return null;
            }
            getSzenarioUserData().setLogo(logo.getContents());
            getSzenarioUserData().setLogoFilename(StringUtil.getLinkName(logo.getFileName()));
        }
        logo=null;
        visibleUploadLogoDialog = false;
        return null;
    }
    
    public String gotoElemente() {
        return redirectTo(szenarioElementeController.display());
    }
    public String back() {
        return redirectTo(szenarienOverviewController.getIdentifier());
    }

    public Localizable getSzenarioName() {
        return szenarioWizardContext.getSzenario().getPresentationName();
    }

    public UploadedFile getLogo() {
        return logo;
    }

    public void setLogo(UploadedFile logo) {
        this.logo = logo;
    }
    public boolean isVisibleUploadLogoDialog() {
        return visibleUploadLogoDialog;
    }

    
    public void setVisibleUploadLogoDialog(boolean visibleUploadLogoDialog) {
        this.visibleUploadLogoDialog = visibleUploadLogoDialog;
    }
}
