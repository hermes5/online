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

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungProjektstrukturplanIndexPageRenderer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.util.StringUtil;

public class ProjektstrukturplanIndexPageGeneratorTest {

    private static final String FILE_CONTENT = "content";
    private static final String ROOT = "rootdir";

    private ProjektstrukturplanIndexPageGenerator projektstrukturplanIndexPageGenerator;

    @Before
    public void setUp() {
        projektstrukturplanIndexPageGenerator = new ProjektstrukturplanIndexPageGenerator();
    }

    @Test
    public void testAddProjektstrukturPlanIndexPage() {
        projektstrukturplanIndexPageGenerator.anwenderloesungProjektstrukturplanIndexPageRenderer = mock(AnwenderloesungProjektstrukturplanIndexPageRenderer.class);
        when(
                projektstrukturplanIndexPageGenerator.anwenderloesungProjektstrukturplanIndexPageRenderer
                        .renderProjektstrukturplanIndexPage(any(LocalizationEngine.class),
                                any(AnwenderloesungRenderingContainer.class), anyBoolean(), anyBoolean())).thenReturn(
                FILE_CONTENT);

        ZipOutputBuilder zipBuilder = mock(ZipOutputBuilder.class);

        projektstrukturplanIndexPageGenerator.addProjektstrukturPlanIndexPage(ROOT,
                mock(AnwenderloesungRenderingContainer.class), zipBuilder, getLocalizationEngineDe(), true, false);

        verify(projektstrukturplanIndexPageGenerator.anwenderloesungProjektstrukturplanIndexPageRenderer)
                .renderProjektstrukturplanIndexPage(any(LocalizationEngine.class),
                        any(AnwenderloesungRenderingContainer.class), anyBoolean(), anyBoolean());

        verify(zipBuilder).addFile(eq(ROOT + "/de/index.html"), eq(StringUtil.getBytes(FILE_CONTENT)));
    }
}
