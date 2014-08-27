/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.onlinepublikation;

import java.util.ArrayList;
import java.util.List;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Localizable;

public class MenuItem {

    private final AbstractMethodenElement methodElement;

    /**
     * a menu item is active if a child is selected or itself
     */
    private boolean active = false;

    private boolean selected = false;

    private boolean subElementsSorted = false;

    private final List<MenuItem> subItems = new ArrayList<MenuItem>();

    public MenuItem(AbstractMethodenElement methodElement) {
        this.methodElement = methodElement;
    }

    public boolean isSubElementsSorted() {
        return subElementsSorted;
    }

    public void setSubElementsSorted(boolean subElementsSorted) {
        this.subElementsSorted = subElementsSorted;
    }

    public boolean contains(String id) {
        if (hasSameId(id)) {
            return true;
        }
        for (MenuItem subitem : subItems) {
            if (subitem.contains(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSameId(String id) {
        return this.methodElement.getId().equals(id);
    }

    public String getLink() {
        return methodElement.getName() + ".html";
    }

    public AbstractMethodenElement getMethodenElement() {
        return methodElement;
    }

    public Localizable getName() {
        return methodElement.getPresentationName();
    }

    public List<MenuItem> getSubItems() {
        return subItems;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setActive() {
        this.active = true;
    }

    public void setSelected() {
        this.selected = true;
    }
}
