/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.presentation.common.controller.ErrorNoTemplateController;
import ch.admin.isb.hermes5.presentation.common.controller.GoogleAnalyticsController;
import ch.admin.isb.hermes5.presentation.webpublisher.servlet.ServletSupport;
import ch.admin.isb.hermes5.util.MimeTypeUtil;

@WebServlet("/userszenario/*")
public class UserSzenarioServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(UserSzenarioServlet.class);
    private static final long serialVersionUID = 1L;

    @Inject
    AnwenderloesungFacade anwenderloesungFacade;
    @Inject
    MimeTypeUtil mimeTypeUtil;
    @Inject
    ServletSupport servletSupport;

    @Inject
    ErrorNoTemplateController errorNoTemplateController;
    @Inject
    GoogleAnalyticsController googleAnalyticsController;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getPathInfo();
        try {

            byte[] content = anwenderloesungFacade.getPublishedUserFile(url);
            if (content != null) {
                String mimeType = mimeTypeUtil.getMimeType(url);

                if ("text/html".equals(mimeType)) {
                    content = googleAnalyticsController.addGoogleAnalyticsSnippetToHtmlContent(content);
                }

                servletSupport.sendFile(resp, mimeType, content);
            } else {
                logger.warn("No file found reading published user file: " + url);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            logger.warn("An error occurred reading published user file: " + url, e);
            resp.sendRedirect(servletSupport.buildRedirect(req.getContextPath(), errorNoTemplateController.display(e)));
        }
    }

}
