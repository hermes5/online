/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import static ch.admin.isb.hermes5.util.ReflectionUtil.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.persistence.localsearchindex.LocalSearchIndexStore;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.persistence.s3.S3LocalAdapter;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.StringUtil;

public class SearchIndexManagerIntegrationTest {

    private static final String TARGET_TMP_LOCAL = "target/tmp/local";
    private static final String TARGET_TMP_S3 = "target/tmp/s3";
    private SearchIndexManager searchIndexManager;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Before
    public void setUp() throws Exception {
        searchIndexManager = new SearchIndexManager();
        searchIndexManager.s3 = new S3();
        S3LocalAdapter s3LocalAdapter = new S3LocalAdapter();

        CacheContainer cacheContainerMock = mock(CacheContainer.class);
        updateField(s3LocalAdapter, "localS3Path", Hardcoded.configuration(TARGET_TMP_S3));
        updateField(searchIndexManager.s3, "cacheContainer", cacheContainerMock);
        updateField(searchIndexManager.s3, "useLocalS3", Hardcoded.configuration(true));
        
        updateField(searchIndexManager.s3, "s3LocalAdapter", s3LocalAdapter);
        Cache cacheMock = mock(Cache.class);
        when(cacheContainerMock.getCache()).thenReturn(cacheMock);
        searchIndexManager.s3.init();
        
        searchIndexManager.localSearchIndexStore = new LocalSearchIndexStore();
        updateField(searchIndexManager.localSearchIndexStore, "searchIndexPath", Hardcoded.configuration(TARGET_TMP_LOCAL));
        
        IOUtil.deleteFile(new File(TARGET_TMP_S3));
        IOUtil.deleteFile(new File(TARGET_TMP_LOCAL));
    }


    @Test
    public void testRestoreIndexFilesFromS3() {
        IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes("mycontent1")), TARGET_TMP_S3+"/model/searchindex/fr/myfile1.txt");
        IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes("mycontent2")), TARGET_TMP_S3+"/model/searchindex/fr/myfile2.txt");
       searchIndexManager.restoreIndexFilesFromS3("model", "fr");
       assertEquals("mycontent1", StringUtil.fromBytes(IOUtil.readFile(TARGET_TMP_LOCAL+"/model/fr/myfile1.txt")));
       assertEquals("mycontent2", StringUtil.fromBytes(IOUtil.readFile(TARGET_TMP_LOCAL+"/model/fr/myfile2.txt")));
    }

    @Test
    public void testCleanOldIndices() {
        IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes("mycontent")), TARGET_TMP_S3+"/model/searchindex/fr/myfile.txt");
        IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes("mycontent")), TARGET_TMP_LOCAL+"/model/fr/myfile.txt");
        searchIndexManager.cleanOldIndices("model", "fr");
        assertFalse(new File(TARGET_TMP_S3+"/model/searchindex/fr/myfile.txt").exists());
        assertFalse(new File(TARGET_TMP_LOCAL+"/model/fr/myfile.txt").exists());
    }

    @Test
    public void testStoreSearchIndexInS3() {
        IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes("mycontent1")), TARGET_TMP_LOCAL+"/model/fr/myfile1.txt");
        IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes("mycontent2")), TARGET_TMP_LOCAL+"/model/fr/myfile2.txt");
        searchIndexManager.storeSearchIndexInS3("model", "fr");
        assertEquals("mycontent1", StringUtil.fromBytes(IOUtil.readFile(TARGET_TMP_S3+"/model/searchindex/fr/myfile1.txt")));
        assertEquals("mycontent2", StringUtil.fromBytes(IOUtil.readFile(TARGET_TMP_S3+"/model/searchindex/fr/myfile2.txt")));
    }

}
