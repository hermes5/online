/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.projektstrukturplan;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioBuilder;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ZipUtil;

public class ProjektstrukturplanGeneratorExcelTest {

    private ProjektstrukturplanGeneratorExcel projektstrukturplanGeneratorExcel;
    private final ZipUtil zipUtil = new ZipUtil();

    @Before
    public void setUp() throws Exception {
        projektstrukturplanGeneratorExcel = new ProjektstrukturplanGeneratorExcel();
        projektstrukturplanGeneratorExcel.translationRepository = mock(TranslationRepository.class);
        Hardcoded.enableDefaults(projektstrukturplanGeneratorExcel);
        when(
                projektstrukturplanGeneratorExcel.translationRepository.getLocalizedText(anyString(), anyString(),
                        anyString(), anyString())).thenAnswer(new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                String s = "";
                Object[] arguments = invocation.getArguments();
                for (Object object : arguments) {
                    s += "/" + object;
                }
                return s;
            }
        });
    }

    @Test
    public void testAddProjektstrukturPlanHeader() throws IOException {
        Szenario szenario = new Szenario(null);
        szenario.getPhasen().add(SzenarioBuilder.phaseFull("phase1"));
        String string = buildProjektstrukturPlan(szenario);
        assertFalse(string, string.contains("|Phase|"));
        assertTrue(string, string.contains("|Phase / Modul / Aufgabe / Ergebnis|"));
    }

    @Test
    public void testAddProjektstrukturPlanPhase() throws IOException {
        Szenario szenario = new Szenario(null);
        szenario.getPhasen().add(SzenarioBuilder.phaseFull("phase1"));
        String string = buildProjektstrukturPlan(szenario);
        assertTrue(string, string.contains("|/model/de/phaseid_phase1/presentationName|".toUpperCase()));
    }

    @Test
    public void testAddProjektstrukturPlanModul() throws IOException {
        Szenario szenario = new Szenario(null);
        szenario.getPhasen().add(SzenarioBuilder.phaseFull("phase1"));
        String string = buildProjektstrukturPlan(szenario);
        assertTrue(string, string.contains("|  /model/de/disciplineid_modul1/presentationName|".toUpperCase()));
    }

    @Test
    public void testAddProjektstrukturPlanAufgabe() throws IOException {
        Szenario szenario = new Szenario(null);
        szenario.getPhasen().add(SzenarioBuilder.phaseFull("phase1"));
        String string = buildProjektstrukturPlan(szenario);
        assertTrue(
                string,
                string.contains("|    /model/de/taskid_task1/presentationName|/model/de/roleid_rolle1/presentationName|"));
        assertFalse(string, string.contains("|    /model/de/taskid_task3/presentationName|"));
    }

    @Test
    public void testAddProjektstrukturPlanErgebnis() throws IOException {
        Szenario szenario = new Szenario(null);
        szenario.getPhasen().add(SzenarioBuilder.phaseFull("phase1"));
        String string = buildProjektstrukturPlan(szenario);
        assertTrue(
                string,
                string.contains("|      /model/de/wpid_ergebnis1/presentationName|/model/de/roleid_rolle2/presentationName|"));
    }

    private String buildProjektstrukturPlan(Szenario szenario) throws IOException {
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();

        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario,
                new SzenarioUserData(), Arrays.asList("de"), true, true, true,true);

        projektstrukturplanGeneratorExcel.addProjektstrukturPlan("workbreakdownstructure", container,
                zipBuilder, new LocalizationEngine(projektstrukturplanGeneratorExcel.translationRepository,"model", "de"));

        byte[] result = zipBuilder.getResult();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(result));
        ZipEntry nextEntry;

        while ((nextEntry = zipInputStream.getNextEntry()) != null) {
            assertEquals(nextEntry.getName(), "workbreakdownstructure/de/Workbreakdownstructure_de.xlsx");
            byte[] byteArray = zipUtil.readZipEntry(zipInputStream);
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream("target/projektstrukturplan.xlsx");
            fileOutputStream.write(byteArray);
            fileOutputStream.close();
            assertNotNull(byteArray);
            XSSFWorkbook wb = new XSSFWorkbook(new ByteArrayInputStream(byteArray));
            XSSFSheet sheet = wb.getSheetAt(0);
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(out2);

            for (Row row : sheet) {
                out.print("|");
                // assertTrue("" + row.getLastCellNum(), row.getLastCellNum() < 3);
                for (Cell cell : row) {
                    out.print(cell.getStringCellValue());
                    out.print("|");
                }
                out.println("");
            }
            return new String(out2.toByteArray());
        }
        throw new AssertionError("workbook not found");
    }
}
