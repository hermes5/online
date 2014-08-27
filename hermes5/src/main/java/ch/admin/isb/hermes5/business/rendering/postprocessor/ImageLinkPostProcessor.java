/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.postprocessor;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageLinkPostProcessor implements Serializable {

    private static final long serialVersionUID = 1L;

    public String adjustImageLinks(String content) {
        return adjustInternalLinksToFullSizeImages(adjustPlainImageLinks(content));
    }

    private String adjustPlainImageLinks(String content) {
        Pattern imageRegex = Pattern.compile("<img(.*?)src=\"\\s*", Pattern.DOTALL);
        Matcher regexMatcher = imageRegex.matcher(content);
        return regexMatcher.replaceAll("<img$1src=\"content/");
    }

    private String adjustInternalLinksToFullSizeImages(String content) {
    	 Pattern imageRegex = Pattern.compile("<a(.*?)href=\"hermes.core(.*?)\"", Pattern.DOTALL);
         Matcher regexMatcher = imageRegex.matcher(content);
         return regexMatcher.replaceAll("<a$1href=\"content/hermes.core$2\"");
    }
}
