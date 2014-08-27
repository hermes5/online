/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.LocalizationEngineBuilder;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.SzenarioBuilder;
import ch.admin.isb.hermes5.util.StringUtil;

public class IndexWriterWrapperTest {

    private IndexWriterWrapper indexWriterWrapper;
    private IndexWriter iwriter = mock(IndexWriter.class);
    private LocalizationEngine localizationEngine;

    @Before
    public void setUp() throws Exception {
        localizationEngine = LocalizationEngineBuilder.getLocalizationEngineDe();
        indexWriterWrapper = new IndexWriterWrapper(iwriter, localizationEngine);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddDocumentFromModelElement() throws Exception {
        indexWriterWrapper.addDocumentFromModelElement(SzenarioBuilder.rolle("projektleiter"), "some dummy text file content");
        verify(iwriter).addDocument((Iterable<? extends IndexableField>) argThat(new ArgumentMatcher<Object>() {

            @Override
            public boolean matches(Object argument) {
                org.apache.lucene.document.Document doc = (org.apache.lucene.document.Document) argument;
                assertEquals("model/de/roleid_projektleiter/presentationName", doc.get("presentationName"));
                assertEquals("rolle_projektleiter", doc.get("name"));
                assertEquals("some dummy text file content", doc.get("content").trim());
                return true;
            }
        }));
    }

    @Test
    public void testClose() throws Exception {
        indexWriterWrapper.close();
        verify(iwriter).close();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddDocumentFromDocument() throws Exception {
        Document document = new Document();
        document.setUrlDe("urlDe.docx");
        indexWriterWrapper.addDocumentFromDocument(document, StringUtil.getBytes("some dummy text file content"));
        verify(iwriter).addDocument((Iterable<? extends IndexableField>) argThat(new ArgumentMatcher<Object>() {

            @Override
            public boolean matches(Object argument) {
                org.apache.lucene.document.Document doc = (org.apache.lucene.document.Document) argument;
                assertEquals("urlDe.docx", doc.get("presentationName"));
                assertEquals("urlDe.docx", doc.get("name"));
                assertEquals("some dummy text file content", doc.get("content").trim());
                return true;
            }
        }));
    }

}
