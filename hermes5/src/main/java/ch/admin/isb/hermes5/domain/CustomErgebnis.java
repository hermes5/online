/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.admin.isb.hermes5.util.StringUtil;

public class CustomErgebnis extends Ergebnis {

    private final byte[] vorlageContent;
    private final String vorlageFilename;
    private final String presentationName;
    private final String briefDescription;
    private final List<String> phasenIds;

    public CustomErgebnis(String presentationName, String briefDescription, String vorlageFilename,
            byte[] vorlageContent, List<String> phasenIds, List<Rolle> verantwortlicheRollen) {
        super(null, null, false, verantwortlicheRollen);
        this.presentationName = presentationName;
        this.briefDescription = briefDescription;
        this.vorlageFilename = vorlageFilename;
        this.vorlageContent = vorlageContent;
        this.phasenIds = phasenIds;
    }

    public List<String> getPhasenIds() {
        return phasenIds;
    }

    @Override
    public String getId() {
        return getName();
    }

    public String getCustomName() {
        return presentationName;
    }

    @Override
    public String getName() {
        return "customergebnis_" + StringUtil.replaceSpecialChars(presentationName).replace(" ", "_");
    }

    @Override
    public Localizable getBriefDescription() {
        return new CustomLocalizable(briefDescription, briefDescription, briefDescription, briefDescription);
    }

    @Override
    public Localizable getPresentationName() {
        return new CustomLocalizable(presentationName, presentationName, presentationName, presentationName);
    }

    @Override
    public List<String> getTemplateAttachmentUrls() {
        if (StringUtil.isNotBlank(vorlageFilename)) {
            return Arrays.asList("custom/" + presentationName + "/" + StringUtil.replaceSpecialChars(vorlageFilename));
        }
        return Arrays.asList();
    }

    @Override
    public List<Localizable> getWebAttachmentUrls() {
        return new ArrayList<Localizable>();
    };

    public String getVorlageFilename() {
        return vorlageFilename;
    }

    public byte[] getVorlageContent() {
        return vorlageContent;
    }

}
