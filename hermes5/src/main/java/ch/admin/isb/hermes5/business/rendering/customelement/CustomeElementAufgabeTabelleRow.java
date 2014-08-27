/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

public class CustomeElementAufgabeTabelleRow extends AbstractCustomElementModulBasedTabelleRow implements
        Comparable<CustomeElementAufgabeTabelleRow> {

    private final String aufgabe;
    private final boolean anwender;
    private final boolean ersteller;
    private final boolean betreiber;
    private final String link;

    public CustomeElementAufgabeTabelleRow(String aufgabe, boolean anwender, boolean ersteller, boolean betreiber,
            String link, ModulCellElement modulCellElement, String language) {
        super(modulCellElement, language);
        this.aufgabe = aufgabe;
        this.anwender = anwender;
        this.ersteller = ersteller;
        this.betreiber = betreiber;
        this.link = link;
    }

    public String getAufgabe() {
        return aufgabe;
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
    public int compareTo(CustomeElementAufgabeTabelleRow o2) {
        return super.compareTo(o2) * 10000 + getCollator().compare(this.getAufgabe(), o2.getAufgabe());
    }

}
