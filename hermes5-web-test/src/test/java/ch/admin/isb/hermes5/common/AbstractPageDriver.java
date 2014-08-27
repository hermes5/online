/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.common;
import static org.junit.Assert.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AbstractPageDriver {

    protected static final Logger logger = LoggerFactory
            .getLogger(AbstractPageDriver.class);
    protected WebDriver driver;
    
    public AbstractPageDriver(WebDriver driver) {
        this.driver=driver;
    }

    public WebElement findElementById(String id) {
        return findElement(By.id(id));
    }

    public WebElement findElement(By by) {
        try {
            return driver.findElement(by);
        } catch (NoSuchElementException e) {
            logger.warn("unabel to find  " + by + " in "
                    + driver.getPageSource());
            throw e;
        }
    }
    public WebElement selectOptionWithName(WebElement selectOneMenu, String string) {
        for (WebElement option : allOptions(selectOneMenu)) {
            if (option.getText().startsWith(string)) {
                return option;
            }
        }
        throw new NoSuchElementException(string + " in " + selectOneMenu);
    }

    public List<WebElement> allOptions(WebElement selectOneMenu) {
        return selectOneMenu.findElements(By.tagName("option"));
    }

    public WebElement selectOptionWithValue(WebElement selectOneMenu, String value) {
        for (WebElement option : allOptions(selectOneMenu)) {
            if (option.getAttribute("value").equals(value)) {
                return option;
            }
        }
        throw new NoSuchElementException(value + " in " + selectOneMenu + "\n" + driver.getPageSource());
    }

    public WebElement selectOptionAtIndex(WebElement selectOneMenu, int index) {
        return allOptions(selectOneMenu).get(index);
    }
    
    public WebElement findElementByXpath(String xpath){
        return driver.findElement(By.xpath(xpath));
    }
    
    public WebElement findElementByLinkText(String linkText) {
        return findElement(By.linkText(linkText));
    }
    
    public List<WebElement> findElementsByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath));      
    }

    public String getPageSource() {
        return driver.getPageSource();
    }
    public void assertNoError() {
        assertFalse(getPageSource(), getPageSource().contains("Exception"));
        assertFalse(getPageSource(), getPageSource().contains("Error"));
    }
    
    public String getBody() {
        return findElement(By.tagName("body")).getText();
    }
    

}