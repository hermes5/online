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
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.presentation.webpublisher.controller.ArtifactUploadController;

public class ArtifactUploadServletTest {

    private ArtifactUploadServlet servlet;

    @Before
    public void setUp() throws Exception {
        servlet = new ArtifactUploadServlet();
        servlet.webPublisherFacade = mock(WebPublisherFacade.class);
        servlet.artifactUploadController = mock(ArtifactUploadController.class);
        servlet.servletSupport = new ServletSupport();
    }

    @Test
    public void testGetImage() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getPathInfo()).thenReturn("fr/image/33");
        when(req.getContextPath()).thenReturn("");
        Image image = new Image();
        when(servlet.webPublisherFacade.getImage(33l)).thenReturn(image);
        when(servlet.artifactUploadController.display(image, "fr", "image-display")).thenReturn("artifact-upload");
        HttpServletResponse resp = mock(HttpServletResponse.class);
        servlet.doGet(req, resp);
        verify(resp).sendRedirect("/webpublisher/artifact-upload.xhtml");
    }
    @Test
    public void testGetDocument() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getPathInfo()).thenReturn("en/document/33");
        when(req.getContextPath()).thenReturn("");
        Document document = new Document();
        when(servlet.webPublisherFacade.getDocument(33l)).thenReturn(document);
        when(servlet.artifactUploadController.display(document, "en", "artifact-overview")).thenReturn("artifact-upload");
        HttpServletResponse resp = mock(HttpServletResponse.class);
        servlet.doGet(req, resp);
        verify(resp).sendRedirect("/webpublisher/artifact-upload.xhtml");
    }

}
