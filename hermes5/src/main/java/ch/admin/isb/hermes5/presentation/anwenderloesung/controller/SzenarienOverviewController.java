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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;

@Named
@SessionScoped
public class SzenarienOverviewController extends AbstractViewController implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(SzenarienOverviewController.class);

    private static final long serialVersionUID = 1L;
    @Inject
    AnwenderloesungFacade anwenderloesungFacade;
    
    @Inject
    SzenarioProjektdatenController szenarioProjektdatenController;
    
    @Inject
    SzenarioWizardContext szenarioWizardContext;
    
    @Inject
    SzenarioDownloadController szenarioDownloadController;
    
    @Inject
    SzenarioEigeneElementeController szenarioEigeneElementeController;
    
    @Inject
    LocaleController localeController;

    private List<Szenario> szenarien;
    private Szenario szenarioSelectedForDownload;

    public Szenario getSzenarioSelectedForDownload() {
        return szenarioSelectedForDownload;
    }

    public List<Szenario> getSzenarien() {
        if (szenarien == null) {
            init();
        }
        return szenarien;
    }

    @PostConstruct
    public void init() {
        szenarien = anwenderloesungFacade.getSzenarien(anwenderloesungFacade.getPublishedModel().getIdentifier());
        Collections.sort(szenarien, new Comparator<Szenario>() {

            @Override
            public int compare(Szenario o1, Szenario o2) {
                if (o1 == null || o2 == null || o1.getMethodConfiguration() == null || o2.getMethodConfiguration() == null
                        || o1.getMethodConfiguration().getName() == null || o2.getMethodConfiguration().getName() == null) {
                    logger.error("unable to compare " + o1 + " to " + o2);
                    return 0;

                }
                return o1.getMethodConfiguration().getName().compareTo(o2.getMethodConfiguration().getName());
            }
        });
    }

    public String downloadSzenarioClicked(Szenario szenario) {
        szenarioWizardContext.resetSzenario();
        szenarioWizardContext.resetSzenarioUserData();
        szenarioSelectedForDownload = szenario;
        szenarioWizardContext.setSzenario(szenario);
        szenarioDownloadController.reset();
        return null;
    }

    public boolean isShowSzenarioDownloadSelection() {
        return szenarioSelectedForDownload != null;
    }

    public String closeDownloadDialog() {
        szenarioSelectedForDownload = null;
        return null;
    }

    public String costumizeSzenario(Szenario szenario) {
        szenarioWizardContext.resetSzenario();
        szenarioWizardContext.resetSzenarioUserData();
        szenarioEigeneElementeController.resetData();

        szenarien = null;
        return redirectTo(szenarioProjektdatenController.display(szenario));
    }

    public String download() {
        this.szenarioSelectedForDownload = null;
        szenarioDownloadController.download();
        return null;
    }

    @Override
    public String getIdentifier() {
        return "szenarien-overview";
    }

    public String getSampleSzenarioLinkFor(Szenario szenario) {
        if (szenario == null) {
            return null;
        }
        
        StringBuilder link = new StringBuilder("/szenarien/");
        link.append(szenario.getName()).append("/");
        link.append("start_").append(localeController.getLanguage()).append(".html");

        return link.toString();
    }
}
