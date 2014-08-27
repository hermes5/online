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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.util.Hardcoded;

public class ProjektstrukturplanGeneratorTest {

    private ProjektstrukturplanGenerator projektstrukturplanGenerator;
    private AnwenderloesungRenderingContainer container;
    private ZipOutputBuilder zipBuilder;
    private LocalizationEngine localizationEngineDe;

    @Before
    public void setUp() {
        projektstrukturplanGenerator = new ProjektstrukturplanGenerator();
        Hardcoded.enableDefaults(projektstrukturplanGenerator);

        projektstrukturplanGenerator.projektstrukturplanGeneratorExcel = mock(ProjektstrukturplanGeneratorExcel.class);
        projektstrukturplanGenerator.projektstrukturplanGeneratorMSProject = mock(ProjektstrukturplanGeneratorMSProject.class);
        projektstrukturplanGenerator.projektstrukturplanIndexPageGenerator = mock(ProjektstrukturplanIndexPageGenerator.class);

        zipBuilder = new ZipOutputBuilder();
        localizationEngineDe = getLocalizationEngineDe();

        container = mock(AnwenderloesungRenderingContainer.class);
    }

    @Test
    public void verifyExcelFileIsCreated() {
        projektstrukturplanGenerator.generateProjektStrukturplan(zipBuilder, container, localizationEngineDe);

        verify(projektstrukturplanGenerator.projektstrukturplanGeneratorExcel).addProjektstrukturPlan(anyString(),
                eq(container), eq(zipBuilder), eq(localizationEngineDe));
    }

    @Test
    public void verifyProjectFileIsCreated() {
        projektstrukturplanGenerator.generateProjektStrukturplan(zipBuilder, container, localizationEngineDe);

        verify(projektstrukturplanGenerator.projektstrukturplanGeneratorMSProject).addProjektstrukturPlan(anyString(),
                eq(container), eq(zipBuilder), eq(localizationEngineDe),
                eq(projektstrukturplanGenerator.projektstrukturplanMSProjectMpx.getBooleanValue()),
                eq(projektstrukturplanGenerator.projektstrukturplanMSProjectXml.getBooleanValue()));
    }

    @Test
    public void verifyIndexFileIsCreated() {
        projektstrukturplanGenerator.generateProjektStrukturplan(zipBuilder, container, localizationEngineDe);

        verify(projektstrukturplanGenerator.projektstrukturplanIndexPageGenerator).addProjektstrukturPlanIndexPage(
                anyString(), eq(container), eq(zipBuilder), eq(localizationEngineDe),
                eq(projektstrukturplanGenerator.projektstrukturplanMSProjectMpx.getBooleanValue()),
                eq(projektstrukturplanGenerator.projektstrukturplanMSProjectXml.getBooleanValue()));
    }
}
