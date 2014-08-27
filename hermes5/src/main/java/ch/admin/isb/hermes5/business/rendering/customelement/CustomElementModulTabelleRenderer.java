/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class CustomElementModulTabelleRenderer extends AbstractSimpleCustomElementRenderer {

    @Inject
    @SystemProperty(value = "customelement.key.table_module_uebersicht", fallback = "${h5_table_module_overview}")
    ConfigurationProperty customElementKey;
    @Inject
    VelocityAdapter velocityAdapter;

    @Override
    protected String getCustomElementKey() {
        return customElementKey.getStringValue();
    }

    @Override
    protected String
            renderCustomElement(PublishContainer publishContainer, final LocalizationEngine localizationEngine) {
        List<Modul> module = publishContainer.getModule();
        Map<String, Object> context = initContext(localizationEngine);
        List<Modul> moduleSorted = new ArrayList<Modul>(module);
        final Collator collator = Collator.getInstance(new Locale(localizationEngine.getLanguage()));
        Collections.sort(moduleSorted, new Comparator<Modul>() {

            @Override
            public int compare(Modul o1, Modul o2) {
                return collator.compare(localizationEngine.localize(o1.getPresentationName()),
                        localizationEngine.localize(o2.getPresentationName()));
            }
        });
        context.put("module", moduleSorted);
        return velocityAdapter.mergeTemplates(context, "custom_module_tabelle.vm");
    }
}
