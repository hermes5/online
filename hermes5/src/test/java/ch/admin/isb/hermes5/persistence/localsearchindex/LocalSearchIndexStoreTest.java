/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.localsearchindex;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.StringUtil;

public class LocalSearchIndexStoreTest {

    private static final String TARGET_TMP_SEARCH = "target/tmp/search";
    private LocalSearchIndexStore localSearchIndexStore;

    @Before
    public void setUp() throws Exception {
        localSearchIndexStore = new LocalSearchIndexStore();
        localSearchIndexStore.searchIndexPath = Hardcoded.configuration(TARGET_TMP_SEARCH);
        IOUtil.deleteFile(new File(TARGET_TMP_SEARCH));
    }

    @Test
    public void testGetSearchIndexPath() {
        assertEquals(TARGET_TMP_SEARCH +"/modelid/languagename",
                localSearchIndexStore.getSearchIndexPath("modelid", "languagename"));
    }

    @Test
    public void testDeleteIndexFiles() throws Exception {
        File file1 = new File(TARGET_TMP_SEARCH +"/modelid1/languagename1/file1.txt");
        File file2 = new File(TARGET_TMP_SEARCH +"/modelid1/languagename1/file2.txt");
        File file3 = new File(TARGET_TMP_SEARCH +"/modelid1/languagename2/file3.txt");
        File file4 = new File(TARGET_TMP_SEARCH +"/modelid2/languagename1/file4.txt");
        for (File file : new File[] { file1, file2, file3, file4 }) {
            IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes(file.getAbsolutePath())),
                    file.getAbsolutePath());
            assertTrue(file.exists());
        }
        localSearchIndexStore.deleteIndexFiles("modelid1", "languagename1");
        assertFalse(file1.exists());
        assertFalse(file2.exists());
        assertTrue(file3.exists());
        assertTrue(file4.exists());
    }

    @Test
    public void testListIndexFiles() {
        File file1 = new File(TARGET_TMP_SEARCH +"/modelid1/languagename1/file1.txt");
        File file2 = new File(TARGET_TMP_SEARCH +"/modelid1/languagename1/file2.txt");
        File file3 = new File(TARGET_TMP_SEARCH +"/modelid1/languagename2/file3.txt");
        File file4 = new File(TARGET_TMP_SEARCH +"/modelid2/languagename1/file4.txt");
        for (File file : new File[] { file1, file2, file3, file4 }) {
            IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes(file.getAbsolutePath())),
                    file.getAbsolutePath());
            assertTrue(file.exists());
        }
        List<String> listIndexFiles = localSearchIndexStore.listIndexFiles("modelid1", "languagename1");
        assertEquals(2, listIndexFiles.size());
        assertTrue("" + listIndexFiles, listIndexFiles.contains("file1.txt"));
        assertTrue("" + listIndexFiles, listIndexFiles.contains("file2.txt"));
    }

    @Test
    public void readIndexFile() {
        File file1 = new File(TARGET_TMP_SEARCH +"/modelid1/languagename1/file1.txt");
        IOUtil.writeFile(new ByteArrayInputStream(StringUtil.getBytes("mycontent")), file1.getAbsolutePath());
        List<String> listIndexFiles = localSearchIndexStore.listIndexFiles("modelid1", "languagename1");
        assertEquals(1, listIndexFiles.size());
        byte[] fileContent = localSearchIndexStore.readIndexFile("modelid1", "languagename1", "file1.txt");
        assertNotNull(fileContent);
        assertEquals("mycontent", StringUtil.fromBytes(fileContent));
    }
    
    @Test
    public void writeIndexFile() {
        byte[] filecontent = StringUtil.getBytes("mycontent");
        localSearchIndexStore.addIndexFile("modelid1", "languagename1", "file1.txt", filecontent);
        byte[] fileContent = localSearchIndexStore.readIndexFile("modelid1", "languagename1", "file1.txt");
        assertNotNull(fileContent);
        assertEquals("mycontent", StringUtil.fromBytes(fileContent));
    }
}
