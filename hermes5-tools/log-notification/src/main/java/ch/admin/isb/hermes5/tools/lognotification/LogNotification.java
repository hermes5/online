/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.tools.lognotification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;

public class LogNotification {

    public void run(String accessKey, String secretKey, String logfileOrDir, String lastRunFile, String regex,
            String ignoreRegex, String topicArn, String snsEndpoint, SimpleDateFormat simpleDateFormat) {

        AmazonSNS sns = sns(accessKey, secretKey, snsEndpoint);

        Date lastRun = getLastRun(lastRunFile, simpleDateFormat);
        Date currentRun = new Date();
        List<String> errors = new ArrayList<String>();

        File fileOrDirectory = new File(logfileOrDir);

        boolean success = runAnalysis(lastRunFile, regex, ignoreRegex, topicArn, simpleDateFormat, sns, lastRun,
                currentRun, errors, fileOrDirectory);
        log("Analysed " + fileOrDirectory.getAbsolutePath() + "... searching for pattern: " + regex
                + " while ignoring " + ignoreRegex + ". Only considering log statements after "
                + simpleDateFormat.format(lastRun)
                + (success ? ". No issues detected." : ". Issues detected and sent to SNS."));
        try {
            setLastRun(currentRun, lastRunFile, simpleDateFormat);
        } catch (IOException e1) {
            errors.add("Unable to write last run file ");
            log("Unable to write last run file " + getStackTrace(e1));
        }
        if (errors.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (String string : errors) {
                sb.append(string).append("\n");
            }
            try {
                sendMessageThroughSNS(topicArn, sns, sb.toString(), "Problem with Log Notification");
            } catch (Exception e) {
                log("ERROR: unable to report issue " + sb.toString());
                e.printStackTrace();
            }
        }

    }

    private AmazonSNS sns(String accessKey, String secretKey, String snsEndpoint) {
        AmazonSNS sns = accessKey == null || "".equals(accessKey) ? new AmazonSNSClient() : new AmazonSNSClient(
                new BasicAWSCredentials(accessKey, secretKey));
        sns.setEndpoint(snsEndpoint);
        return sns;
    }

    private boolean runAnalysis(String lastRunFile, String regex, String ignoreRegex, String topicArn,
            SimpleDateFormat simpleDateFormat, AmazonSNS sns, Date lastRun, Date currentRun, List<String> errors,
            File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            boolean success = true;
            File[] listFiles = fileOrDirectory.listFiles();
            for (File fileInDirectory : listFiles) {
                boolean analyseLogFile = analyseLogFile(lastRunFile, regex, ignoreRegex, topicArn, simpleDateFormat,
                        sns, lastRun, currentRun, fileInDirectory, errors);
                if (!analyseLogFile) {
                    success = false;
                }
            }
            return success;
        } else {
            return analyseLogFile(lastRunFile, regex, ignoreRegex, topicArn, simpleDateFormat, sns, lastRun,
                    currentRun, fileOrDirectory, errors);
        }
    }

    private boolean analyseLogFile(String lastRunFile, String regex, String ignoreRegex, String topicArn,
            SimpleDateFormat simpleDateFormat, AmazonSNS sns, Date lastRun, Date currentRun, File file,
            List<String> errors) {

        try {
            StringBuilder message = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));

            Pattern pattern = Pattern.compile(regex);
            Pattern ignorePattern = null;
            if (ignoreRegex != null & !ignoreRegex.isEmpty()) {
                ignorePattern = Pattern.compile(ignoreRegex);
            }

            String str;
            while ((str = in.readLine()) != null) {
                try {
                    Date logStatementDate = simpleDateFormat.parse(str);
                    if (logStatementDate.after(lastRun)) {
                        if (pattern.matcher(str).matches()) {
                            if (ignorePattern == null || !ignorePattern.matcher(str).matches()) {
                                message.append(str + "\n");
                            }
                        }
                    }
                } catch (ParseException e) {
                    // ignore
                } finally {
                    in.close();
                }
            }

            String msg = message.toString();
            if (!msg.isEmpty()) {
                sendMessageThroughSNS(topicArn, sns, msg, "LOG NOTIFICATION " + file.getName());
                return false;
            }
        } catch (Exception e) {
            log("Analysing " + file.getAbsolutePath() + "... failed" + "\n" + getStackTrace(e));
            errors.add(timestamp() + " Problem analysing " + file.getAbsolutePath() + "\n" + getStackTrace(e));
        }
        return true;
    }

    private void setLastRun(Date startDate, String lastRunFile, SimpleDateFormat simpleDateFormat) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File(lastRunFile));
            fileOutputStream.write(simpleDateFormat.format(startDate).getBytes("UTF8"));
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }
        }

    }

    private Date getLastRun(String lastRunFile, SimpleDateFormat simpleDateFormat) {
        Date lastRunFromFile = getLastRunFromFile(lastRunFile, simpleDateFormat);
        if (lastRunFromFile != null) {
            return lastRunFromFile;
        }
        return new Date(0);
    }

    private Date getLastRunFromFile(String lastRunFile, SimpleDateFormat simpleDateFormat) {
        File file = new File(lastRunFile);
        BufferedReader in = null;
        if (file.exists()) {
            try {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
                StringBuilder fileContents = new StringBuilder();
                String str;
                while ((str = in.readLine()) != null) {
                    fileContents.append(str);
                }
                return simpleDateFormat.parse(fileContents.toString());
            } catch (Exception e) {
                return null;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return null;
    }

    private void sendMessageThroughSNS(String topicArn, AmazonSNS sns, String message, String subject) {
        if (message.isEmpty()) {
            return;
        }
        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setTopicArn(topicArn);
        publishRequest.setSubject(subject);
        publishRequest.setMessage(truncateUTF8Tail(message, 8192));
        sns.publish(publishRequest);
    }

    private String truncateUTF8Tail(String s, int maxBytes) {
        // reverse for tail
        String sReversed = new StringBuffer(s).reverse().toString();
        String truncateUTF8 = truncateUTF8(sReversed, maxBytes);
        return new StringBuffer(truncateUTF8).reverse().toString();
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

    private void log(String message) {
        System.out.println(timestamp() + " " + message);
    }

    private String timestamp() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date());
    }

    private String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
}
