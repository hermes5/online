/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package com.zuehlke.hermes5.s3mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * A simple servlet taking advantage of features added in 3.0.
 * </p>
 * 
 * <p>
 * The servlet is registered and mapped to /HelloServlet using the {@linkplain WebServlet
 * @HttpServlet}. The {@link HelloService} is injected by CDI.
 * </p>
 * 
 * @author Pete Muir
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/")
public class S3MockServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getParameter("url");
        File file = new File("C:/Daten/LocalS3/"+url);
        byte[] bytesFromFile = getBytesFromFile(file);
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write(bytesFromFile);
        outputStream.close();
        resp.setContentType(typeFromUrl(url.toLowerCase()));
        
    }
    
    private String typeFromUrl(String url) {
        if(url.endsWith("xml")) {
            return "text/xml";
        }
        if(url.endsWith("doc") || url.endsWith("docx")) {
            return "application/msword";
        }
        if(url.endsWith("xls") || url.endsWith("xlsx")) {
            return "application/msexcel";
        }
        return "text/plain";
    }

    private byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Close the input stream
        is.close();

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        return bytes;
    }

}
