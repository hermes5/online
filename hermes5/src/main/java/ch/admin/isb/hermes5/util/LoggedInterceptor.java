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

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger("Hermes5-Logger");
    
    @SystemProperty(value="hermes5logger", fallback="debug")
    @Inject
    ConfigurationProperty hermes5logger;

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        long before = System.currentTimeMillis();
        Object proceed = null;
        try {
            proceed = invocationContext.proceed();
            return proceed;
        } finally {
            if (!"none".equals(hermes5logger.getStringValue())) {
                long after = System.currentTimeMillis();
                Object[] parameters = invocationContext.getParameters();
                StringBuilder sb = new StringBuilder();
                if (parameters.length > 0) {
                    for (Object object : parameters) {
                        sb.append(object).append(", ");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.deleteCharAt(sb.length() - 1);
                }
                String logString = invocationContext.getMethod().getDeclaringClass().getSimpleName() + "."
                        + invocationContext.getMethod().getName() + "(" + sb + ")" + " => " + " " + proceed + " " + "+("
                        + (after - before) + "ms)";
                if ("info".equals(hermes5logger.getStringValue())) {
                    logger.info(logString);
                }
                else {
                    logger.debug(logString);
                }
            }
        }
    }
}
