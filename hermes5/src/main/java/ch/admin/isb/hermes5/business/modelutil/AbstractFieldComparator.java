/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelutil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFieldComparator implements Serializable{

    private static final long serialVersionUID = 1L;
    protected final Map<String, Integer> fieldOrderMap = new HashMap<String, Integer>();

    public int compare(Field o1, Field o2) {
        if (o1 != null && o2 != null) {
            String n1 = simpleName(o1);
            String n2 = simpleName(o2);
            if (n1 != null && n2 != null) {
                Integer r1 = fieldOrderMap.get(n1);
                Integer r2 = fieldOrderMap.get(n2);
                if (r1 != null && r2 != null) {
                    // S ystem.out.println(getClass().getSimpleName() + ": " + (r1 - r2 < 0 ? n1 : n2) + " < "
                    // + (r1 - r2 < 0 ? n2 : n1));
                    return r1 - r2;
                }
                if (r1 != null) {
                    // S ystem.out.println(getClass().getSimpleName() + ": " + n1 + " < " + n2);
                    return -1;
                }
                if (r2 != null) {
                    // S ystem.out.println(getClass().getSimpleName() + ": " + n2 + " < " + n1);
                    return +1;
                }
                // S ystem.out.println(getClass().getSimpleName() + ": Do not know how to compare " + n1 + " to " + n2);
            }
        }
        return 0;
    }

    private String simpleName(Field o) {
        String name = o.getName();
        return name;
    }
}
