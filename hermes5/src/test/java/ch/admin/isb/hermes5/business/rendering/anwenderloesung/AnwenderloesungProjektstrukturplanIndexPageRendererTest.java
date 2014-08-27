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

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungProjektstrukturplanIndexPageRendererTest extends AbstractRendererTest {

    private AnwenderloesungProjektstrukturplanIndexPageRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungProjektstrukturplanIndexPageRenderer();
        Hardcoded.enableDefaults(renderer);

        renderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
    }

    @Test
    public void pageContainsExcelLink() {
        assertTrue(renderHtml().contains(
                "<a href=\"Workbreakdownstructure_de.xlsx\">Workbreakdownstructure_de.xlsx</a>"));
    }

    @Test
    public void pageContainsMSProjectLink() {
        assertTrue(renderHtml().contains("<a href=\"Workbreakdownstructure_de.mpx\">Workbreakdownstructure_de.mpx</a>"));
    }

    @Test
    public void checkIfHtmlIsValid() {
        checkHtmlString(renderHtml());
    }

    @Test
    public void checkNavigation() {
        String html = renderHtml();
        assertTrue(
                "Navigation point not present!",
                html.contains("<a href=\"../../workbreakdownstructure/de/index.html\" class=\"navi-ebene1\">Projektstrukturplan</a>"));

        assertTrue("Navigation point not marked as selected!",
                html.contains("<div class=\"navi-ebene1-div selected\">"));
    }

    private String renderHtml() {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("",
                szenario("projektstrukturPlanPageSzenario"), null, Arrays.asList("de"), false, true, false,true);

        return renderer.renderProjektstrukturplanIndexPage(getLocalizationEngineDe(), container, true, false);
    }
}
