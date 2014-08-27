/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.util;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipOutputBuilder   {

    private static final Logger logger = LoggerFactory.getLogger(ZipOutputBuilder.class);

    private ByteArrayOutputStream result;
    private ZipOutputStream zos;

    private boolean empty;
    
    private Set<String> files;
    
    public ZipOutputBuilder() {
        result = new ByteArrayOutputStream();
        zos = new ZipOutputStream(result);
        empty=true;
        files = new HashSet<String>();
    }

    public byte[] getResult() {
        close();
        return result.toByteArray();
    }

    public void close() {
        try {
            if(! isEmpty()){
                zos.close();
            }
        } catch (IOException e) {
            logger.warn("Unable to close ZipOutputStream", e);
        }
    }

    public void addFile(String filename, byte[] b)  {
        String adaptedFilename = replaceSpecialChars(filename);
        try {
            zos.putNextEntry(new ZipEntry(adaptedFilename));
            zos.write(b);
            empty = false;
            files.add(adaptedFilename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    
    public boolean isEmpty() {
        return empty;
    }

   
    public boolean containsFile(String path) {
        return files.contains(replaceSpecialChars(path));
    }

}
