/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelutil;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Ergebnis;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.RollenGruppe;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.RoleSet;

public class MethodenElementFactoryTest {

    private MethodenElementFactory methodenElementFactory;

    @Before
    public void setUp() throws Exception {
        methodenElementFactory = new MethodenElementFactory();
    }

    @Test
    public void getOrCloneErgebnis() {
        Ergebnis original = ergebnis("abc");
        Ergebnis clone = methodenElementFactory.getOrCloneErgebnis(new ArrayList<Ergebnis>(), original);
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertEquals(original.getName(), clone.getName());
    }

    @Test
    public void getOrCloneErgebnissWithExisting() {
        Ergebnis original1 = ergebnis("abc");
        Ergebnis original2 = ergebnis("abc");
        Ergebnis original3 = ergebnis("abcd");
        Ergebnis clone = methodenElementFactory.getOrCloneErgebnis(Arrays.asList(original1, original3), original2);
        assertNotSame(original2, clone);
        assertSame(original1, clone);
    }

    @Test
    public void getOrCloneAufgabeAufgabe() {
        Aufgabe original = aufgabe("abc", modul("m"));
        Aufgabe clone = methodenElementFactory.getOrCloneAufgabe(new ArrayList<Aufgabe>(), original);
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertEquals(original.getName(), clone.getName());
    }

    @Test
    public void getOrCloneAufgabeWithExisting() {
        Aufgabe original1 = aufgabe("abc", modul("m"));
        Aufgabe original2 = aufgabe("abc", modul("m"));
        Aufgabe original3 = aufgabe("abcd", modul("m"));
        Aufgabe clone = methodenElementFactory.getOrCloneAufgabe(Arrays.asList(original1, original3), original2);
        assertNotSame(original2, clone);
        assertSame(original1, clone);
    }

    @Test
    public void getOrCloneRolle() {
        Rolle original = rolle("abc");
        Rolle clone = methodenElementFactory.getOrCloneRolle(new ArrayList<Rolle>(), original);
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertEquals(original.getName(), clone.getName());
    }
    
    @Test
    public void getOrCloneRolleWithRollenGruppe() {
        Rolle original = rolle("abc");
        Map<String, MethodElement> index = new HashMap<String, MethodElement>();
        RoleSet roleset = new RoleSet();
        roleset.setName("anwender");
        RollenGruppe originalRollenGruppe = new RollenGruppe(roleset, index);
        original.addRollenGruppe(originalRollenGruppe);
        Rolle clone = methodenElementFactory.getOrCloneRolle(new ArrayList<Rolle>(), original);
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertEquals(original.getName(), clone.getName());
        List<RollenGruppe> rollengruppen = clone.getRollengruppen();
        assertEquals(1, rollengruppen.size());
        assertNotSame(originalRollenGruppe, rollengruppen.get(0));
        assertEquals(originalRollenGruppe.getName(), rollengruppen.get(0).getName());
    }

    @Test
    public void getOrCloneRolleWithExisting() {
        Rolle original1 = rolle("abc");
        Rolle original2 = rolle("abc");
        Rolle original3 = rolle("abcd");
        Rolle clone = methodenElementFactory.getOrCloneRolle(Arrays.asList(original1, original3), original2);
        assertNotSame(original2, clone);
        assertSame(original1, clone);
    }

    @Test
    public void getOrCloneModul() {
        Modul original = modul("abc");
        Modul clone = methodenElementFactory.getOrCloneModul(new ArrayList<Modul>(), original);
        assertNotNull(clone);
        assertNotSame(original, clone);
        assertEquals(original.getName(), clone.getName());
    }
    
    @Test
    public void getOrCloneModulWithExisting() {
        Modul original1 = modul("abc");
        Modul original2 = modul("abc");
        Modul original3 = modul("abcd");
        Modul clone = methodenElementFactory.getOrCloneModul(Arrays.asList(original1, original3), original2);
        assertNotSame(original2, clone);
        assertSame(original1, clone);
    }

}
