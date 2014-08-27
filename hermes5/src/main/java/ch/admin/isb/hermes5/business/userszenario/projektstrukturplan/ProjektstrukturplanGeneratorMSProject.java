/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.projektstrukturplan;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import net.sf.mpxj.Duration;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Resource;
import net.sf.mpxj.Task;
import net.sf.mpxj.TimeUnit;
import net.sf.mpxj.mpx.MPXWriter;
import net.sf.mpxj.mspdi.MSPDIWriter;

import org.apache.commons.io.output.ByteArrayOutputStream;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.SzenarioItemUtil;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class ProjektstrukturplanGeneratorMSProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    @SystemProperty(value = "al.projektstrukturplan.msproject.defaultDuration", fallback = "1")
    ConfigurationProperty projektstrukturplanDefaultDuration;

    @Inject
    SzenarioItemUtil szenarioItemUtil;

    public void addProjektstrukturPlan(String root, AnwenderloesungRenderingContainer container,
            ZipOutputBuilder zipBuilder, LocalizationEngine localizationEngine, boolean generateMpxFile,
            boolean generateXmlFile) {

        Szenario szenario = container.getSzenario();
        SzenarioItem szenarioTree = container.getSzenarioUserData().getSzenarioTree();

        try {
            ProjectFile projectFile = new ProjectFile();

            Task predecessorPhaseTask = null;
            for (Phase phase : szenario.getPhasen()) {
                Task phaseTask = addPhase(projectFile, phase, localizationEngine, szenarioTree);
                if (predecessorPhaseTask != null) {
                    phaseTask.addPredecessor(predecessorPhaseTask, RelationType.FINISH_START, null);
                }

                predecessorPhaseTask = phaseTask;
            }

            // MPX File
            if (generateMpxFile) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                MPXWriter mpxWriter = new MPXWriter();
                mpxWriter.write(projectFile, out);
                zipBuilder.addFile(root + "/" + localizationEngine.getLanguage() + "/" + "Workbreakdownstructure_"
                        + localizationEngine.getLanguage() + ".mpx", out.toByteArray());
            }

            // XML File
            if (generateXmlFile) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                MSPDIWriter mspdiWriter = new MSPDIWriter();
                mspdiWriter.write(projectFile, out);
                zipBuilder.addFile(root + "/" + localizationEngine.getLanguage() + "/" + "Workbreakdownstructure_"
                        + localizationEngine.getLanguage() + ".xml", out.toByteArray());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Task addPhase(ProjectFile projectFile, Phase phase, LocalizationEngine localizationEngine,
            SzenarioItem szenarioTree) {

        Task phaseTask = projectFile.addTask();
        phaseTask.setExpanded(false);

        String phaseName = localizationEngine.localize(phase.getPresentationName());
        phaseTask.setName(isNotBlank(phaseName) ? phaseName.toUpperCase() : phaseName);

        List<Aufgabe> aufgabenInPhase = phase.getAufgaben();
        List<Modul> module = phase.getModule();
        for (Modul modul : module) {
            addModul(projectFile, phaseTask, phase, modul, aufgabenInPhase, localizationEngine, szenarioTree);
        }

        return phaseTask;
    }

    private void addModul(ProjectFile projectFile, Task phaseTask, Phase phase, Modul modul,
            List<Aufgabe> aufgabenInPhase, LocalizationEngine localizationEngine, SzenarioItem szenarioTree) {

        String modulName = localizationEngine.localize(modul.getPresentationName());
        Task modulTask = createSubTask(phaseTask, isNotBlank(modulName) ? modulName.toUpperCase() : modulName);

        for (Aufgabe aufgabe : modul.getAufgaben()) {
            if (aufgabenInPhase.contains(aufgabe)) {
                addAufgabe(projectFile, modulTask, phase, modul, aufgabe, localizationEngine, szenarioTree);
            }
        }
    }

    private void addAufgabe(ProjectFile projectFile, Task modulTask, Phase phase, Modul modul, Aufgabe aufgabe,
            LocalizationEngine localizationEngine, SzenarioItem szenarioTree) {

        String aufgabeName = localizationEngine.localize(aufgabe.getPresentationName());
        Task aufgabeTask = createSubTask(modulTask, aufgabeName);

        Rolle verantwortlicheRolle = aufgabe.getVerantwortlicheRolle();
        if (verantwortlicheRolle != null) {
            String roleName = localizationEngine.localize(verantwortlicheRolle.getPresentationName());
            addRoleToTask(projectFile, aufgabeTask, roleName);

            aufgabeTask.setContact(roleName);
        }

        List<Ergebnis> ergebnisse = aufgabe.getErgebnisse();
        for (Ergebnis ergebnis : ergebnisse) {
            if (szenarioTree == null || modul.isCustom()
                    || szenarioItemUtil.isErgebnisSelected(szenarioTree, phase, modul, aufgabe, ergebnis)) {

                String ergebnisName = localizationEngine.localize(ergebnis.getPresentationName());
                Task ergebnisTask = createSubTask(aufgabeTask, ergebnisName);
                ergebnisTask.setDuration(Duration.getInstance(projektstrukturplanDefaultDuration.getIntegerValue(),
                        TimeUnit.DAYS));

                for (Rolle rolle : ergebnis.getVerantwortlicheRollen()) {
                    addRoleToTask(projectFile, ergebnisTask, localizationEngine.localize(rolle.getPresentationName()));
                }
            }
        }
    }

    private Task createSubTask(Task task, String taskName) {
        Task subTask = task.addTask();
        subTask.setExpanded(false);
        subTask.setName(taskName);

        return subTask;
    }

    private void addRoleToTask(ProjectFile projectFile, Task task, String roleName) {
        Resource resource = null;
        for (Resource candidate : projectFile.getAllResources()) {
            String candidateName = candidate.getName();
            if (candidateName != null && candidateName.equals(roleName)) {
                resource = candidate;
                break;
            }
        }

        if (resource == null) {
            resource = projectFile.addResource();
            resource.setName(roleName);
        }

        task.addResourceAssignment(resource);
    }
}
