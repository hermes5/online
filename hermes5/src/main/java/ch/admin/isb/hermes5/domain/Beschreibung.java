/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.Map;

import ch.admin.isb.hermes5.epf.uma.schema.DescribableElement;
import ch.admin.isb.hermes5.epf.uma.schema.Guidance;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.util.StringUtil;

public class Beschreibung extends AbstractMethodenElement {

    private final Guidance guidance;

    public Beschreibung(Guidance guidance, Map<String, MethodElement> methodLibraryIndex) {
        super(methodLibraryIndex);
        this.guidance = guidance;
    }

    @Override
    public DescribableElement getElement() {
        return guidance;
    }

    @Override
    public String getName() {
        return StringUtil.replaceSpecialChars(guidance.getClass().getSimpleName().toLowerCase() + "_" + guidance.getName());
    }

}
