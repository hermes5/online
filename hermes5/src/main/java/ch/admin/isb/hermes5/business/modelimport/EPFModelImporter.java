/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import static ch.admin.isb.hermes5.domain.Status.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.DateUtil;
import ch.admin.isb.hermes5.util.Logged;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;
import ch.admin.isb.hermes5.util.ZipUtil;

public class EPFModelImporter implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(EPFModelImporter.class);
    
    @Inject
    S3 s3;

    @Inject
    ZipUtil zipUtil;

    @Inject
    DateUtil dateUtil;

    @Inject
    @SystemProperty(value = "zipRootIdentifier", fallback = "hermes.core")
    ConfigurationProperty zipRootIdentifier;

    @Inject
    ModelRepository modelRepository;

    @Inject
    ModelImportHandlerRepository importHandlerRepository;

    private ImportResult extractAndUpload(String modelIdentifier, byte[] contents) {
        ImportResult result = new ImportResult();
        boolean atLeastOnEntry = false;
        try {
            int prefixToBeRemoved = determinePrefixToBeRemoved(contents);
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(contents));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                } else {
                    String url = entry.getName().substring(prefixToBeRemoved);
                    atLeastOnEntry = true;
                    byte[] byteArray = zipUtil.readZipEntry(zis);
                    List<ModelImportHandler> lookupImportHandler = importHandlerRepository.lookupImportHandler(url);
                    addFile(modelIdentifier, "de", url, byteArray);
                    for (ModelImportHandler importHandler : lookupImportHandler) {
                        result.add(importHandler.handleImport(modelIdentifier, byteArray, url));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error on import", e);
            result.addError(e.getMessage());
        }
        if (!atLeastOnEntry) {
            throw new IllegalArgumentException("No Zip file or no content");
        }

        return result;
    }

    private int determinePrefixToBeRemoved(byte[] contents) throws IOException {
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(contents));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            String url = entry.getName();
            if (url != null && url.contains(zipRootIdentifier.getStringValue())) {
                return url.indexOf(zipRootIdentifier.getStringValue());
            }
        }
        return 0;
    }

    public void addFile(String modelIdentifier, String lang, String url, byte[] byteArray)  {
          s3.addFile(new ByteArrayInputStream(byteArray), byteArray.length, modelIdentifier, lang, url);
    }

    @Logged
    public ImportResult importEPFModelFromZipFile(byte[] contents, String fileName, String title, String version) {
        String identTitle = StringUtil.replaceSpecialChars(title).replaceAll("\\s+", "");
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(dateUtil.currentDate());
        String modelIdentifier = date + "_" + identTitle;

        EPFModel model = new EPFModel();
        model.setIdentifier(modelIdentifier);
        model.setTitle(title);
        model.setVersion(version);
        model.setStatusFr(UNVOLLSTAENDIG);
        model.setStatusIt(UNVOLLSTAENDIG);
        model.setStatusEn(UNVOLLSTAENDIG);
        modelRepository.saveNewModel(model);
        ImportResult extractAndUpload = this.extractAndUpload(modelIdentifier, contents);
        modelRepository.updateStatusAndUpdateDate(modelIdentifier);
        return extractAndUpload;
    }
}
