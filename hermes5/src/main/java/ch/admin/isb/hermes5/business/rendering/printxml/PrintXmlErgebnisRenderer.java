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

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlErgebnisRenderer implements PrintXmlRenderer {

    @Inject
    PrintXmlRendererUtil printXmlRendererUtil;

    @Override
    public boolean isResponsibleForPrintXml(AbstractMethodenElement methodElement) {
        return Ergebnis.class.equals(methodElement.getClass());
    }

    @Override
    public void renderPrintXml(AbstractMethodenElement methodElement, Object target,
            LocalizationEngine localizationEngine, PublishContainer publishContainer) {
        Ergebnis ergebnis = (Ergebnis) methodElement;
        String name = localizationEngine.localize(ergebnis.getPresentationName());
        printXmlRendererUtil.updateNameAndContent(target, name,  localizationEngine.localize(ergebnis.getBriefDescription()));
        addSubElement(target, localizationEngine, "h5_ergebnis_inhalt", ergebnis.getPurpose());
    }
    
    private void addSubElement(Object target, LocalizationEngine localizationEngine, String namekey, Localizable content) {
        String scontent = localizationEngine.localize(content);
        addSubElement(target, localizationEngine, namekey, scontent);
    }
    
    @SuppressWarnings("unchecked")
    private void addSubElement(Object target, LocalizationEngine localizationEngine, String namekey, String content) {
        if (StringUtil.isNotBlank(content)) {
            Object section = printXmlRendererUtil.getInstanceOfSub(target);
            printXmlRendererUtil.updateNameAndContent(section, localizationEngine.text(namekey), content);
            printXmlRendererUtil.getSubList(target).add(section);
        }
    }

}
