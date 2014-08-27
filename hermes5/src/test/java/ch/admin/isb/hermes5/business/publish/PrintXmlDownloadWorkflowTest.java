/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static ch.admin.isb.hermes5.util.ReflectionUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.rendering.postprocessor.InternalLinkPostProcessor;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlErgebnisRenderer;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlKategorieRenderer;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlRendererRepository;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlRendererUtil;
import ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.util.Hardcoded;

public class PrintXmlDownloadWorkflowTest {

    private static final String MODEL = "identifier";
    private PrintXmlDownloadWorkflow workflow;

    @Before
    public void setUp() {
        workflow = new PrintXmlDownloadWorkflow();
        workflow.modelRepository = mock(ModelRepository.class);
        workflow.internalLinkPostProcessor = new InternalLinkPostProcessor();
        Hardcoded.enableDefaults( workflow.internalLinkPostProcessor);
        workflow.printXmlRendererRepository = mock(PrintXmlRendererRepository.class);
        PrintXmlKategorieRenderer kategorieXmlRenderer = new PrintXmlKategorieRenderer();
        Hardcoded.enableDefaults(kategorieXmlRenderer);
        updateField(kategorieXmlRenderer, "printXmlRendererUtil", new PrintXmlRendererUtil());
        PrintXmlRendererRepository xmlMock = mock(PrintXmlRendererRepository.class);
        updateField(kategorieXmlRenderer, "printXmlRendererRepository", xmlMock);
        PrintXmlErgebnisRenderer printXmlBeschreibungRenderer = new PrintXmlErgebnisRenderer();
        updateField(printXmlBeschreibungRenderer, "printXmlRendererUtil", new PrintXmlRendererUtil());
        when(xmlMock.lookupPrintXmlRenderer((AbstractMethodenElement) anyObject())).thenReturn(
                printXmlBeschreibungRenderer);
        when(workflow.printXmlRendererRepository.lookupPrintXmlRenderer((AbstractMethodenElement) anyObject()))
                .thenReturn(kategorieXmlRenderer);
    }

    private PublishContainer publishContainer() {
        CustomCategory root = new CustomCategory();
        root.setId("hermes_handbuch");
        Map<String, MethodElement> index = new HashMap<String, MethodElement>();
        Ergebnis supportingMaterial = ergebnis("Vorwort");
        Kategorie root2 = new Kategorie(root, index);
        root2.getChildren().add(supportingMaterial);
        return new PublishContainer(root2, null, null, new ArrayList<Szenario>());

    }

    @Test
    public void testGeneratePrintXml() {
        when(workflow.modelRepository.getHermesHandbuch(MODEL)).thenReturn(publishContainer());
        String generatePrintXml = workflow.generatePrintXml(MODEL, LocalizationEngineBuilder.getLocalizationEngineDe());
        assertNotNull(generatePrintXml);
        assertTrue(generatePrintXml, generatePrintXml.contains("presentation_id_Vorwort"));
    }

}
