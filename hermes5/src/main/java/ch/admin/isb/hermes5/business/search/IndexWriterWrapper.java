/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.search;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Document;

public class IndexWriterWrapper {


    private final IndexWriter iwriter;
    private final LocalizationEngine localizationEngine;

    public IndexWriterWrapper(IndexWriter iwriter, LocalizationEngine localizationEngine) {
        this.iwriter = iwriter;
        this.localizationEngine = localizationEngine;
    }

    
    public LocalizationEngine getLocalizationEngine() {
        return localizationEngine;
    }

    public void addDocumentFromModelElement(AbstractMethodenElement abstractMethodenElement, String htmlContent) {
        try {

            byte[] bytes = htmlContent.getBytes("UTF8");
            String content = parse(bytes);
            String presentationName = localizationEngine.localize(abstractMethodenElement.getPresentationName());
            String name = abstractMethodenElement.getName();
            String type = SearchResultType.PAGE.toString();
            iwriter.addDocument(buildDocument(content, presentationName, name, type));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String parse(byte[] bytes) throws IOException, SAXException, TikaException {
        ContentHandler contenthandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        Parser parser = new AutoDetectParser();
        parser.parse(new ByteArrayInputStream(bytes), contenthandler, metadata, new ParseContext());
        String content = contenthandler.toString();
        return content;
    }

    public void close() {
        try {
            iwriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDocumentFromDocument(Document document, byte[] readFile) {
        try {
            String content = parse(readFile);
            String presentationName = document.getName(localizationEngine.getLanguage());
            String name = document.getUrl(localizationEngine.getLanguage());
            String type = SearchResultType.fromFilename(presentationName).toString();
            iwriter.addDocument(buildDocument(content, presentationName, name, type));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private org.apache.lucene.document.Document buildDocument(String content, String presentationName, String name,
            String type) {
        org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
        Field presentationNameField = new Field("presentationName", presentationName, TextField.TYPE_STORED);
        doc.add(presentationNameField);
        doc.add(new Field("content", content, TextField.TYPE_STORED));
        doc.add(new Field("name", name, StringField.TYPE_STORED));
        doc.add(new Field("type", type, StringField.TYPE_STORED));
        return doc;
    }

}
