/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.servlet;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.presentation.anwenderloesung.controller.SzenarioWizardContext;
import ch.admin.isb.hermes5.presentation.webpublisher.servlet.ServletSupport;
import ch.admin.isb.hermes5.util.MimeTypeUtil;

public class LogoServletTest {

    private static final byte[] BYTES = "bytes".getBytes();
    private LogoServlet servlet;

    @Before
    public void setUp() throws Exception {
        servlet = new LogoServlet();
        servlet.szenarioWizardContext = new SzenarioWizardContext();
        servlet.szenarioWizardContext.init();
        servlet.mimeTypeUtil = new MimeTypeUtil();
        servlet.servletSupport = new ServletSupport();
    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
        servlet.szenarioWizardContext.getSzenarioUserData().setLogo(BYTES);
        servlet.szenarioWizardContext.getSzenarioUserData().setLogoFilename("logo.jpg");

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        ServletOutputStream servletOutput = mock(ServletOutputStream.class);
        when(resp.getOutputStream()).thenReturn(servletOutput);
        servlet.doGet(req, resp);
        verify(servletOutput).write(BYTES);
        verify(resp).setContentType("image/jpeg");
        verify(resp).setContentLength(BYTES.length);

    }

}
