/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelutil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElementProperty;
import ch.admin.isb.hermes5.epf.uma.schema.PackageableElement;


public class MethodElementUtil implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = LoggerFactory.getLogger(MethodElementUtil.class);
    

    public boolean isCreatedByReference(MethodElement element) {
        List<PackageableElement> properties = element.getOwnedRuleOrMethodElementProperty();
        for (PackageableElement packageableElement : properties) {
            if (packageableElement instanceof MethodElementProperty) {
                MethodElementProperty property = (MethodElementProperty) packageableElement;
                if ("descriptor_createdByReference".equalsIgnoreCase(property.getName())
                        && "true".equals(property.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public MethodElement getElementWithName(List<MethodElement> result, String name) {
        for (MethodElement methodElement : result) {
            if(methodElement.getName().equals(name)) {
                return methodElement;
            }
        }
        return null;
    }
    
    public List<MethodElement> getChildren(CustomCategory customCategory, Map<String, MethodElement> index) {
        List<MethodElement> result = new ArrayList<MethodElement>();
        List<JAXBElement<String>> categorizedElementOrSubCategory = customCategory.getCategorizedElementOrSubCategory();
        for (JAXBElement<String> jaxbElement : categorizedElementOrSubCategory) {
            if (index.containsKey(jaxbElement.getValue())) {
                result.add(index.get(jaxbElement.getValue()));
            } else {
                logger.warn("unable to resolve: " + jaxbElement);
            }
        }
        return result;
    }
}
