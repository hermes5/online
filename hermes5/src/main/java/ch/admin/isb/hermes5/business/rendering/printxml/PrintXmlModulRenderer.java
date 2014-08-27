/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.printxml;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.customelement.ModulAufgabeTableBuilder;
import ch.admin.isb.hermes5.business.rendering.customelement.SzenarioTable;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlModulRenderer implements PrintXmlRenderer {

    @Inject
    PrintXmlRendererUtil printXmlRendererUtil;

    @Inject
    ModulAufgabeTableBuilder modulAufgabeTableBuilder;

    @Inject
    VelocityAdapter velocityAdapter;

    @Override
    public boolean isResponsibleForPrintXml(AbstractMethodenElement methodElement) {
        return Modul.class.equals(methodElement.getClass());
    }

    @Override
    public void renderPrintXml(AbstractMethodenElement methodElement, Object target,
            LocalizationEngine localizationEngine, PublishContainer publishContainer) {
        Modul modul = (Modul) methodElement;
        String name = localizationEngine.localize(modul.getPresentationName());
        String p = localizationEngine.localize(modul.getBriefDescription());
        String content = paragraph(p);
        content += paragraph(localizationEngine.localize(modul.getMainDescription()));

        content += buildModulTable(localizationEngine, modul, publishContainer);

        printXmlRendererUtil.updateNameAndContent(target, name, content);
    }

    private String
            buildModulTable(LocalizationEngine localizationEngine, Modul modul, PublishContainer publishContainer) {
        SzenarioTable scenarioTable = modulAufgabeTableBuilder.buildModulTable(publishContainer.getPhasen(), modul);

        Map<String, Object> initContext = new HashMap<String, Object>();
        initContext.put("l", localizationEngine);
        initContext.put("t", scenarioTable);
        initContext.put("StringUtil", StringUtil.class);

        String result = velocityAdapter.mergeTemplates(initContext, "overviewtable.vm");
        return result != null ? result : "";
    }

    private String paragraph(String p) {
        if (StringUtil.isBlank(p)) {
            return "";
        }
        return "<p>" + p + "</p>";
    }
}
