/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.servlet;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.presentation.webpublisher.controller.ImageDisplayController;


public class ImageDisplayServletTest {

    private ImageDisplayServlet servlet;

    @Before
    public void setUp() throws Exception {
        servlet = new ImageDisplayServlet();
        servlet.webPublisherFacade = mock(WebPublisherFacade.class);
        servlet.imageDisplayController = mock(ImageDisplayController.class);
        servlet.servletSupport = new ServletSupport();
    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getPathInfo()).thenReturn("222");
        when(req.getContextPath()).thenReturn("");
        HttpServletResponse resp = mock(HttpServletResponse.class);
        Image image = new Image();
        when(servlet.webPublisherFacade.getImage(222l)).thenReturn(image);
        when(servlet.imageDisplayController.display(image)).thenReturn("image-display");
        servlet.doGet(req, resp);
        verify(resp).sendRedirect("/webpublisher/image-display.xhtml");
    }

}
