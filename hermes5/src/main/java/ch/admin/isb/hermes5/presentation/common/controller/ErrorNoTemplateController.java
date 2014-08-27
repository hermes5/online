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

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@Named
@SessionScoped
public class ErrorNoTemplateController implements Serializable{
    private static final Logger logger =  LoggerFactory.getLogger(ErrorNoTemplateController.class);
    private static final long serialVersionUID = 1L;
    
    private Exception currentException;
    @Inject
    @SystemProperty(value="errors.showStackTrace", fallback="false")
    ConfigurationProperty showStackTrace;
    
    @Inject
    FacesContext facesContext;
    
    public boolean isShowStackTrace() {
        return showStackTrace.getBooleanValue();
    }
    
    public String getStackTrace()  
    {   
        StringBuilder builder = new StringBuilder();  
        builder.append(currentException.getMessage()).append("\n");  
   
        for (StackTraceElement element : currentException.getStackTrace())  
        {  
          builder.append(element).append("\n");  
        }  
   
        return builder.toString();  
    }
    
    public Exception getCurrentException() {
        return currentException;
    }
    
    public String display(Exception currentException) {
        logger.warn("an error occurred", currentException);
        this.currentException = currentException;
        return getIdentifier();
    }

    private String getIdentifier() {
        return "error_no_template";
    }  
}
