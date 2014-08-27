/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.zip.ZipInputStream;

public class ZipUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public byte[] readZipEntry(ZipInputStream zis) {
        try {
            byte[] buffer = new byte[2048];

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out, buffer.length);

            int size;
            while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, size);
            }
            bos.flush();
            bos.close();
            byte[] byteArray = out.toByteArray();
            return byteArray;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
