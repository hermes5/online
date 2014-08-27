/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.printxml;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.print.api.BookType;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class PrintXmlKategorieRenderer implements PrintXmlRenderer {

    private static final Logger logger = LoggerFactory.getLogger(PrintXmlKategorieRenderer.class);

    @Inject
    PrintXmlRendererRepository printXmlRendererRepository;

    @Inject
    PrintXmlRendererUtil printXmlRendererUtil;

    @Inject
    @SystemProperty(
            value = "printXml.sortedClasses",
            fallback = "ch.admin.isb.hermes5.domain.Aufgabe ch.admin.isb.hermes5.domain.Ergebnis ch.admin.isb.hermes5.domain.Modul ch.admin.isb.hermes5.domain.Rolle")
    ConfigurationProperty sortedClasses;

    @Override
    public boolean isResponsibleForPrintXml(AbstractMethodenElement methodElement) {
        return methodElement != null && Kategorie.class.equals(methodElement.getClass());
    }

    @Override
    public void renderPrintXml(AbstractMethodenElement me, Object target, LocalizationEngine localizationEngine,
            PublishContainer publishContainer) {
        Kategorie customCategory = (Kategorie) me;
        if (!(target instanceof BookType)) {
            String name = localizationEngine.localize(customCategory.getPresentationName());
            printXmlRendererUtil.updateName(target, name);

        }

        if (customCategory.getEinleitung() != null) {
            renderChildElement(target, localizationEngine, publishContainer, customCategory.getEinleitung());
        }

        List<AbstractMethodenElement> children = customCategory.getChildren();
        if (isSortingRequired(children)) {
            children = sort(children, localizationEngine);
        }

        for (AbstractMethodenElement methodElement : children) {
            renderChildElement(target, localizationEngine, publishContainer, methodElement);
        }
    }

    @SuppressWarnings("unchecked")
    private void renderChildElement(Object target, LocalizationEngine localizationEngine,
            PublishContainer publishContainer, AbstractMethodenElement element) {
        PrintXmlRenderer printXmlRenderer = printXmlRendererRepository.lookupPrintXmlRenderer(element);
        if (printXmlRenderer != null) {
            Object subTarget = printXmlRendererUtil.getInstanceOfSub(target);
            printXmlRenderer.renderPrintXml(element, subTarget, localizationEngine, publishContainer);
            printXmlRendererUtil.getSubList(target).add(subTarget);
        }
    }

    private List<AbstractMethodenElement> sort(List<AbstractMethodenElement> children,
            final LocalizationEngine localizationEngine) {
        ArrayList<AbstractMethodenElement> sorted = new ArrayList<AbstractMethodenElement>(children);
        final Collator collator = Collator.getInstance(new Locale(localizationEngine.getLanguage()));
        Collections.sort(sorted, new Comparator<AbstractMethodenElement>() {

            @Override
            public int compare(AbstractMethodenElement o1, AbstractMethodenElement o2) {
                return collator.compare(localizationEngine.localize(o1.getPresentationName()),
                        localizationEngine.localize(o2.getPresentationName()));
            }
        });
        return sorted;
    }

    @SuppressWarnings("rawtypes")
    private boolean isSortingRequired(List<AbstractMethodenElement> children) {
        if (children == null || children.isEmpty()) {
            return false;
        }
        Class clazz = children.get(0).getClass();
        for (AbstractMethodenElement abstractMethodenElement : children) {
            if (!abstractMethodenElement.getClass().equals(clazz)) {
                return false;
            }
        }
        List<String> sortedClassesNames = Arrays.asList(sortedClasses.getStringValue().split(" "));
        boolean contains = sortedClassesNames.contains(clazz.getName());
        if (contains) {
            logger.info("Sorting " + clazz);
        }
        return contains;
    }

}
