/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import static junit.framework.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.DateUtil;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ZipUtil;

public class EPFModelImporterTest {

    private static final byte[] CONTENT1 = "content_file1".getBytes();
    private EPFModelImporter epfModelImporter;
    private Date now = new GregorianCalendar(2012, 7, 15, 2, 3, 4).getTime();

    @Before
    public void setUp() {
        epfModelImporter = new EPFModelImporter();
        epfModelImporter.modelRepository = mock(ModelRepository.class);
        epfModelImporter.zipUtil = mock(ZipUtil.class);
        epfModelImporter.dateUtil = mock(DateUtil.class);
        when(epfModelImporter.dateUtil.currentDate()).thenReturn(now);
        epfModelImporter.s3 = mock(S3.class);
        epfModelImporter.importHandlerRepository = mock(ModelImportHandlerRepository.class);
        Hardcoded.enableDefaults(epfModelImporter);
    }

    @Test
    public void testStoreZipFile() throws IOException {
        when(epfModelImporter.importHandlerRepository.lookupImportHandler(anyString())).thenReturn(
                new ArrayList<ModelImportHandler>());
        when(epfModelImporter.zipUtil.readZipEntry(any(ZipInputStream.class))).thenReturn(
                "content".getBytes());
        ByteArrayOutputStream out = outputStreamWithZipTwoEntry();
        epfModelImporter.importEPFModelFromZipFile(out.toByteArray(), "fileName", "title ä", "version");
        verify(epfModelImporter.s3, times(2)).addFile(any(InputStream.class), anyLong(), anyString(),
                anyString(), anyString());
        
        verify(epfModelImporter.modelRepository).saveNewModel(argThat(new ArgumentMatcher<EPFModel>() {

            @Override
            public boolean matches(Object argument) {
                EPFModel model = (EPFModel) argument;
                assertEquals("title ä", model.getTitle());
                assertEquals("20120815020304_titleae", model.getIdentifier());
                return true;
            }
        }));
    }

    private ByteArrayOutputStream outputStreamWithZipTwoEntry() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(out);
        zipOutputStream.putNextEntry(new ZipEntry("file1"));
        zipOutputStream.write(CONTENT1);
        zipOutputStream.putNextEntry(new ZipEntry("file2"));
        zipOutputStream.write("content_file2".getBytes());
        zipOutputStream.close();
        return out;
    }

    @Test
    public void testStoreZipFileWithImportHandler() throws IOException {
        ModelImportHandler importHandler = mock(ModelImportHandler.class);
        when(importHandler.handleImport(anyString(), (byte[]) any(), anyString())).thenReturn(new ImportResult());
        when(epfModelImporter.importHandlerRepository.lookupImportHandler("file1")).thenReturn(
                Arrays.asList(importHandler));
        when(epfModelImporter.importHandlerRepository.lookupImportHandler("file2")).thenReturn(
                new ArrayList<ModelImportHandler>());
        byte[] CONTENT = "content".getBytes();
        when(epfModelImporter.zipUtil.readZipEntry(any(ZipInputStream.class))).thenReturn(CONTENT);
        ByteArrayOutputStream out = outputStreamWithZipTwoEntry();
        epfModelImporter.importEPFModelFromZipFile(out.toByteArray(), "fileName", "title", "version");
        verify(epfModelImporter.s3, times(2)).addFile(any(InputStream.class), anyLong(), anyString(),
                anyString(), anyString());
        verify(importHandler).handleImport(eq("20120815020304_title"), eq(CONTENT), eq("file1"));

    }

}
