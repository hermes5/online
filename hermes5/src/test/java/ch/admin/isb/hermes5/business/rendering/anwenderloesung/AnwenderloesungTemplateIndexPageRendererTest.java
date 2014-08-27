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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.userszenario.vorlagen.ErgebnisLink;
import ch.admin.isb.hermes5.business.userszenario.vorlagen.ErgebnisLink.Type;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungTemplateIndexPageRendererTest extends AbstractRendererTest {

    private AnwenderloesungTemplateIndexPageRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungTemplateIndexPageRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
    }

    @Test
    public void buildTemplateIndexPage() throws IOException {
        List<ErgebnisLink> templateUrls = Arrays.asList(new ErgebnisLink[] {ergebnisTemplateLink("t1"), ergebnisTemplateLink("t2"), ergebnisTemplateLink("t3") });
        String x = renderTemplateIndexPage("", templateUrls, Arrays.asList("de"));
        checkHtmlString(x);
        assertTrue(x, x.contains("templatePageSzenario"));
        assertTrue(x, x.contains("<a href=\"t1\">t1</a>"));
        assertTrue(x, x.contains("<a href=\"t2\">t2</a>"));
        assertTrue(x, x.contains("<a href=\"t3\">t3</a>"));
        assertFalse(x, x.contains("<img id=\"logo\""));
    }
    
    @Test
    public void buildTemplateIndexPageUrl() throws IOException {
        List<ErgebnisLink> templateUrls = Arrays.asList(new ErgebnisLink[] {new ErgebnisLink("name", "url", Type.URL)  });
        String x = renderTemplateIndexPage("", templateUrls, Arrays.asList("de"));
        checkHtmlString(x);
        assertTrue(x, x.contains("<a href=\"url\" target=\"_blank\">name</a>"));
    }

    private ErgebnisLink ergebnisTemplateLink(String url) {
        return new ErgebnisLink(url, url, Type.TEMPLATE);
    }

    @Test
    public void buildTemplateIndexPageContainsSzenarioName() throws IOException {
        List<ErgebnisLink> templateUrls = Arrays.asList(new ErgebnisLink[] { ergebnisTemplateLink("t1") });
        String x = renderTemplateIndexPage("", templateUrls, Arrays.asList("de"));
        checkHtmlString(x);
        assertTrue(x, x.contains("templatePageSzenario"));
    }

    private String renderTemplateIndexPage(String identifier, List<ErgebnisLink> templateNames, List<String> languages) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(identifier,
                szenario("templatePageSzenario"), null, languages, false, false, true,true);
        return renderer.renderTemplateIndexPage(getLocalizationEngineDe(), templateNames, container);
    }

    @Test
    public void buildTemplateIndexPageWithCustom() throws IOException {
        List<ErgebnisLink> templateUrls = Arrays.asList(new ErgebnisLink[] { ergebnisTemplateLink("t1"), new ErgebnisLink("t2", "custom/t2", Type.TEMPLATE), ergebnisTemplateLink("t3") });
        String x = renderTemplateIndexPage("", templateUrls, Arrays.asList("de"));
        checkHtmlString(x);
        assertTrue(x, x.contains("templatePageSzenario"));
        assertTrue(x, x.contains("<a href=\"t1\">t1</a>"));
        assertTrue(x, x.contains("<a href=\"t1\">t1</a>"));
        assertTrue(x, x.contains("<a href=\"custom/t2\">t2</a>"));
        assertTrue(x, x.contains("<a href=\"t3\">t3</a>"));
        assertFalse(x, x.contains("<img id=\"logo\""));
    }

}
