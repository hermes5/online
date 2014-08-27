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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.LocalizationEngineSupport;
import ch.admin.isb.hermes5.business.util.HtmlChecker;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;

public class AnwenderloesungStartPageRendererTest {

    private HtmlChecker htmlChecker = new HtmlChecker();
    private AnwenderloesungStartPageRenderer renderer;
    private LocalizationEngineSupport localizationEngineSupport;

    @Before
    public void setUp() throws Exception {
        renderer = new AnwenderloesungStartPageRenderer();
        VelocityAdapter velocityAdapter = new VelocityAdapter();

        renderer.velocityAdapter = velocityAdapter;
        Hardcoded.enableDefaults(renderer.velocityAdapter);
        localizationEngineSupport = mock(LocalizationEngineSupport.class);
        when(localizationEngineSupport.getLocalizedText(anyString(), anyString(), anyString(), anyString()))
                .thenAnswer(new Answer<String>() {

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
        when(localizationEngineSupport.getDocumentUrl(anyString(), anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return invocation.getArguments()[2] + "_" + invocation.getArguments()[1];
                    }
                });
    }

    @Test
    public void renderStartPage() {
        Szenario szenario = szenario("s1");
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("id", szenario,
                new SzenarioUserData(), Arrays.asList("de"), true, true, true,true);
        String x = renderer.renderStartPage(container, new LocalizationEngine(localizationEngineSupport, "model", "de"));
        assertTrue(x, x.contains(szenario.getId()));
        htmlChecker.checkHtmlString(x);
    }

    @Test
    public void renderStartPageWithCustomProjektName() {
        Szenario szenario = szenario("s1");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setProjektname("projekt abc");
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("id", szenario,
                szenarioUserData, Arrays.asList("de"), true, true, true,true);
        String x = renderer.renderStartPage(container, new LocalizationEngine(localizationEngineSupport, "model", "de"));
        assertTrue(x, x.contains("projekt abc"));
        assertFalse(x, x.contains(szenario.getId()));
        htmlChecker.checkHtmlString(x);
    }
    
    @Test
    public void renderStartPageWithXMLModel() {
        Szenario szenario = szenario("s1");
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setProjektname("projekt abc");
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer("id", szenario,
                szenarioUserData, Arrays.asList("de"), true, true, true,true);
        String x = renderer.renderStartPage(container, new LocalizationEngine(localizationEngineSupport, "model", "de"));
        assertTrue(x, x.contains("projekt abc"));
        assertTrue(x, x.toLowerCase().contains("xml"));
        
        assertFalse(x, x.contains(szenario.getId()));
        htmlChecker.checkHtmlString(x);
    }

}
