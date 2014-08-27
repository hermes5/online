/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.szenario;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungModelElementDocumentationRenderer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.rendering.postprocessor.InternalLinkPostProcessor;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class SzenarioDocumentationGenerator implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(SzenarioDocumentationGenerator.class);

    @Inject
    @SystemProperty(value = "al.documentation.root", fallback = "scenario")
    ConfigurationProperty documentationRoot;

    @Inject
    AnwenderloesungModelElementDocumentationRenderer anwenderloesungModelElementDocumentationRenderer;

    @Inject
    InternalLinkPostProcessor internalLinkPostProcessor;


    public void addDocumentation(AnwenderloesungRenderingContainer container, ZipOutputBuilder zipBuilder,
            LocalizationEngine localizationEngine) {

        List<AbstractMethodenElement> elementsToWrite = collectElementsToWrite(container.getSzenario(),
                container.getSzenarioUserData());

        for (AbstractMethodenElement abstractMethodenElement : elementsToWrite) {
            writeModelElement(abstractMethodenElement, zipBuilder, localizationEngine,
                    getPathOfModelElement(localizationEngine.getLanguage(), abstractMethodenElement.getName()),
                    elementsToWrite, container);
        }

        writeSzenarioPage(zipBuilder, localizationEngine,
                getPathOfModelElement(localizationEngine.getLanguage(), "index"), container);

    }

    private List<AbstractMethodenElement> collectElementsToWrite(Szenario szenario, SzenarioUserData szenarioUserData) {
        List<AbstractMethodenElement> elementsToWrite = new ArrayList<AbstractMethodenElement>();
        elementsToWrite.addAll(szenario.getPhasen());
        elementsToWrite.addAll(szenario.getModule());
        elementsToWrite.addAll(szenario.getRollen());
        List<Aufgabe> aufgaben = szenario.getAufgaben();
        for (Aufgabe aufgabe : aufgaben) {
            if (!aufgabe.isHidden()) {
                elementsToWrite.add(aufgabe);
            }
        }
        elementsToWrite.addAll(szenario.getErgebnisse());
        return elementsToWrite;
    }

    private void writeModelElement(AbstractMethodenElement modelElement, ZipOutputBuilder zipBuilder,
            LocalizationEngine localizationEngine, String path, List<AbstractMethodenElement> elementsToWrite,
            AnwenderloesungRenderingContainer container) {
        if (zipBuilder.containsFile(path)) {
            logger.debug(path + " already in zip file");
            return;
        }
        String fileContent = anwenderloesungModelElementDocumentationRenderer.renderModelElement(modelElement,
                localizationEngine, container);

        fileContent = internalLinkPostProcessor.adjustInternalLinks(modelElement, fileContent, elementsToWrite);

        byte[] writeModelElement = getBytes(fileContent);
        zipBuilder.addFile(path, writeModelElement);

    }

    private void writeSzenarioPage(ZipOutputBuilder zipBuilder, LocalizationEngine localizationEngine, String path,
            AnwenderloesungRenderingContainer container) {
        byte[] writeSzenarioPage = getBytes(anwenderloesungModelElementDocumentationRenderer.renderSzenario(
                localizationEngine, container));
        zipBuilder.addFile(path, writeSzenarioPage);
    }

    private String getPathOfModelElement(String lang, String modelElement) {
        return documentationRoot.getStringValue() + "/" + lang + "/" + modelElement + ".html";
    }
}
