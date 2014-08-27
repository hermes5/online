/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.anwenderloesung;

import java.io.Serializable;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;

public class AnwenderloesungModelElementDocumentationRenderer implements Serializable {

    private static final Logger logger = LoggerFactory
            .getLogger(AnwenderloesungModelElementDocumentationRenderer.class);

    private static final long serialVersionUID = 1L;


    @Inject
    @Any
    Instance<AnwenderloesungModelElementRenderer> anwenderloesungModelElementRenderers;

    @Inject
    AnwenderloesungSzenarioRenderer anwenderloesungSzenarioRenderer;

    public String renderModelElement(AbstractMethodenElement modelElement, LocalizationEngine localizationEngine,
            AnwenderloesungRenderingContainer container) {
        for (AnwenderloesungModelElementRenderer renderer : anwenderloesungModelElementRenderers) {
            if (renderer.isResponsible(modelElement)) {
                return renderer.renderModelElement(modelElement, localizationEngine, container);
            }
        }
        logger.error("No renderer found for " + modelElement);
        return "";
    }

    public String renderSzenario(LocalizationEngine localizationEngine, AnwenderloesungRenderingContainer container) {
        return anwenderloesungSzenarioRenderer.renderSzenario(localizationEngine, container);
    }
}
