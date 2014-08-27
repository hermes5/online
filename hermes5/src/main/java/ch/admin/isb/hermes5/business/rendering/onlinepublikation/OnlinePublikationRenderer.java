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
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.StringUtil;

public abstract class OnlinePublikationRenderer implements Serializable {

    protected String OP_HEADER_AND_MENU = "op_header.vm";
    protected String OP_FOOTER = "op_footer.vm";
    
    private static final long serialVersionUID = 1L;

    @Inject
    VelocityAdapter velocityAdapter;
    
   
    
    public abstract boolean isResponsibleFor(AbstractMethodenElement methodElement);

    public abstract String renderModelElement(AbstractMethodenElement abstractMethodenElement,
            List<MenuItem> adjustedMenu, LocalizationEngine localizationEngine, PublishContainer hermesWebsite, boolean onlySearchableContent);

    protected Map<String, Object> initContext(AbstractMethodenElement methodenElement,
            LocalizationEngine localizationEngine, List<MenuItem> adjustedMenu, boolean onlySearchableContent) {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("l", localizationEngine);
        context.put("me", methodenElement);
        context.put("urlprefix", "index.xhtml?element=");
        context.put("contentprefix", "content/");
        context.put("menu", adjustedMenu);
        context.put("onlySearchableContent", onlySearchableContent);
        context.put("app", "op");
        context.put("StringUtil", StringUtil.class);
        return context;
    }
}
