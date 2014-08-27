/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.vorlagen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungTemplateIndexPageRenderer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.userszenario.vorlagen.ErgebnisLink.Type;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.word.WordDocumentCustomizer;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

public class ErgebnisVorlagenGenerator implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ErgebnisVorlagenGenerator.class);

    private static final long serialVersionUID = 1L;
    private static final Comparator<ErgebnisLink> LINK_NAME_COMPARATOR = new Comparator<ErgebnisLink>() {

        @Override
        public int compare(ErgebnisLink o1, ErgebnisLink o2) {
            return StringUtil.getLinkName(o1.getName()).toLowerCase()
                    .compareTo(StringUtil.getLinkName(o2.getName()).toLowerCase());
        }
    };

    @Inject
    AnwenderloesungTemplateIndexPageRenderer anwenderloesungTemplateIndexPageRenderer;

    @Inject
    S3 s3;
    @Inject
    WordDocumentCustomizer documentCustomizer;

    @Inject
    @SystemProperty(value = "al.vorlagen.root", fallback = "templates")
    ConfigurationProperty vorlagenRoot;

    public void addErgebnisVorlagen(String modelIdentifier, Szenario szenario, SzenarioUserData szenarioUserData,
            LocalizationEngine localizationEngine, ZipOutputBuilder zipBuilder) {
        List<Ergebnis> ergebnisse = szenario.getErgebnisse();
        for (Ergebnis ergebnis : ergebnisse) {
            addErgebnisvorlagen(modelIdentifier, szenarioUserData, zipBuilder, localizationEngine, ergebnis);
        }

    }

    public void addTemplateIndexPage(AnwenderloesungRenderingContainer container, ZipOutputBuilder zipBuilder,
            LocalizationEngine localizationEngine) {

        List<Ergebnis> ergebnisse = container.getSzenario().getErgebnisse();
        List<CustomErgebnis> customErgebnisse = container.getSzenarioUserData().getAllCustomErgebnisse();

        Set<ErgebnisLink> templateNames = new LinkedHashSet<ErgebnisLink>();
        for (Ergebnis ergebnis : ergebnisse) {
            if (!(ergebnis instanceof CustomErgebnis)) {
                for (String urlDe : ergebnis.getTemplateAttachmentUrls()) {
                    String linkName = StringUtil.getLinkName(localizationEngine.documentUrl(urlDe));
                    templateNames.add(new ErgebnisLink(linkName, linkName, Type.TEMPLATE));
                }
                List<Localizable> webAttachmentUrls = ergebnis.getWebAttachmentUrls();
                for (Localizable webAttachmentUrl : webAttachmentUrls) {
                    String url = localizationEngine.localize(webAttachmentUrl);
                    String linkName = StringUtil.getLinkName(url);
                    String linkUrl = StringUtil.getLinkUrl(url);
                    templateNames.add(new ErgebnisLink(linkName, linkUrl, Type.URL));
                }
            }
        }
        for (CustomErgebnis customErgebnis : customErgebnisse) {
            for (String url : customErgebnis.getTemplateAttachmentUrls()) {
                String name = StringUtil.getLinkName(url);
                String customUrl = "custom/" + name;
                templateNames.add(new ErgebnisLink(name, customUrl, Type.TEMPLATE));
            }

        }
        ArrayList<ErgebnisLink> templateNamesList = new ArrayList<ErgebnisLink>(templateNames);
        Collections.sort(templateNamesList, LINK_NAME_COMPARATOR);
        String fileContent = anwenderloesungTemplateIndexPageRenderer.renderTemplateIndexPage(localizationEngine,
                templateNamesList, container);
        byte[] writeTemplateIndex = StringUtil.getBytes(fileContent);
        zipBuilder.addFile("templates/" + localizationEngine.getLanguage() + "/index.html", writeTemplateIndex);
    }

    private void addErgebnisvorlagen(String modelIdentifier, SzenarioUserData szenarioUserData,
            ZipOutputBuilder zipBuilder, LocalizationEngine localizationEngine, Ergebnis ergebnis) {
        List<String> templateAttachmentUrls = ergebnis.getTemplateAttachmentUrls();
        for (String urlDe : templateAttachmentUrls) {
            if (ergebnis instanceof CustomErgebnis) {
                CustomErgebnis customErgebnis = (CustomErgebnis) ergebnis;
                String filename = vorlagenRoot.getStringValue() + "/" + localizationEngine.getLanguage() + "/custom/"
                        + StringUtil.getLinkName(urlDe);
                if (!zipBuilder.containsFile(filename)) {
                    byte[] readFile = customErgebnis.getVorlageContent();
                    zipBuilder.addFile(filename, readFile);
                }

            } else {
                String url = localizationEngine.documentUrl(urlDe);
                String filename = vorlagenRoot.getStringValue() + "/" + localizationEngine.getLanguage() + "/"
                        + StringUtil.getLinkName(url);
                if (!zipBuilder.containsFile(filename)) {
                    try {
                        byte[] readFile = s3.readFile(modelIdentifier, localizationEngine.getLanguage(), url);
                        readFile = documentCustomizer.adjustDocumentWithUserData(url, readFile, szenarioUserData);
                        if (readFile != null) {
                            zipBuilder.addFile(filename, readFile);
                        } else {
                            logger.warn("Unexpected null value after document customizer " + url);
                        }
                    } catch (Exception e) {
                        logger.warn("Unable to add files for ergebnis: " + ergebnis.getId());
                    }
                }
            }
        }
    }
}
