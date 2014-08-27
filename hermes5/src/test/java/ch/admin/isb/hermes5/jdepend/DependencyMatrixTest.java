/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.jdepend;

import static org.junit.Assert.*;

import java.util.Collection;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;

import org.junit.Test;

public class DependencyMatrixTest {
    @Test
    public void testArchitectureLayeringConstraints() throws Exception {
        JDepend jdepend = new JDepend();
        jdepend.addDirectory("target/classes");
        
        @SuppressWarnings("unchecked")
        Collection<JavaPackage> packages = jdepend.analyze();
        
        DependencyMatrix matrix = new DependencyMatrix();
        matrix.parseFile("src/test/java/ch/admin/isb/hermes5/jdepend/layering.csv");
        
        String violations = matrix.violationReport(packages);
        
        if (!"[]".equals(violations)) {
            fail(violations);
        }
    }
    
    @Test
    public void testCycles() throws Exception {
        JDepend jdepend = new JDepend();
        jdepend.addDirectory("target/classes");
        @SuppressWarnings("unchecked")
        Collection<JavaPackage> packages = jdepend.analyze();
        for (JavaPackage p : packages) {
            assertFalse("Cycle exists: " + p.getName(), p.containsCycle());
        }
        
    }
}
