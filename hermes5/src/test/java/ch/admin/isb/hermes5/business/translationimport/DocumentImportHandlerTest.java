/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationimport;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.persistence.db.dao.DocumentDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;


public class DocumentImportHandlerTest {
    private DocumentImportHandler DocumentImportHandler;
    
    @Before
    public void setUp() throws Exception {
        DocumentImportHandler = new DocumentImportHandler();
        DocumentImportHandler.documentDAO = mock(DocumentDAO.class);
        DocumentImportHandler.s3 = mock(S3.class);
    }

    @Test
    public void testHandleImport() {
        String url="MethodLibrary_20120828021719/Vorlagen/hermes_core_guidances_templates_resources_Anwendungshandbuch_dotx/it/Anwendungshandbuch.dotx";
        List<String> langs=new ArrayList<String>();
        langs.add("it");
        Document document=new Document();
        document.setUrlDe("hermes.core/guidances/templates/resources/Anwendungshandbuch.dotx");
        List<Document> Documents=new ArrayList<Document>(); 
        Documents.add(document);
        when(DocumentImportHandler.documentDAO.getDocuments(anyString())).thenReturn(Documents);
        DocumentImportHandler.handleImport("model_identifier", "content".getBytes(), langs, url);
        verify(DocumentImportHandler.documentDAO).getDocuments("model_identifier");
        verify(DocumentImportHandler.s3).
                addFile((InputStream)any(), anyLong(), eq("model_identifier"), eq("it"), eq("hermes.core/guidances/templates/resources/Anwendungshandbuch.dotx"));
    }
    
    @Test
    public void testHandleImportWithNewDocname() {
        String url="MethodLibrary_20120828021719/Vorlagen/hermes_core_guidances_templates_resources_Anwendungshandbuch_dotx/en/NewDocname.dotx";
        List<String> langs=new ArrayList<String>();
        langs.add("en");
        Document document=new Document();
        document.setUrlDe("hermes.core/guidances/templates/resources/Anwendungshandbuch.dotx");
        List<Document> Documents=new ArrayList<Document>(); 
        Documents.add(document);
        when(DocumentImportHandler.documentDAO.getDocuments(anyString())).thenReturn(Documents);
        DocumentImportHandler.handleImport("model_identifier", "content".getBytes(), langs, url);
        verify(DocumentImportHandler.documentDAO).getDocuments("model_identifier");
        verify(DocumentImportHandler.s3).
                addFile((InputStream)any(), anyLong(), eq("model_identifier"), eq("en"), eq("hermes.core/guidances/templates/resources/NewDocname.dotx"));
    }

}
