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
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.util.MimeTypeUtil;
import ch.admin.isb.hermes5.util.StringUtil;

@WebServlet("/anwenderloesung/vorlagen/*")
public class VorlagenDownloadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    MimeTypeUtil mimeTypeUtil;
    @Inject
    AnwenderloesungFacade anwenderloesungFacade;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringTokenizer st = new StringTokenizer(req.getPathInfo(), "/");
        String modelIdentifier = String.valueOf(st.nextElement());
        String lang = String.valueOf(st.nextElement());
        List<String> urlList = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            urlList.add(st.nextToken());
        }
        String url = StringUtil.join(urlList, "/");
        byte[] document = anwenderloesungFacade.readDocument(modelIdentifier, lang, url);
        sendFile(resp, url, document);
    }

    public void sendFile(HttpServletResponse resp, String url, byte[] document) throws IOException {
        String mimeType = mimeTypeUtil.getMimeType(url);
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write(document);
        resp.setContentLength(document.length);
        resp.setContentType(mimeType);
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + StringUtil.getLinkName(url) + "\"");
        outputStream.flush();
    }
}
