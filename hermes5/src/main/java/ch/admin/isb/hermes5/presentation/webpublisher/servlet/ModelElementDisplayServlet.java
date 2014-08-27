/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.servlet;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.admin.isb.hermes5.presentation.webpublisher.controller.ModelElementDisplayController;

/**
 * workaround for the filtering bug in primefaces http://code.google.com/p/primefaces/issues/detail?id=2211
 * expected call /webpublisher/modelelement/{modelidentifier}/{rootelementid}
 */
@WebServlet("/webpublisher/modelelement/*")
public class ModelElementDisplayServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    ServletSupport servletSupport;

    @Inject
    ModelElementDisplayController modelElementDisplayController;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringTokenizer st = new StringTokenizer(req.getPathInfo(), "/");
        String modelIdentifier= st.nextToken();
        String rootElementId= st.nextToken();
        String display = modelElementDisplayController.display(modelIdentifier, rootElementId);
        resp.sendRedirect(servletSupport.buildRedirect(req.getContextPath(), "webpublisher/" + display));
    }

}
