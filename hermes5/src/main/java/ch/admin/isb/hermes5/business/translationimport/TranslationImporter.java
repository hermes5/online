/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationimport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.util.ZipUtil;

public class TranslationImporter implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(TranslationImporter.class);

    @Inject
    ZipUtil zipUtil;

    @Inject
    TranslationImportHandlerRepository importHandlerRepository;

    public ImportResult importTranslations(String modelIdentifier, byte[] contents, List<String> languages) {
        ImportResult result = new ImportResult();
        boolean atLeastOnEntry = false;
        try {
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(contents));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                try {
                    String url = entry.getName();
                    if (entry.isDirectory()) {
                        continue;
                    } else {
                        atLeastOnEntry = true;
                        byte[] byteArray = zipUtil.readZipEntry(zis);
                        List<TranslationImportHandler> lookupImportHandler = importHandlerRepository
                                .lookupImportHandler(url);
                        for (TranslationImportHandler importHandler : lookupImportHandler) {
                            result.add(importHandler.handleImport(modelIdentifier, byteArray, languages, url));
                        }
                    }
                } catch (Exception e) {
                   result.addError(e.getClass() +" "+e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.warn("Error on unzipping",e);
            result.addError(e.getMessage());
        }
        if (!atLeastOnEntry) {
            throw new IllegalArgumentException("No Zip file or no content");
        }
        return result;
    }
}
