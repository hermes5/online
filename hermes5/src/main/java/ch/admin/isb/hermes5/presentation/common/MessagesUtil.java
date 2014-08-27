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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class MessagesUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    FacesContext facesContext;
    @Inject
    MessageProvider messageProvider;

    /**
     * ONLY possible if in JSF Context (i.e. faces context is available)! Not from Servlet
     */
    public void addGlobalInfo(String key) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageProvider.getLocalized(key), ""));
    }

    /**
     * ONLY possible if in JSF Context (i.e. faces context is available)! Not from Servlet
     */
    public void addGlobalError(String key) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageProvider.getLocalized(key), ""));
    }

    public void addGlobalInfoMessage(String message) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
    }
    
    public void addGlobalErrorMessage(String message) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
    }
}
