/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.localsearchindex;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.persistence.SearchIndexStore;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

public class LocalSearchIndexStore implements SearchIndexStore {

    @Inject
    @SystemProperty(value = "searchIndexPath", fallback = "/home/wildfly/search")
    ConfigurationProperty searchIndexPath;

    private static final Logger logger = LoggerFactory.getLogger(LocalSearchIndexStore.class);

    public String getSearchIndexPath(String modelIdentifier, String lang) {
        return searchIndexPath.getStringValue() + "/" + modelIdentifier + "/" + lang;
    }

    @Override
    public void deleteIndexFiles(String modelIdentifier, String lang) {
        File[] listFiles = new File(getSearchIndexPath(modelIdentifier, lang)).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (!file.delete()) {
                    logger.error("Unable to delete old index file " + file.getAbsolutePath());
                }
            }
        }
    }

    @Override
    public List<String> listIndexFiles(String modelIdentifier, String language) {
        String path = getSearchIndexPath(modelIdentifier, language);
        return IOUtil.listFilesRelativeToGivenPath(path);
    }

    @Override
    public byte[] readIndexFile(String modelIdentifier, String language, String file) {
        String pathname = getSearchIndexPath(modelIdentifier, language) + "/" + file;
        return IOUtil.readFile(pathname);
    }

    @Override
    public void addIndexFile(String modelIdentifier, String language, String filename, byte[] filecontent) {
        String pathname = getSearchIndexPath(modelIdentifier, language) + "/" + filename;
        IOUtil.writeFile(new ByteArrayInputStream(filecontent), pathname);
    }
}
