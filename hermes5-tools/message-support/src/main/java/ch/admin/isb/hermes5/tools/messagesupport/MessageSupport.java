/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.messagesupport;

import java.util.Map;
import java.util.Map.Entry;

public class MessageSupport {

    private FileRead fileRead = new FileRead();

    public void run(String lang, String directory) {
        String sourceFile = directory + "/messages_de.properties";
        String targetFile = directory + "/messages_" + lang + ".properties";

        Map<String, String> source = fileRead.readFile(sourceFile);
        Map<String, String> target = fileRead.readFile(targetFile);

        System.out.println("Missing Keys:");
        for (Entry<String, String> entry : source.entrySet()) {
            String key = entry.getKey();
            if (!target.containsKey(key) || target.get(key) == null || target.get(key).trim().isEmpty()) {
                System.out.println(key +"="+entry.getValue());
            }
        }

    }
}