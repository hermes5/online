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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishContainer {

    private static final Logger logger = LoggerFactory.getLogger(PublishContainer.class);
    
    private final Kategorie publishingRoot;

    private final List<AbstractMethodenElement> elementsToPublish;
    private final List<Phase> phasen;
    private final List<Szenario> szenarien;

    public PublishContainer(Kategorie root, List<AbstractMethodenElement> elementsToPublish, List<Phase> phasen, List<Szenario> szenarien) {
        this.publishingRoot = root;
        this.elementsToPublish = elementsToPublish;
        this.phasen = phasen;
        this.szenarien = szenarien;
    }

    public List<Szenario> getSzenarien() {
        return szenarien;
    }

    public List<Phase> getPhasen() {
        return phasen;
    }

    public Kategorie getPublishingRoot() {
        return publishingRoot;
    }

    public List<AbstractMethodenElement> getElementsToPublish() {
        return elementsToPublish;
    }

    public List<Aufgabe> getAufgaben() {
        List<Aufgabe> result = new ArrayList<Aufgabe>();
        for (AbstractMethodenElement aufgabe : elementsToPublish) {
            if (aufgabe instanceof Aufgabe) {
                result.add((Aufgabe) aufgabe);
            }
        }
        return result;
    }

    public List<Ergebnis> getErgebnisse() {
        List<Ergebnis> result = new ArrayList<Ergebnis>();
        for (AbstractMethodenElement ergebnis : elementsToPublish) {
            if (ergebnis instanceof Ergebnis) {
                result.add((Ergebnis) ergebnis);
            }
        }
        return result;
    }

    public List<Modul> getModule() {
        ArrayList<Modul> result = new ArrayList<Modul>( );
        for (AbstractMethodenElement modul : elementsToPublish) {
            if (modul instanceof Modul) {
                result.add((Modul) modul);
            }
        }
        return result;
    }

    public Szenario getSzenarioWithMethodConfigurationName(String name) {
        for (Szenario szenario : szenarien) {
            if(szenario.getMethodConfiguration().getName().equals(name)) {
                return szenario;
            }
        }
        logger.warn("Unable to find method configuration with name: "+name);
        return null;
    }

    public List<String> getAvailableSzenariosNames() {
        List<String> list = new ArrayList<String>();
        for (Szenario szenario : szenarien) {
            list.add(szenario.getMethodConfiguration().getName());
        }
        return list;
    }
}
