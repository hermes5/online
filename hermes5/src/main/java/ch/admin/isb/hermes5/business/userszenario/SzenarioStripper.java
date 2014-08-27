/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.modelutil.MethodenElementFactory;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;

public class SzenarioStripper implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    MethodenElementFactory methodenElementFactory;

    public Szenario stripSzenarioToSelectedItems(Szenario szenarioFull, SzenarioItem szenarioTree) {
        if (szenarioTree == null) {
            return szenarioFull;
        }
        Szenario szenario = new Szenario(szenarioFull.getMethodConfiguration());
        List<Modul> module = new ArrayList<Modul>();
        List<Aufgabe> aufgaben = new ArrayList<Aufgabe>();
        List<Ergebnis> ergebnisse = new ArrayList<Ergebnis>();
        List<Rolle> rollen = new ArrayList<Rolle>();
        for (Phase phaseOriginal : szenarioFull.getPhasen()) {
            SzenarioItem phaseSelected = selected(phaseOriginal, szenarioTree);
            if (phaseSelected != null) {
                Phase phase = new Phase((ch.admin.isb.hermes5.epf.uma.schema.Phase) phaseOriginal.getElement(),
                        phaseOriginal.getMethodLibraryIndex());
                for (Aufgabe aufgabeOriginal : phaseOriginal.getAufgaben()) {
                    List<Modul> modulesOfAufgabe = new ArrayList<Modul>();
                    List<Ergebnis> ergebnisOfAufgabe = new ArrayList<Ergebnis>();
                    for (Modul modulOriginal : aufgabeOriginal.getModule()) {
                        SzenarioItem moduleSelected = selected(modulOriginal, phaseSelected);
                        if (moduleSelected != null) {
                            SzenarioItem selectedAufgabe = selected(aufgabeOriginal, moduleSelected);
                            if (selectedAufgabe != null) {
                                modulesOfAufgabe.add(methodenElementFactory.getOrCloneModul(module, modulOriginal));
                                for (Ergebnis ergebnisOriginal : aufgabeOriginal.getErgebnisse()) {
                                    if (selected(ergebnisOriginal, selectedAufgabe) != null) {
                                        Ergebnis orCreateErgebnis = methodenElementFactory.getOrCloneErgebnis(ergebnisse, ergebnisOriginal);
                                        for (Rolle rolleOriginal : ergebnisOriginal.getVerantwortlicheRollen()) {
                                            orCreateErgebnis.addVerantwortlicheRolle(methodenElementFactory.getOrCloneRolle(rollen,
                                                    rolleOriginal));
                                        }
                                        ergebnisOfAufgabe.add(orCreateErgebnis);
                                    }
                                }
                            }
                        }
                    }
                    if (!modulesOfAufgabe.isEmpty()) {
                        Aufgabe aufgabe = methodenElementFactory.getOrCloneAufgabe(aufgaben, aufgabeOriginal);
                        if (aufgabeOriginal.getVerantwortlicheRolle() != null) {
                            Rolle rolle = methodenElementFactory.getOrCloneRolle(rollen, aufgabeOriginal.getVerantwortlicheRolle());
                            aufgabe.setVerantwortlicheRolle(rolle);
                        }
                        aufgabe.addModule(modulesOfAufgabe);
                        for (Ergebnis ergebnis : ergebnisOfAufgabe) {
                            aufgabe.addErgebnis(ergebnis);
                        }
                        phase.addAufgabe(aufgabe);
                    }
                }
                szenario.getPhasen().add(phase);
            }
        }

        return szenario;
    }

    private SzenarioItem selected(AbstractMethodenElement methodElement, SzenarioItem szenarioTree) {
        for (SzenarioItem phase : szenarioTree.getChildren()) {
            if (methodElement.getId().equals(phase.getId()) && phase.isSelected() && !phase.isDisabled()) {
                return phase;
            }
        }
        return null;
    }

}
