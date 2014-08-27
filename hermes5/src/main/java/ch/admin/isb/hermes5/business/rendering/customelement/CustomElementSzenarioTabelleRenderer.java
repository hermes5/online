/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

public class CustomElementSzenarioTabelleRenderer extends CustomElementRenderer {

    private static final Logger logger = LoggerFactory.getLogger(CustomElementSzenarioTabelleRenderer.class);

    @Inject
    SzenarioAufgabeTableBuilder szenarioAufgabeTableBuilder;

    @Inject
    VelocityAdapter velocityAdapter;

    @Inject
    @SystemProperty(value = "customelement.key.table_szenario_key_start", fallback = "${h5_table_szenario_")
    ConfigurationProperty customElementKeyStart;

    @Inject
    @SystemProperty(value = "customelement.key.table_szenario_key_end", fallback = "}")
    ConfigurationProperty customElementKeyEnd;

    private String renderSzenario(Szenario szenario, LocalizationEngine localizationEngine) {
        Map<String, Object> context = initContext(localizationEngine);
        context.put("me", szenario);
        SzenarioTable scenarioTable = szenarioAufgabeTableBuilder.buildSzenarioTable(szenario.getPhasen(),
                szenario.getModule());
        context.put("t", scenarioTable);

        return velocityAdapter.mergeTemplates(context, "overviewtable.vm");
    }

 
    @Override
    public boolean matches(String html) {
        return html != null
                && html.contains(customElementKeyStart.getStringValue())
                && html.substring(html.indexOf(customElementKeyStart.getStringValue())).contains(
                        customElementKeyEnd.getStringValue());
    }

    @Override
    public String applyCustomElementRenderer(String html, PublishContainer publishContainer,
            LocalizationEngine localizationEngine) {
        int begin = html.indexOf(customElementKeyStart.getStringValue());
        String substring = html.substring(begin);
        int end = substring.indexOf(customElementKeyEnd.getStringValue());
        String name = substring.substring(customElementKeyStart.getStringValue().length(), end);
        Szenario s = publishContainer.getSzenarioWithMethodConfigurationName(name);
        if (s == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Unable to find szenario with name " + name + " available szenarios are ("
                        + StringUtil.join(publishContainer.getAvailableSzenariosNames(), "|") + ")");
            }
            return html;
        }
        String szenario = renderSzenario(s, localizationEngine);
        return html.replace(customElementKeyStart.getStringValue() + name + customElementKeyEnd.getStringValue(),
                szenario);
    }

}
