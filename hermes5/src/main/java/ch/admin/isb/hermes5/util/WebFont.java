/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import static ch.admin.isb.hermes5.util.FileType.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This class holds references to the web-font CSS class and char unicode of h5-font created by http://icomoon.io and
 * their file associations.
 * 
 * @author Nicolas Oeschger <nioe@zuehlke.com>
 * 
 */

public enum WebFont {
    
    DEFAULT_FILE(".icon-file-default", "\ue605"), EXCEL_FILE("icon-file-excel", "\ue601"), PDF_FILE("icon-file-pdf",
            "\ue602"), WORD_FILE("icon-file-word", "\ue603"), POWERPOINT_FILE("icon-file-powerpoint", "\ue604");

    private static final Map<FileType, WebFont> FILE_TYPE_WEB_FONT_MAP;
    static {
        FILE_TYPE_WEB_FONT_MAP = new HashMap<FileType, WebFont>();
        FILE_TYPE_WEB_FONT_MAP.put(EXCEL, EXCEL_FILE);
        FILE_TYPE_WEB_FONT_MAP.put(WORD, WORD_FILE);
        FILE_TYPE_WEB_FONT_MAP.put(POWERPOINT, POWERPOINT_FILE);
        FILE_TYPE_WEB_FONT_MAP.put(PDF, PDF_FILE);
        FILE_TYPE_WEB_FONT_MAP.put(HTML, null);
        FILE_TYPE_WEB_FONT_MAP.put(OTHER, DEFAULT_FILE);
    }

    private final String cssClass;
    private final String unicode;
    
    private WebFont(String cssClass, String unicode) {
        this.cssClass = cssClass;
        this.unicode = unicode;
    }
    
    public String getCssClass() {
        return cssClass;
    }

    public String getUnicode() {
        return unicode;
    }

    public static WebFont forFileExtension(String fileExtension) {
        return forFileType(FileType.forFileExtension(fileExtension));
    }

    public static WebFont forFilename(String filename) {
        return forFileType(FileType.forFilename(filename));
    }

    public static WebFont forFileType(FileType fileType) {
        return FILE_TYPE_WEB_FONT_MAP.get(fileType);
    }

    public static Map<FileType, WebFont> getFileTypeWebFontMap() {
        return new HashMap<FileType, WebFont>(FILE_TYPE_WEB_FONT_MAP);
    }
}
