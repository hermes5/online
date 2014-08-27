/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelutil.ComplexFieldComparator;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.business.modelutil.MethodenElementFactory;
import ch.admin.isb.hermes5.domain.Aufgabe;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.epf.uma.schema.Discipline;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;

public class ModulAssemblerIntegrationTest {

    private ReflectionTestHelper helper = new ReflectionTestHelper();
    private Map<String, MethodElement> index;
    private ModulAssembler modulAssembler;
    private List<Discipline> disciplines;
    private List<Rolle> rollen = new ArrayList<Rolle>();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void setUp() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        MethodLibraryUnmarshaller methodLibraryUnmarshaller = new MethodLibraryUnmarshaller();
        MethodLibrary methodLibrary = methodLibraryUnmarshaller.unmarshalMethodLibrary(resourceAsStream);
        ElementIndexVisitor elementIndexVisitor = new ElementIndexVisitor();
        ElementExtractorVisitor elementExtractorVisitor = new ElementExtractorVisitor();
        elementExtractorVisitor.init(Discipline.class, Role.class);
        MethodLibraryVisitorDriver methodLibraryVisitorDriver = new MethodLibraryVisitorDriver();
        ComplexFieldComparator complexFieldComparator = new ComplexFieldComparator();
        Hardcoded.enableDefaults(complexFieldComparator);
        complexFieldComparator.init();
        helper.updateField(methodLibraryVisitorDriver, "fieldComparator", complexFieldComparator);

        methodLibraryVisitorDriver.visit(methodLibrary, elementExtractorVisitor, elementIndexVisitor);
        index = elementIndexVisitor.getResult();
        disciplines = (List) elementExtractorVisitor.getResult(Discipline.class);

        List<Role> roles = (List) elementExtractorVisitor.getResult(Role.class);
        for (Role role : roles) {
            rollen.add(new Rolle(role, index));
        }

        modulAssembler = new ModulAssembler();
        modulAssembler.methodenElementFactory = new MethodenElementFactory();
    }

    @Test
    public void assembleModulProjektFuehrung() {
        Discipline discipline = disciplines.get(0);
        Modul modul = modulAssembler.assembleModules(index, disciplines, rollen).get(0);
        assertNotNull(modul);
        assertEquals(discipline, modul.getElement());
        assertTrue(modul.getAufgaben().size() > 0);
        assertTrue(modul.getErgebnisse().size() > 0);

    }

    @Test
    public void assembleModulAufgabeVerantwortlicheRolle() {
        Modul modul = modulAssembler.assembleModules(index, disciplines, rollen).get(0);
        assertNotNull(modul);
        assertTrue(modul.getAufgaben().size() > 0);
        boolean found = false;
        for (Aufgabe a : modul.getAufgaben()) {

            if ("projekt_fuehren_und_kontrollieren".equals(a.getElement().getName())) {
                found = true;
                assertEquals("projektleiter", a.getVerantwortlicheRolle().getElement().getName());
            }

        }
        assertTrue(found);

    }
}
