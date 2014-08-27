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
import java.util.List;

import ch.admin.isb.hermes5.business.rendering.customelement.AbstractCustomElementModulBasedTabelleRow.ModulCellElement;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.RollenGruppe;

public class CustomElementRendererUtil {

    ModulCellElement renderModulCellElements(LocalizationEngine localizationEngine, Modul modul) {
        return new ModulCellElement(localizationEngine.localize(modul.getPresentationName()), modul.getName() + ".html");
    }

    boolean roleGroupMatch(String key, List<Rolle> verantwortlicheRollen) {
        if (verantwortlicheRollen == null) {
            return false;
        }
        List<RollenGruppe> rollengruppen = new ArrayList<RollenGruppe>();
        for (Rolle rolle : verantwortlicheRollen) {
            rollengruppen.addAll(rolle.getRollengruppen());
        }

        for (RollenGruppe rollenGruppe : rollengruppen) {
            if (key.equals(rollenGruppe.getElement().getName())) {
                return true;
            } else {
            }
        }
        return false;
    }

    void removeModulesToClearUpTableIfItIsTheSameAsTheRowAbove(
            List<? extends AbstractCustomElementModulBasedTabelleRow> model) {
        for (int i = model.size() - 1; i >= 0; i--) {
            if (i > 0 && model.get(i).getModul().equals(model.get(i - 1).getModul())) {
                model.get(i).clearModul();
            }
        }
    }

    /**
     * set even or odd based on the modules
     */
    public void setRowClassesBasedOnModules(List<? extends AbstractCustomElementModulBasedTabelleRow> model) {
        String currentStyle = null;
        for (AbstractCustomElementModulBasedTabelleRow customeElementAufgabeTabelleRow : model) {
            if (!customeElementAufgabeTabelleRow.getModul().isCleared()) {
                currentStyle = toggleCurrentStyle(currentStyle);
            }
            customeElementAufgabeTabelleRow.setRowClass(currentStyle);
        }
    }

    private String toggleCurrentStyle(String currentStyle) {
        return "even".equals(currentStyle) ? "odd" : "even";
    }

}
