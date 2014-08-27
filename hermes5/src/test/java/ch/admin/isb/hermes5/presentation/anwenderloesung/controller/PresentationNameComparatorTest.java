/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.domain.DefaultLocalizable;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.SzenarioBuilder;

public class PresentationNameComparatorTest {

    private PresentationNameComparator comparator;

    @Before
    public void setUp() throws Exception {
        comparator = new PresentationNameComparator();
        comparator.localizer = mock(Localizer.class);
        when(comparator.localizer.localize(any(Localizable.class))).thenAnswer(new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                DefaultLocalizable l = (DefaultLocalizable) invocation.getArguments()[0];
                return l.getElementIdentifier() + "_" + l.getTextIdentifier();
            }
        });
    }

    @Test
    public void testCompareBefore() {
        int compare = comparator.compare(SzenarioBuilder.rolle("ba"), SzenarioBuilder.rolle("bb"));
        assertTrue(compare+"", compare < 0);
    }
    @Test
    public void testCompareAfter() {
        int compare = comparator.compare(SzenarioBuilder.rolle("bb"), SzenarioBuilder.rolle("ba"));
        assertTrue(compare+"", compare > 0);
    }

}
