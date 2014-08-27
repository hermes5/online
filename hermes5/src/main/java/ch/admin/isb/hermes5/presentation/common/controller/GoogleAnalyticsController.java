/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.common.controller;

import java.io.UnsupportedEncodingException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

@RequestScoped
@Named
public class GoogleAnalyticsController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(GoogleAnalyticsController.class);

    @Inject
    @SystemProperty(value = "googleanalytics.account")
    ConfigurationProperty googleAnalyticsAccount;

    public String getSnippet() {
        if (googleAnalyticsAccount != null && StringUtil.isNotBlank(googleAnalyticsAccount.getStringValue())) {
            return "<script type=\"text/javascript\"> var _gaq = _gaq || [];\n _gaq.push(['_setAccount', '"
                    + googleAnalyticsAccount.getStringValue()
                    + "']);\n"
                    + "_gaq.push(['_trackPageview']);\n  (function() {\n var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\n "
                    + "ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\n "
                    + "var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);   \n})(); \n</script>";
        }
        return "";
    }

    public byte[] addGoogleAnalyticsSnippetToHtmlContent(byte[] content) {
        try {
            String htmlString = new String(content, "UTF8");
            String snippet = getSnippet();
            int indexOf = htmlString.indexOf("</head>");
            if (indexOf > 0) {
                String before = htmlString.substring(0, indexOf);
                String after = htmlString.substring(indexOf);
                return (before + snippet + after).getBytes("UTF8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn("unable to convert to string ", e);
        }
        return content;
    }
}
