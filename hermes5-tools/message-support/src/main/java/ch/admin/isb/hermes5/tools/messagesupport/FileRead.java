/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.messagesupport;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileRead {

    
    public Map<String, String> readFile(String filename) {
        DataInputStream in = null;
        try {
            Map<String,String> result = new LinkedHashMap<String, String>();
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(filename);
            // Get the object of DataInputStream
            in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            // Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                if(! strLine.trim().isEmpty() && strLine.contains("=")) {
                    String[] split = strLine.split("=", 2);
                    String key = split[0];
                    if(result.containsKey(key)) {
                        System.err.println("Duplicate Key: "+ key);
                    } else {
                        result.put(key, split[1]);
                    }
                }
            }
            // Close the input stream
            in.close();
            return result;
        } catch (Exception e) {// Catch exception if any
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    
}