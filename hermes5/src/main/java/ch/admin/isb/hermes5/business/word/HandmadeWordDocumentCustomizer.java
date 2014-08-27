/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.word;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.MimeTypeUtil;
import ch.admin.isb.hermes5.util.SystemProperty;
import ch.admin.isb.hermes5.util.ZipUtil;

@ApplicationScoped
public class HandmadeWordDocumentCustomizer {

    @Inject
    @SystemProperty(value = "docx.encoding", fallback = "UTF8")
    ConfigurationProperty encoding;

    @Inject
    @SystemProperty(value = "template_logo_bund_classpath", fallback = "Logo_BV_sw.png")
    ConfigurationProperty logoBundClasspath;
    @Inject
    @SystemProperty(value = "template_logo_snippet", fallback = "logo_snippet.xml")
    ConfigurationProperty logoSnippet;
    @Inject
    @SystemProperty(value = "template_logo_rels_snippet", fallback = "logo_rels_snippet.xml")
    ConfigurationProperty logoRelsSnippet;

    @Inject
    MimeTypeUtil mimeTypeUtil;

    private ZipUtil zipUtil = new ZipUtil();
    private final String CHARSETNAME = "UTF8";

    /**
     * Hack around the block because docx4j is slow and even slower if executed in parallel
     */
    public byte[] adjustDocumentWithUserData(InputStream is, SzenarioUserData szenarioUserData,
            HashMap<String, String> replaceMap, String keyLogo) {
        ZipInputStream zipInputStream = new ZipInputStream(is);
        ZipOutputBuilder zipOutputBuilder = new ZipOutputBuilder();
        try {
            byte[] logo = szenarioUserData.getLogo();
            String logoFilename = szenarioUserData.getLogoFilename();
            if (logo == null || logoFilename == null) {
                logo = read(logoBundClasspath.getStringValue());
                logoFilename = "logo.png";
            }
            zipOutputBuilder.addFile("word/media/" + logoFilename, logo);
            ZipEntry nextEntry = null;
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                String name = nextEntry.getName();
                byte[] readZipEntry = zipUtil.readZipEntry(zipInputStream);
                if (name.toLowerCase().endsWith(".xml")) {
                    String content = new String(readZipEntry, encoding.getStringValue());
                    for (Entry<String, String> entry : replaceMap.entrySet()) {
                        content = content.replaceAll("\\$\\{" + entry.getKey() + "\\}", entry.getValue());
                    }

                    if (content.contains(keyLogo)) {

                        String filename = new File(name).getName();
                        String path = new File(name).getParent();
                        byte[] read = new String(read(logoRelsSnippet.getStringValue()), CHARSETNAME).replace(
                                "${h5_logo_filename}", logoFilename).getBytes(CHARSETNAME);
                        zipOutputBuilder.addFile(path + "/_rels/" + filename + ".rels", read);
                        String string = new String(read(logoSnippet.getStringValue()), CHARSETNAME).replace(
                                "${h5_logo_filename}", logoFilename);
                        content = content.replace("${" + keyLogo + "}", string);
                    }

                    if (name.equals("[Content_Types].xml")) {
                        content = content.replace("</Types>", "<Override PartName=\"/word/media/" + logoFilename
                                + "\" ContentType=\"" + mimeTypeUtil.getMimeType(logoFilename) + "\" /></Types>");
                    }

                    readZipEntry = content.getBytes(encoding.getStringValue());
                }

                zipOutputBuilder.addFile(name, readZipEntry);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return zipOutputBuilder.getResult();
    }

    private byte[] read(String url) {
        InputStream logoBund = getClass().getResourceAsStream(url);
        byte[] readToByteArray = IOUtil.readToByteArray(logoBund);
        return readToByteArray;
    }
}
