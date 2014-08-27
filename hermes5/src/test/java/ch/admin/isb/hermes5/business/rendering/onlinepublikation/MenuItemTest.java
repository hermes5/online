/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.onlinepublikation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.Guidance;

public class MenuItemTest {

    private MenuItem root;
    private MenuItem child1;

    @Before
    public void setUp() throws Exception {
        root = new MenuItem(new Kategorie(customCategory("root"), null));
        child1 = new MenuItem(new Kategorie(customCategory("child1"), null));
        child1.getSubItems().add(new MenuItem(new Beschreibung(guidance("childchild1"), null)));
        child1.getSubItems().add(new MenuItem(new Beschreibung(guidance("childchild2"), null)));
        root.getSubItems().add(child1);
        root.getSubItems().add(new MenuItem(new Beschreibung(guidance("child2"), null)));
    }

    private Guidance guidance(String id) {
        Guidance guidance = new Guidance();
        guidance.setId(id);
        return guidance;
    }

    private CustomCategory customCategory(String id) {
        CustomCategory customCategory = new CustomCategory();
        customCategory.setId(id);
        return customCategory;
    }

    public void testGetSubItems() {
        assertEquals(2, root.getSubItems());
        assertEquals(child1, root.getSubItems().get(0));
    }

    @Test
    public void testContainsRoot() {
        assertTrue(root.contains("root"));
    }
    @Test
    public void testContainsNotRoot3() {
        assertFalse(root.contains("root3"));
    }

    @Test
    public void testContainsChild() {
        assertTrue(root.contains("child2"));
    }

    @Test
    public void testContainsChildChild() {
        assertTrue(root.contains("childchild1"));
    }
    

}
