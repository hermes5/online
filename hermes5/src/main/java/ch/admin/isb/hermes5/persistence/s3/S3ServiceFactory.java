/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.s3;

import java.io.Serializable;

import javax.inject.Inject;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3ServiceFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    @SystemProperty(value = "s3.accessKey", fallback = "")
    ConfigurationProperty accessKey;
    @Inject
    @SystemProperty(value = "s3.secretKey", fallback = "")
    ConfigurationProperty secretKey;

    public AmazonS3 getAmazonS3() {
        return "".equals(accessKey.getStringValue()) ? new AmazonS3Client() : new AmazonS3Client(
                new BasicAWSCredentials(accessKey.getStringValue(), secretKey.getStringValue()));

    }

}
