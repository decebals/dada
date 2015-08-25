/*
 * Copyright (C) 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.fortsoft.dada.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Decebal Suiu
 */
public class BeanUtils {

    public static Map<String, Method> findGetters(Object example) throws Exception {
        Map<String, Method> getters = new HashMap<>();

        BeanInfo info = Introspector.getBeanInfo(example.getClass());
        for (PropertyDescriptor propertyDescriptor : info.getPropertyDescriptors()) {
            Method reader = propertyDescriptor.getReadMethod();
            String propertyName = propertyDescriptor.getName();
            if (!"class".equals(propertyName) && (reader != null)) {
                getters.put(propertyName, reader);
            }
        }

        return getters;
    }

    /**
     * Returns the properties whose values are not null and different from the defaults.
     *
     * @param object
     * @param getters
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getProperties(Object object, Map<String, Method> getters) throws Exception {
        Map<String, Object> properties = new HashMap<>();
        for (Map.Entry<String, Method> getter : getters.entrySet()) {
            String propertyName = getter.getKey();
            Object propertyValue = getter.getValue().invoke(object);
            if (propertyValue != null) {
                Class<?> propertyType = getter.getValue().getReturnType();
                Object propertyDefaultValue = getDefaultValueForType(propertyType);
                if (!propertyValue.equals(propertyDefaultValue)) {
                    properties.put(propertyName, propertyValue);
                }
            }
        }

        return properties;
    }

    public static Object getDefaultValueForType(Class<?> type) {
        if (type.isPrimitive()) {
            if (type == Double.TYPE) {
                return (double) 0;
            }

            if (type == Float.TYPE) {
                return (float) 0;
            }

            if (type == Byte.TYPE) {
                return (byte) 0;
            }

            if (type == Integer.TYPE) {
                return (int) 0;
            }

            if (type == Long.TYPE) {
                return (long) 0;
            }

            if (type == Short.TYPE) {
                return (short) 0;
            }

            if (type == Character.TYPE) {
                return (char) 0;
            }

            if (type == Boolean.TYPE) {
                return false;
            }
        }

        return null;
    }

}
