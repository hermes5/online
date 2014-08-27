/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.io.Serializable;

public class CustomLocalizable implements Serializable, Localizable {

    private static final long serialVersionUID = 1L;

    private final String textDe;
    private final String textFr;
    private final String textIt;
    private final String textEn;

    public CustomLocalizable(String textDe, String textFr, String textIt, String textEn) {
        this.textDe = textDe;
        this.textFr = textFr;
        this.textIt = textIt;
        this.textEn = textEn;
    }
 
    public String getText(String lang) {
        if("fr".equals(lang)) {
            return textFr;
        }
        if("it".equals(lang)) {
            return textIt;
        }
        if("en".equals(lang)) {
            return textEn;
        }
        return textDe;
    }
    
}
