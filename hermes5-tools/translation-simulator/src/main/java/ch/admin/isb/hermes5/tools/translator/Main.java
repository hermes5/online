/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java -jar hermes5-translation-simulator.jar <input-zip>");
            System.exit(-1);
        }
        File zip = new File(args[0]);
        File outZip = new File(zip.getAbsoluteFile()+"_translated.zip");
        FileOutputStream out = null;
        InputStream in = null;
        try {
            in = new FileInputStream(zip);
            Translator translator = new Translator();
            byte[] content = new byte[(int) zip.length()];
            in.read(content);
            byte[] result = translator.translate(content);
            out = new FileOutputStream(outZip);
            out.write(result);

        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
    }
}
