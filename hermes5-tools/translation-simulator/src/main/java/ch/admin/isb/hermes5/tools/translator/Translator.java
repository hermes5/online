/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.translator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class Translator {

    private ZipUtil zipUtil;
    private TranslationZipOutputBuilder translationZipOutputBuilder;

    public Translator() {
        zipUtil = new ZipUtil();
        translationZipOutputBuilder = new TranslationZipOutputBuilder();
    }

    public byte[] translate(byte[] contents) {
        try {
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(contents));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String url = entry.getName();

                if (entry.isDirectory()) {
                    continue;
                } else {

                    byte[] byteArray = zipUtil.readZipEntry(zis, entry);
                    byte[] translated = translateFile(byteArray, url);
                    if (translated != null) {
                        String translatedUrl = translateUrl(url);
                        translationZipOutputBuilder.addFile(translatedUrl, translated);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        byte[] result = translationZipOutputBuilder.getResult();
        translationZipOutputBuilder.close();
        return result;
    }

    private String translateUrl(String url) {
        if (url.contains("Vorlagen")) {
            int lastIndexOf = url.lastIndexOf(".");
            String ending = url.substring(lastIndexOf);
            String[] split = url.split("/");
            String lang = split[split.length - 2];
            
            String result = url.substring(0, lastIndexOf) + "_" + lang + ending;
            System.out.println(result);
            return result;
        }
        return url;
    }

    private byte[] translateFile(byte[] byteArray, String url) {
        if (url.contains("Textelemente")) {
            String lang = url.substring(url.lastIndexOf(".") - 2, url.lastIndexOf("."));
            System.out.println(url);
            return translateWordFile(new ByteArrayInputStream(byteArray), lang);
        }
        if (url.contains("Bilder")) {
            return translateImageFile(byteArray, url);
        }
        return byteArray;
    }

    private byte[] translateImageFile(byte[] byteArray, String url) {
        String[] split = url.split("/");
        String lang = split[split.length - 2];
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArray));
            Graphics2D gO = image.createGraphics();
            gO.setColor(Color.red);
            gO.setFont(new Font("SansSerif", Font.BOLD, 45));
            gO.drawString(lang, image.getWidth() / 2 - 45, image.getHeight() / 2);
            gO.dispose();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            String format = url.substring(url.lastIndexOf(".") + 1);
            System.out.println(url + "=>" + format);
            ImageIO.write(image, format, output);
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArray;
    }

    public byte[] translateWordFile(InputStream in, String lang) {
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
            r.getCell(1).setText(
                    lang + " " + lang);
        }
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            document.write(outStream);
            return outStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
