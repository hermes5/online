/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.persistence.SearchIndexStore;
import ch.admin.isb.hermes5.persistence.localsearchindex.LocalSearchIndexStore;
import ch.admin.isb.hermes5.persistence.s3.S3;

public class SearchIndexManager {

    @Inject
    S3 s3;
    @Inject
    LocalSearchIndexStore localSearchIndexStore;

    public String getSearchIndexPath(String modelIdentifier, String lang) {
        return localSearchIndexStore.getSearchIndexPath(modelIdentifier, lang);
    }

    public void restoreIndexFilesFromS3(String modelIdentifier, String language) {
        copySearchIndex(modelIdentifier, language, s3, localSearchIndexStore);
    }

    public void cleanOldIndices(String modelIdentifier, String language) {
        localSearchIndexStore.deleteIndexFiles(modelIdentifier, language);
        s3.deleteIndexFiles(modelIdentifier, language);
    }

    public void storeSearchIndexInS3(String modelIdentifier, String language) {
        copySearchIndex(modelIdentifier, language, localSearchIndexStore, s3);
    }

    private void copySearchIndex(String modelIdentifier, String language, SearchIndexStore source,
            SearchIndexStore target) {
        List<String> indexFiles = source.listIndexFiles(modelIdentifier, language);
        for (String file : indexFiles) {
            byte[] readIndexFile = source.readIndexFile(modelIdentifier, language, file);
            target.addIndexFile(modelIdentifier, language, file, readIndexFile);
        }
    }
}
