/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SzenarioItem implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean selected;
    private String id;
    private Localizable name;
    private SzenarioItem parent;
    private final List<SzenarioItem> children;

    public SzenarioItem() {
        children = new ArrayList<SzenarioItem>();
        selected = true;
    }

    public SzenarioItem(String id, Localizable name, SzenarioItem parent) {
        children = new ArrayList<SzenarioItem>();
        selected = true;
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Localizable getName() {
        return name;
    }

    public void setName(Localizable name) {
        this.name = name;
    }

    public SzenarioItem getParent() {
        return parent;
    }


    public List<SzenarioItem> getChildren() {
        return children;
    }

    public boolean isDisabled() {
        SzenarioItem itemParent = parent;
        while (itemParent != null) {
            if (!itemParent.selected) {
                return true;
            }
            itemParent = itemParent.getParent();
        }
        return false;
    }

  
}
