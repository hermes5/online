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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Beschreibung;
import ch.admin.isb.hermes5.util.MockInstance;

public class OnlinePublikationRendererRepositoryTest {


    private OnlinePublikationRendererRepository importHandlerRepository;
    private OnlinePublikationRenderer rendererA = mockPrintXmlRenderer(false);
    private OnlinePublikationRenderer rendererB = mockPrintXmlRenderer(true);

    @Test
    public void testPng() {
        importHandlerRepository = new OnlinePublikationRendererRepository();
        importHandlerRepository.onlinePublikationRenderers = new MockInstance<OnlinePublikationRenderer>(rendererA,  rendererB);
        OnlinePublikationRenderer result = importHandlerRepository.lookupOnlinePublikationRenderer(new Beschreibung(null, null));
        assertEquals(rendererB, result);
    }

    private OnlinePublikationRenderer mockPrintXmlRenderer(boolean b) {
        OnlinePublikationRenderer mock = mock(OnlinePublikationRenderer.class);
        when(mock.isResponsibleFor(any(AbstractMethodenElement.class))).thenReturn(b);
        return mock;
    }

}
