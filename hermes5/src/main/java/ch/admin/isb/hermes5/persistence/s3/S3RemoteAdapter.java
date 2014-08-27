/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.s3;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.Logged;
import ch.admin.isb.hermes5.util.MimeTypeUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;


@Stateless
@LocalBean
public class S3RemoteAdapter implements Serializable, S3Adapter {

    private static final long serialVersionUID = 1L;

    private AmazonS3 s3;

    @Inject
    MimeTypeUtil mimeTypeUtil;

    @Inject
    S3ServiceFactory s3ServiceFactory;

    
    @Inject
    @SystemProperty(value = "s3.bucketName")
    ConfigurationProperty bucketName;

    @PostConstruct
    public void init() {
        s3 = s3ServiceFactory.getAmazonS3();

    }

    @Override
    @Asynchronous
    @Logged
    public Future<Void> addFile(InputStream file, long size, String path) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();

            metadata.setContentLength(size);
            metadata.setContentType(mimeTypeUtil.getMimeType(path));
            s3.putObject(new PutObjectRequest(bucketName.getStringValue(), path, file, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            return new AsyncResult<Void>(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public byte[] readFile(String key) {
        try {
            S3Object s3Object = s3.getObject(bucketName.getStringValue(), key);
            if (s3Object == null) {
                return null;
            }
            byte[] file = IOUtil.readToByteArray(s3Object.getObjectContent());
            return file;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<String> deletePath(String path) {
        List<String> keys = new ArrayList<String>();
        ObjectListing listObjects = s3.listObjects(this.bucketName.getStringValue(), path);
        List<S3ObjectSummary> objectSummaries = listObjects.getObjectSummaries();
        if (! objectSummaries.isEmpty()) {
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName.getStringValue());
            for (S3ObjectSummary s3ObjectSummary : objectSummaries) {
                String key = s3ObjectSummary.getKey();
                keys.add(key);
                deleteObjectsRequest.getKeys().add(new KeyVersion(key));
            }
            s3.deleteObjects(deleteObjectsRequest);
        }
        return keys;
    }

    @Override
    public List<String> listFiles(String path) {
        ObjectListing listObjects = s3.listObjects(this.bucketName.getStringValue(), path);
        List<S3ObjectSummary> objectSummaries = listObjects.getObjectSummaries();
        List<String> files = new ArrayList<String>();
        for (S3ObjectSummary s3ObjectSummary : objectSummaries) {
            String key = s3ObjectSummary.getKey();
            files.add(key.substring(path.length() + 1));
        }
        return files;
    }

}
