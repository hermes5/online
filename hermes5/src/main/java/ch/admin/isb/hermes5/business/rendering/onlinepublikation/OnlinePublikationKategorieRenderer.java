/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.onlinepublikation;

import java.util.List;
import java.util.Map;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.PublishContainer;

public class OnlinePublikationKategorieRenderer extends OnlinePublikationRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean isResponsibleFor(AbstractMethodenElement methodElement) {
        return methodElement instanceof Kategorie;
    }

    @Override
    public String renderModelElement(AbstractMethodenElement methodenElement, List<MenuItem> adjustedMenu,
            LocalizationEngine localizationEngine, PublishContainer hermesWebsite, boolean onlySearchableContent) {
        Kategorie kategorie = (Kategorie) methodenElement;
        if (kategorie.getEinleitung() != null) {
            methodenElement = (kategorie).getEinleitung();
        }
        Map<String, Object> initContext = initContext(methodenElement, localizationEngine, adjustedMenu,
                onlySearchableContent);

        return velocityAdapter.mergeTemplates(initContext, OP_HEADER_AND_MENU, "kategorie.vm", OP_FOOTER);
    }
}
