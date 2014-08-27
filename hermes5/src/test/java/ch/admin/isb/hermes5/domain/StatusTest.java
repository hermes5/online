/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class StatusTest {

    @Test
    public void testValueOfStrings() {
        List<Status> valueOf = Status.valueOfStrings(Arrays.asList("UNVOLLSTAENDIG", "IN_ARBEIT"));
        assertEquals(valueOf, Arrays.asList(Status.UNVOLLSTAENDIG, Status.IN_ARBEIT));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testValueOfStringsUnknown() {
        Status.valueOfStrings(Arrays.asList("bla", "IN_ARBEIT"));
    }

}
