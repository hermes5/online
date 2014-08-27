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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.MimeTypeUtil;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3RemoteAdapterTest {

    private static final byte[] BYTES = "bytes".getBytes();
    private S3RemoteAdapter s3RemoteAdapter;
    private AmazonS3 s3Mock;
    MimeTypeUtil mimeTypeUtil = new MimeTypeUtil();

    @Before
    public void setUp() throws Exception {
        s3RemoteAdapter = new S3RemoteAdapter();
        s3RemoteAdapter.mimeTypeUtil = new MimeTypeUtil();
        s3RemoteAdapter.s3ServiceFactory = mock(S3ServiceFactory.class);
        s3Mock = mock(AmazonS3.class);
        when(s3RemoteAdapter.s3ServiceFactory.getAmazonS3()).thenReturn(s3Mock);
        s3RemoteAdapter.bucketName = Hardcoded.configuration("bucket");
        s3RemoteAdapter.init();
    }

    @Test
    public void testAddFileDocx() {
        addFileHelper("path/to/file.docx");
    }

    @Test
    public void testAddFileDoc() {
        addFileHelper("path/to/file.doc");
    }

    @Test
    public void testAddFileDot() {
        addFileHelper("path/to/file.dot");
    }

    @Test
    public void testAddFileDotx() {
        addFileHelper("path/to/file.dotx");
    }

    @Test
    public void testAddFileXlsx() {
        addFileHelper("path/to/file.xlsx");
    }

    @Test
    public void testAddFileXls() {
        addFileHelper("path/to/file.xls");
    }

    @Test
    public void testAddFilePpt() {
        addFileHelper("path/to/file.ppt");
    }

    @Test
    public void testAddFilePptx() {
        addFileHelper("path/to/file.pptx");
    }

    @Test
    public void testAddFilePdf() {
        addFileHelper("path/to/file.pdf");
    }

    @Test
    public void testAddFilePng() {
        addFileHelper("path/to/file.png");
    }

    @Test
    public void testAddFileJpg() {
        addFileHelper("path/to/file.jpg");
    }

    @Test
    public void testAddFileJPEG() {
        addFileHelper("path/to/file.JPEG");
    }

    private void addFileHelper(final String file) {
        final String contentType = mimeTypeUtil.getMimeType(file);
         s3RemoteAdapter.addFile(new ByteArrayInputStream(BYTES), BYTES.length, file);
        verify(s3Mock).putObject(argThat(new ArgumentMatcher<PutObjectRequest>() {

            @Override
            public boolean matches(Object argument) {
                PutObjectRequest o = (PutObjectRequest) argument;
                assertEquals("bucket", o.getBucketName());
                assertEquals(file, o.getKey());
                assertEquals(contentType, o.getMetadata().getContentType());
                return true;
            }
        }));
    }

    @Test
    public void testReadFile() {
        S3Object value = new S3Object();
        value.setObjectContent(new ByteArrayInputStream(BYTES));
        when(s3Mock.getObject("bucket", "path/to/file")).thenReturn(value);
        byte[] readFile = s3RemoteAdapter.readFile("path/to/file");
        assertTrue(new String(BYTES) + " <> " + new String(readFile), Arrays.equals(BYTES, readFile));
    }

    @Test
    public void testDeletePath() {
        registerObjectListing();
        List<String> deletePath = s3RemoteAdapter.deletePath("path");
        assertTrue(deletePath.contains("path/1"));
        assertTrue(deletePath.contains("path/2"));
        verify(s3Mock).deleteObjects(argThat(new ArgumentMatcher<DeleteObjectsRequest>() {

            @Override
            public boolean matches(Object argument) {
                DeleteObjectsRequest dor = (DeleteObjectsRequest) argument;
                assertEquals("path/1", dor.getKeys().get(0).getKey());
                assertEquals("path/2", dor.getKeys().get(1).getKey());
                assertEquals("bucket", dor.getBucketName());
                return true;
            }
        }));

    }

    @Test
    public void testDeletePathNotCalledWhenEmpty() {
        registerEmptyObjectListing();
        List<String> deletePath = s3RemoteAdapter.deletePath("path");
        verify(s3Mock, never()).deleteObjects(any(DeleteObjectsRequest.class));
        assertTrue(deletePath.isEmpty());
    }

    private void registerObjectListing() {
        ObjectListing ol = new ObjectListing();
        S3ObjectSummary s3ObjectSummary1 = new S3ObjectSummary();
        s3ObjectSummary1.setKey("path/1");
        ol.getObjectSummaries().add(s3ObjectSummary1);
        S3ObjectSummary s3ObjectSummary2 = new S3ObjectSummary();
        s3ObjectSummary2.setKey("path/2");
        ol.getObjectSummaries().add(s3ObjectSummary2);
        when(s3Mock.listObjects("bucket", "path")).thenReturn(ol);
    }

    private void registerEmptyObjectListing() {
        ObjectListing ol = new ObjectListing();
        when(s3Mock.listObjects("bucket", "path")).thenReturn(ol);
    }

    
    @Test
    public void testListFiles() {
        registerObjectListing();
        List<String> listFiles = s3RemoteAdapter.listFiles("path");
        assertEquals(2, listFiles.size());
        assertTrue("" + listFiles, listFiles.contains("1"));
        assertTrue("" + listFiles, listFiles.contains("2"));
    }

}
