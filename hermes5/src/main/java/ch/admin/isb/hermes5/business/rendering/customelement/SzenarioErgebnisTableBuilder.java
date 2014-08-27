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

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.customelement.SzenarioTable.SzenarioCell;
import ch.admin.isb.hermes5.business.rendering.customelement.SzenarioTable.SzenarioRow;
import ch.admin.isb.hermes5.business.util.SzenarioItemUtil;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.domain.SzenarioUserData;

public class SzenarioErgebnisTableBuilder {

    @Inject
    SzenarioItemUtil szenarioItemUtil;

    public SzenarioTable buildSzenarioTable(Szenario szenario, SzenarioUserData szenarioUserData) {
        SzenarioTable table = new SzenarioTable();
        List<Phase> phasen = szenario.getPhasen();
        SzenarioRow header = new SzenarioRow();
        header.setHeader();
        SzenarioCell empty = new SzenarioCell().markEmptyCell();
        header.getCells().add(empty);
        for (Phase phase : phasen) {
            header.getCells().add(new SzenarioCell(phase));
        }
        table.getRows().add(header);
        List<Modul> module = szenario.getModule();
        for (Modul modul : module) {
            SzenarioRow row = new SzenarioRow();
            SzenarioCell modulCell = new SzenarioCell(modul);
            row.getCells().add(modulCell);
            for (Phase phase : phasen) {
                SzenarioCell cell = new SzenarioCell();
                List<Aufgabe> aufgaben = phase.getAufgaben();
                for (Aufgabe aufgabe : aufgaben) {
                    if (aufgabe.getModule().contains(modul)) {
                        List<Ergebnis> ergebnisse = aufgabe.getErgebnisse();
                        SzenarioItem szenarioTree = szenarioUserData.getSzenarioTree();
                        if (szenarioTree == null || modul.isCustom()) {
                            cell.getElements().addAll(ergebnisse);
                        } else {
                            for (Ergebnis ergebnis : ergebnisse) {
                                if (szenarioItemUtil.isErgebnisSelected(szenarioTree, phase, modul, aufgabe, ergebnis)) {
                                    cell.getElements().add(ergebnis);
                                }
                            }
                        }
                    }
                }
                row.getCells().add(cell);
            }
            table.getRows().add(row);
        }
        return table;
    }
}
