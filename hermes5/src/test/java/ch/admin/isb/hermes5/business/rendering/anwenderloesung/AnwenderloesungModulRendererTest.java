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
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.AbstractRendererTest;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.CustomModul;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungModulRendererTest extends AbstractRendererTest {

    private static final String MODEL = "model";

    private AnwenderloesungModulRenderer renderer;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungModulRenderer();
        renderer.velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(renderer.velocityAdapter);
    }

    @Test
    public void testResponsilbeModul() {
        assertTrue(renderer.isResponsible(modul("m1")));
    }
    @Test
    public void testResponsilbeCustomModul() {
        assertTrue(renderer.isResponsible(new CustomModul("cm1")));
    }
    @Test
    public void testNotResponsilbeAufgabe() {
        assertFalse(renderer.isResponsible(aufgabe("a1", modul("m1t"))));
    }
    
    @Test
    public void buildModul() {
        Modul modul = modul("my_module");
        String x = renderModelElement(MODEL, modul, new SzenarioUserData(), Arrays.asList("de"), szenario("s1"));
        assertTrue(x, x.contains("model/de/id_my_module/mainDescription"));
        assertTrue(x, x.contains("model/de/disciplineid_my_module/briefDescription"));
        assertTrue(x, x.contains("Modul: "));
        checkHtmlString(x);
    }

    @Test
    public void buildModulWithCustomProjektname() {
        Modul modul = modul("my_module");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setProjektname("My new Project");
        String x = renderModelElement(MODEL, modul, szenarioUserData, Arrays.asList("de"), szenario("s1"));
        assertTrue(x, x.contains("My new Project"));
        checkHtmlString(x);
    }

    @Test
    public void buildModulWithoutCustomProjektname() {
        Modul modul = modul("my_module");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        Szenario szenario = szenario("s1");
        String x = renderModelElement(MODEL, modul, szenarioUserData, Arrays.asList("de"), szenario);
        assertTrue(x, x.contains(szenario.getId()));
        checkHtmlString(x);
    }

    @Test
    public void buildWithLanguageHeader() {
        Modul modul = modul("my_module");
        String x = renderModelElement(MODEL, modul, new SzenarioUserData(), Arrays.asList("de", "fr"), szenario("s1"));
        assertTrue(x, x.contains("model/de/id_my_module/mainDescription"));
        assertTrue(x, x.contains("model/de/disciplineid_my_module/briefDescription"));
        assertTrue(x, x.contains("Deutsch"));
        assertTrue(x, x.contains("Français"));
        assertTrue(x, x.contains("last"));
        checkHtmlString(x);
    }

    private String renderModelElement(String identifier, AbstractMethodenElement modelElement,
            SzenarioUserData szenarioUserData, List<String> languages, Szenario szenario) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(identifier, szenario,
                szenarioUserData, languages, true, true, true,true);

        return renderer.renderModelElement(modelElement, super.getLocalizationEngineDe(), container);
    }

}
