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
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.customelement.SzenarioErgebnisTableBuilder;
import ch.admin.isb.hermes5.business.rendering.customelement.SzenarioTable;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.util.StringUtil;

public class AnwenderloesungSzenarioRenderer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    VelocityAdapter velocityAdapter;

    @Inject
    SzenarioErgebnisTableBuilder szenarioErgebnisTableBuilder;

    public String renderSzenario(LocalizationEngine localizationEngine, AnwenderloesungRenderingContainer container) {
        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("c", container);
        ctx.put("StringUtil", StringUtil.class);
        ctx.put("me", container.getSzenario());
        ctx.put("lang", localizationEngine.getLanguage());
        ctx.put("pathprefix", "../../");
        ctx.put("urlprefix", "");
        ctx.put("contentprefix", "");
        ctx.put("menu", "dokumentation");
        SzenarioTable scenarioTable = szenarioErgebnisTableBuilder.buildSzenarioTable(container.getSzenario(),
                container.getSzenarioUserData());
        ctx.put("t", scenarioTable);
        ctx.put("l", localizationEngine);
        return velocityAdapter.mergeTemplates(ctx, "al_header.vm", "overviewtable.vm", "al_footer.vm");
    }
}
