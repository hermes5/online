/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.DescribableElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.util.StringUtil;

public class Kategorie extends AbstractMethodenElement {

    private final CustomCategory customCategory;
    private AbstractMethodenElement einleitung;
    private List<AbstractMethodenElement> children;
    
    public AbstractMethodenElement getEinleitung() {
        return einleitung;
    }

    
    public void setEinleitung(AbstractMethodenElement einleitung) {
        this.einleitung = einleitung;
    }


    public Kategorie(CustomCategory customCategory, Map<String, MethodElement> methodLibraryIndex) {
        super(methodLibraryIndex);
        this.customCategory = customCategory;
        children = new ArrayList<AbstractMethodenElement>();
    }

    @Override
    public DescribableElement getElement() {
        return customCategory;
    }

    @Override
    public String getName() {
        return StringUtil.replaceSpecialChars("kategorie_" + customCategory.getName());
    }

    public List<AbstractMethodenElement> getChildren() {
        return children;
    }

}
