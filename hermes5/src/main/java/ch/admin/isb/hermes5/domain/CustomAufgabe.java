/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import ch.admin.isb.hermes5.util.StringUtil;



public class CustomAufgabe extends Aufgabe {
    private String name;
    
    public CustomAufgabe(String name) {
        super(null, null);
        this.name=name;
    }
    
    public Localizable getPresentationName() {
        return new CustomLocalizable(name, name, name, name);
    }
    
    @Override
    public String getId() {
        return getName();
    }
    
        
    public boolean isHidden() {
        return true;
    }

    
    public String getName(){
        return "customaufgabe_"+StringUtil.replaceSpecialChars(name).replace(" ", "_");
    }
    @Override
    public Localizable getPurpose() {
        return null;
    }
    @Override
    public Localizable getBriefDescription() {
        return null;
    }
    @Override
    public Localizable getMainDescription() {
        return null;
    }

    @Override
    public Localizable getKeyConsiderations() {
        return null;
    }
    @Override
    public Localizable getAlternatives() {
        return null;
    }
    @Override
    public Localizable getChecklist() {
        return null;
    }
    

}
