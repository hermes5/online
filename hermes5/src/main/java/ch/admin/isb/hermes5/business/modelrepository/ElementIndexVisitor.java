/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import ch.admin.isb.hermes5.business.modelutil.ModelVisitor;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;

/**
 * Creates a map with ids as key and value is a reference to the model element
 *
 */
@RequestScoped
public class ElementIndexVisitor implements ModelVisitor, Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String,MethodElement> result;


    @Override
    public void visitStart(MethodElement element, MethodElement parent) {
         getResult().put(element.getId(), element);
    }

    @Override
    public void visitEnd(MethodElement element, MethodElement parent) {
    }

    
    public Map<String, MethodElement> getResult() {
        if(result == null) {
            result =   new HashMap<String,MethodElement>();
        }
        return result;
    }

    

}
