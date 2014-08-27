/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.word;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.MimeTypeUtil;
import ch.admin.isb.hermes5.util.ResourceUtils;
import ch.admin.isb.hermes5.util.StringUtil;

@Ignore
public class Docx4jWordDocumentCustomizerTest {

    private static final Logger logger = LoggerFactory.getLogger(Docx4jWordDocumentCustomizerTest.class);

    private static final String WORD_FILE = "Arbeitsauftrag.docx";
    private static final String HANS = "Hans";
    private static final String _9867 = "9867";
    private static final String HERMES5 = "Hermes5";
    private static final String TRUDI = "Trudi";
    private static final String FIRMA_1 = "Firma1";
    private static final String FIRMA_2 = "Firma2";
    private WordDocumentCustomizer wordDocumentCustomizer;

    @Before
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setUp() throws Exception {
        wordDocumentCustomizer = new WordDocumentCustomizer();
        wordDocumentCustomizer.docx4jWordDocumentCustomizer = new Docx4jWordDocumentCustomizer();
        wordDocumentCustomizer.handmadeWordDocumentCustomizer = new HandmadeWordDocumentCustomizer();
        wordDocumentCustomizer.handmadeWordDocumentCustomizer.mimeTypeUtil = new MimeTypeUtil();
        Hardcoded.enableDefaults(wordDocumentCustomizer, wordDocumentCustomizer.docx4jWordDocumentCustomizer,
                wordDocumentCustomizer.handmadeWordDocumentCustomizer);
        wordDocumentCustomizer.cacheContainer = mock(CacheContainer.class);
        Cache cache = mock(Cache.class);
        when(cache.containsKey(any())).thenReturn(false);
        when(wordDocumentCustomizer.cacheContainer.getCache()).thenReturn(cache);
        wordDocumentCustomizer.init();

    }

    @Test
    public void testWithEmptyValuesDocx() throws IOException {
        byte[] result = adjustDocumentWithUserData("testWithEmptyValuesDocx.docx",
                ResourceUtils.loadResource(getClass().getResourceAsStream(WORD_FILE)), new SzenarioUserData());
        assertNotNull(result);
        Map<String, String> map = getTableMap(result);
        assertEquals("", map.get("Projektleiter"));
        assertEquals("", map.get("Auftraggeber"));
        assertEquals("", map.get("Projektnummer"));
        assertEquals("", map.get("Projektname"));
    }

    @Test
    public void testWithLogoValuesDocx() throws IOException {
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setLogoFilename("logo.jpg");
        szenarioUserData.setLogo(ResourceUtils.loadResource(getClass().getResourceAsStream("logo.jpg")));
        byte[] result = adjustDocumentWithUserData("testWithLogoValuesDocx.docx",
                ResourceUtils.loadResource(getClass().getResourceAsStream(WORD_FILE)), szenarioUserData);
        assertNotNull(result);
        Map<String, String> map = getTableMap(result);
        assertEquals("", map.get("Projektleiter"));
        assertEquals("", map.get("Auftraggeber"));
        assertEquals("", map.get("Projektnummer"));
        assertEquals("", map.get("Projektname"));
    }

    @Test
    public void testWithValuesDocx() throws Exception {
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setAuftraggeber(TRUDI);
        szenarioUserData.setProjektleiter(HANS);
        szenarioUserData.setProjektname(HERMES5);
        szenarioUserData.setProjektnummer(_9867);
        szenarioUserData.setFirma1(FIRMA_1);
        szenarioUserData.setFirma2(FIRMA_2);
        szenarioUserData.setLogo(ResourceUtils.loadResource(getClass().getResourceAsStream("logo.jpg")));
        byte[] result = adjustDocumentWithUserData("testWithValuesDocx.docx",
                ResourceUtils.loadResource(getClass().getResourceAsStream(WORD_FILE)), szenarioUserData);
        assertNotNull(result);
        Map<String, String> map = getTableMap(result);
        assertEquals(HANS, map.get("Projektleiter"));
        assertEquals(_9867, map.get("Projektnummer"));
        assertEquals(HERMES5, map.get("Projektname"));
        assertEquals(TRUDI, map.get("Auftraggeber"));
        assertHeaderContains(result, FIRMA_1, FIRMA_2);
        FileOutputStream fileOutputStream = new FileOutputStream("target/new.docx");
        fileOutputStream.write(result);
        fileOutputStream.close();
    }

    @Test
    public void testWithValuesAndLogo() throws Exception {
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setAuftraggeber(TRUDI);
        szenarioUserData.setProjektleiter(HANS);
        szenarioUserData.setProjektname(HERMES5);
        szenarioUserData.setProjektnummer(_9867);
        szenarioUserData.setFirma1(FIRMA_1);
        szenarioUserData.setFirma2(FIRMA_2);
        szenarioUserData.setLogoFilename("logo.jpg");
        szenarioUserData.setLogo(ResourceUtils.loadResource(getClass().getResourceAsStream("logo.jpg")));
        byte[] result = adjustDocumentWithUserData("testWithValuesAndLogo.docx",
                ResourceUtils.loadResource(getClass().getResourceAsStream(WORD_FILE)), szenarioUserData);
        assertNotNull(result);
        Map<String, String> map = getTableMap(result);
        assertEquals(HANS, map.get("Projektleiter"));
        assertEquals(_9867, map.get("Projektnummer"));
        assertEquals(HERMES5, map.get("Projektname"));
        assertEquals(TRUDI, map.get("Auftraggeber"));
        assertHeaderContains(result, FIRMA_1, FIRMA_2);
        FileOutputStream fileOutputStream = new FileOutputStream("target/new.docx");
        fileOutputStream.write(result);
        fileOutputStream.close();
    }

