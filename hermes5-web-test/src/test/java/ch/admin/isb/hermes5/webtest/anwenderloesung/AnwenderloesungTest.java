/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.webtest.anwenderloesung;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.webtest.SeleniumTest;


public class AnwenderloesungTest extends SeleniumTest {
    

    @Before 
    public void setUp(){
        anwenderloesung.gotoAnwenderloesung();
    }
    
    
    @Test
    public void assertSzenarienOpens() {
        assertTrue(anwenderloesung.getPageSource(),anwenderloesung.getBody().contains("Szenarien"));
    }

    @Test
    public void clickTillEndSzenarien() {
        anwenderloesung.selectFirstSzenarioAnpassen();
        anwenderloesung.clickNext().clickNext().clickNext();
        
        assertTrue(anwenderloesung.getPageSource(),anwenderloesung.getPageSource().contains("Online Bereitstellen"));
    }
    
    @Test
    public void clickDownloadSzenario() {
       long before = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            anwenderloesung.selectFirstDownload().confirmDownload();
            assertTrue(anwenderloesung.getPageSource(),anwenderloesung.getPageSource().contains("erfolgreich"));
            anwenderloesung.closeDownloadConfirmation();
        }
        System.out.println("2x download first szenario: "+ (System.currentTimeMillis()- before)+"ms");
    }
    
    @Test
    public void assertVorlagen() {
        anwenderloesung.clickMenuVorlagen();
        assertTrue(anwenderloesung.getPageSource(),anwenderloesung.getBody().contains("docx"));
    }
    
}
