/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.xmlmodel;

import static ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder.*;
import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungXMLModelIndexPageRenderer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilderUtils;
import ch.admin.isb.hermes5.domain.SzenarioUserData;

public class XMLModelGeneratorTest {

    private XMLModelGenerator xmlModelGenerator;
    private LocalizationEngine localizationEngine;

    @Before
    public void setUp() throws Exception {
        xmlModelGenerator = new XMLModelGenerator();
        xmlModelGenerator.xmlModelAssembler = mock(XMLModelAssembler.class);
        xmlModelGenerator.anwenderloesungXMLModelIndexPageRenderer = mock(AnwenderloesungXMLModelIndexPageRenderer.class);
        xmlModelGenerator.zipOutputBuilderUtils = new ZipOutputBuilderUtils();

        localizationEngine = getLocalizationEngineDe();

    }

    @Test
    public void testAddXMLModelWithContent() {
        AnwenderloesungRenderingContainer container = dummyContainer();
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        when(xmlModelGenerator.xmlModelAssembler.assembleXML(container, localizationEngine)).thenReturn("mystring");
        xmlModelGenerator.addXMLModelWithContent(container, zipBuilder, localizationEngine);
        assertEquals("mystring", new XMLModelGeneratorTestUtil().readFileToString("model/de/model_de.xml", zipBuilder));
    }

    @Test
    public void testAddModelIndex() {
        AnwenderloesungRenderingContainer container = dummyContainer();
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        when(
                xmlModelGenerator.anwenderloesungXMLModelIndexPageRenderer.renderXMLModelIndexPage(container,
                        localizationEngine)).thenReturn("mystring");
        xmlModelGenerator.addIndexPage(container, zipBuilder, localizationEngine);
        assertEquals("mystring", new XMLModelGeneratorTestUtil().readFileToString("model/de/index.html", zipBuilder));
    }

    @Test
    public void testAddXSDToZip() {
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        xmlModelGenerator.addXSDToZip(zipBuilder);

        String firstFile = new XMLModelGeneratorTestUtil().readFileToString("model/schema/model_schema.xsd", zipBuilder);

        assertTrue(firstFile, firstFile.contains("targetNamespace=\"http://www.hermes.admin.ch/model/hermes5/v1\""));
    }

    private AnwenderloesungRenderingContainer dummyContainer() {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario("abc"),
                new SzenarioUserData(), Arrays.asList("de", "fr"), true, true, true, true);
        return container;
    }

}
