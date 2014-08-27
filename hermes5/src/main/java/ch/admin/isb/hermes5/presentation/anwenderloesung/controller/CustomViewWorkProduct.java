/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.SzenarioItem;
import ch.admin.isb.hermes5.presentation.common.SelectableRow;
import ch.admin.isb.hermes5.validation.UserInput;

public class CustomViewWorkProduct implements Serializable  {

    private static final long serialVersionUID = 1L;

    private List<SelectableRow<Rolle>> roles;
    private List<SelectableRow<SzenarioItem>> phases;
    private byte[] templateContent;

    @UserInput
    private String templateFileName;

    @UserInput(min = 1)
    private String presentationName;

    @UserInput(max = Integer.MAX_VALUE, pattern = UserInput.HTML_PATTERN)
    private String briefDescription;

    private String id;

    public CustomViewWorkProduct() {
        phases = new ArrayList<SelectableRow<SzenarioItem>>();
        roles = new ArrayList<SelectableRow<Rolle>>();
        id = UUID.randomUUID().toString();
    }

    public CustomViewWorkProduct(String presetationName) {
        this();
        this.presentationName = presetationName;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomViewWorkProduct other = (CustomViewWorkProduct) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (presentationName == null) {
            if (other.presentationName != null)
                return false;
        } else if (!presentationName.equals(other.presentationName))
            return false;
        return true;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public String getId() {
        return id;
    }

    public List<SelectableRow<SzenarioItem>> getPhases() {
        return phases;
    }

    public String getPresentationName() {
        return presentationName;
    }

    public byte[] getTemplateContent() {
        return templateContent;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public List<SelectableRow<Rolle>> getRoles() {
        return roles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((presentationName == null) ? 0 : presentationName.hashCode());
        return result;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhases(List<SelectableRow<SzenarioItem>> phases) {
        this.phases = phases;
    }

    public void setPresentationName(String presentationName) {
        this.presentationName = presentationName;
    }

    public void setRoles(List<SelectableRow<Rolle>> roles) {
        this.roles = roles;
    }

    public void setTemplateContent(byte[] templateContent) {
        this.templateContent = templateContent;
    }

    public void setTemplateFileName(String vorlageFilaneme) {
        this.templateFileName = vorlageFilaneme;
    }
}
