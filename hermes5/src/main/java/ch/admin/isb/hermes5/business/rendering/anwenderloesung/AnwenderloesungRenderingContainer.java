/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.anwenderloesung;

import java.util.List;

import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;

public class AnwenderloesungRenderingContainer {

    private final String modelIdentifier;
    private final Szenario szenario;
    private final SzenarioUserData szenarioUserData;
    private final List<String> languages;
    private final boolean dokumentation;
    private final boolean projektstrukturplan;
    private final boolean ergebnisvorlagen;
    private final boolean xmlmodel;

    public AnwenderloesungRenderingContainer(String modelIdentifier, Szenario szenario,
            SzenarioUserData szenarioUserData, List<String> languages, boolean dokumentation,
            boolean projektstrukturplan, boolean ergebnisvorlagen, boolean xmlmodel) {
        this.modelIdentifier = modelIdentifier;
        this.szenario = szenario;
        this.szenarioUserData = szenarioUserData;
        this.languages = languages;
        this.dokumentation = dokumentation;
        this.projektstrukturplan = projektstrukturplan;
        this.ergebnisvorlagen = ergebnisvorlagen;
        this.xmlmodel = xmlmodel;
    }

    
    public boolean isXmlmodel() {
        return xmlmodel;
    }

    public String getModelIdentifier() {
        return modelIdentifier;
    }

    public Szenario getSzenario() {
        return szenario;
    }

    public SzenarioUserData getSzenarioUserData() {
        return szenarioUserData;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public boolean isDokumentation() {
        return dokumentation;
    }

    public boolean isProjektstrukturplan() {
        return projektstrukturplan;
    }

    public boolean isErgebnisvorlagen() {
        return ergebnisvorlagen;
    }

}
