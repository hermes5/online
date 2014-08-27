/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.common;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class MessageProvider implements Serializable{

    private static final long serialVersionUID = 1L;

    @Inject
    FacesContext facesContext;

    private ResourceBundle getBundle() {
        return facesContext.getApplication().getResourceBundle(facesContext, "msg");
    }

    public String getLocalized(String key) {
        try {
            return getBundle().getString(key);
        } catch (MissingResourceException e) {
            return "???" + key + "???";
        }
    }
    
    /**
     * Tries to resolve the entered key. If no message key found, the key is returned.
     * 
     * @param key
     * @param clientId
     * @return
     */
    public String tryToResolveMessageKey(String key) {
        try {
            return getBundle().getString(key);
        } catch (MissingResourceException e) {
            return  key  ;
        }
    }


}
