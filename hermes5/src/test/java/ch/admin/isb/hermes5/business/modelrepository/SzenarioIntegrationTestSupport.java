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

import ch.admin.isb.hermes5.business.modelutil.ComplexFieldComparator;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.business.modelutil.MethodenElementFactory;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.DeliveryProcess;
import ch.admin.isb.hermes5.epf.uma.schema.Discipline;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.RoleSet;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;

public class SzenarioIntegrationTestSupport {

    private final ReflectionTestHelper helper = new ReflectionTestHelper();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Szenario buildSzenario(InputStream resourceAsStream, int configurationIndex) {
        Map<String, MethodElement> index;

        MethodLibraryUnmarshaller methodLibraryUnmarshaller = new MethodLibraryUnmarshaller();
        MethodLibrary methodLibrary = methodLibraryUnmarshaller.unmarshalMethodLibrary(resourceAsStream);
        ElementIndexVisitor elementIndexVisitor = new ElementIndexVisitor();
        ElementExtractorVisitor elementExtractorVisitor = new ElementExtractorVisitor();
        elementExtractorVisitor.init(DeliveryProcess.class, Discipline.class, Role.class);
        elementExtractorVisitor.init(DeliveryProcess.class, Discipline.class, Role.class, MethodConfiguration.class);
        MethodLibraryVisitorDriver methodLibraryVisitorDriver = new MethodLibraryVisitorDriver();
        ComplexFieldComparator complexFieldComparator = new ComplexFieldComparator();
        Hardcoded.enableDefaults(complexFieldComparator);
        complexFieldComparator.init();
        helper.updateField(methodLibraryVisitorDriver, "fieldComparator", complexFieldComparator);

        methodLibraryVisitorDriver.visit(methodLibrary, elementExtractorVisitor, elementIndexVisitor);
        index = elementIndexVisitor.getResult();
        List<MethodElement> deliveryProcesses = elementExtractorVisitor.getResult(DeliveryProcess.class);
        assertEquals(1, deliveryProcesses.size());
        DeliveryProcess deliveryProcess = (DeliveryProcess) deliveryProcesses.get(0);
        List modules = elementExtractorVisitor.getResult(Discipline.class);
        List<Role> roles = (List) elementExtractorVisitor.getResult(Role.class);
        List  rollen = new RollenAssembler().assembleRollen(index, roles, new ArrayList<RoleSet>());
        List  configurations = elementExtractorVisitor.getResult(MethodConfiguration.class);
        SzenarioAssembler szenarioAssembler = new SzenarioAssembler();
        szenarioAssembler.methodenElementFactory = new MethodenElementFactory();
        return szenarioAssembler.buildScenario(deliveryProcess,
                (MethodConfiguration) configurations.get(configurationIndex), index, modules, rollen);
    }

}