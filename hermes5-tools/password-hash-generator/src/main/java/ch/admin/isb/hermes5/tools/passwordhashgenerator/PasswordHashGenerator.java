/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.passwordhashgenerator;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class PasswordHashGenerator {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Required argument: password");
            return;
        }
        String password = args[0];
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(password.getBytes());
        BASE64Encoder base64Encoder = new BASE64Encoder();
        System.out.println("password hash: " + base64Encoder.encode(digest));
    }

}
