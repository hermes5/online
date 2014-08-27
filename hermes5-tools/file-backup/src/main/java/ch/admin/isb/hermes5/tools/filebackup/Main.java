/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.filebackup;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

public class Main {

    private static final String TARGET_PREFIX = "targetPrefix";
    private static final String RETENTION_PERIOD = "retentionPeriod";
    private static final String SOURCE = "source";
    private static final String SECRET_KEY = "secretKey";
    private static final String ACCESS_KEY = "accessKey";
    private static final String BUCKET_NAME = "bucketName";
    private static final String SNS_TOPIC_ARN = "snsTopicARN";
    private static final String SNS_ENDPOINT= "snsEnpoint";
    private static final String S3_ENDPOINT= "s3Enpoint";

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            usage(null, null);
        }
        Properties p = loadProperties(args[0]);
        checkProperties(p);
        new FileBackup().run(s(p.get(BUCKET_NAME)), s(p.get(ACCESS_KEY)), s(p.get(SECRET_KEY)), s(p.get(SOURCE)),
                s(p.get(TARGET_PREFIX)), l(p.get(RETENTION_PERIOD)), s(p.get(SNS_TOPIC_ARN)),  s(p.get(SNS_ENDPOINT)),  s(p.get(S3_ENDPOINT)));

    }

    private static Long l(Object object) {
        try {
            Long l = Long.valueOf(String.valueOf(object).trim());
            return l; 
        } catch (Exception e) {
            usage(e, null);
        }
        return null;
    }

    private static String s(Object object) {
        return String.valueOf(object);
    }

    private static void checkProperties(Properties p) {
        for (String string : Arrays.asList(BUCKET_NAME, SOURCE, TARGET_PREFIX, RETENTION_PERIOD, SNS_TOPIC_ARN, SNS_ENDPOINT, S3_ENDPOINT)) {
            if (!p.containsKey(string)) {
                usage(null, "Missing property " + string);
            }
        }
        File file = new File(s(p.get(SOURCE)));
        if(! file.isDirectory()) {
            usage(null, "Specified source is no directory");
        }
    }

    private static void usage(Exception e, String msg) {
        if (msg != null) {
            System.out.println(msg + "\n");
        }
        System.out.println("Usage: <properties.file>");
        System.out.println(" \nwhere properties file contains:\n");
        System.out
                .println(BUCKET_NAME+"=name of the bucket to put the backup");
        System.out.println(ACCESS_KEY + "=IAM access key (ignore for instance role authentication)");
        System.out.println(SECRET_KEY + "=IAM secret key (ignore for instance role authentication)");
        System.out.println(SOURCE + "=source folder to be backed up");
        System.out.println(TARGET_PREFIX + "=prefix for backed up files");
        System.out.println(SNS_TOPIC_ARN + "=SNS topic ARN for issue reporting");
        System.out.println(SNS_ENDPOINT+ "=SNS endpoint (e.g. for Europe: https://sns.eu-west-1.amazonaws.com)");
        System.out.println(S3_ENDPOINT+ "=S3 endpoint (e.g. for Europe: https://eu-west-1.ec2.amazonaws.com)");
        System.out.println(RETENTION_PERIOD
                + "=number of days after which the source files should be removed (-1 means never)");

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
