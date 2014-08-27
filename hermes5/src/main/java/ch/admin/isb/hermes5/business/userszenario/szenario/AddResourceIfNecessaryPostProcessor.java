/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.szenario;

import java.io.Serializable;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilderUtils;
import ch.admin.isb.hermes5.persistence.s3.S3;

public class AddResourceIfNecessaryPostProcessor implements Serializable {

    public static final Logger logger = LoggerFactory.getLogger(AddResourceIfNecessaryPostProcessor.class);

    private static final long serialVersionUID = 1L;

    @Inject
    S3 s3;

    @Inject
    ZipOutputBuilderUtils zipOutputBuilderUtils;


    public void addImagesIfNecessary(String documentationRoot, String fileContent, ZipOutputBuilder zipBuilder,
            String lang, String modelIdentifier) {

        String imgTag = "<IMG ";
        int indexOf = fileContent.toUpperCase().indexOf(imgTag);
        if (indexOf > 0) {
            String substring = fileContent.substring(indexOf + imgTag.length());
            String href = "src=\"";
            int start = substring.indexOf(href) + href.length();
            if (start - href.length() < 0) {
                logger.warn("artifact link found but no src " + fileContent);
            } else {
                String beginWithUrl = substring.substring(start);
                int end = beginWithUrl.indexOf("\"");
                String url = beginWithUrl.substring(0, end);
                String path = this.getPath(documentationRoot, lang, url);
                if (!zipBuilder.containsFile(path)) {
                    try {
                        byte[] readFile = s3.readFile(modelIdentifier, lang, url);
                        zipOutputBuilderUtils.addIfNotYetPresent(zipBuilder, readFile, path);
                    } catch (Exception e) {
                        logger.warn("Unable to read file " + modelIdentifier + " " + lang + " " + url, e);
                    }
                }
                addImagesIfNecessary(documentationRoot, beginWithUrl, zipBuilder, lang, modelIdentifier);
            }
        }
    }

    private String getPath(String documentationRoot, String lang, String filename) {
        return documentationRoot + "/" + lang + "/" + filename;
    }
}
