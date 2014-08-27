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

public enum FileType {
    EXCEL, WORD, POWERPOINT, PDF, HTML, OTHER;

    private static final Map<String, FileType> FILE_EXTENSION_MAP;
    static {
        FILE_EXTENSION_MAP = new HashMap<String, FileType>();
        FILE_EXTENSION_MAP.put("xls", EXCEL);
        FILE_EXTENSION_MAP.put("xlt", EXCEL);
        FILE_EXTENSION_MAP.put("xlm", EXCEL);
        FILE_EXTENSION_MAP.put("xlsx", EXCEL);
        FILE_EXTENSION_MAP.put("xlsm", EXCEL);
        FILE_EXTENSION_MAP.put("xltx", EXCEL);
        FILE_EXTENSION_MAP.put("xltm", EXCEL);
        FILE_EXTENSION_MAP.put("pdf", PDF);
        FILE_EXTENSION_MAP.put("doc", WORD);
        FILE_EXTENSION_MAP.put("docx", WORD);
        FILE_EXTENSION_MAP.put("docm", WORD);
        FILE_EXTENSION_MAP.put("dot", WORD);
        FILE_EXTENSION_MAP.put("dotx", WORD);
        FILE_EXTENSION_MAP.put("dotm", WORD);
        FILE_EXTENSION_MAP.put("ppt", POWERPOINT);
        FILE_EXTENSION_MAP.put("pot", POWERPOINT);
        FILE_EXTENSION_MAP.put("pps", POWERPOINT);
        FILE_EXTENSION_MAP.put("pptx", POWERPOINT);
        FILE_EXTENSION_MAP.put("pptm", POWERPOINT);
        FILE_EXTENSION_MAP.put("potx", POWERPOINT);
        FILE_EXTENSION_MAP.put("potm", POWERPOINT);
        FILE_EXTENSION_MAP.put("ppam", POWERPOINT);
        FILE_EXTENSION_MAP.put("ppsx", POWERPOINT);
        FILE_EXTENSION_MAP.put("ppsm", POWERPOINT);
        FILE_EXTENSION_MAP.put("sldx", POWERPOINT);
        FILE_EXTENSION_MAP.put("sldm", POWERPOINT);
        FILE_EXTENSION_MAP.put("html", HTML);
        FILE_EXTENSION_MAP.put("htm", HTML);
    }

    public static FileType forFileExtension(String fileExtension) {
        FileType fileType = FILE_EXTENSION_MAP.get(fileExtension.toLowerCase());
        if (fileType == null) {
            fileType = OTHER;
        }

        return fileType;
    }

    public static FileType forFilename(String filename) {
        String[] split = filename.split("\\.");
        if (split.length < 2) {
            return OTHER;
        }

        return forFileExtension(split[1]);
    }
}
