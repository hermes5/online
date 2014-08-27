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
import java.util.Collection;
import java.util.List;

public class ReflectionUtil {

    public static List<Field> getFieldsWithType(Class<? extends Object> clazz, Class<? extends Object> type,
            String... ignoreList) {
        List<Field> declaredField = filterFields(clazz.getPackage().getName(), clazz.getDeclaredFields(), type,
                ignoreList);

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass.getPackage().getName().equals(clazz.getPackage().getName())) {
            Collection<Field> superClassFields = getFieldsWithType(superClass, type, ignoreList);
            declaredField.addAll(superClassFields);
        }
        return declaredField;
    }

    private static List<Field> filterFields(String packageName, Field[] declaredFields, Class<? extends Object> type,
            String... ignoreList) {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : declaredFields) {
            if (type.equals(field.getType()) && containsNot(ignoreList, field.getName())) {
                fields.add(field);
            }
        }
        return fields;
    }

    private static boolean containsNot(String[] ignoreList, String name) {
        for (String string : ignoreList) {
            if (string.equals(name)) {
                return false;
            }
        }
        return true;
    }

    public static List<Field> getFieldsInGivenPackageCollectionsOrObject(Class<? extends Object> clazz) {
        List<Field> declaredField = filterFieldsInGivenPackageCollectionsOrObject(clazz.getPackage().getName(),
                clazz.getDeclaredFields());

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass.getPackage().getName().equals(clazz.getPackage().getName())) {
            Collection<Field> superClassFields = getFieldsInGivenPackageCollectionsOrObject(superClass);
            declaredField.addAll(superClassFields);
        }
        return declaredField;
    }

    private static List<Field>
            filterFieldsInGivenPackageCollectionsOrObject(String packageName, Field[] declaredFields) {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : declaredFields) {
            if (fieldTypeInPackageOrCollectionOrObject(packageName, field)) {
                fields.add(field);
            }
        }
        return fields;
    }

    private static boolean fieldTypeInPackageOrCollectionOrObject(String packageName, Field field) {
        return field.getType().getPackage().getName().equals(packageName) || field.getType().equals(Object.class)
                || Collection.class.isAssignableFrom(field.getType());
    }

    public static void updateField(Object o, String fieldname, Object value) {
        Class<? extends Object> clazz = o.getClass();
        updateField(o, fieldname, value, clazz);
    }

    private static void updateField(Object o, String fieldname, Object value, Class<? extends Object> clazz) {
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

    public static <T> T getField(Object o, String fieldname) {
        Class<? extends Object> clazz = o.getClass();
        return getField(o, fieldname, clazz);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object o, String fieldname, Class<? extends Object> clazz) {
        try {
            Field declaredField = clazz.getDeclaredField(fieldname);
            declaredField.setAccessible(true);
            return (T) declaredField.get(o);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw new RuntimeException(e);
            } else {
                return getField(o, fieldname, superClass);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
