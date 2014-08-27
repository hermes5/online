/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.customelement.AbstractCustomElementModulBasedTabelleRow.ModulCellElement;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class CustomElementAufgabeTabelleRenderer extends AbstractSimpleCustomElementRenderer {

    @Inject
    @SystemProperty(value = "customelement.key.table_aufgabe_uebersicht", fallback = "${h5_table_aufgabe_uebersicht}")
    ConfigurationProperty customElementKey;

    @Inject
    @SystemProperty(value = "customelement.table_aufgabe_anwenderModelName", fallback = "anwender")
    ConfigurationProperty anwenderModelName;

    @Inject
    @SystemProperty(value = "customelement.table_aufgabe_erstellerModelName", fallback = "ersteller")
    ConfigurationProperty erstellerModelName;
    @Inject
    @SystemProperty(value = "customelement.table_aufgabe_betreiberModelName", fallback = "betreiber")
    ConfigurationProperty betreiberModelName;

    @Inject
    @SystemProperty(value = "customelement.table_aufgabe_selected_character", fallback = "X")
    ConfigurationProperty selectedCharacter;

    @Inject
    VelocityAdapter velocityAdapter;

    @Inject
    CustomElementRendererUtil customElementRendererUtil;

    @Override
    protected String getCustomElementKey() {
        return customElementKey.getStringValue();
    }

    @Override
    protected String renderCustomElement(PublishContainer publishContainer, LocalizationEngine localizationEngine) {
        List<Aufgabe> aufgaben = publishContainer.getAufgaben();
        if (aufgaben.isEmpty()) {
            return "";
        }
        Map<String, Object> context = initContext(localizationEngine);
        context.put("aufgaben", buildModel(aufgaben, localizationEngine));
        context.put("x", selectedCharacter.getStringValue());
        return velocityAdapter.mergeTemplates(context, "custom_aufgabe_tabelle.vm");
    }

    private List<CustomeElementAufgabeTabelleRow> buildModel(List<Aufgabe> aufgaben,
            LocalizationEngine localizationEngine) {
        List<CustomeElementAufgabeTabelleRow> model = new ArrayList<CustomeElementAufgabeTabelleRow>();
        for (Aufgabe aufgabe : aufgaben) {
            List<Modul> module = aufgabe.getModule();
            for (Modul modul : module) {
                ModulCellElement modulCellElement = customElementRendererUtil.renderModulCellElements(
                        localizationEngine, modul);
                String aufgabeString = localizationEngine.localize(aufgabe.getPresentationName());
                boolean anwender = isAnwender(aufgabe);
                boolean ersteller = isErsteller(aufgabe);
                boolean betreiber = isBetreiber(aufgabe);
                String link = aufgabe.getName() + ".html";
                model.add(new CustomeElementAufgabeTabelleRow(aufgabeString, anwender, ersteller, betreiber, link,
                        modulCellElement, localizationEngine.getLanguage()));
            }
           
        }
        
        Collections.sort(model);
        
        customElementRendererUtil.removeModulesToClearUpTableIfItIsTheSameAsTheRowAbove(model);
        customElementRendererUtil.setRowClassesBasedOnModules(model);

        return model;
    }

    private boolean isBetreiber(Aufgabe aufgabe) {
        return rollenGruppeMatch(aufgabe, betreiberModelName.getStringValue());
    }

    private boolean isErsteller(Aufgabe aufgabe) {
        return rollenGruppeMatch(aufgabe, erstellerModelName.getStringValue());
    }

    private boolean isAnwender(Aufgabe aufgabe) {
        return rollenGruppeMatch(aufgabe, anwenderModelName.getStringValue());
    }

    private boolean rollenGruppeMatch(Aufgabe aufgabe, String key) {
        Rolle verantwortlicheRolle = aufgabe.getVerantwortlicheRolle();
        if (verantwortlicheRolle == null) {
            return false;
        }
        return customElementRendererUtil.roleGroupMatch(key, Arrays.asList(verantwortlicheRolle));
    }
}
