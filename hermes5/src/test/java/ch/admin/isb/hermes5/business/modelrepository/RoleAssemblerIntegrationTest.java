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
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelutil.ComplexFieldComparator;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.RollenGruppe;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.RoleSet;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;

public class RoleAssemblerIntegrationTest {

    private ReflectionTestHelper helper = new ReflectionTestHelper();
    private Map<String, MethodElement> index;
    private RollenAssembler roleAssembler;
    private List<Role> roles;
    private List<RoleSet> rolesets;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void setUp() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        MethodLibraryUnmarshaller methodLibraryUnmarshaller = new MethodLibraryUnmarshaller();
        MethodLibrary methodLibrary = methodLibraryUnmarshaller.unmarshalMethodLibrary(resourceAsStream);
        ElementIndexVisitor elementIndexVisitor = new ElementIndexVisitor();
        ElementExtractorVisitor elementExtractorVisitor = new ElementExtractorVisitor();
        elementExtractorVisitor.init(Role.class, RoleSet.class);
        MethodLibraryVisitorDriver methodLibraryVisitorDriver = new MethodLibraryVisitorDriver();
        ComplexFieldComparator complexFieldComparator = new ComplexFieldComparator();
        Hardcoded.enableDefaults(complexFieldComparator);
        complexFieldComparator.init();
        helper.updateField(methodLibraryVisitorDriver, "fieldComparator", complexFieldComparator);

        methodLibraryVisitorDriver.visit(methodLibrary, elementExtractorVisitor, elementIndexVisitor);
        index = elementIndexVisitor.getResult();
        roles = (List) elementExtractorVisitor.getResult(Role.class);
        rolesets = (List) elementExtractorVisitor.getResult(RoleSet.class);

        roleAssembler = new RollenAssembler();
    }

    @Test
    public void assembleRollen() {
        List<Rolle> assembleRollen = roleAssembler.assembleRollen(index, roles, rolesets);
        assertNotNull(assembleRollen);
        assertEquals(roles.size(), assembleRollen.size());
    }

    @Test
    public void assembleRolleProjektleiterWithRollenGruppe() {
        List<Rolle> assembleRollen = roleAssembler.assembleRollen(index, roles, rolesets);
        boolean found = false;
        for (Rolle rolle : assembleRollen) {
            if (rolle.getElement().getName().equals("projektleiter")) {
                found = true;
                List<RollenGruppe> rollengruppen = rolle.getRollengruppen();
                assertTrue(rollengruppen.size() > 0);
                boolean anwenderFound = false;
                for (RollenGruppe rollenGruppe : rollengruppen) {
                    if(rollenGruppe.getElement().getName().equals("anwender")) {
                        anwenderFound = true;
                    }
                }
                assertTrue(anwenderFound);

            }
        }

        assertTrue("no projektleiter found", found);

    }
}
