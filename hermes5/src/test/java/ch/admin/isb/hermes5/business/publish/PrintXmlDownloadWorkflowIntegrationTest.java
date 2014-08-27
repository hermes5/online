/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import static ch.admin.isb.hermes5.util.ReflectionUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.modelimport.TranslationVisitor;
import ch.admin.isb.hermes5.business.modelimport.XMLImportHandler;
import ch.admin.isb.hermes5.business.modelimport.XMLImportHandlerIntegrationTestSupport;
import ch.admin.isb.hermes5.business.modelrepository.ModelRepositoryIntegrationTestSupport;
import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementAufgabeTabelleRenderer;
import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementErgebnisTabelleRenderer;
import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementModulTabelleRenderer;
import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementPostProcessor;
import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementRenderer;
import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementRendererUtil;
import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementSzenarioTabelleRenderer;
import ch.admin.isb.hermes5.business.rendering.customelement.ModulAufgabeTableBuilder;
import ch.admin.isb.hermes5.business.rendering.customelement.RelationshipTablePreprocessor;
import ch.admin.isb.hermes5.business.rendering.customelement.SzenarioAufgabeTableBuilder;
import ch.admin.isb.hermes5.business.rendering.postprocessor.InternalLinkPostProcessor;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXMLBeschreibungRenderer;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlAufgabeRenderer;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlErgebnisRenderer;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlKategorieRenderer;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlModulRenderer;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlRenderer;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlRendererRepository;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlRendererUtil;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlRolleRenderer;
import ch.admin.isb.hermes5.business.rendering.velocity.VelocityAdapter;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.MockInstance;
import ch.admin.isb.hermes5.util.ReflectionUtil;
import ch.admin.isb.hermes5.util.ResourceUtils;
import ch.admin.isb.hermes5.util.StringUtil;

public class PrintXmlDownloadWorkflowIntegrationTest {

    private final ModelRepositoryIntegrationTestSupport support = new ModelRepositoryIntegrationTestSupport();
    private final XMLImportHandlerIntegrationTestSupport xmlImportHandlerIntegrationTestSupport = new XMLImportHandlerIntegrationTestSupport();
    private PrintXmlDownloadWorkflow workflow;
    private byte[] xmlFileContents;
    private LocalizationEngine localizationEngineDe;

    @Before
    public void setup() {
        workflow = new PrintXmlDownloadWorkflow();

        InputStream resourceAsStream = getClass().getResourceAsStream("../modelrepository/20121219_export.xml");
        xmlFileContents = ResourceUtils.loadResource(resourceAsStream);
        
        workflow.internalLinkPostProcessor = new InternalLinkPostProcessor();
      
        Hardcoded.enableDefaults(workflow.internalLinkPostProcessor);
        
        XMLImportHandler xmlImportHandler = xmlImportHandlerIntegrationTestSupport.setupXmlImportHandler();
        xmlImportHandler.handleImport(support.getEpfModel().getIdentifier(), xmlFileContents, "filename");
        TranslationVisitor visitor = ReflectionUtil.getField(xmlImportHandler, "visitor");
        List<TranslateableText> translateableTexts = visitor.getTranslateableTexts();
        
        TranslationRepository translationRepository = buildTranslationRepositoryMock(translateableTexts);
        localizationEngineDe = new LocalizationEngine(translationRepository, "model", "de");

        workflow.modelRepository = support.getInitializedModelRepository();
        workflow.printXmlRendererRepository = setupPrintXmlRendererRepository();
        support.registerFile(xmlFileContents, workflow.modelRepository);
    }

    private PrintXmlRendererRepository setupPrintXmlRendererRepository() {
        PrintXmlRendererRepository printXmlRendererRepository = new PrintXmlRendererRepository();

        VelocityAdapter velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(velocityAdapter);

        PrintXmlRendererUtil printXmlRendererUtil = new PrintXmlRendererUtil();

        PrintXmlKategorieRenderer printXmlKategorieRenderer = new PrintXmlKategorieRenderer();
        Hardcoded.enableDefaults(printXmlKategorieRenderer);
        updateField(printXmlKategorieRenderer, "printXmlRendererRepository", printXmlRendererRepository);
        updateField(printXmlKategorieRenderer, "printXmlRendererUtil", printXmlRendererUtil);

        PrintXmlErgebnisRenderer printXmlErgebnisRenderer = new PrintXmlErgebnisRenderer();
        updateField(printXmlErgebnisRenderer, "printXmlRendererUtil", printXmlRendererUtil);

        PrintXmlAufgabeRenderer printXmlAufgabeRenderer = new PrintXmlAufgabeRenderer();
        updateField(printXmlAufgabeRenderer, "printXmlRendererUtil", printXmlRendererUtil);

        PrintXmlRolleRenderer printXmlRolleRenderer = new PrintXmlRolleRenderer();
        updateField(printXmlRolleRenderer, "printXmlRendererUtil", printXmlRendererUtil);
        updateField(printXmlRolleRenderer, "velocityAdapter", velocityAdapter);
        updateField(printXmlRolleRenderer, "relationshipTablePreprocessor", new RelationshipTablePreprocessor());

        PrintXmlModulRenderer printXmlModulRenderer = new PrintXmlModulRenderer();
        updateField(printXmlModulRenderer, "printXmlRendererUtil", printXmlRendererUtil);
        updateField(printXmlModulRenderer, "velocityAdapter", velocityAdapter);
        ModulAufgabeTableBuilder modulAufgabeTableBuilder = new ModulAufgabeTableBuilder();
        updateField(printXmlModulRenderer, "modulAufgabeTableBuilder", modulAufgabeTableBuilder);

        PrintXMLBeschreibungRenderer printXmlBeschreibungRenderer = new PrintXMLBeschreibungRenderer();
        updateField(printXmlBeschreibungRenderer, "printXmlRendererUtil", printXmlRendererUtil);
        CustomElementPostProcessor customElementPostProcessor = setUpCustomElementPostProcessor();
        updateField(printXmlBeschreibungRenderer, "customElementPostProcessor", customElementPostProcessor);

        MockInstance<PrintXmlRenderer> renderers = new MockInstance<PrintXmlRenderer>(printXmlKategorieRenderer,
                printXmlRolleRenderer, printXmlModulRenderer, printXmlErgebnisRenderer, printXmlAufgabeRenderer,
                printXmlBeschreibungRenderer);
        updateField(printXmlRendererRepository, "printXmlRenderers", renderers);

        return printXmlRendererRepository;
    }

