/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.inject.Inject;

import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.ZipUtil;

public class UserSzenarioPublisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    ZipUtil zipUtil;

    @Inject
    S3 s3;

    public String publishOnline(byte[] zipFile, String mainLanguage) {
        return publishOnline(zipFile, mainLanguage, generateUserszenarioId());
    }

    public String publishOnline(byte[] zipFile, String mainLanguage, String id) {
        List<Future<Void>> futures = new ArrayList<Future<Void>>();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipFile));
        ZipEntry nextEntry;
        try {
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                String url = nextEntry.getName();
                byte[] byteArray = zipUtil.readZipEntry(zipInputStream);
                futures.add(s3.addUserszenarioFile(id, url, byteArray));
            }
            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return S3.USERSZENARIO + "/" + id + "/start_" + mainLanguage + ".html";
    }

    private String generateUserszenarioId() {
        return UUID.randomUUID().toString();
    }
}
