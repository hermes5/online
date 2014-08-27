/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Hardcoded {

    public static ConfigurationProperty configuration(final String value) {
        return new ConfigurationProperty() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getStringValue() {
                return value;
            }
        };
    }

    public static ConfigurationProperty configuration(boolean value) {
        return configuration(value ? "true" : "false");
    }

    public static ConfigurationProperty configuration(int i) {
        return configuration(String.valueOf(i));
    }

    public static void enableDefaults(Object... objectsWithConfigurationProperties) {
        for (Object objectWithConfigurationProperties : objectsWithConfigurationProperties) {
            for (Field field : getDeclaredFields(objectWithConfigurationProperties)) {
                SystemProperty systemProperty = field.getAnnotation(SystemProperty.class);
                if (systemProperty != null && systemProperty.fallback() != null) {
                    try {
                        field.setAccessible(true);
                        field.set(objectWithConfigurationProperties, configuration(systemProperty.fallback()));
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot set fallback value of system property field "
                                + field.getName(), e);
                    }
                }
            }
        }
    }

    private static ArrayList<Field> getDeclaredFields(Object o) {
        ArrayList<Field> declaredFields = new ArrayList<Field>();
        Class<?> clazz = o.getClass();

        do {
            declaredFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        } while (clazz != null && clazz != Object.class);

        return declaredFields;
    }
}
