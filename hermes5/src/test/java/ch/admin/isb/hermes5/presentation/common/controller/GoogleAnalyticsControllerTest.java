/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.common.controller;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.util.HtmlChecker;
import ch.admin.isb.hermes5.util.Hardcoded;

public class GoogleAnalyticsControllerTest {

    private GoogleAnalyticsController googleAnalyticsController;
    private HtmlChecker htmlChecker = new HtmlChecker();

    @Before
    public void setUp() throws Exception {
        googleAnalyticsController = new GoogleAnalyticsController();
        googleAnalyticsController.googleAnalyticsAccount = Hardcoded.configuration("googleaccount_1234");
    }

    @Test
    public void testGetSnippet() {
        String expected = "<script type=\"text/javascript\"> "
                + "var _gaq = _gaq || [];"
                + "_gaq.push(['_setAccount', 'googleaccount_1234']);"
                + "_gaq.push(['_trackPageview']);"
                + "(function() {"
                + "var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;"
                + "ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';"
                + "var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);" + "})();"
                + "</script>";
        assertEquals(removeNewLineAndWhiteSpaces(expected),
                removeNewLineAndWhiteSpaces(googleAnalyticsController.getSnippet()));
    }

    @Test
    public void testGetSnippetNullAccount() {
        googleAnalyticsController.googleAnalyticsAccount = null;
        assertEquals("", googleAnalyticsController.getSnippet());
    }

    @Test
    public void testGetSnippetAccountEmpty() {
        googleAnalyticsController.googleAnalyticsAccount = Hardcoded.configuration("");
        assertEquals("", googleAnalyticsController.getSnippet());
    }

    private String removeNewLineAndWhiteSpaces(String snippet) {
        return snippet.replaceAll("\\s+", "").replaceAll("\n", "");
    }

    @Test
    public void testAddGoogleAnalyticsSnippetToHtmlContent() throws UnsupportedEncodingException {
        String string = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><title>title</title></head><body>bodycontent</body></html>";
        htmlChecker.checkHtmlString(string);
        byte[] html = string.getBytes();
        byte[] googleAnalyticsSnippetToHtmlContent = googleAnalyticsController.addGoogleAnalyticsSnippetToHtmlContent(html);
        assertNotNull(googleAnalyticsSnippetToHtmlContent);
        String result = new String(googleAnalyticsSnippetToHtmlContent, "UTF8");
        assertTrue(result, result.contains("googleaccount_1234"));
        htmlChecker.checkHtmlString(result);
    }
    @Test
    public void testAddGoogleAnalyticsSnippetToNoHtml() throws UnsupportedEncodingException {
        byte[] googleAnalyticsSnippetToHtmlContent = googleAnalyticsController.addGoogleAnalyticsSnippetToHtmlContent("test".getBytes());
        assertNotNull(googleAnalyticsSnippetToHtmlContent);
        String result = new String(googleAnalyticsSnippetToHtmlContent, "UTF8");
        assertEquals("test", result);
    }

}
