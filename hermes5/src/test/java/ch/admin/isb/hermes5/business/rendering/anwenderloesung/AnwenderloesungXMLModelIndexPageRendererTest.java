/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.anwenderloesung;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungXMLModelIndexPageRendererTest {

    private AnwenderloesungXMLModelIndexPageRenderer anwenderloesungXMLModelIndexPageRenderer;

    @Before
    public void setUp() throws Exception {
        anwenderloesungXMLModelIndexPageRenderer = new AnwenderloesungXMLModelIndexPageRenderer();
        anwenderloesungXMLModelIndexPageRenderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(anwenderloesungXMLModelIndexPageRenderer.velocityAdapter);
    }

    @Test
    public void testRenderXMLModelIndexPageContainsXML() {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario("szenario"), new SzenarioUserData(), Arrays.asList("de"), true, true, true, true);
        String indexPage = anwenderloesungXMLModelIndexPageRenderer.renderXMLModelIndexPage(container , LocalizationEngineBuilder
                .getLocalizationEngineDe());
        
        assertTrue(indexPage, indexPage.contains("<a href=\"model_de.xml\">model_de.xml</a>"));
    }
    
    @Test
    public void testRenderXMLModelIndexPageContainsSchema() {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("model", szenario("szenario"), new SzenarioUserData(), Arrays.asList("de"), true, true, true, true);
        String indexPage = anwenderloesungXMLModelIndexPageRenderer.renderXMLModelIndexPage(container , LocalizationEngineBuilder
                .getLocalizationEngineDe());
        
        assertTrue(indexPage, indexPage.contains("<a href=\"../schema/model_schema.xsd\">model_schema.xsd</a>"));
    }
}
