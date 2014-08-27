/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.startpage;

import static ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungStartPageRenderer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilderUtils;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.AssertUtils;
import ch.admin.isb.hermes5.util.ZipUtil;

public class StartPageGeneratorTest {

    private StartPageGenerator startPageGenerator;
    private ZipUtil zipUtil;

    @Before
    public void setUp() throws Exception {
        zipUtil = new ZipUtil();
        startPageGenerator = new StartPageGenerator();
        startPageGenerator.anwenderloesungStartPageRenderer = mock(AnwenderloesungStartPageRenderer.class);
        startPageGenerator.zipOutputBuilderUtils = new ZipOutputBuilderUtils();
    }

    @Test
    public void testAddStartPage() {
        ZipOutputBuilder zipBuilder = mock(ZipOutputBuilder.class);

        boolean projektstrukturplan = true;
        boolean dokumentation = false;
        boolean ergebnisvorlagen = true;
        boolean xmlmodel = false;
        List<String> languages = Arrays.asList(new String[] { "de", "fr", "it", "en" });
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        LocalizationEngine localizationEngineDe = getLocalizationEngineDe();

        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model_id", null,
                szenarioUserData, languages, dokumentation, projektstrukturplan, ergebnisvorlagen, xmlmodel);
        when(startPageGenerator.anwenderloesungStartPageRenderer.renderStartPage(container, localizationEngineDe))
                .thenReturn("content_de");
        startPageGenerator.addStartPage(container, zipBuilder, localizationEngineDe);
        verify(zipBuilder).addFile(eq("start_de.html"), eq("content_de".getBytes()));
    }

    @Test
    public void testAddResources() throws IOException {
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();

        startPageGenerator.addCommonResources(zipBuilder, new SzenarioUserData());

        byte[] result = zipBuilder.getResult();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(result));
        ZipEntry nextEntry;
        List<String> names = new ArrayList<String>();
        while ((nextEntry = zipInputStream.getNextEntry()) != null) {
            names.add(nextEntry.getName());
            byte[] byteArray = zipUtil.readZipEntry(zipInputStream);
            assertNotNull(byteArray);
        }
        assertEquals(11, names.size());
        AssertUtils.assertContains(names, "resources/service_image.png", "resources/style.css",
                "resources/style_print.css", "resources/h5-font.eot", "resources/h5-font.svg", "resources/h5-font.ttf",
                "resources/h5-font.woff", "resources/ico_extern.gif", "resources/Bulletpoint_Hermes.png",
                "resources/HERMES_Internet_Manual.png", "resources/logo.png");
    }

}
