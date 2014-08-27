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

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration sample: 
 *          
            <periodic-rotating-file-handler name="PERFORMANCE_FILE" autoflush="true">
                <level name="DEBUG"/>
                <file relative-to="jboss.server.log.dir" path="performance.log"/>
                <formatter>
                    <pattern-formatter pattern="%d{yyyy-MM-dd:HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n"/>
                </formatter>
                <suffix value=".yyyy-MM-dd"/>
                <append value="true"/>
            </periodic-rotating-file-handler>
            <logger category="PerformanceLog" use-parent-handlers="false">
                <level name="DEBUG"/>
                <handlers>
                    <handler name="PERFORMANCE_FILE"/>
                </handlers>
            </logger>
 */
@PerformanceLogged
@Interceptor
public class PerformanceLoggedInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;
    private final static Logger perfLogger = LoggerFactory.getLogger("PerformanceLog");
    private final static Logger logger = LoggerFactory.getLogger(PerformanceLoggedInterceptor.class);

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        final long start = perfLogger.isDebugEnabled() ? now() : 0;
        boolean success = false;
        try {
            Object result = invocationContext.proceed();
            success = true;
            return result;
        } finally {
            try {
                if (perfLogger.isDebugEnabled()) {

                    final double duration = ((double) now() - start) / 1000.;
                    perfLogger.debug("{};{};{};{};{}", new Object[] { start,
                            invocationContext.getMethod().getDeclaringClass().getSimpleName(),
                            invocationContext.getMethod().getName(), success, duration });
                }
            } catch (Exception e) {
                logger.warn("Exception occurred during performance logging ", e);
            }
        }
    }

    private long now() {
        return System.currentTimeMillis();
    }
}
