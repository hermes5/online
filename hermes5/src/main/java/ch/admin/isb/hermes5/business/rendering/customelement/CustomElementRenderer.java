/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import java.util.HashMap;
import java.util.Map;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.StringUtil;

public abstract class CustomElementRenderer {

   
    public abstract boolean matches(String html);

    public abstract String applyCustomElementRenderer(String html, PublishContainer publishContainer,
            LocalizationEngine localizationEngine);
    
    protected Map<String, Object> initContext(LocalizationEngine localizationEngine) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("urlprefix", "index.xhtml?element=");
        context.put("contentprefix", "content/");
        context.put("l", localizationEngine);
        context.put("StringUtil", StringUtil.class);
        return context;
    }

}
