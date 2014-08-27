/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.common;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@ApplicationScoped
public class ImageUrlUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    @SystemProperty(value = "s3.publicBucketUrl")
    ConfigurationProperty s3PublicBucketUrl;

    public String adaptImageUrls(String text, String modelIdentifier, String lang) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("src=\"", "src=\"" + s3PublicBucketUrl.getStringValue() + "/" + modelIdentifier + "/"
                + lang + "/");
    }
}
