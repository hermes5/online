/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.filebackup;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;

public class FileBackup {

    public void run(String bucketName, String accessKey, String secretKey, String source, String targetPrefix,
            Long retentionPeriod, String topicArn, String snsEndpoint, String s3Endpoint) {

        AmazonS3 s3 = s3(accessKey, secretKey, s3Endpoint);

        AmazonSNS sns = sns(accessKey, secretKey, snsEndpoint);

        List<String> errors = new ArrayList<String>();
        String[] list = new File(source).list();
        for (String string : list) {
            File file = new File(source + "/" + string);
            System.out.print(timestamp() + " Backing up " + file.getAbsolutePath() + " to " + bucketName + "/"
                    + targetPrefix + string + "...");
            try {
                byte[] data = readFileToByteArray(file);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(data.length);
                s3.putObject(bucketName, targetPrefix + string, new ByteArrayInputStream(data), metadata);
                System.out.println("done");
                long lastModified = file.lastModified();
                long now = System.currentTimeMillis();
                if (retentionPeriod > 0 && differenceInDays(lastModified, now) > retentionPeriod) {
                    System.out.println(timestamp() + " File " + source + "/" + string
                            + " is removed because it is older than " + retentionPeriod + " days.");
                    boolean delete = file.delete();
                    if (!delete) {
                        errors.add("Unable to delete " + file.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                System.out.println("failed " + e.getMessage());
                errors.add(timestamp() + " Problem Backing up " + file.getAbsolutePath() + " to " + bucketName + "/"
                        + targetPrefix + string + "\n" + getStackTrace(e));
            }
        }

        if (errors.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String string : errors) {
                sb.append(string).append("\n");
            }
            try {
                sendMessageThroughSNS(topicArn, sns, sb.toString(), "Problem with backup");
            } catch (Exception e) {
                System.out.println(timestamp() + "ERROR: unable to report issue " + sb.toString());
                e.printStackTrace();
            }
        }

    }

    private byte[] readFileToByteArray(File file) throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fileInputStream.read(data);
            return data;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private AmazonSNS sns(String accessKey, String secretKey, String snsEndpoint) {
        AmazonSNS sns = accessKey == null || "".equals(accessKey) ? new AmazonSNSClient() : new AmazonSNSClient(
                new BasicAWSCredentials(accessKey, secretKey));
        sns.setEndpoint(snsEndpoint);
        return sns;
    }

    private AmazonS3 s3(String accessKey, String secretKey, String s3Endpoint) {
        AmazonS3 s3 = accessKey == null || "".equals(accessKey) ? new AmazonS3Client() : new AmazonS3Client(
                new BasicAWSCredentials(accessKey, secretKey));
        s3.setEndpoint(s3Endpoint);
        return s3;
    }

    private String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
    }

    private long differenceInDays(long lastModified, long now) {
        long diff = now - lastModified;
        long diffDays = (diff / (24 * 60 * 60 * 1000));
        return diffDays;
    }

    private String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    private void sendMessageThroughSNS(String topicArn, AmazonSNS sns, String message, String subject) {
        if (message.isEmpty()) {
            return;
        }
        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setTopicArn(topicArn);
        publishRequest.setSubject(subject);
        publishRequest.setMessage(truncateUTF8(message, 8192));
        sns.publish(publishRequest);
    }

    private String truncateUTF8(String s, int maxBytes) {
        int b = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // ranges from http://en.wikipedia.org/wiki/UTF-8
            int skip = 0;
            int more;
            if (c <= 0x007f) {
                more = 1;
            } else if (c <= 0x07FF) {
                more = 2;
            } else if (c <= 0xd7ff) {
                more = 3;
            } else if (c <= 0xDFFF) {
                // surrogate area, consume next char as well
                more = 4;
                skip = 1;
            } else {
                more = 3;
            }
            if (b + more > maxBytes) {
                return s.substring(0, i);
            }
            b += more;
            i += skip;
        }
        return s;
    }
}
