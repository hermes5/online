/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.rendering.onlinepublikation.MenuItem;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class OnlinePublikationMenuGenerator {

    @Inject
    @SystemProperty(
            value = "onlinepublikationMenuSortedClasses",
            fallback = "ch.admin.isb.hermes5.domain.Aufgabe ch.admin.isb.hermes5.domain.Ergebnis ch.admin.isb.hermes5.domain.Modul ch.admin.isb.hermes5.domain.Rolle")
    ConfigurationProperty sortedClasses;

    private static final Logger logger = LoggerFactory.getLogger(OnlinePublikationMenuGenerator.class);

    public List<MenuItem> generateMenu(PublishContainer hermesWebsite) {

        List<MenuItem> menu = new ArrayList<MenuItem>();

        Kategorie publishingRoot = hermesWebsite.getPublishingRoot();
        List<AbstractMethodenElement> children = publishingRoot.getChildren();
        for (AbstractMethodenElement methodElement : children) {
            menu.add(generateMenu(methodElement));
        }
        return menu;
    }

    private MenuItem generateMenu(AbstractMethodenElement methodElement) {
        MenuItem menu = new MenuItem(methodElement);
        List<AbstractMethodenElement> children = getChildren(methodElement);
        menu.setSubElementsSorted(areChildrenSorted(children));
        for (AbstractMethodenElement child : children) {
            if (child == null) {
                logger.warn("child of " + methodElement.getName() + " is null");
            } else {
                menu.getSubItems().add(generateMenu(child));
            }
        }
        return menu;
    }

    private boolean areChildrenSorted(List<AbstractMethodenElement> children) {
        if (children.size() < 2) {
            return false;
        }
        Class<? extends AbstractMethodenElement> clazz = null;
        for (AbstractMethodenElement abstractMethodenElement : children) {
            if (abstractMethodenElement == null) {
                return false;
            }
            if (clazz == null) {
                clazz = abstractMethodenElement.getClass();
            } else {
                if (!clazz.equals(abstractMethodenElement.getClass())) {
                    return false;
                }
            }

        }
        return sortedClasses.getStringValue().contains(children.get(0).getClass().getName());
    }

    private List<AbstractMethodenElement> getChildren(AbstractMethodenElement methodElement) {
        if (methodElement instanceof Kategorie) {
            Kategorie kategorie = (Kategorie) methodElement;
            return kategorie.getChildren();
        }

        return new ArrayList<AbstractMethodenElement>();
    }

    public List<MenuItem> adjustMenuForItem(List<MenuItem> menu, AbstractMethodenElement abstractMethodenElement,
            final LocalizationEngine localizationEngine) {
        List<MenuItem> list = new ArrayList<MenuItem>();
        for (MenuItem menuItem : menu) {
            MenuItem clone = new MenuItem(menuItem.getMethodenElement());
            if (menuItem.hasSameId(abstractMethodenElement.getId())) {
                clone.setSelected();
            }
            if (menuItem.contains(abstractMethodenElement.getId())) {
                if (!clone.isSelected()) {
                    clone.setActive();
                }
                List<MenuItem> subItems = menuItem.getSubItems();
                if (menuItem.isSubElementsSorted()) {
                    Collections.sort(subItems, new Comparator<MenuItem>() {

                        @Override
                        public int compare(MenuItem m1, MenuItem m2) {
                            Collator collator = Collator.getInstance(new Locale(localizationEngine.getLanguage()));
                            return collator.compare(
                                    localizationEngine.localize(m1.getMethodenElement().getPresentationName()),
                                    localizationEngine.localize(m2.getMethodenElement().getPresentationName()));
                        }

                    });
                }
                clone.getSubItems().addAll(adjustMenuForItem(subItems, abstractMethodenElement, localizationEngine));
            }
            list.add(clone);
        }
        return list;
    }

    public List<MenuItem> trimToTopMenu(List<MenuItem> menu) {
        List<MenuItem> list = new ArrayList<MenuItem>();
        for (MenuItem menuItem : menu) {
            MenuItem clone = new MenuItem(menuItem.getMethodenElement());
            list.add(clone);
        }
        return list;
    }

}
