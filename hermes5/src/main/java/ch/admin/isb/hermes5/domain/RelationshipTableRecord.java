/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.List;

public class RelationshipTableRecord {

    private final Modul modul;
    private final Aufgabe aufgabe;
    private final Rolle verantwortlichFuerAufgabe;
    private final Ergebnis ergebnis;
    private final List<Rolle> verantwortlichFuerErgebnis;
  
    private boolean evenRow;
    private boolean renderModulAndAufgabe;

    public RelationshipTableRecord(Modul modul, Aufgabe aufgabe, Rolle verantwortlichFuerAufgabe, Ergebnis ergebnis,
          List<Rolle> verantwortlichFuerErgebnis) {
        this.modul = modul;
        this.aufgabe = aufgabe;
        this.verantwortlichFuerAufgabe = verantwortlichFuerAufgabe;
        this.ergebnis = ergebnis;
        this.verantwortlichFuerErgebnis = verantwortlichFuerErgebnis;
        evenRow = true;
        renderModulAndAufgabe = true;
    }


    public Modul getModul() {
        return modul;
    }

    public Aufgabe getAufgabe() {
        return aufgabe;
    }

    public Rolle getVerantwortlichFuerAufgabe() {
        return verantwortlichFuerAufgabe;
    }

    public Ergebnis getErgebnis() {
        return ergebnis;
    }


    public boolean isEvenRow() {
        return evenRow;
    }

    public List<Rolle> getVerantwortlichFuerErgebnis() {
        return verantwortlichFuerErgebnis;
    }

    
    public void setEvenRow(boolean evenRow) {
        this.evenRow = evenRow;
    }

    public void setRenderModulAndAufgabe(boolean renderModulAndAufgabe) {
        this.renderModulAndAufgabe = renderModulAndAufgabe;
    }

    public boolean isRenderModulAndAufgabe() {
        return renderModulAndAufgabe;
    }

}
