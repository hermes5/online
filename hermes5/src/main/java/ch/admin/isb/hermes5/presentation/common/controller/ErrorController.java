/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.common.controller;

import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@Named
@RequestScoped
public class ErrorController {

    @Inject
    @SystemProperty(value = "errors.showStackTrace", fallback = "false")
    ConfigurationProperty showStackTrace;

    @Inject
    FacesContext facesContext;

    public boolean isShowStackTrace() {
        return showStackTrace.getBooleanValue();
    }

    public String getStackTrace() {
        try {
            Map<String, Object> map = facesContext.getExternalContext().getRequestMap();
            Throwable throwable = (Throwable) map.get("javax.servlet.error.exception");
            return getStackTrace(throwable);
        } catch (Throwable t) {
            return getStackTrace(t);
        }
    }

    private String getStackTrace(Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        if (throwable != null) {
            builder.append(throwable.getMessage()).append("\n");

            for (StackTraceElement element : throwable.getStackTrace()) {
                builder.append(element).append("\n");
            }
        }
        return builder.toString();
    }
}
