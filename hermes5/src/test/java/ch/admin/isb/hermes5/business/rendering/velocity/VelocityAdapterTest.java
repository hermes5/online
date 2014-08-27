/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.velocity;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.StringUtil;

public class VelocityAdapterTest {

    private VelocityAdapter velocityAdapter;

    @Before
    public void setUp() throws Exception {
        velocityAdapter = new VelocityAdapter();
        Hardcoded.enableDefaults(velocityAdapter);

    }
    
    @Test
    public void mergeTemplates() {
        Map<String, Object> context = new HashMap<String, Object>();
        String templates = "beschreibung.vm";
        String mergeTemplates = velocityAdapter.mergeTemplates(context, templates);
        assertNotNull(mergeTemplates);
        assertTrue(mergeTemplates, StringUtil.isNotBlank(mergeTemplates));
    }
}
