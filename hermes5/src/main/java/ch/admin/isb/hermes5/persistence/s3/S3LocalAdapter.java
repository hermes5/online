/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.s3;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

@Stateless
@LocalBean
public class S3LocalAdapter implements Serializable, S3Adapter {

    private static final long serialVersionUID = 1L;

    @Inject
    @SystemProperty(value = "s3.local_path", fallback = "C:/Daten/LocalS3")
    ConfigurationProperty localS3Path;

    public static final Logger logger = LoggerFactory.getLogger(S3LocalAdapter.class);

    @Asynchronous
    public Future<Void> addFile(InputStream in, long size, String path) {
        String pathname = localS3Path.getStringValue() + "/" + path;
        IOUtil.writeFile(in, pathname);
        return new AsyncResult<Void>(null);
    }

    public byte[] readFile(String path) {
        return IOUtil.readFile(localS3Path.getStringValue() + "/" + path);
    }

    public List<String> deletePath(String path) {
        String pathname = localS3Path.getStringValue() + "/";
        List<File> deletedFiles = IOUtil.deleteFile(new File(pathname + path));
        List<String> result = new ArrayList<String>();
        for (File file : deletedFiles) {
            String filePath = file.getPath().replaceAll("\\\\", "/");
            String substring = filePath.substring(filePath.lastIndexOf(pathname) + pathname.length());
            result.add(substring);
        }
        return result;
    }

    @Override
    public List<String> listFiles(String path) {    
        return IOUtil.listFilesRelativeToGivenPath(localS3Path.getStringValue()+"/"+path);
    }
}
