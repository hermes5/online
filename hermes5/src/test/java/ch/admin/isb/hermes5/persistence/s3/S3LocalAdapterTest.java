/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.s3;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.StringUtil;

public class S3LocalAdapterTest {

    private static final String TARGET_TMP_S3 = "target/tmp/s3";
    private S3LocalAdapter s3LocalAdapter;

    @Before
    public void setUp() throws Exception {
        s3LocalAdapter = new S3LocalAdapter();
        s3LocalAdapter.localS3Path = Hardcoded.configuration(TARGET_TMP_S3);
        IOUtil.deleteFile(new File(TARGET_TMP_S3));
    }
    

    @Test
    public void testAddFile() {
        byte[] bytes = StringUtil.getBytes("simple file");
        s3LocalAdapter.addFile(new ByteArrayInputStream(bytes), bytes.length, "test/file.txt");
        assertTrue(new File("target/tmp/s3/test/file.txt").exists());

    }

    @Test
    public void testReadFile() {
        byte[] bytes = StringUtil.getBytes("simple file");
        String path = "test/file.txt";
        s3LocalAdapter.addFile(new ByteArrayInputStream(bytes), bytes.length, path);
        byte[] readFile = s3LocalAdapter.readFile(path);
        assertEquals("simple file", StringUtil.fromBytes(readFile));

    }

    @Test
    public void testDeletePath() {
        byte[] bytes = StringUtil.getBytes("simple file");
        s3LocalAdapter.addFile(new ByteArrayInputStream(bytes), bytes.length, "test1/file1.txt");
        s3LocalAdapter.addFile(new ByteArrayInputStream(bytes), bytes.length, "test1/file2.txt");
        s3LocalAdapter.addFile(new ByteArrayInputStream(bytes), bytes.length, "test2/file1.txt");
        List<String> deletePath = s3LocalAdapter.deletePath("test1");
        assertEquals(""+deletePath, 2, deletePath.size());
        assertTrue(""+deletePath, deletePath.contains("test1/file1.txt"));
        assertTrue(""+deletePath, deletePath.contains("test1/file2.txt"));
    }

}
