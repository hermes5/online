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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.customelement.AbstractCustomElementModulBasedTabelleRow.ModulCellElement;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class CustomElementErgebnisTabelleRenderer extends AbstractSimpleCustomElementRenderer {

    @Inject
    @SystemProperty(value = "customelement.key.table_ergebnis_uebersicht", fallback = "${h5_table_ergebnis_uebersicht}")
    ConfigurationProperty customElementKey;

    @Inject
    @SystemProperty(value = "customelement.table_ergebnis_anwenderModelName", fallback = "anwender")
    ConfigurationProperty anwenderModelName;

    @Inject
    @SystemProperty(value = "customelement.table_ergebnis_erstellerModelName", fallback = "ersteller")
    ConfigurationProperty erstellerModelName;
    @Inject
    @SystemProperty(value = "customelement.table_ergebnis_betreiberModelName", fallback = "betreiber")
    ConfigurationProperty betreiberModelName;

    @Inject
    @SystemProperty(value = "customelement.table_ergebnis_selected_character", fallback = "X")
    ConfigurationProperty selectedCharacter;
    @Inject
    @SystemProperty(value = "customelement.table_ergebnis_selected__and_required_character", fallback = "X*")
    ConfigurationProperty selectedAndRequiredCharacter;

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
        List<Ergebnis> ergebnisse = publishContainer.getErgebnisse();
        if (ergebnisse.isEmpty()) {
            return "";
        }
        Map<String, Object> context = initContext(localizationEngine);
        context.put("ergebnisse", buildModel(ergebnisse, localizationEngine));
        context.put("x", selectedCharacter.getStringValue());
        context.put("x_required", selectedAndRequiredCharacter.getStringValue());
        return velocityAdapter.mergeTemplates(context, "custom_ergebnis_tabelle.vm");
    }

    private List<CustomeElementErgebnisTabelleRow> buildModel(List<Ergebnis> ergebnisse,
            LocalizationEngine localizationEngine) {
        List<CustomeElementErgebnisTabelleRow> model = new ArrayList<CustomeElementErgebnisTabelleRow>();
        for (Ergebnis ergebnis : ergebnisse) {
            List<Modul> module = ergebnis.getModule();
            for (Modul modul : module) {
                ModulCellElement modulCells = customElementRendererUtil.renderModulCellElements(localizationEngine,
                        modul);
                String ergebnisString = localizationEngine.localize(ergebnis.getPresentationName());
                boolean anwender = isAnwender(ergebnis);
                boolean ersteller = isErsteller(ergebnis);
                boolean betreiber = isBetreiber(ergebnis);
                String link = ergebnis.getName() + ".html";
                model.add(new CustomeElementErgebnisTabelleRow(ergebnisString, anwender, ersteller, betreiber, link,
                        modulCells, ergebnis.isRequired(), localizationEngine.getLanguage()));
            }
        }

        Collections.sort(model);

        customElementRendererUtil.removeModulesToClearUpTableIfItIsTheSameAsTheRowAbove(model);
        customElementRendererUtil.setRowClassesBasedOnModules(model);

        return model;
    }

    private boolean isBetreiber(Ergebnis ergebnis) {
        return customElementRendererUtil.roleGroupMatch(betreiberModelName.getStringValue(),
                ergebnis.getVerantwortlicheRollen());
    }

    private boolean isErsteller(Ergebnis ergebnis) {
        return customElementRendererUtil.roleGroupMatch(erstellerModelName.getStringValue(),
                ergebnis.getVerantwortlicheRollen());
    }

    private boolean isAnwender(Ergebnis ergebnis) {
        return customElementRendererUtil.roleGroupMatch(anwenderModelName.getStringValue(),
                ergebnis.getVerantwortlicheRollen());
    }
}
