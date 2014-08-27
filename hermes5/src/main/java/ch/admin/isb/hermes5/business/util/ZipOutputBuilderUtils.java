/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.util;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.util.ResourceUtils;

@ApplicationScoped
public class ZipOutputBuilderUtils {

    private static final Logger logger = LoggerFactory.getLogger(ZipOutputBuilderUtils.class);

    public void addIfNotYetPresent(ZipOutputBuilder zipBuilder, byte[] fileContent, String path) {
        if (fileContent != null) {
            if (!zipBuilder.containsFile(path)) {
                zipBuilder.addFile(path, fileContent);
            } else if (logger.isDebugEnabled()) {
                logger.debug(path + " already in zip file");
            }
        }
    }

    public void addResource(Object resourceBase, ZipOutputBuilder zipBuilder, String source, String target) {
        InputStream is = null;
        try {
            is = resourceBase.getClass().getResourceAsStream(source);
            byte[] bytes = ResourceUtils.loadResource(is);
            zipBuilder.addFile(target, bytes);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.warn("unable to close " + source + " stream");
                }
            }
        }
    }

}
