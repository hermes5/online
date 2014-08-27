/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.translation.TranslationRepositoryWrapper;
import ch.admin.isb.hermes5.business.userszenario.projektstrukturplan.ProjektstrukturplanGenerator;
import ch.admin.isb.hermes5.business.userszenario.startpage.StartPageGenerator;
import ch.admin.isb.hermes5.business.userszenario.szenario.SzenarioDocumentationGenerator;
import ch.admin.isb.hermes5.business.userszenario.vorlagen.ErgebnisVorlagenGenerator;
import ch.admin.isb.hermes5.business.userszenario.xmlmodel.XMLModelGenerator;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;

public class SzenarioDownloadWorkflow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    SzenarioDocumentationGenerator szenarioDocumentationGenerator;

    @Inject
    XMLModelGenerator xmlModelGenerator;

    @Inject
    ProjektstrukturplanGenerator projektstrukturplanGenerator;

    @Inject
    TranslationRepository translationRepository;

    @Inject
    SzenarioStripper szenarioStripper;

    @Inject
    SzenarioCustomizer szenarioCustomizer;

    @Inject
    ErgebnisVorlagenGenerator ergebnisVorlagenGenerator;

    @Inject
    StartPageGenerator startPageGenerator;

  
    public byte[] generateDownloadZip(String modelIdentifier, Szenario szenarioFull, SzenarioUserData szenarioUserData,
            boolean projektstrukturplan, boolean dokumentation, boolean ergebnisvorlagen, boolean xmlModel,
            List<String> languages) {

        Szenario szenario = szenarioStripper.stripSzenarioToSelectedItems(szenarioFull,
                szenarioUserData.getSzenarioTree());

        Map<String, List<CustomErgebnis>> customModules = szenarioUserData.getCustomModules();
        for (String moduleName : customModules.keySet()) {
            szenarioCustomizer.addCustomModule(szenario, moduleName, customModules.get(moduleName));
        }

        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(modelIdentifier, szenario,
                szenarioUserData, languages, dokumentation, projektstrukturplan, ergebnisvorlagen, xmlModel);

        return generateSzenarioZip(container, languages);
    }

    public byte[] generateSampleSzenarioZip(String modelIdentifier, Szenario szenario, List<String> languages) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(modelIdentifier, szenario,
                new SzenarioUserData(), languages, true, true, true, true);
        return generateSzenarioZip(container, languages);
    }

    private byte[] generateSzenarioZip(AnwenderloesungRenderingContainer container, List<String> languages) {
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();

        TranslationRepositoryWrapper translationRepositoryWrapper = new TranslationRepositoryWrapper(
                container.getModelIdentifier(), translationRepository, languages);

        for (String lang : languages) {
            LocalizationEngine localizationEngine = new LocalizationEngine(translationRepositoryWrapper,
                    container.getModelIdentifier(), lang);

            if (container.isDokumentation()) {
                szenarioDocumentationGenerator.addDocumentation(container, zipBuilder, localizationEngine);
            }

            if (container.isProjektstrukturplan()) {
                projektstrukturplanGenerator.generateProjektStrukturplan(zipBuilder, container, localizationEngine);
            }

            if (container.isErgebnisvorlagen() || container.isDokumentation() || container.isXmlmodel()) {
                ergebnisVorlagenGenerator.addErgebnisVorlagen(container.getModelIdentifier(), container.getSzenario(),
                        container.getSzenarioUserData(), localizationEngine, zipBuilder);
            }

            if (container.isErgebnisvorlagen()) {
                ergebnisVorlagenGenerator.addTemplateIndexPage(container, zipBuilder, localizationEngine);
            }

            if (container.isXmlmodel()) {
                xmlModelGenerator.addXMLModelWithContent(container, zipBuilder, localizationEngine);
                xmlModelGenerator.addIndexPage(container, zipBuilder, localizationEngine);
            }
        }

        if (container.isXmlmodel()) {
            xmlModelGenerator.addXSDToZip(zipBuilder);
        }

        if (zipBuilder.isEmpty()) {
            zipBuilder.close();
            return null;
        }

        startPageGenerator.addCommonResources(zipBuilder, container.getSzenarioUserData());
        for (String lang : languages) {
            LocalizationEngine localizationEngine = new LocalizationEngine(translationRepositoryWrapper,
                    container.getModelIdentifier(), lang);
            startPageGenerator.addStartPage(container, zipBuilder, localizationEngine);
        }

        return zipBuilder.getResult();
    }

}
