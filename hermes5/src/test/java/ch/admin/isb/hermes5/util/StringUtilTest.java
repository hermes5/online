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

public class StringUtilTest {

    private static final String FILE_NAME = "test.xlsx";
    private static final String FILE_LINK = "C:\\blubb\\" + FILE_NAME;

    private static final String HTML_LINK_NAME = "Google Schweiz";
    private static final String HTML_LINK_URL = "http://www.google.ch";
    private static final String HTML_LINK = "<a href=\"" + HTML_LINK_URL + "\">" + HTML_LINK_NAME + "</a>";

    @Test
    public void testIsWebAttachmentUrl() {
        assertTrue(StringUtil.isWebAttachmentUrl(HTML_LINK));
        assertFalse(StringUtil.isWebAttachmentUrl(FILE_LINK));
    }

    @Test
    public void testGetLinkName_FromWebUrl() {
        assertEquals(HTML_LINK_NAME, StringUtil.getLinkName(HTML_LINK));
    }

    @Test
    public void testGetLinkName_FromFileLink() {
        assertEquals(FILE_NAME, StringUtil.getLinkName(FILE_LINK));
    }

    @Test
    public void testGetLinkUrl_FromWebUrl() {
        assertEquals(HTML_LINK_URL, StringUtil.getLinkUrl(HTML_LINK));
    }

    @Test
    public void testGetLinkUrl_FromFileLink() {
        assertEquals(HTML_LINK_NAME, StringUtil.getLinkUrl(HTML_LINK_NAME));
    }

    @Test
    public void testShortFilename_alreadyShort() {
        String filename = "abcd.xls";
        String newFilename = StringUtil.shortFilename(filename, filename.length());

        assertEquals(filename, newFilename);
    }

    @Test
    public void testShortFilename_withExtension() {
        String filename = "abcdefghijklmnopqrstuvw.xls";
        String newFilename = StringUtil.shortFilename(filename, 16);

        assertEquals("abcde...tuvw.xls", newFilename);
    }

    @Test
    public void testShortFilename_withoutExtension() {
        String filename = "abcdefghijklmnopqrstuvw";
        String newFilename = StringUtil.shortFilename(filename, 16);

        assertEquals("abcdefg...rstuvw", newFilename);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShortFilename_tooShortMaxLength() {
        String filename = "abcdefghijklmnopqrstuvw.xls";
        StringUtil.shortFilename(filename, 5);
    }
    
    @Test
    public void testAddTargetToHtmlLink_linkWithoutTarget() {
        String link = "<a href=\"http://www.google.ch\">Google</a>";
        String newLink = StringUtil.addTargetToHtmlLink(link);
        assertEquals("<a href=\"http://www.google.ch\" target=\"_blank\">Google</a>", newLink);
    }

    @Test
    public void testAddTargetToHtmlLink_linkWithTarget() {
        String link = "<a href=\"http://www.google.ch\" target=\"_self\">Google</a>";
        String newLink = StringUtil.addTargetToHtmlLink(link);
        assertEquals("<a href=\"http://www.google.ch\" target=\"_self\">Google</a>", newLink);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddTargetToHtmlLink_noWebLink() {
        String link = "abc";
        StringUtil.addTargetToHtmlLink(link);
    }
}
