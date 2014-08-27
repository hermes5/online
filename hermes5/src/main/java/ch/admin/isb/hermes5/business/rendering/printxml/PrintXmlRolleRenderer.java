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

import ch.admin.isb.hermes5.business.rendering.customelement.RelationshipTablePreprocessor;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlRolleRenderer implements PrintXmlRenderer {

    @Inject
    PrintXmlRendererUtil printXmlRendererUtil;

    @Inject
    VelocityAdapter velocityAdapter;

    @Inject
    RelationshipTablePreprocessor relationshipTablePreprocessor;

    @Override
    public boolean isResponsibleForPrintXml(AbstractMethodenElement methodElement) {
        return methodElement instanceof Rolle;
    }

    @Override
    public void renderPrintXml(AbstractMethodenElement methodElement, Object target,
            LocalizationEngine localizationEngine, PublishContainer publishContainer) {
        Rolle rolle = (Rolle) methodElement;
        String name = localizationEngine.localize(rolle.getPresentationName());
        String content = localizationEngine.localize(rolle.getBriefDescription());
        printXmlRendererUtil.updateNameAndContent(target, name, content);
        addSubElement(target, localizationEngine, "h5_rolle_maindescription", rolle.getMainDescription());
        addSubElement(target, localizationEngine, "h5_rolle_assignmentapproaches", rolle.getAssignementApproaches());
        addSubElement(target, localizationEngine, "h5_rolle_skills", rolle.getSkills());
        String relationShipTable = buildRelationShipTable(localizationEngine, rolle);
        addSubElement(target, localizationEngine, "al_doc_beziehungen_title", relationShipTable);
    }

    private String buildRelationShipTable(LocalizationEngine localizationEngine, Rolle rolle) {
        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("relationshipTableRecords",
                relationshipTablePreprocessor.preprocess(rolle.getRelationshipTableRecords(), localizationEngine));
        ctx.put("app", "print");
        ctx.put("l", localizationEngine);
        return velocityAdapter.mergeTemplates(ctx, "relationshiptable.vm");
    }

    private void
            addSubElement(Object target, LocalizationEngine localizationEngine, String namekey, Localizable content) {
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
