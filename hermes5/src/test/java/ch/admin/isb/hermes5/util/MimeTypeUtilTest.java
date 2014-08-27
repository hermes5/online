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

import org.junit.Test;

public class MimeTypeUtilTest {

    private MimeTypeUtil mimeTypeUtil = new MimeTypeUtil();

    @Test
    public void test_new() {
        assertNull(mimeTypeUtil.getMimeType("file.new"));
    }

    @Test
    public void test_css() {
        assertEquals("text/css", mimeTypeUtil.getMimeType("file.css"));
    }

    @Test
    public void test_txt() {
        assertEquals("text/plain", mimeTypeUtil.getMimeType("file.txt"));
    }

    @Test
    public void test_html() {
        assertEquals("text/html", mimeTypeUtil.getMimeType("file.html"));
    }

    @Test
    public void test_xml() {
        assertEquals("text/xml", mimeTypeUtil.getMimeType("file.xml"));
    }

    @Test
    public void test_js() {
        assertEquals("text/javascript", mimeTypeUtil.getMimeType("file.js"));
    }

    @Test
    public void test_gif() {
        assertEquals("image/gif", mimeTypeUtil.getMimeType("file.gif"));
    }

    @Test
    public void test_png() {
        assertEquals("image/png", mimeTypeUtil.getMimeType("file.png"));
    }

    @Test
    public void test_jpg() {
        assertEquals("image/jpeg", mimeTypeUtil.getMimeType("file.jpg"));
    }

    @Test
    public void test_jpeg() {
        assertEquals("image/jpeg", mimeTypeUtil.getMimeType("file.jpeg"));
    }

    @Test
    public void test_pdf() {
        assertEquals("application/pdf", mimeTypeUtil.getMimeType("file.pdf"));
    }

    @Test
    public void test_doc() {
        assertEquals("application/msword", mimeTypeUtil.getMimeType("file.doc"));
    }

    @Test
    public void test_dot() {
        assertEquals("application/msword", mimeTypeUtil.getMimeType("file.dot"));
    }

    @Test
    public void test_xls() {
        assertEquals("application/vnd.ms-excel", mimeTypeUtil.getMimeType("file.xls"));
    }

    @Test
    public void test_ppt() {
        assertEquals("application/vnd.ms-powerpoint", mimeTypeUtil.getMimeType("file.ppt"));
    }

    @Test
    public void test_docm() {
        assertEquals("application/vnd.ms-word.document.macroEnabled.12", mimeTypeUtil.getMimeType("file.docm"));
    }

    @Test
    public void test_docx() {
        assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                mimeTypeUtil.getMimeType("file.docx"));
    }

    @Test
    public void test_dotm() {
        assertEquals("application/vnd.ms-word.template.macroEnabled.12", mimeTypeUtil.getMimeType("file.dotm"));
    }

    @Test
    public void test_dotx() {
        assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.template",
                mimeTypeUtil.getMimeType("file.dotx"));
    }

    @Test
    public void test_potm() {
        assertEquals("application/vnd.ms-powerpoint.template.macroEnabled.12", mimeTypeUtil.getMimeType("file.potm"));
    }

    @Test
    public void test_potx() {
        assertEquals("application/vnd.openxmlformats-officedocument.presentationml.template",
                mimeTypeUtil.getMimeType("file.potx"));
    }

    @Test
    public void test_ppam() {
        assertEquals("application/vnd.ms-powerpoint.addin.macroEnabled.12", mimeTypeUtil.getMimeType("file.ppam"));
    }

    @Test
    public void test_ppsm() {
        assertEquals("application/vnd.ms-powerpoint.slideshow.macroEnabled.12", mimeTypeUtil.getMimeType("file.ppsm"));
    }

    @Test
    public void test_ppsx() {
        assertEquals("application/vnd.openxmlformats-officedocument.presentationml.slideshow",
                mimeTypeUtil.getMimeType("file.ppsx"));
    }

    @Test
    public void test_pptm() {
        assertEquals("application/vnd.ms-powerpoint.presentation.macroEnabled.12",
                mimeTypeUtil.getMimeType("file.pptm"));
    }

    @Test
    public void test_pptx() {
        assertEquals("application/vnd.openxmlformats-officedocument.presentationml.presentation",
                mimeTypeUtil.getMimeType("file.pptx"));
    }

    @Test
    public void test_xlam() {
        assertEquals("application/vnd.ms-excel.addin.macroEnabled.12", mimeTypeUtil.getMimeType("file.xlam"));
    }

    @Test
    public void test_xlsb() {
        assertEquals("application/vnd.ms-excel.sheet.binary.macroEnabled.12", mimeTypeUtil.getMimeType("file.xlsb"));
    }

    @Test
    public void test_xlsm() {
        assertEquals("application/vnd.ms-excel.sheet.macroEnabled.12", mimeTypeUtil.getMimeType("file.xlsm"));
    }

    @Test
    public void test_xlsx() {
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                mimeTypeUtil.getMimeType("file.xlsx"));
    }

    @Test
    public void test_xltm() {
        assertEquals("application/vnd.ms-excel.template.macroEnabled.12", mimeTypeUtil.getMimeType("file.xltm"));
    }

    @Test
    public void test_xltx() {
        assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.template",
                mimeTypeUtil.getMimeType("file.xltx"));
    }

    @Test
    public void test_mpx() {
        assertEquals("application/x-project", mimeTypeUtil.getMimeType("file.mpx"));
    }

    @Test
    public void test_eot() {
        assertEquals("application/vnd.ms-fontobject", mimeTypeUtil.getMimeType("file.eot"));
    }

    @Test
    public void test_svg() {
        assertEquals("image/svg+xml", mimeTypeUtil.getMimeType("file.svg"));
    }

    @Test
    public void test_tt() {
        assertEquals("application/octet-stream", mimeTypeUtil.getMimeType("file.tt"));
    }

    @Test
    public void test_ttf() {
        assertEquals("application/x-font-ttf", mimeTypeUtil.getMimeType("file.ttf"));
    }

    @Test
    public void test_woff() {
        assertEquals("application/x-font-woff", mimeTypeUtil.getMimeType("file.woff"));
    }

    @Test
    public void test_xsd() {
        assertEquals("text/xml", mimeTypeUtil.getMimeType("file.xsd"));
    }

}
