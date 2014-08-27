/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.io.Serializable;

import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;

public class DefaultLocalizable implements Serializable , Localizable{

    private static final long serialVersionUID = 1L;

    private String elementIdentifier;

    private String textIdentifier;

    public DefaultLocalizable(MethodElement element, String textIdentifier) {
        this(element.getId(), textIdentifier);
    }

    public DefaultLocalizable(String elementId, String textIdentifier) {
        this.elementIdentifier = elementId;

        this.textIdentifier = textIdentifier;
    }

    @Override
    public String toString() {
        return "DefaultLocalizable [elementIdentifier=" + elementIdentifier + ", textIdentifier=" + textIdentifier
                + "]";
    }

    public String getElementIdentifier() {
        return elementIdentifier;
    }

    public String getTextIdentifier() {
        return textIdentifier;
    }

}
