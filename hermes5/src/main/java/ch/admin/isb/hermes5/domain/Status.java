/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.ArrayList;
import java.util.List;

public enum Status {
    UNVOLLSTAENDIG("unvollständig"), IN_ARBEIT("in Arbeit"), FREIGEGEBEN("freigegeben");

    private final String nameDe;

    private Status(String nameDe) {
        this.nameDe = nameDe;
    }

    public static List<Status> valueOfStrings(List<String> selectedStatus) {
        List<Status> list = new ArrayList<Status>();
        for (String string : selectedStatus) {
            list.add(valueOf(string));
        }

        return list;
    }

    public String getNameDe() {
        return nameDe;
    }
}
