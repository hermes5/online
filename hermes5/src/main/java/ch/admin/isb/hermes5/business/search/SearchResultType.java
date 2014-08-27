/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import ch.admin.isb.hermes5.util.FileType;
import ch.admin.isb.hermes5.util.WebFont;


public enum SearchResultType {
    PAGE(FileType.HTML), WORD(FileType.WORD), EXCEL(FileType.EXCEL), POWERPOINT(FileType.POWERPOINT), PDF(FileType.PDF);

    private final FileType fileType;
    private final WebFont webFont;

    private SearchResultType(FileType fileType) {
        this.fileType = fileType;
        this.webFont = WebFont.forFileType(fileType);
    }
    
    public static SearchResultType fromFilename(String filename) {
        FileType fileType = FileType.forFilename(filename);
        for (SearchResultType searchResultType : values()) {
            if (searchResultType.getFileType() == fileType) {
                return searchResultType;
            }
        }
        throw new IllegalArgumentException("Unable to detect search result type: " +filename);
    }
    
    public FileType getFileType() {
        return fileType;
    }
    
    public boolean hasWebFont() {
        return webFont != null;
    }

    public WebFont getWebFont() {
        return webFont;
    }
}
