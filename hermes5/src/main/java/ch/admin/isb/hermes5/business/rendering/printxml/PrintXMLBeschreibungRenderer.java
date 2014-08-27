/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.printxml;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementPostProcessor;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.domain.PublishContainer;

public class PrintXMLBeschreibungRenderer implements PrintXmlRenderer {

    @Inject
    PrintXmlRendererUtil printXmlRendererUtil;

    @Inject
    CustomElementPostProcessor customElementPostProcessor;

    @Override
    public boolean isResponsibleForPrintXml(AbstractMethodenElement methodElement) {
        return Beschreibung.class.equals(methodElement.getClass());
    }

    @Override
    public void renderPrintXml(AbstractMethodenElement methodElement, Object target,
            LocalizationEngine localizationEngine, PublishContainer publishContainer) {
        String name = localizationEngine.localize(methodElement.getPresentationName());
        String content = localizationEngine.localize(methodElement.getMainDescription());
        String contentWithCustomElements = customElementPostProcessor.renderCustomElements(content, publishContainer, localizationEngine);
        printXmlRendererUtil.updateNameAndContent(target, name, contentWithCustomElements);
    }

}
