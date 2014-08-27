/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class WebFontTest {
    
    @Test
    public void testForFileExtensionWord() {
        assertTrue(WebFont.forFileExtension("docx").getCssClass().contains("word"));
        assertTrue(WebFont.forFileExtension("doc").getCssClass().contains("word"));
    }

    @Test
    public void testForFileExtensionExcel() {
        assertTrue(WebFont.forFileExtension("xlsx").getCssClass().contains("excel"));
        assertTrue(WebFont.forFileExtension("xls").getCssClass().contains("excel"));
    }

    @Test
    public void testForFileExtensionPdf() {
        assertTrue(WebFont.forFileExtension("pdf").getCssClass().contains("pdf"));
        assertTrue(WebFont.forFileExtension("PDF").getCssClass().contains("pdf"));
    }

    @Test
    public void testForFileExtensionPowerpoint() {
        assertTrue(WebFont.forFileExtension("pptx").getCssClass().contains("powerpoint"));
        assertTrue(WebFont.forFileExtension("ppt").getCssClass().contains("powerpoint"));
        assertTrue(WebFont.forFileExtension("ppsx").getCssClass().contains("powerpoint"));
        assertTrue(WebFont.forFileExtension("pps").getCssClass().contains("powerpoint"));
    }
    
    @Test
    public void testForFileExtensionHtml() {
        assertNull(WebFont.forFileExtension("html"));
        assertNull(WebFont.forFileExtension("htm"));
    }

    @Test
    public void testForFileExtensionOther() {
        assertTrue(WebFont.forFileExtension("blubb").getCssClass().contains("default"));
        assertTrue(WebFont.forFileExtension("BLA").getCssClass().contains("default"));
    }

    @Test
    public void testForFilenameWord() {
        assertTrue(WebFont.forFilename("test.docx").getCssClass().contains("word"));
        assertTrue(WebFont.forFilename("test.doc").getCssClass().contains("word"));
    }

    @Test
    public void testForFilenameExcel() {
        assertTrue(WebFont.forFilename("test.xlsx").getCssClass().contains("excel"));
        assertTrue(WebFont.forFilename("test.xls").getCssClass().contains("excel"));
        assertTrue(WebFont.forFilename("test.XLsX").getCssClass().contains("excel"));
    }

    @Test
    public void testForFilenamePdf() {
        assertTrue(WebFont.forFilename("test.pdf").getCssClass().contains("pdf"));
    }

    @Test
    public void testForFilenamePowerpoint() {
        assertTrue(WebFont.forFilename("test.pptx").getCssClass().contains("powerpoint"));
        assertTrue(WebFont.forFilename("test.ppt").getCssClass().contains("powerpoint"));
        assertTrue(WebFont.forFilename("test.ppsx").getCssClass().contains("powerpoint"));
        assertTrue(WebFont.forFilename("test.pps").getCssClass().contains("powerpoint"));
    }

    @Test
    public void testForFilenameHtml() {
        assertNull(WebFont.forFilename("test.html"));
        assertNull(WebFont.forFilename("test.htm"));
    }

    @Test
    public void testForFilenameOther() {
        assertTrue(WebFont.forFilename("test.blubb").getCssClass().contains("default"));
    }

    @Test
    public void testAllFileTypesCovered() {
        assertTrue("Not all file types covered!",
                WebFont.getFileTypeWebFontMap().keySet().containsAll(Arrays.asList(FileType.values())));
    }
}
