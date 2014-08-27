/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import static ch.admin.isb.hermes5.business.search.SearchResultType.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class SearchResultTypeTest {

    @Test
    public void testFromFilenamePpt() {
        assertEquals(POWERPOINT, fromFilename("file.ppt"));
    }
    @Test
    public void testFromFilenamePptx() {
        assertEquals(POWERPOINT, fromFilename("file.pptx"));
    }
    @Test
    public void testFromFilenamePPT() {
        assertEquals(POWERPOINT, fromFilename("file.PPT"));
    }
    
    @Test
    public void testFromFilenameXls() {
        assertEquals(EXCEL, fromFilename("file.xls"));
    }
    @Test
    public void testFromFilenamepptx() {
        assertEquals(EXCEL, fromFilename("file.xlsx"));
    }
    @Test
    public void testFromFilenameDoc() {
        assertEquals(WORD, fromFilename("file.doc"));
    }
    @Test
    public void testFromFilenameDocx() {
        assertEquals(WORD, fromFilename("file.docx"));
    }
    
    @Test
    public void testFromFilenameHtml() {
        assertEquals(PAGE, fromFilename("file.Html"));
    }
    @Test(expected=IllegalArgumentException.class)
    public void testFromFilenameUnknown() {
         fromFilename("file.png");
    }

}
