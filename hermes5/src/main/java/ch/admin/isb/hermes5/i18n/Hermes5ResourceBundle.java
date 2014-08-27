/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * Responsible to load UTF-8 encoded resource bundles
 * 
 */
public class Hermes5ResourceBundle extends ResourceBundle {

    private static final String BUNDLE_NAME = "ch.admin.isb.hermes5.i18n.messages";
    private static final String BUNDLE_EXTENSION = "properties";
    private static final String CHARSET = "UTF-8";
    private static final Control UTF8_CONTROL = new UTF8Control();

    public Hermes5ResourceBundle() {
        setParent(ResourceBundle.getBundle(BUNDLE_NAME, FacesContext.getCurrentInstance().getViewRoot().getLocale(),
                UTF8_CONTROL));
    }

    public Hermes5ResourceBundle(String lang) {
        setParent(ResourceBundle.getBundle(BUNDLE_NAME, new Locale(lang), UTF8_CONTROL));
    }

    @Override
    protected Object handleGetObject(String key) {
        return parent.getObject(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return parent.getKeys();
    }

    protected static class UTF8Control extends Control {

        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
                boolean reload) throws IllegalAccessException, InstantiationException, IOException {
            // The below code is copied from default Control#newBundle() implementation.
            // Only the PropertyResourceBundle line is changed to read the file as UTF-8.
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
            ResourceBundle bundle = null;
            InputStream stream = null;
            if (reload) {
                URL url = loader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        stream = connection.getInputStream();
                    }
                }
            } else {
                stream = loader.getResourceAsStream(resourceName);
            }
            if (stream != null) {
                try {
                    bundle = new PropertyResourceBundle(new InputStreamReader(stream, CHARSET));
                } finally {
                    stream.close();
                }
            }
            return bundle;
        }
    }
}