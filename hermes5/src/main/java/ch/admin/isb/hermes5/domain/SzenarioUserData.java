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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.admin.isb.hermes5.validation.UserInput;

public class SzenarioUserData implements Serializable {

    private static final long serialVersionUID = 1L;

    @UserInput
    private String projektname = "";

    @UserInput
    private String firma1 = "";

    @UserInput
    private String firma2 = "";

    @UserInput
    private String auftraggeber = "";

    @UserInput
    private String projektleiter = "";

    // projektnummer is removed in ui because of HERMES-102
    @UserInput
    private String projektnummer = "";

    @UserInput
    private String logoFilename;

    private byte[] logo;

    private SzenarioItem szenarioTree;

    private final Map<String, List<CustomErgebnis>> customModules;


    public SzenarioUserData() {
        customModules = new HashMap<String, List<CustomErgebnis>>();
    }

    public void setLogoFilename(String logoFilename) {
        this.logoFilename = logoFilename;
    }

    public String getProjektname() {
        return projektname;
    }

    public void setProjektname(String projektname) {
        this.projektname = projektname;
    }

    public String getFirma1() {
        return firma1;
    }

    public void setFirma1(String firma) {
        this.firma1 = firma;
    }

    public String getFirma2() {
        return firma2;
    }

    public void setFirma2(String firma) {
        this.firma2 = firma;
    }

    public String getAuftraggeber() {
        return auftraggeber;
    }

    public void setAuftraggeber(String auftraggeber) {
        this.auftraggeber = auftraggeber;
    }

    public String getProjektleiter() {
        return projektleiter;
    }

    public void setProjektleiter(String projektleiter) {
        this.projektleiter = projektleiter;
    }

    public String getProjektnummer() {
        return projektnummer;
    }

    public void setProjektnummer(String projektnummer) {
        this.projektnummer = projektnummer;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public SzenarioItem getSzenarioTree() {
        return szenarioTree;
    }

    public void setSzenarioTree(SzenarioItem szenarioTree) {
        this.szenarioTree = szenarioTree;
    }

    public String getLogoFilename() {
        return logoFilename;
    }

    public List<CustomErgebnis> addCustomModule(String name) {
        if (customModules.containsKey(name)) {
            throw new IllegalArgumentException("Duplicate key!");
        }

        ArrayList<CustomErgebnis> customErgebnisse = new ArrayList<CustomErgebnis>();
        customModules.put(name, customErgebnisse);

        return customErgebnisse;
    }

    public List<CustomErgebnis> getCustomErgebnisse(String moduleName) {
        return customModules.get(moduleName);
    }

    public List<CustomErgebnis> getAllCustomErgebnisse() {
        List<CustomErgebnis> customErgebnisse = new ArrayList<CustomErgebnis>();
        for (List<CustomErgebnis> ces : customModules.values()) {
            customErgebnisse.addAll(ces);
        }

        return customErgebnisse;
    }

    public Map<String, List<CustomErgebnis>> getCustomModules() {
        return customModules;
    }
}
