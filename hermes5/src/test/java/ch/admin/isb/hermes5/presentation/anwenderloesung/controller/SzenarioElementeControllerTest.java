/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class SzenarioElementeControllerTest {

    private SzenarioElementeController controller;

    @Before
    public void setUp() throws Exception {
        controller = new SzenarioElementeController();
        controller.szenarioEigeneElementeController = mock(SzenarioEigeneElementeController.class);
        controller.szenarioProjektdatenController = mock(SzenarioProjektdatenController.class);
        controller.szenarioWizardContext = new SzenarioWizardContext();
        
        controller.szenarioWizardContext.init();
        controller.szenarioWizardContext.setSzenario(szenario("szenario"));
    }

    @Test
    public void test() {
        String display = controller.display();
        assertEquals("szenario-elemente",display);
    }

}
