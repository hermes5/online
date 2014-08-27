/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import java.util.List;

import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;

public class ModulAufgabeTableBuilder {

    public SzenarioTable buildModulTable(List<Phase> phasen, Modul modul) {
        SzenarioTable table = new SzenarioTable();
        SzenarioTable.SzenarioRow header = new SzenarioTable.SzenarioRow();
        header.setHeader();
        table.getRows().add(header);
        SzenarioTable.SzenarioRow contentRow = new SzenarioTable.SzenarioRow();
        boolean atLeastOneAufgabeInRow = false;
        for (Phase phase : phasen) {
            header.getCells().add(new SzenarioTable.SzenarioCell(phase));
            SzenarioTable.SzenarioCell cell = new SzenarioTable.SzenarioCell();
            for (Aufgabe aufgabe : phase.getAufgaben()) {
                List<Modul> modulOfAufgabe = aufgabe.getModule();
                for (Modul m : modulOfAufgabe) {
                    if (m.getId().equals(modul.getId())) {
                        atLeastOneAufgabeInRow = true;
                        cell.getElements().add(aufgabe);
                    }
                }
            }
            contentRow.getCells().add(cell);
        }
        if (atLeastOneAufgabeInRow) {
            table.getRows().add(contentRow);
        }

        return table;
    }

   
}
