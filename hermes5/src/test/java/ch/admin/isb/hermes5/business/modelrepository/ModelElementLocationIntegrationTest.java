/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelutil.ComplexFieldComparator;
import ch.admin.isb.hermes5.business.modelutil.MethodElementUtil;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.epf.uma.schema.Artifact;
import ch.admin.isb.hermes5.epf.uma.schema.ContentDescription;
import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;
import ch.admin.isb.hermes5.epf.uma.schema.MethodPlugin;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProduct;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.JAXBUtils;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;

public class ModelElementLocationIntegrationTest {

    private MethodLibrary methodLibrary;
    private ReflectionTestHelper helper = new ReflectionTestHelper();
    private MethodLibraryVisitorDriver methodLibraryVisitorDriver;
    private ElementExtractorVisitor elementExtractorVisitor;
    private ElementIndexVisitor elementIndexVisitor;

    private MethodElementUtil util = new MethodElementUtil();

    @Before
    public void setUp() {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        MethodLibraryUnmarshaller methodLibraryUnmarshaller = new MethodLibraryUnmarshaller();
        methodLibrary = methodLibraryUnmarshaller.unmarshalMethodLibrary(resourceAsStream);
        methodLibraryVisitorDriver = new MethodLibraryVisitorDriver();
        ComplexFieldComparator complexFieldComparator = new ComplexFieldComparator();
        Hardcoded.enableDefaults(complexFieldComparator);
        complexFieldComparator.init();
        helper.updateField(methodLibraryVisitorDriver, "fieldComparator", complexFieldComparator);
        elementExtractorVisitor = new ElementExtractorVisitor();
        elementIndexVisitor = new ElementIndexVisitor();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void locateProjektmanagementplanAttachement() {
        elementExtractorVisitor.init(Artifact.class);
        methodLibraryVisitorDriver.visit(methodLibrary, elementExtractorVisitor, elementIndexVisitor);
        List<MethodElement> result = elementExtractorVisitor.getResult(Artifact.class);
        Map<String, MethodElement> index = elementIndexVisitor.getResult();
        WorkProduct wp = (WorkProduct) util.getElementWithName(result, "projektmanagementplan");
        List<JAXBElement<String>> examples = wp.getChecklistOrConceptOrExample();
        assertTrue(examples.size() > 0);
        for (JAXBElement<String> jaxbElement : examples) {
            assertEquals("Example", jaxbElement.getName().getLocalPart());
        }
        Ergebnis ergebnis = new Ergebnis(wp, index, false);
        List<String> templateAttachmentUrls = ergebnis.getTemplateAttachmentUrls();
        assertEquals(String.valueOf(templateAttachmentUrls), 1, templateAttachmentUrls.size());
        assertEquals("hermes.core/guidances/examples/resources/Projekthandbuch.docx", templateAttachmentUrls.get(0));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void locateMitarbeitendAnArbeitsauftrag() {
        elementExtractorVisitor.init(Artifact.class, Role.class);
        methodLibraryVisitorDriver.visit(methodLibrary, elementExtractorVisitor, elementIndexVisitor);
        List<MethodElement> artifacts = elementExtractorVisitor.getResult(Artifact.class);
        List<MethodElement> roles = elementExtractorVisitor.getResult(Role.class);
        WorkProduct wp = (WorkProduct) util.getElementWithName(artifacts, "arbeitsauftrag");
        Role role = (Role) util.getElementWithName(roles, "anwendervertreter");
        List<String> exampleOfWP = JAXBUtils.getValuesWithName(wp.getChecklistOrConceptOrExample(), "Example");
        List<String> exampleOfRole = JAXBUtils.getValuesWithName(role.getChecklistOrConceptOrExample(), "Example");
        assertEquals(exampleOfRole.get(0), exampleOfWP.get(0));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void locateRequiredArtifact() {
        elementExtractorVisitor.init(Artifact.class);
        methodLibraryVisitorDriver.visit(methodLibrary, elementExtractorVisitor, elementIndexVisitor);
        List<MethodElement> artifacts = elementExtractorVisitor.getResult(Artifact.class);
        WorkProduct wp = (WorkProduct) util.getElementWithName(artifacts, "studie");
        assertTrue(isRequired(wp));
    }

    private boolean isRequired(WorkProduct workprodukt) {
        ContentDescription presentation = workprodukt.getPresentation();
        return presentation != null && presentation.getKeyConsiderations() != null
                && presentation.getKeyConsiderations().contains("required");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void locateHermesHandbuch() {
        elementExtractorVisitor.init(MethodPlugin.class);
        methodLibraryVisitorDriver.visit(methodLibrary, elementExtractorVisitor, elementIndexVisitor);
        List<MethodElement> plugins = elementExtractorVisitor.getResult(MethodPlugin.class);
        MethodPlugin hermesPublish = (MethodPlugin) util.getElementWithName(plugins, "hermes.publish");
        assertNotNull(hermesPublish);
        Map<String, MethodElement> index = elementIndexVisitor.getResult();

        elementExtractorVisitor.init(CustomCategory.class);
        methodLibraryVisitorDriver.visit(hermesPublish, elementExtractorVisitor);
        List<MethodElement> customCategories = elementExtractorVisitor.getResult(CustomCategory.class);
        CustomCategory handbuch = (CustomCategory) util.getElementWithName(customCategories, "hermes_handbuch");
        assertNotNull(customCategories.toString(), handbuch);
        assertEquals(8, handbuch.getCategorizedElementOrSubCategory().size());
        for (JAXBElement<String> methodElement : handbuch.getCategorizedElementOrSubCategory()) {
            assertEquals("CategorizedElement", methodElement.getName().getLocalPart());
            MethodElement object = index.get(methodElement.getValue());
            assertNotNull(object);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void locateHermesWebsite() {
        elementExtractorVisitor.init(MethodPlugin.class);
        methodLibraryVisitorDriver.visit(methodLibrary, elementExtractorVisitor, elementIndexVisitor);
        List<MethodElement> plugins = elementExtractorVisitor.getResult(MethodPlugin.class);
        MethodPlugin hermesPublish = (MethodPlugin) util.getElementWithName(plugins, "hermes.publish");
        assertNotNull(hermesPublish);
        Map<String, MethodElement> index = elementIndexVisitor.getResult();

        elementExtractorVisitor.init(CustomCategory.class);
        methodLibraryVisitorDriver.visit(hermesPublish, elementExtractorVisitor);
        List<MethodElement> customCategories = elementExtractorVisitor.getResult(CustomCategory.class);
        CustomCategory handbuch = (CustomCategory) util.getElementWithName(customCategories, "HERMES_Methode");
        assertNotNull(customCategories.toString(), handbuch);

        assertEquals(7, handbuch.getCategorizedElementOrSubCategory().size());
        for (JAXBElement<String> methodElement : handbuch.getCategorizedElementOrSubCategory()) {
            assertEquals("CategorizedElement", methodElement.getName().getLocalPart());
            MethodElement object = index.get(methodElement.getValue());
            assertNotNull(object);
        }
    }

}
