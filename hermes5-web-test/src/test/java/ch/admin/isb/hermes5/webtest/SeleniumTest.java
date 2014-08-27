/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.webtest;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.common.AnwenderloesungPageDriver;
import ch.admin.isb.hermes5.common.OnlinePublikationPageDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

public class SeleniumTest {

    private static final String BAMBOO_SERVER_URL = "bamboo_serverUrl";
    private static final Logger logger = LoggerFactory.getLogger(SeleniumTest.class);
    private WebDriver driver;
    protected String url;

    protected OnlinePublikationPageDriver onlinePublikation;
    protected AnwenderloesungPageDriver anwenderloesung;

    private boolean useChrome = false;

    @Before
    public void init() {
        url = useChrome ? "http://admin:admin@localhost:8080" : "http://localhost:8080";
//        url = "http://development.hermes5-online.ch";
        Map<String, String> env = System.getenv();
        if (env.containsKey(BAMBOO_SERVER_URL)) {
            useChrome = false;
            url = env.get(BAMBOO_SERVER_URL);
        }
        logger.info("remoteLink: " + url);

        driver = useChrome ? getChromeDriver() : getHtmlUnitDriver();

        onlinePublikation = new OnlinePublikationPageDriver(driver);
        anwenderloesung = new AnwenderloesungPageDriver(driver);
        driver.get(url);

    }

    private HtmlUnitDriver getHtmlUnitDriver() {
        BrowserVersion bv = new BrowserVersion("", "", "", 0);
        bv.setBrowserLanguage("de");
        // HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(bv) {
        //
        // protected WebClient modifyWebClient(WebClient client) {
        // DefaultCredentialsProvider creds = new DefaultCredentialsProvider();
        // creds.addCredentials("admin", "admin");
        // client.setCredentialsProvider(creds);
        // return client;
        // }
        // };
        HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver(bv);

        htmlUnitDriver.manage().deleteAllCookies();
        
        // htmlUnitDriver.setJavascriptEnabled(true);
        return htmlUnitDriver;
    }

    private ChromeDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Daten\\Hermes5\\Tools\\seleniumdrivers\\chromedriver.exe");

        BrowserVersion bv = new BrowserVersion("", "", "", 0);
        bv.setBrowserLanguage("de");
        return new ChromeDriver();
    }

    @After
    public void afterClass() {
        driver.quit();
    }

}
