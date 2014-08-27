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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.userszenario.SzenarioDownloadWorkflow;
import ch.admin.isb.hermes5.business.util.FutureUtil;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.PerformanceLogged;
import ch.admin.isb.hermes5.util.ZipUtil;

public class OnlinePublikationPublishSampleSzenarienWorkflow {

    @Inject
    ZipUtil zipUtil;

    @Inject
    S3 s3;

    @Inject
    SzenarioDownloadWorkflow szenarioDownloadWorkflow;

    @Inject
    FutureUtil futureUtil;

    @PerformanceLogged
    public void
            publishSampleSzenarios(final String modelIdentifier, List<String> langs, PublishContainer hermesWebsite)
                    throws InterruptedException, ExecutionException {

        List<Future<Void>> futures = new ArrayList<Future<Void>>();
        for (Szenario szenario : hermesWebsite.getSzenarien()) {
            byte[] generateSampleSzenarioZip = szenarioDownloadWorkflow.generateSampleSzenarioZip(modelIdentifier,
                    szenario, langs);
            futures.addAll(uploadSampleSzenario(modelIdentifier, szenario.getName(), generateSampleSzenarioZip));
        }
        futureUtil.waitForFuturesToComplete(futures);
    }

    private List<Future<Void>> uploadSampleSzenario(String modelIdentifier, String szenarioName, byte[] result) {
        List<Future<Void>> futures = new ArrayList<Future<Void>>();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(result));
        ZipEntry nextEntry;
        try {
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                String url = szenarioName + "/" + nextEntry.getName();
                byte[] byteArray = zipUtil.readZipEntry(zipInputStream);
                futures.add(s3.addSampleSzenarioFile(modelIdentifier, url, byteArray));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return futures;
    }

}
