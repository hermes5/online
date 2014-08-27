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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelutil.MethodElementUtil;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.MethodElementBuilder;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.Phase;
import ch.admin.isb.hermes5.util.Hardcoded;

public class PublishContainerAssemblerTest {

    private PublishContainerAssembler assembler;
    private CustomCategory root;
    private Map<String, MethodElement> index;
    private final List<Modul> modules = new ArrayList<Modul>();
    private final List<Rolle> rollen = new ArrayList<Rolle>();

    @Before
    public void setUp() throws Exception {
        assembler = new PublishContainerAssembler();
        assembler.methodElementUtil = new MethodElementUtil();
        Hardcoded.enableDefaults(assembler);
        root = new CustomCategory();
        root.setId("HERMES_Methode");
        index = new HashMap<String, MethodElement>();
        root.getCategorizedElementOrSubCategory().add(
                jaxbCategorizedElement(MethodElementBuilder.report("phasenmodell"), index));
        String einleitung = jaxbCategorizedElement(MethodElementBuilder.supportingMaterial("reporte_einleitung"), index)
                .getValue();
        String anotherReport1 = jaxbCategorizedElement(MethodElementBuilder.report("anotherReport1"), index).getValue();
        String anotherReport2 = jaxbCategorizedElement(MethodElementBuilder.report("anotherReport2"), index).getValue();
        root.getCategorizedElementOrSubCategory().add(
                jaxbCategorizedElement(
                        MethodElementBuilder.customCategory("reporte", einleitung, anotherReport1, anotherReport2),
                        index));
    }

    @Test
    public void testAssemblePublishContainer() {
        PublishContainer publishContainer = assembler.assemblePublishContainer(root, index, modules, rollen,
                new ArrayList<Szenario>(), new ArrayList<Phase>());
        assertNotNull(publishContainer);
        assertNotNull(publishContainer.getPublishingRoot());
        assertFalse(publishContainer.getPublishingRoot().getChildren().isEmpty());
        assertEquals(2, publishContainer.getPublishingRoot().getChildren().size());
        Kategorie reporte = (Kategorie) publishContainer.getPublishingRoot().getChildren().get(1);
        assertNotNull(reporte.getEinleitung());
        assertEquals(2, reporte.getChildren().size());
    }

    @Test
    public void assemblePublishContainerForWebsite() {
        PublishContainer publishContainer = assembler.assemblePublishContainer(root, index, modules, rollen,
                new ArrayList<Szenario>(), new ArrayList<Phase>());
        assertNotNull(publishContainer);
        assertNotNull(publishContainer.getPublishingRoot());
        assertFalse(publishContainer.getPublishingRoot().getChildren().isEmpty());
        assertEquals(2, publishContainer.getPublishingRoot().getChildren().size());
        Kategorie reporte = (Kategorie) publishContainer.getPublishingRoot().getChildren().get(1);
        assertEquals(2, reporte.getChildren().size());
        assertNotNull(reporte.getEinleitung());
        List<AbstractMethodenElement> elementsToPublish = publishContainer.getElementsToPublish();
        assertEquals("" + elementsToPublish, 5, elementsToPublish.size());
        for (AbstractMethodenElement abstractMethodenElement : elementsToPublish) {
            assertFalse(abstractMethodenElement.getName(),
                    abstractMethodenElement.getName().contains("reporte_einleitung"));
        }
    }

    private JAXBElement<String> jaxbCategorizedElement(MethodElement me, Map<String, MethodElement> index) {
        JAXBElement<String> jaxb = new JAXBElement<String>(new QName("CategorizedElement"), String.class, me.getId());
        index.put(me.getId(), me);
        return jaxb;
    }
}
