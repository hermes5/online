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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.customelement.SzenarioErgebnisTableBuilder;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.domain.CustomAufgabe;
import ch.admin.isb.hermes5.domain.CustomErgebnis;
import ch.admin.isb.hermes5.domain.CustomModul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungSzenarioRendererTest extends AbstractRendererTest {

    private static final String MODEL = "model";

    private AnwenderloesungSzenarioRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungSzenarioRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        renderer.szenarioErgebnisTableBuilder = new SzenarioErgebnisTableBuilder();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
    }

    @Test
    public void buildSzenarioPage() throws IOException {
        MethodConfiguration methodConfiguration = new MethodConfiguration();
        methodConfiguration.setId("id");
        Szenario szenario = new Szenario(methodConfiguration);
        szenario.getPhasen().add(phaseFull("eins"));
        szenario.getPhasen().add(phaseFull("zwei"));
        String x = renderSzenarioPage(MODEL, szenario, new SzenarioUserData(), Arrays.asList("de"));
        assertTrue(x, x.contains("<a href=\"ergebnis_ergebnis2.html\">model/de/wpid_ergebnis2/presentationName</a>"));
        assertTrue(x, x.contains("model/de/id/presentationName"));
        assertTrue(x, x.contains("<title>HERMES</title>"));
    }

    private String renderSzenarioPage(String identifier, Szenario szenario, SzenarioUserData szenarioUserData,
            List<String> languages) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(identifier, szenario,
                szenarioUserData, languages, true, true, true,true);
        return renderer.renderSzenario(getLocalizationEngineDe(), container);
    }

    @Test
    public void buildSzenarioPageWithTitle() throws IOException {
        MethodConfiguration methodConfiguration = new MethodConfiguration();
        methodConfiguration.setId("id");
        Szenario szenario = new Szenario(methodConfiguration);
        szenario.getPhasen().add(phaseFull("eins"));
        szenario.getPhasen().add(phaseFull("zwei"));
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setProjektname("projektname");
        String page = renderSzenarioPage(MODEL, szenario, szenarioUserData, Arrays.asList("de"));
        assertTrue(page, page.contains("projektname"));
        assertFalse(page, page.contains("<div class=\"h1\">§model/de/id/presentationName</div>"));

    }

    @Test
    public void buildSzenarioPageWithCustomErgebnis() throws IOException {
        MethodConfiguration methodConfiguration = new MethodConfiguration();
        methodConfiguration.setId("id");
        Szenario szenario = new Szenario(methodConfiguration);
        Phase phaseFull = phaseFull("eins");
        CustomErgebnis ce = new CustomErgebnis("cName", "cDescription", null, null, new ArrayList<String>(),
                new ArrayList<Rolle>());
        CustomAufgabe ca = new CustomAufgabe("cA");
        CustomModul cm = new CustomModul("cmodul");
        ca.addModul(cm);
        ca.addErgebnis(ce);
        phaseFull.addAufgabe(ca);
        szenario.getPhasen().add(phaseFull);
        szenario.getPhasen().add(phaseFull("zwei"));
        String buildSzenarioPage = renderSzenarioPage(MODEL, szenario, new SzenarioUserData(), Arrays.asList("de"));
        assertTrue(buildSzenarioPage.contains("cmodul"));
        assertTrue(buildSzenarioPage.contains("cName"));
    }

}
