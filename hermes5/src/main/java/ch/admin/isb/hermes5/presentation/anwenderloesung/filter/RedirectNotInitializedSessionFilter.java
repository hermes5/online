/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.presentation.anwenderloesung.controller.SzenarioWizardContext;

@WebFilter(urlPatterns = { "/anwenderloesung/szenario-projektdaten.xhtml", "/anwenderloesung/szenario-elemente.xhtml",
        "/anwenderloesung/szenario-eigene-elemente.xhtml", "/anwenderloesung/szenario-download.xhtml" }, filterName = "RedirectNotInitializedSessionFilter")
public class RedirectNotInitializedSessionFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RedirectNotInitializedSessionFilter.class);

    @Inject
    SzenarioWizardContext context;

    @Override
    public void destroy() {
        // nothing to do
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (context.getSzenario() == null) {
            logger.info("context not initialsied");
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("szenarien-overview.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // nothing to do
    }

}
