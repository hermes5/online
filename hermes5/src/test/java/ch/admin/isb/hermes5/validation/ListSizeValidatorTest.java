/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.validation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class ListSizeValidatorTest {
    ListSizeValidator listSizeValidator = new ListSizeValidator();

    @Test
    public void testEmpty() {
        ListSize mock = listSize(1, -1);
        listSizeValidator.initialize(mock);
        assertFalse(listSizeValidator.isValid(new ArrayList<String>(), null));
    }
    @Test
    public void testNull() {
        ListSize mock = listSize(1, -1);
        listSizeValidator.initialize(mock);
        assertFalse(listSizeValidator.isValid(null, null));
    }
    @Test
    public void testNotMin() {
        ListSize mock = listSize(2, -1);
        listSizeValidator.initialize(mock);
        assertFalse(listSizeValidator.isValid(Arrays.asList("a"), null));
    }
    @Test
    public void testMin() {
        ListSize mock = listSize(2, -1);
        listSizeValidator.initialize(mock);
        assertTrue(listSizeValidator.isValid(Arrays.asList("a", "b"), null));
    }

    private ListSize listSize(int min, int max) {
        ListSize mock = mock(ListSize.class);
        when(mock.min()).thenReturn(min);
        when(mock.max()).thenReturn(max);
        return mock;
    }

}
