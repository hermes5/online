/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MimeTypeUtil {
    private static final Logger logger = LoggerFactory.getLogger(MimeTypeUtil.class);

    private final Map<String, String> mimeTypes = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put(".css", "text/css");
            put(".txt", "text/plain");
            put(".html", "text/html");
            put(".xml", "text/xml");
            put(".js", "text/javascript");
            put(".gif", "image/gif");
            put(".png", "image/png");
            put(".jpg", "image/jpeg");
            put(".jpeg", "image/jpeg");
            put(".pdf", "application/pdf");
            put(".doc", "application/msword");
            put(".dot", "application/msword");
            put(".xls", "application/vnd.ms-excel");
            put(".ppt", "application/vnd.ms-powerpoint");
            put(".docm", "application/vnd.ms-word.document.macroEnabled.12");
            put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            put(".dotm", "application/vnd.ms-word.template.macroEnabled.12");
            put(".dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");
            put(".potm", "application/vnd.ms-powerpoint.template.macroEnabled.12");
            put(".potx", "application/vnd.openxmlformats-officedocument.presentationml.template");
            put(".ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12");
            put(".ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
            put(".ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow");
            put(".pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12");
            put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
            put(".xlam", "application/vnd.ms-excel.addin.macroEnabled.12");
            put(".xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12");
            put(".xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12");
            put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            put(".xltm", "application/vnd.ms-excel.template.macroEnabled.12");
            put(".xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template");
            put(".mpx", "application/x-project");
            put(".eot", "application/vnd.ms-fontobject");
            put(".svg", "image/svg+xml");
            put(".tt",   "application/octet-stream");
            put(".ttf", "application/x-font-ttf");
            put(".woff", "application/x-font-woff");
            put(".xsd", "text/xml");
            
            
        }
    };

    /**
     * http://www.webdeveloper.com/forum/showthread.php?162526-Is-your-docx-file-turning-into-a-zip-SOLUTION
     * 
     * @param filename
     * @return
     */
    public String getMimeType(String filename) {
        if (filename == null) {
            return null;
        }
        String lowerCase = filename.toLowerCase();
        for (Entry<String, String> entry : mimeTypes.entrySet()) {
            if(lowerCase.endsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        logger.warn("No mimetype found for "+filename);
        return null;
    }
}
