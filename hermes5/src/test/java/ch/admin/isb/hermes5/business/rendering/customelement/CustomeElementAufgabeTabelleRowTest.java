/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.customelement;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.rendering.customelement.AbstractCustomElementModulBasedTabelleRow.ModulCellElement;

public class CustomeElementAufgabeTabelleRowTest {

    private CustomeElementAufgabeTabelleRow row1;
    private CustomeElementAufgabeTabelleRow row2;
    private CustomeElementAufgabeTabelleRow row3;

    @Before
    public void setUp() throws Exception {
        ModulCellElement modul1 = new ModulCellElement("f_modul", "modulLink");
        ModulCellElement modul2 = new ModulCellElement("e_modul", "modulLink");
        row1 = new CustomeElementAufgabeTabelleRow("a_ergebnis", false, false, false, "link", modul1, "de");
        row2 = new CustomeElementAufgabeTabelleRow("b_ergebnis", false, false, false, "link", modul1, "de");
        row3 = new CustomeElementAufgabeTabelleRow("a_ergebnis", false, false, false, "link", modul2, "de");
    }

    @Test
    public void testCompareToCustomeElementAufgabeTabelleRow() {
        List<CustomeElementAufgabeTabelleRow> asList = Arrays.asList(row1, row2, row3);

        Collections.sort(asList);

        assertEquals(row3, asList.get(0));
        assertEquals(row1, asList.get(1));
        assertEquals(row2, asList.get(2));
    }

}
