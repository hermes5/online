/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.common.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class FacesMessagesController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    FacesContext facesContext;

    public List<String> getClientIdsWithMessages() {
        
        Iterator<String> clientIdIterator = facesContext.getClientIdsWithMessages();
        List<String> clientIdsWithMessages = new ArrayList<String>();
        while (clientIdIterator.hasNext()) {
            clientIdsWithMessages.add(clientIdIterator.next());
        }
        return clientIdsWithMessages;
    }

    public List<FacesMessage> getMessagesForClientId(String clientId) {
        return facesContext.getMessageList(clientId);
    }

}