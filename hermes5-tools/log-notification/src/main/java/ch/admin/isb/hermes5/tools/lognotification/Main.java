/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.lognotification;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Properties;

public class Main {

    private static final String REGEX = "regex";
    private static final String IGNORE_REGEX = "ignoreRegex";
    private static final String LOG_FILE = "logfile";
    private static final String LAST_RUN_FILE = "lastrunfile";
    private static final String SECRET_KEY = "secretKey";
    private static final String ACCESS_KEY = "accessKey";
    private static final String SNS_TOPIC_ARN = "snsTopicARN";
    private static final String SNS_ENDPOINT = "snsEnpoint";
    private static final String DATE_PATTERN = "datePattern";

    public static void main(String[] args) {
        if (args.length != 1) {
            usage(null, null);
        }
        Properties p = loadProperties(args[0]);
        checkProperties(p);
        new LogNotification().run(s(p.get(ACCESS_KEY)), s(p.get(SECRET_KEY)), s(p.get(LOG_FILE)), s(p.get(LAST_RUN_FILE)), s(p.get(REGEX)),
                s(p.get(IGNORE_REGEX)), s(p.get(SNS_TOPIC_ARN)), s(p.get(SNS_ENDPOINT)), new SimpleDateFormat(s(p.get(DATE_PATTERN))));

    }

    private static String s(Object object) {
        if(object == null) {
            return null;
        }
        return String.valueOf(object);
    }

    private static void checkProperties(Properties p) {
        for (String string : Arrays.asList(LOG_FILE, REGEX, SNS_TOPIC_ARN, SNS_ENDPOINT,
                IGNORE_REGEX,LAST_RUN_FILE, DATE_PATTERN)) {
            if (!p.containsKey(string)) {
                usage(null, "Missing property " + string);
            }
        }
        File file = new File(s(p.get(LOG_FILE)));
        if (!file.exists()) {
            usage(null, "Specified log file (" + file.getAbsolutePath() + ") does not exist");
        }
        try {
            new SimpleDateFormat(s(p.get(DATE_PATTERN)));
        } catch (Exception e) {
            usage(e, "Unable to parse date pattern "+s(p.get(DATE_PATTERN)));
        }

    }

    private static void usage(Exception e, String msg) {
        if (msg != null) {
            System.out.println(msg + "\n");
        }
        System.out.println("Usage: <properties.file>");
        System.out.println(" \nwhere properties file contains:\n");
        System.out.println(ACCESS_KEY + "=IAM access key (ignore for instance role authentication)");
        System.out.println(SECRET_KEY + "=IAM secret key (ignore for instance role authentication)");
        System.out.println(LOG_FILE + "=log file or directory containing to be analysed");
        System.out.println(LAST_RUN_FILE + "=tmp file to store the timestamp of the last run");
        System.out.println(REGEX + "=regex to notify on match");
        System.out.println(IGNORE_REGEX + "=regex to ignore even on match");
        System.out.println(DATE_PATTERN + "=date pattern (e.g. yyyy-MM-dd:HH:mm:ss,SSS)");
        System.out.println(SNS_TOPIC_ARN + "=SNS topic ARN for notification reporting");
        System.out.println(SNS_ENDPOINT + "=SNS endpoint (e.g. for Europe: https://sns.eu-west-1.amazonaws.com)");

        if (e != null) {
            e.printStackTrace();
        }
        System.exit(-1);
    }

    private static Properties loadProperties(String file) {
        try {
            FileInputStream f = new FileInputStream(new File(file));
            Properties p = new Properties();
            p.load(f);
            return p;
        } catch (Exception e) {
            usage(e, null);
        }
        return null;
    }

}
