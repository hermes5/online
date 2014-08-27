/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;

@Named
@SessionScoped
public class SzenarioElementeController extends AbstractViewController implements Serializable {

    private static final long serialVersionUID = 1L;
    private SzenarioItem highlightedPhase;
    private SzenarioItem highlightedModule;
    private SzenarioItem highlightedTask;

    @Inject
    SzenarioWizardContext szenarioWizardContext;
    @Inject
    SzenarioProjektdatenController szenarioProjektdatenController;
    @Inject
    SzenarioEigeneElementeController szenarioEigeneElementeController;

    public String display() {
        if (getSzenario() == null) {
            inizializeTree();
        }
        highlightedPhase = null;
        highlightedModule = null;
        highlightedTask = null;
        return getIdentifier();
    }

    private SzenarioItem getSzenario() {
        return szenarioWizardContext.getSzenarioUserData().getSzenarioTree();
    }

    private void inizializeTree() {
        SzenarioItem szenario = new SzenarioItem();
        szenario.setSelected(true);
        List<Phase> phasen = szenarioWizardContext.getSzenario().getPhasen();
        for (Phase p : phasen) {
            SzenarioItem phaseItem = new SzenarioItem(p.getId(), p.getPresentationName(), szenario);
            szenario.getChildren().add(phaseItem);
            List<Modul> module = p.getModule();
            for (Modul m : module) {
                SzenarioItem modulItem = new SzenarioItem(m.getId(), m.getPresentationName(), phaseItem);
                phaseItem.getChildren().add(modulItem);
                List<Aufgabe> aufgabenModul = m.getAufgaben();
                List<Aufgabe> aufgabenPhase = p.getAufgaben();
                for (Aufgabe a : aufgabenModul) {
                    if (aufgabenPhase.contains(a)) {
                        SzenarioItem aufgabeItem = new SzenarioItem(a.getId(), a.getPresentationName(), modulItem);
                        modulItem.getChildren().add(aufgabeItem);
                        List<Ergebnis> ergebnisse = a.getErgebnisse();
                        for (Ergebnis e : ergebnisse) {
                            aufgabeItem.getChildren().add(
                                    new SzenarioItem(e.getId(), e.getPresentationName(), aufgabeItem));
                        }
                    }
                }

            }
        }
        szenarioWizardContext.getSzenarioUserData().setSzenarioTree(szenario);
    }

    public List<SzenarioItem> getPhasen() {
        return getSzenario().getChildren();
    }

    public List<SzenarioItem> getModules() {
        if (getHighlightedPhase() == null) {
            return null;
        }
        return getHighlightedPhase().getChildren();
    }

    public List<SzenarioItem> getTasks() {
        if (getHighlightedModule() == null) {
            return null;
        }
        return getHighlightedModule().getChildren();
    }

    public List<SzenarioItem> getWorkProducts() {
        if (getHighlightedTask() == null) {
            return null;
        }
        return getHighlightedTask().getChildren();
    }

    public String goBack() {
        return redirectTo(szenarioProjektdatenController.display());
    }

    public String gotoEigeneElemente() {
        return redirectTo(szenarioEigeneElementeController.display());
    }

    @Override
    public String getIdentifier() {
        return "szenario-elemente";
    }

    public SzenarioItem getHighlightedPhase() {
        return highlightedPhase;
    }


    public void setHighlightedPhase(SzenarioItem highlightedPhase) {
        this.highlightedPhase = highlightedPhase;
        highlightedModule = null;
        highlightedTask = null;
    }

    public SzenarioItem getHighlightedModule() {
        return highlightedModule;
    }

    public void setHighlightedModule(SzenarioItem highlightedModule) {
        this.highlightedModule = highlightedModule;
        highlightedTask = null;
    }

    public SzenarioItem getHighlightedTask() {
        return highlightedTask;
    }

    public void setHighlightedTask(SzenarioItem highlightedTask) {
        this.highlightedTask = highlightedTask;
    }
}
