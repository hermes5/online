/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.word;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.TranslateableText;

public class TranslationWordAdapterTest {

    private TranslationWordAdapter wa;
    private List<TranslateableText> texts = new ArrayList<TranslateableText>();

    @Before
    public void init() {
        texts.add(new TranslateableText(
                "modelIdentifier",
                "type",
                "rootid1",
                "fileType",
                "fileName",
                "elementIdentifier1",
                "presentationName",
                "textDe1textDe1textDe1text De1textDe1textDe1textDe1text De1textDe1textDe1textDe1text De1textDe1text De1textDe1textDe1textDe1textDe1textDe1text De1textDe1textDe1text De1textDe1text De1textDe1textDe1textDe1textDe1textDe1text De1textDe1text De1textDe1text De1text De1textDe1textDe1"));
        texts.add(new TranslateableText("modelIdentifier","type", "rootid2",  "fileType", "fileName", "elementIdentifier2",
                "mainDescription", "textDe2"));
        texts.add(new TranslateableText("modelIdentifier", "type", "rootid3", "fileType", "fileName", "elementIdentifier3",
                "briefDescription", "textDe3"));
        wa = new TranslationWordAdapter();
    }

    @Test
    public void testWriteAndRead() throws FileNotFoundException {
        byte[] write = wa.write(texts, "de");

        List<TranslateableText> texts = wa.read(new ByteArrayInputStream(write),"it");

        Assert.assertEquals("presentationName", texts.get(0).getTextIdentifier());
        Assert.assertEquals("elementIdentifier1", texts.get(0).getElementIdentifier());
        Assert.assertEquals(
                "textDe1textDe1textDe1text De1textDe1textDe1textDe1text De1textDe1textDe1textDe1text De1textDe1text De1textDe1textDe1textDe1textDe1textDe1text De1textDe1textDe1text De1textDe1text De1textDe1textDe1textDe1textDe1textDe1text De1textDe1text De1textDe1text De1text De1textDe1textDe1",
                texts.get(0).getTextIt());

        Assert.assertEquals("mainDescription", texts.get(1).getTextIdentifier());
        Assert.assertEquals("elementIdentifier2", texts.get(1).getElementIdentifier());
        Assert.assertEquals("textDe2", texts.get(1).getTextIt());

        Assert.assertEquals("briefDescription", texts.get(2).getTextIdentifier());
        Assert.assertEquals("elementIdentifier3", texts.get(2).getElementIdentifier());
        Assert.assertEquals("textDe3", texts.get(2).getTextIt());
    }
    

    

}