    @Test
    public void testWithValuesAndLogo100times() throws Exception {
        long currentTimeMillis2 = System.currentTimeMillis();
        List<String> results = new ArrayList<String>();
        byte[] loadResource = ResourceUtils.loadResource(getClass().getResourceAsStream(WORD_FILE));
        byte[] logo = ResourceUtils.loadResource(getClass().getResourceAsStream("logo.jpg"));
        SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setAuftraggeber(TRUDI);
        szenarioUserData.setProjektleiter(HANS);
        szenarioUserData.setProjektname(HERMES5);
        szenarioUserData.setProjektnummer(_9867);
        szenarioUserData.setFirma1(FIRMA_1);
        szenarioUserData.setFirma2(FIRMA_2);
        szenarioUserData.setLogoFilename("logo.jpg");
        szenarioUserData.setLogo(logo);
        for (int i = 0; i < 100; i++) {
            long currentTimeMillis = System.currentTimeMillis();
            byte[] result = adjustDocumentWithUserData("testWithValuesAndLogo.docx", loadResource, szenarioUserData);
            assertNotNull(result);
            results.add("Single " + i + " " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
        }
        logger.info("Total sequential: " + (System.currentTimeMillis() - currentTimeMillis2));
    }

    @Test
    public void testWithValuesAndLogo100timesParallel() throws Exception {
        long currentTimeMillis2 = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final byte[] loadResource = ResourceUtils.loadResource(getClass().getResourceAsStream(WORD_FILE));
        final byte[] logo = ResourceUtils.loadResource(getClass().getResourceAsStream("logo.jpg"));
        final SzenarioUserData szenarioUserData = new SzenarioUserData();
        szenarioUserData.setAuftraggeber(TRUDI);
        szenarioUserData.setProjektleiter(HANS);
        szenarioUserData.setProjektname(HERMES5);
        szenarioUserData.setProjektnummer(_9867);
        szenarioUserData.setFirma1(FIRMA_1);
        szenarioUserData.setFirma2(FIRMA_2);
        szenarioUserData.setLogoFilename("logo.jpg");
        szenarioUserData.setLogo(logo);
        Collection<Callable<String>> callables = new ArrayList<Callable<String>>();
        for (int i = 0; i < 100; i++) {
            final int j = i;
            final long currentTimeMillis = System.currentTimeMillis();
            callables.add(new Callable<String>() {

                @Override
                public String call() {
                    byte[] result = adjustDocumentWithUserData("testWithValuesAndLogo.docx", loadResource,
                            szenarioUserData);
                    assertNotNull(result);
                    return "Single " + j + " " + (System.currentTimeMillis() - currentTimeMillis) + "ms";
                }
            });
        }
        List<Future<String>> invokeAll = executorService.invokeAll(callables);

        for (Future<String> future : invokeAll) {
            future.get();
        }
        logger.info("Total parallel: " + (System.currentTimeMillis() - currentTimeMillis2));
    }

    private byte[] adjustDocumentWithUserData(String url, byte[] wordFile, SzenarioUserData szenarioUserData) {
        byte[] adjustDocumentWithUserData = this.wordDocumentCustomizer.adjustDocumentWithUserData(url, wordFile,
                szenarioUserData);
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream("target/worddocumentcustomizertest" + StringUtil.getLinkName(url));
            fs.write(adjustDocumentWithUserData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                }
            }
        }
        return adjustDocumentWithUserData;
    }

    public void assertHeaderContains(byte[] doc, String... strings) throws Exception {
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new ByteArrayInputStream(doc));

        Collection<Part> values = wordMLPackage.getParts().getParts().values();
        StringBuilder sb = new StringBuilder();
        for (Part part : values) {
            if (part instanceof HeaderPart) {
                sb.append(XmlUtils.marshaltoString(((HeaderPart) part).getJaxbElement(), true, true));
            }
        }
        String content = sb.toString();
        for (String s : strings) {
            assertTrue(s, content.contains(s));
        }
    }

    private Map<String, String> getTableMap(byte[] result) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(result));
        List<XWPFTable> tables = new ArrayList<XWPFTable>();
        tables.addAll(document.getTables());
        List<XWPFHeader> headers = document.getHeaderList();
        for (XWPFHeader header : headers) {
            tables.addAll(header.getTables());
        }
        for (XWPFTable xwpfTable : tables) {
            List<XWPFTableRow> rows = xwpfTable.getRows();
            for (XWPFTableRow xwpfTableRow : rows) {
                List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
                if (tableCells.size() == 2) {
                    map.put(tableCells.get(0).getText(), tableCells.get(1).getText());
                }
                if (tableCells.size() == 1) {
                    map.put(tableCells.get(0).getText(), "");
                }
            }
        }

        return map;
    }

}
