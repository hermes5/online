/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.velocity;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@ApplicationScoped
public class VelocityAdapter implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String LOGGER_NAME = "VelocityLog";

    @Inject
    @SystemProperty(value = "al.documentation.templateroot", fallback = "ch/admin/isb/hermes5/business/rendering/")
    ConfigurationProperty templateRoot;
    
    transient private VelocityEngine ve;

    private VelocityEngine getVelocityEngine() {
        if (ve == null) {
            ve = new VelocityEngine();
            ve.setProperty(VelocityEngine.RESOURCE_LOADER, "class");
            ve.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
            ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                    "org.apache.velocity.runtime.log.Log4JLogChute");
            ve.setProperty("runtime.log.logsystem.log4j.logger", LOGGER_NAME);
            ve.init();
        }
        return ve;
    }

    public String mergeTemplates(Map<String, Object> context, String... templates) {
        StringWriter writer = new StringWriter();
        VelocityContext ctx = copyContext(context);
        for (String templateName : templates) {
            getVelocityEngine().mergeTemplate(templateRoot.getStringValue() + templateName, "UTF-8", ctx, writer);
        }
        return String.valueOf(writer);
    }

    private VelocityContext copyContext(Map<String, Object> context) {
        VelocityContext ctx = new VelocityContext();
        for (Entry<String, Object> entry : context.entrySet()) {
            ctx.put(entry.getKey(), entry.getValue());
        }
        return ctx;
    }
}
