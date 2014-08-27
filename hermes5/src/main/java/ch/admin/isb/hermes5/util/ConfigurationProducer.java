/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Configuration sample in JBoss standalone.xml:
 * 
 * <pre>
 * ...
 * </extensions>
 *  <system-properties>
 *   <property name="wsbc.endpointURL" value="https://ws-bc.zuehlke.com/wsbc/barcode/v2_0"/>
 *   <property name="wsbc.username" value="wsbce"/>
 *   <property name="wsbc.password" value="seccret"/>
 *  </system-properties>
 *  <management>
 *  ...
 * </pre>
 */
@ApplicationScoped
public class ConfigurationProducer implements Serializable {
    private static final long serialVersionUID = 1L;



    @Produces @SystemProperty("")
    ConfigurationProperty getParamValue(InjectionPoint ip) {
        final SystemProperty annotation = ip.getAnnotated().getAnnotation(SystemProperty.class);
        return new ConfigurationProperty(annotation);
    }
}
