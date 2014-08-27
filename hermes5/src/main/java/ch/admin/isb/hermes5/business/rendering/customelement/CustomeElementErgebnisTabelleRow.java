/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;


public class CustomeElementErgebnisTabelleRow extends AbstractCustomElementModulBasedTabelleRow implements
        Comparable<CustomeElementErgebnisTabelleRow> {

    private final String ergebnis;
    private final boolean anwender;
    private final boolean ersteller;
    private final boolean betreiber;
    private final String link;
    private final boolean required;

    public CustomeElementErgebnisTabelleRow(String ergebnis, boolean anwender, boolean ersteller, boolean betreiber,
            String link, ModulCellElement modulCells, boolean required, String language) {
        super(modulCells, language);
        this.ergebnis = ergebnis;
        this.anwender = anwender;
        this.ersteller = ersteller;
        this.betreiber = betreiber;
        this.link = link;
        this.required = required;
    }

    public boolean isRequired() {
        return required;
    }

    public String getErgebnis() {
        return ergebnis;
    }


    public String getLink() {
        return link;
    }

    public boolean isAnwender() {
        return anwender;
    }

    public boolean isErsteller() {
        return ersteller;
    }

    public boolean isBetreiber() {
        return betreiber;
    }

    @Override
    public int compareTo(CustomeElementErgebnisTabelleRow o2) {
        return super.compareTo(o2) * 10000 + getCollator().compare(this.getErgebnis(), o2.getErgebnis());
    }

}
