/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class StringUtil {

    public static boolean isNotBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlank(String str) {
        return !isNotBlank(str);
    }

    public static String nullForBlank(String str) {
        if (isBlank(str)) {
            return null;
        } else {
            return str;
        }
    }

    public static String join(List<? extends Object> list, String separator) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            stringBuilder.append(list.get(i)).append(separator);
        }
        stringBuilder.append(list.get(list.size() - 1));
        return stringBuilder.toString();
    }

    public static String replaceSpecialChars(String s) {
        return s.replace("Ä", "Ae").replace("ä", "ae").replace("Ö", "Oe").replace("ö", "oe").replace("Ü", "Ue")
                .replace("ü", "ue").replace("É", "e").replace("é", "e").replace("È", "e").replace("è", "e")
                .replace("Ç", "C").replace("ç", "c").replace("à", "a").replace("À", "A");
    }

    public static String getLinkName(String url) {
        if (url != null) {
            if (isWebAttachmentUrl(url)) {
                String linkName = url.substring(url.indexOf('>') + 1);
                return linkName.substring(0, linkName.indexOf('<'));
            } else {
                String[] split = url.split("\\/");
                if (split.length == 1 && url.contains("\\")) { // wenn mit dem IE ein file hochgeladen wird, enthält
                                                               // dessen Pfad als sperator \
                    split = url.split("\\\\");
                }
                return split[split.length - 1];
            }
        }
        return "";
    }

    public static String getLinkUrl(String htmlLink) {
        if (htmlLink != null) {
            if (isWebAttachmentUrl(htmlLink)) {
                String url = htmlLink.substring(htmlLink.indexOf('"') + 1);
                return url.substring(0, url.indexOf('"'));
            } else {
                return htmlLink;
            }
        }
        return "";
    }

    public static boolean isWebAttachmentUrl(String url) {
        return url != null && url.trim().startsWith("<a href");
    }

    public static String shortFilename(String filename, int maxLength) {
        if (filename.length() <= maxLength) {
            return filename;
        } else {
            StringBuilder shortFilename = new StringBuilder();

            String[] split = filename.split("\\.");
            if (split.length == 2) {
                int extensionLength = split[1].length() + 1;
                if (maxLength - 5 < extensionLength) {
                    throw new IllegalArgumentException(maxLength + " is too short!");
                }

                int fileNameLength = split[0].length();
                int fileNameFirstPartLength = (maxLength - extensionLength) / 2 - 1;
                int fileNameSecondPartLength = (maxLength - extensionLength) / 2 - 2;

                shortFilename.append(filename.substring(0, fileNameFirstPartLength));
                shortFilename.append("...");
                shortFilename.append(filename.substring(fileNameLength - fileNameSecondPartLength, fileNameLength));
                shortFilename.append(".").append(split[1]);
            } else {
                if (maxLength < 5) {
                    throw new IllegalArgumentException(maxLength + " is too short!");
                }

                int fileNameLength = filename.length();
                int fileNameFirstPartLength = maxLength / 2 - 1;
                int fileNameSecondPartLength = maxLength / 2 - 2;

                shortFilename.append(filename.substring(0, fileNameFirstPartLength));
                shortFilename.append("...");
                shortFilename.append(filename.substring(fileNameLength - fileNameSecondPartLength, fileNameLength));
            }

            return shortFilename.toString();
        }
    }

    public static byte[] getBytes(String string) {
        try {
            return string.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fromBytes(byte[] content) {
        try {
            return new String(content, "UTF8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String addTargetToHtmlLink(String htmlLink) {
        if (!isWebAttachmentUrl(htmlLink)) {
            throw new IllegalArgumentException("Argument is no HTML Link! "+ htmlLink);
        }

        if (htmlLink.contains("target=")) {
            return htmlLink;
        }

        int index = htmlLink.indexOf('>');
        return htmlLink.substring(0, index) + " target=\"_blank\"" + htmlLink.substring(index, htmlLink.length());
    }
}