    private CustomElementPostProcessor setUpCustomElementPostProcessor() {
        CustomElementPostProcessor customElementPostProcessor = new CustomElementPostProcessor();
        CustomElementRendererUtil customElementRendererUtil = new CustomElementRendererUtil();
        VelocityAdapter velocityAdapter = new VelocityAdapter();

        CustomElementAufgabeTabelleRenderer customElementAufgabeTabelleRenderer = new CustomElementAufgabeTabelleRenderer();
        updateField(customElementAufgabeTabelleRenderer, "customElementRendererUtil", customElementRendererUtil);
        updateField(customElementAufgabeTabelleRenderer, "velocityAdapter", velocityAdapter);

        CustomElementErgebnisTabelleRenderer customElementErgebnisTabelleRenderer = new CustomElementErgebnisTabelleRenderer();
        updateField(customElementErgebnisTabelleRenderer, "customElementRendererUtil", customElementRendererUtil);
        updateField(customElementErgebnisTabelleRenderer, "velocityAdapter", velocityAdapter);

        CustomElementModulTabelleRenderer customElementModulTabelleRenderer = new CustomElementModulTabelleRenderer();
        updateField(customElementModulTabelleRenderer, "velocityAdapter", velocityAdapter);

        CustomElementSzenarioTabelleRenderer customElementSzenarioTabelleRenderer = new CustomElementSzenarioTabelleRenderer();
        updateField(customElementSzenarioTabelleRenderer, "velocityAdapter", velocityAdapter);
        updateField(customElementSzenarioTabelleRenderer, "szenarioAufgabeTableBuilder",
                new SzenarioAufgabeTableBuilder());

        Hardcoded.enableDefaults(customElementAufgabeTabelleRenderer, customElementErgebnisTabelleRenderer,
                customElementSzenarioTabelleRenderer, customElementModulTabelleRenderer, velocityAdapter);
        updateField(customElementPostProcessor, "customElementRenderers",
                new MockInstance<CustomElementRenderer>(customElementAufgabeTabelleRenderer,
                        customElementSzenarioTabelleRenderer, customElementModulTabelleRenderer,
                        customElementErgebnisTabelleRenderer));
        return customElementPostProcessor;
    }

    private TranslationRepository buildTranslationRepositoryMock(final List<TranslateableText> translateableTexts) {
        TranslationRepository mock = mock(TranslationRepository.class);

        when(mock.getLocalizedText(anyString(), anyString(), anyString(), anyString())).thenAnswer(
                new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        String elementIdentifier = String.valueOf(invocation.getArguments()[2]);
                        String textIdentifier = String.valueOf(invocation.getArguments()[3]);

                        for (TranslateableText translateableText : translateableTexts) {
                            if (elementIdentifier.equals(translateableText.getElementIdentifier())
                                    && textIdentifier.equals(translateableText.getTextIdentifier())) {
                                return translateableText.getTextDe();
                            }
                        }

                        return StringUtil.join(Arrays.asList(invocation.getArguments()), "/");
                    }
                });
        return mock;
    }

    @Test
    public void testNoNull() {
      
       
        String generatePrintXml = workflow.generatePrintXml(support.getEpfModel().getIdentifier(), localizationEngineDe);
        assertNotNull(generatePrintXml);
        assertNotContains(generatePrintXml, "null");
    }

    private void assertNotContains(String generatePrintXml, String string) {
        if (generatePrintXml.contains(string)) {
            int indexOf = generatePrintXml.indexOf(string);
            fail("should not contain " + string + "\n" + generatePrintXml.substring(indexOf - 100, indexOf + 100));
        }

    }

    @Test
    public void testNoElementLink() {
        String generatePrintXml = workflow.generatePrintXml(support.getEpfModel().getIdentifier(),  localizationEngineDe);
        assertNotNull(generatePrintXml);
        assertNotContains(generatePrintXml, "elementLink");
    }
}
