/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.printxml;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.util.MockInstance;

public class PrintXmlRendererRepositoryTest {


    private PrintXmlRendererRepository importHandlerRepository;
    private PrintXmlRenderer rendererA = mockPrintXmlRenderer(false);
    private PrintXmlRenderer rendererB = mockPrintXmlRenderer(true);

    @Test
    public void testPng() {
        importHandlerRepository = new PrintXmlRendererRepository();
        importHandlerRepository.printXmlRenderers = new MockInstance<PrintXmlRenderer>(rendererA,  rendererB);
        PrintXmlRenderer result = importHandlerRepository.lookupPrintXmlRenderer(new Beschreibung(null, null));
        assertEquals(rendererB, result);
    }

    private PrintXmlRenderer mockPrintXmlRenderer(boolean b) {
        PrintXmlRenderer mock = mock(PrintXmlRenderer.class);
        when(mock.isResponsibleForPrintXml(any(AbstractMethodenElement.class))).thenReturn(b);
        return mock;
    }

}
