/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class AnwenderloesungPageDriver extends AbstractPageDriver {

    public AnwenderloesungPageDriver(WebDriver driver) {
        super(driver);
    }

    public AnwenderloesungPageDriver gotoAnwenderloesung() {
        findElementById("anwenderloesung").click();
        return this;
    }

    public AnwenderloesungPageDriver clickMenuVorlagen() {
        findElementById("anwenderloesungmenu_menu-vorlagen").click();
        return this;
        
    }

    public AnwenderloesungPageDriver selectFirstSzenarioAnpassen() {
        findElementById("szenarien-form_szenarien_0_szenario-anpassen").click();
        return this;
        
    }

    public AnwenderloesungPageDriver clickNext() {
        findElement(By.xpath("//input[@value='Weiter']")).click();
        return this;
        
    }   

    public AnwenderloesungPageDriver selectFirstDownload() {
        findElementById("szenarien-form_szenarien_0_download-projektleitfaden").click();
        return this;
    }

    public AnwenderloesungPageDriver confirmDownload() {
        findElementById("szenarien-form_download-button").click();
        return this;
    }

    public AnwenderloesungPageDriver closeDownloadConfirmation() {
        findElementById("szenarien-form_szenarien_8_cancel-download-button").click();
        return this;
    }

}
