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

public class CustomModul extends Modul {

    private final String name;

    public CustomModul(String customModulName) {
        super(null, null);
        name = customModulName;
    }

    @Override
    public String getId() {
        return getName();
    }
    
    public boolean isCustom() {
        return true;
    }

    

    @Override
    public Localizable getMainDescription() {
        return new CustomLocalizable("", "", "", "");
    }
    
    @Override
    public Localizable getBriefDescription() {
        return new CustomLocalizable("", "", "", "");
    }

    @Override
    public String getName() {
        return "custommodul_" + StringUtil.replaceSpecialChars(name).replace(" ", "_");
    }

    @Override
    public Localizable getPresentationName() {
        return new CustomLocalizable(name, name, name, name);
    }

}
