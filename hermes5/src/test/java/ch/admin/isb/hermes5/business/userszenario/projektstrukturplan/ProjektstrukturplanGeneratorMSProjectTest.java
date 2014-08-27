/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.projektstrukturplan;

import static ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mpx.MPXReader;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioBuilder;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ZipUtil;

public class ProjektstrukturplanGeneratorMSProjectTest {

    private static final String ROOT = "rootdir";

    private ProjektstrukturplanGeneratorMSProject projektstrukturplanGeneratorMSProject;
    private final ZipUtil zipUtil = new ZipUtil();

    @Before
    public void setUp() throws Exception {
        projektstrukturplanGeneratorMSProject = new ProjektstrukturplanGeneratorMSProject();
        Hardcoded.enableDefaults(projektstrukturplanGeneratorMSProject);
    }

    @Test
    public void phaseName() throws IOException, MPXJException {
        ProjectFile projectFile = buildProjektstrukturPlan();
        assertEquals("MODEL/DE/PHASEID_PHASE1/PRESENTATIONNAME", projectFile.getAllTasks().get(0).getName());
    }

    @Test
    public void moduleName() throws IOException, MPXJException {
        ProjectFile projectFile = buildProjektstrukturPlan();
        assertEquals("MODEL/DE/DISCIPLINEID_MODUL1/PRESENTATIONNAME", projectFile.getAllTasks().get(1).getName());
    }

    @Test
    public void taskName() throws IOException, MPXJException {
        ProjectFile projectFile = buildProjektstrukturPlan();
        assertEquals("model/de/taskid_task1/presentationName", projectFile.getAllTasks().get(2).getName());
    }

    @Test
    public void taskResources() throws IOException, MPXJException {
        ProjectFile projectFile = buildProjektstrukturPlan();
        assertEquals("model/de/roleid_rolle1/presentationName", projectFile.getAllTasks().get(2)
                .getResourceAssignments().get(0).getResource().getName());
    }

    @Test
    public void ereignisName() throws IOException, MPXJException {
        ProjectFile projectFile = buildProjektstrukturPlan();
        assertEquals("model/de/wpid_ergebnis1/presentationName", projectFile.getAllTasks().get(3).getName());
    }

    @Test
    public void ergebnisResources() throws IOException, MPXJException {
        ProjectFile projectFile = buildProjektstrukturPlan();
        assertEquals("model/de/roleid_rolle2/presentationName", projectFile.getAllTasks().get(3)
                .getResourceAssignments().get(0).getResource().getName());
    }

    private ProjectFile buildProjektstrukturPlan() throws IOException, MPXJException {
        Szenario szenario = new Szenario(null);
        szenario.getPhasen().add(SzenarioBuilder.phaseFull("phase1"));

        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();

        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario,
                new SzenarioUserData(), Arrays.asList("de"), true, true, true,true);

        projektstrukturplanGeneratorMSProject.addProjektstrukturPlan(ROOT, container, zipBuilder,
                getLocalizationEngine("de"), true, false);

        byte[] result = zipBuilder.getResult();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(result));
        ZipEntry nextEntry;

        while ((nextEntry = zipInputStream.getNextEntry()) != null) {
            assertEquals(ROOT + "/de/Workbreakdownstructure_de.mpx", nextEntry.getName());

            byte[] byteArray = zipUtil.readZipEntry(zipInputStream);
            assertNotNull(byteArray);

            // java.io.FileOutputStream fileOutputStream = new
            // java.io.FileOutputStream("target/projektstrukturplan.mpx");
            // fileOutputStream.write(byteArray);
            // fileOutputStream.close();
            
            return new MPXReader().read(new ByteArrayInputStream(byteArray));
        }

        throw new AssertionError("project file not found");
    }
}
