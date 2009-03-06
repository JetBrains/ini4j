/**
 * Copyright 2005,2009 Ivan SZKIBA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ini4j;

import org.ini4j.spi.AbstractBeanInvocationHandler;

import java.lang.reflect.Proxy;

import java.util.prefs.Preferences;

public class PreferencesBean
{
    private PreferencesBean()
    {
    }

    public static <T> T newInstance(Class<T> clazz, final Preferences prefs)
    {
        return clazz.cast(Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new AbstractBeanInvocationHandler()
                    {
                        @Override protected Object getPropertySpi(String property, Class<?> clazz)
                        {
                            return prefs.get(property, null);
                        }

                        @Override protected void setPropertySpi(String property, Object value, Class<?> clazz)
                        {
                            prefs.put(property, value.toString());
                        }

                        @Override protected boolean hasPropertySpi(String property)
                        {
                            return prefs.get(property, null) != null;
                        }
                    }));
    }
}
