/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.modelutil.MethodElementUtil;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.Guidance;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.Task;
import ch.admin.isb.hermes5.epf.uma.schema.TaskDescriptor;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProduct;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class PublishContainerAssembler implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(PublishContainerAssembler.class);
    private static final long serialVersionUID = 1l;

    @Inject
    @SystemProperty(value = "op_publish_einleitung_identifier", fallback = "einleitung")
    ConfigurationProperty einleitungIdentifier;

    @Inject
    MethodElementUtil methodElementUtil;

    public PublishContainer assemblePublishContainer(CustomCategory rootCustomCategory,
            Map<String, MethodElement> index,
            List<Modul> modules, List<Rolle> rollen, List<Szenario> szenarien,
            List<ch.admin.isb.hermes5.epf.uma.schema.Phase> umaPhasen) {
        Map<String, AbstractMethodenElement> methodenElemente = getAllElements(rollen, modules, index);
        Kategorie root = buildKategorie(rootCustomCategory, index, methodenElemente);
        List<Phase> phasen = getPhasen(index, umaPhasen, methodenElemente);
        return new PublishContainer(root, new ArrayList<AbstractMethodenElement>(methodenElemente.values()), phasen,
                szenarien);
    }

    private List<Phase> getPhasen(Map<String, MethodElement> index,
            List<ch.admin.isb.hermes5.epf.uma.schema.Phase> umaPhasen,
            Map<String, AbstractMethodenElement> methodenElemente) {
        List<Phase> phasen = new ArrayList<Phase>();
        for (ch.admin.isb.hermes5.epf.uma.schema.Phase umaPhase : umaPhasen) {
            Phase e = new Phase(umaPhase, index);
            for (Object object : umaPhase.getBreakdownElementOrRoadmap()) {
                if (object instanceof TaskDescriptor) {
                    TaskDescriptor taskDescriptor = (TaskDescriptor) object;
                    AbstractMethodenElement methodElement = methodenElemente.get(taskDescriptor.getTask());
                    if (methodElement != null && methodElement instanceof Aufgabe) {
                        e.getAufgaben().add((Aufgabe) methodElement);
                    }
                }
            }
            phasen.add(e);
        }
        return phasen;
    }

    private Kategorie buildKategorie(CustomCategory rootCustomCategory, Map<String, MethodElement> index,
            Map<String, AbstractMethodenElement> methodenElemente) {
        Kategorie root = new Kategorie(rootCustomCategory, index);
        methodenElemente.put(root.getId(), root);
        List<MethodElement> children = methodElementUtil.getChildren(rootCustomCategory, index);
        for (MethodElement methodElement : children) {
            AbstractMethodenElement child = null;
            if (methodenElemente.containsKey(methodElement.getId())) { // already exists
                child = methodenElemente.get(methodElement.getId());
            } else if (isGuidance(methodElement)) {
                child = new Beschreibung((Guidance) methodElement, index);
                if(! isEinleitung(root, methodElement)) {
                    methodenElemente.put(child.getId(), child);
                }
            } else if (isKategorie(methodElement)) {
                child = buildKategorie((CustomCategory) methodElement, index, methodenElemente);
            }
            if (isEinleitung(root, methodElement)) {
                root.setEinleitung(child);
            } else if (child != null) {
                root.getChildren().add(child);
            } else {
                if (methodElement instanceof Task) {
                    logger.error("Aufgabe " + methodElement.getName() + "(" + methodElement.getId()
                            + ") not found in moduls");
                } else if (methodElement instanceof WorkProduct) {
                    logger.error("Ergebnis " + methodElement.getName() + "(" + methodElement.getId()
                            + ") not found in moduls");
                } else {
                    logger.error("Unable to map " + methodElement.getClass() + " - " + methodElement.getName() + "("
                            + methodElement.getId() + ")");
                }
            }
        }
        return root;
    }

    private boolean isEinleitung(Kategorie root, MethodElement methodElement) {
        return root.getEinleitung() == null && methodElement.getName().endsWith(einleitungIdentifier.getStringValue());
    }

    private boolean isGuidance(MethodElement methodElement) {
        return methodElement instanceof Guidance;
    }

    private boolean isKategorie(MethodElement methodElement) {
        return CustomCategory.class.equals(methodElement.getClass());
    }

    private Map<String, AbstractMethodenElement> getAllElements(List<Rolle> rollen, List<Modul> modules,
            Map<String, MethodElement> index) {
        Map<String, AbstractMethodenElement> map = new LinkedHashMap<String, AbstractMethodenElement>();
        for (Rolle rolle : rollen) {
            map.put(rolle.getId(), rolle);
        }
        for (Modul modul : modules) {
            map.put(modul.getId(), modul);
            for (Aufgabe aufgabe : modul.getAufgaben()) {
                map.put(aufgabe.getId(), aufgabe);
            }
            for (Ergebnis ergebnis : modul.getErgebnisse()) {
                map.put(ergebnis.getId(), ergebnis);
            }
        }

        return map;
    }

}
