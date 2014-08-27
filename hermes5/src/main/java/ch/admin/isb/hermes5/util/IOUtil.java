/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);
    
    public static byte[] readToByteArray(InputStream inputStream) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            return buffer.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.warn("unable to close ", e);
            }
        }
    }

    public static List<File> deleteFile(File f) {
        List<File> result = new ArrayList<File>();
    
        boolean directory = f.isDirectory();
        if (directory) {
            for (File c : f.listFiles()) {
                result.addAll(deleteFile(c));
    
            }
        }
        if (!f.delete()) {
            logger.error("Failed to delete file: " + f);
        } else if (!directory) {
            result.add(f);
        }
        return result;
    }

    public static void writeFile(InputStream in, String pathname) {
        FileOutputStream out = null;
        try {
            File file = new File(pathname);
            if (file.exists()) {
                deleteFile(file);
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
    
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static byte[] readFile(String pathname) {
        try {
            File f = new File(pathname);
            if (!f.exists()) {
                return null;
            }
            return readToByteArray(new FileInputStream(f));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    public static List<String> listFilesRelativeToGivenPath(String path) {
        List<String> result = new ArrayList<String>();
        File[] listFiles = new File(path).listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isFile()) {
                    String filePath = file.getPath().replaceAll("\\\\", "/"); // only necessary on windows
                    String substring = filePath.substring(filePath.lastIndexOf(path) + path.length() + 1);
                    result.add(substring);
                }
            }
        }
        return result;
    }

}
