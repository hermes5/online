/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.szenario;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungModelElementDocumentationRenderer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.rendering.postprocessor.InternalLinkPostProcessor;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.util.AssertUtils;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.ZipUtil;

public class SzenarioDocumentationGeneratorTest {

    private SzenarioDocumentationGenerator szenarioDocumentationGenerator;
    private final ZipUtil zipUtil = new ZipUtil();

    @Before
    public void setUp() throws Exception {
        szenarioDocumentationGenerator = new SzenarioDocumentationGenerator();
        szenarioDocumentationGenerator.internalLinkPostProcessor = mock(InternalLinkPostProcessor.class);

        when(
                szenarioDocumentationGenerator.internalLinkPostProcessor.adjustInternalLinks(
                        any(AbstractMethodenElement.class), anyString(), anyListOf(AbstractMethodenElement.class)))
                .thenAnswer(new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return invocation.getArguments()[0].toString();
                    }
                });

        szenarioDocumentationGenerator.anwenderloesungModelElementDocumentationRenderer = mock(AnwenderloesungModelElementDocumentationRenderer.class);

        when(
                szenarioDocumentationGenerator.anwenderloesungModelElementDocumentationRenderer.renderModelElement(
                        any(AbstractMethodenElement.class), (LocalizationEngine) anyObject(),
                        any(AnwenderloesungRenderingContainer.class))).thenAnswer(new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return StringUtil.join(Arrays.asList(invocation.getArguments()), " ");
            }
        });
        when(
                szenarioDocumentationGenerator.anwenderloesungModelElementDocumentationRenderer.renderSzenario(
                        (LocalizationEngine) anyObject(), any(AnwenderloesungRenderingContainer.class))).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return StringUtil.join(Arrays.asList(invocation.getArguments()), " ");
                    }
                });

        Hardcoded.enableDefaults(szenarioDocumentationGenerator);
    }

    @Test
    public void testAddDocumentation() throws IOException {
        MethodConfiguration methodConfiguration = new MethodConfiguration();
        methodConfiguration.setId("id");
        Szenario szenario = new Szenario(methodConfiguration);
        szenario.getPhasen().add(phaseFull("eins"));
        szenario.getPhasen().add(phaseFull("zwei"));
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();
        this.szenarioDocumentationGenerator.addDocumentation(
                container("modelIdentifier", szenario, szenarioUserData, Arrays.asList("de", "fr")), zipBuilder,
                LocalizationEngineBuilder.getLocalizationEngineDe());
        byte[] result = zipBuilder.getResult();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(result));
        ZipEntry nextEntry;
        List<String> names = new ArrayList<String>();
        while ((nextEntry = zipInputStream.getNextEntry()) != null) {
            names.add(nextEntry.getName());
            byte[] byteArray = zipUtil.readZipEntry(zipInputStream);
            assertNotNull(byteArray);
            // S ystem.out.println("\n======================================");
            // S ystem.out.println(nextEntry.getName());
            // S ystem.out.println(new String(byteArray));
        }
        AssertUtils.assertContains(names, "scenario/de/index.html", "scenario/de/phase_eins.html",
                "scenario/de/phase_zwei.html");
    }

    private AnwenderloesungRenderingContainer container(String modelIdentifier, Szenario szenario,
            SzenarioUserData szenarioUserData, List<String> langs) {
        AnwenderloesungRenderingContainer container = new AnwenderloesungRenderingContainer(modelIdentifier, szenario,
                szenarioUserData, langs, true, false, false, true);
        return container;
    }
}
