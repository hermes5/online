/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.onlinepublikation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;

public class OnlinePublikationMenuRenderer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    VelocityAdapter velocityAdapter;

    public String renderTopMenu(List<MenuItem> topMenu, LocalizationEngine localizationEngine) {
        Map<String, Object> initContext = initContext(localizationEngine, topMenu);
        return velocityAdapter.mergeTemplates(initContext, "menu.vm");
    }

    private Map<String, Object> initContext(LocalizationEngine localizationEngine, List<MenuItem> topMenu) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("l", localizationEngine);
        context.put("urlprefix", "index.xhtml?element=");
        context.put("menu", topMenu);
        return context;
    }
}
