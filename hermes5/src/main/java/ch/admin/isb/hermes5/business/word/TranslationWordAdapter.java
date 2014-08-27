/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.word;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.util.StringUtil;

public class TranslationWordAdapter implements Serializable {

    private static final long serialVersionUID = 1L;

    public byte[] write(List<TranslateableText> texts, String lang) {
        XWPFDocument document = new XWPFDocument();

        // insert text into first row
        XWPFTable table = document.createTable();
        table.setCellMargins(0, 100, 0, 100);
        XWPFTableRow rowOne = table.getRow(0);
        TranslateableText first = texts.get(0);
        rowOne.getCell(0).setText(first.getElementIdentifier() + "/ " + first.getTextIdentifier());
        rowOne.addNewTableCell().setText(getText(first, lang));
        if (isDirty(first, lang)) {
            rowOne.getCell(1).setColor("FFFF99");
        }
        // add more rows and insert text
        if (texts.size() > 1) {
            for (int c = 1; c < texts.size(); c++) {
                TranslateableText text = texts.get(c);
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(text.getElementIdentifier() + "/ " + text.getTextIdentifier());
                row.getCell(1).setText(getText(text, lang));
                if (isDirty(text, lang)) {
                    row.getCell(1).setColor("FFFF99");
                }
            }
        }

        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            document.write(outStream);
            return outStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isDirty(TranslateableText text, String lang) {
        return ("fr".equals(lang) && text.getStatusFr() != Status.FREIGEGEBEN)
                || ("it".equals(lang) && text.getStatusIt() != Status.FREIGEGEBEN)
                || ("en".equals(lang) && text.getStatusEn() != Status.FREIGEGEBEN);
    }

    private String getText(TranslateableText text, String lang) {
        if ("de".equals(lang)) {
            return text.getTextDe();
        }
        if ("fr".equals(lang)) {
            return StringUtil.isNotBlank(text.getTextFr()) ? text.getTextFr() : text.getTextDe();
        }
        if ("it".equals(lang)) {
            return StringUtil.isNotBlank(text.getTextIt()) ? text.getTextIt() : text.getTextDe();
        }
        if ("en".equals(lang)) {
            return StringUtil.isNotBlank(text.getTextEn()) ? text.getTextEn() : text.getTextDe();
        }
        throw new IllegalStateException("Unknown Language: " + lang);
    }

    public List<TranslateableText> read(InputStream in, String docLang) {
        List<TranslateableText> result = new ArrayList<TranslateableText>();
        XWPFDocument document = null;
        try {
            document = new XWPFDocument(in);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        List<XWPFTableRow> rows = document.getTables().get(0).getRows();
        for (XWPFTableRow r : rows) {
            String[] identifiers = r.getCell(0).getText().split("\\/");
            if (identifiers.length != 2) {
                throw new RuntimeException("Unable to parse identifier " + r.getCell(0).getText());
            }
            TranslateableText text = new TranslateableText("", "", "", "", "", identifiers[0].trim(),
                    identifiers[1].trim(), "");
            if (docLang.equals("fr")) {
                text.setTextFr(r.getCell(1).getText());
            }
            if (docLang.equals("it")) {
                text.setTextIt(r.getCell(1).getText());
            }
            if (docLang.equals("en")) {
                text.setTextEn(r.getCell(1).getText());
            }
            result.add(text);
        }
        return result;
    }

}
