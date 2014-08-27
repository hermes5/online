/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.servlet;

import static ch.admin.isb.hermes5.domain.EPFModelBuilder.*;
import static org.mockito.Mockito.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.presentation.common.controller.ErrorNoTemplateController;
import ch.admin.isb.hermes5.presentation.webpublisher.servlet.ServletSupport;
import ch.admin.isb.hermes5.util.MimeTypeUtil;

public class SampleSzenarioServletTest {

    private static final String modelIdentifier = "123";
    private SampleSzenarioServlet servlet;
    private static final byte[] BYTES = "bytes".getBytes();

    @Before
    public void setUp() throws Exception {
        servlet = new SampleSzenarioServlet();
        servlet.anwenderloesungFacade = mock(AnwenderloesungFacade.class);
        servlet.mimeTypeUtil = new MimeTypeUtil();
        servlet.servletSupport = new ServletSupport();
        servlet.errorNoTemplateController = mock(ErrorNoTemplateController.class);

    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponse() throws Exception {

        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getPathInfo()).thenReturn("/url/abc.docx");
        when(servlet.anwenderloesungFacade.getPublishedModel()).thenReturn(epfModel(modelIdentifier));
        when(servlet.anwenderloesungFacade.getPublishedSzenarioFile(modelIdentifier, "/url/abc.docx"))
                .thenReturn(BYTES);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        ServletOutputStream servletOutput = mock(ServletOutputStream.class);
        when(resp.getOutputStream()).thenReturn(servletOutput);
        servlet.doGet(req, resp);

        verify(servlet.anwenderloesungFacade).getPublishedSzenarioFile(modelIdentifier, "/url/abc.docx");
        verify(servletOutput).write(BYTES);
    }

}
