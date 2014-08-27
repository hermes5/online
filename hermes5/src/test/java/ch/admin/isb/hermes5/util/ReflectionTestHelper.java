/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTestHelper {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTestHelper.class);

    public <T> T fillInSetter(T source) {
        Method[] methods = source.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                method.setAccessible(true);
                if (method.getParameterTypes().length == 1) {
                    Object value = null;
                    Class<?> parameterType = method.getParameterTypes()[0];
                    if (parameterType.equals(java.lang.Long.class) || parameterType.toString().equals("long")) {
                        value = UUID.randomUUID().getMostSignificantBits();
                    } else if (parameterType.equals(java.lang.Integer.class) || parameterType.toString().equals("int")) {
                        value = (int) UUID.randomUUID().getMostSignificantBits();
                    } else if (parameterType.equals(java.lang.String.class)) {
                        value = UUID.randomUUID().toString();
                    } else if (parameterType.equals(java.util.Date.class)) {
                        value = new Date((long) (Math.random() * 10000 * 1000 * 24 * 3600));
                    } else if (parameterType.toString().equals("boolean")) {
                        value = randomInt(2) == 0;
                    } else if (parameterType.isEnum()) {
                        Field[] fields = parameterType.getFields();
                        int randomInt = randomInt(fields.length);
                        @SuppressWarnings({ "unchecked", "rawtypes" })
                        Object valueOf = Enum.valueOf((Class<Enum>) parameterType,
                                String.valueOf(fields[randomInt].getName()));
                        value = valueOf;
                    } else {
                        logger.warn("Unknown argument type " + parameterType);
                        continue;
                    }

                    try {
                        method.invoke(source, value);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return source;
    }

    private int randomInt(int lessThan) {
        return (int) (Math.random() * lessThan);
    }

    public void updateField(Object o, String fieldname, Object value) {
        Class<? extends Object> clazz = o.getClass();
        updateField(o, fieldname, value, clazz);
    }

    private void updateField(Object o, String fieldname, Object value, Class<? extends Object> clazz) {
        try {
            Field declaredField = clazz.getDeclaredField(fieldname);
            declaredField.setAccessible(true);
            declaredField.set(o, value);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw new RuntimeException(e);
            } else {
                updateField(o, fieldname, value, superClass);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void assertGettersResultEqual(Object source, Object copy, String... ignored)  {
        Method[] methods = source.getClass().getMethods();
        List<String> ignoredGetters = new ArrayList<String>();
        for (String ingore : ignored) {
            ignoredGetters.add("get" + ingore.toLowerCase());
        }
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !ignoredGetters.contains(method.getName().toLowerCase())) {
                try {
                    assertEquals(method.getName() + " is not equal", method.invoke(source), method.invoke(copy));
                } catch (Exception e) {
                    logger.info("issue on " + method + " source: " + source + " copy: " + copy, e);
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
