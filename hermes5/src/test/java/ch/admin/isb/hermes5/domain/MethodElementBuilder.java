/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.HashMap;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import ch.admin.isb.hermes5.epf.uma.schema.ContentDescription;
import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.DescribableElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.Report;
import ch.admin.isb.hermes5.epf.uma.schema.SupportingMaterial;

public class MethodElementBuilder {

    public static Kategorie kategorie(String name) {
        return new Kategorie(customCategory(name), emptyIndex());
    }

    private static HashMap<String, MethodElement> emptyIndex() {
        return new HashMap<String, MethodElement>();
    }

    public static SupportingMaterial supportingMaterial(String name) {
        SupportingMaterial methodElement = new SupportingMaterial();
        fillDescribableElement(name, methodElement);
        return methodElement;
    }

    public static Report report(String name) {
        Report methodElement = new Report();
        fillDescribableElement(name, methodElement);
        return methodElement;
    }

    private static void fillDescribableElement(String name, DescribableElement methodElement) {
        methodElement.setPresentationName(name);
        methodElement.setId("id_" + name);
        methodElement.setName(name);
        ContentDescription contentDescription = new ContentDescription();
        contentDescription.setId("presentation_id_" + name);
        contentDescription.setMainDescription("presentation_maindescription_" + name);
        methodElement.setPresentation(contentDescription);
    }

    public static CustomCategory customCategory(String name, String... contents) {
        CustomCategory customCategory = new CustomCategory();
        fillDescribableElement(name, customCategory);
        for (String string : contents) {
            customCategory.getCategorizedElementOrSubCategory().add(jaxbCategorizedElement(string));
        }
        return customCategory;
    }

    private static JAXBElement<String> jaxbCategorizedElement(String id) {
        return new JAXBElement<String>(new QName("CategorizedElement"), String.class, id);
    }
}
