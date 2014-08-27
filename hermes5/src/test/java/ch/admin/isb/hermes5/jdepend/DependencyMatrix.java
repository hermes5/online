/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.jdepend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import jdepend.framework.JavaPackage;

public class DependencyMatrix {
    private static final String COL_SEPARATOR = ";";
    private static final String NO_DEP_ALLOWED = "no";
    
    private final List<InvalidOutgoingDependencyPattern> invalidDependencies = new LinkedList<InvalidOutgoingDependencyPattern>();
    
    public void parseFile(String fileName) {
        try {
            BufferedReader r = new BufferedReader(new FileReader(fileName));
            try {
                parse(r);
            }
            finally {
                r.close();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void parse(BufferedReader r) throws IOException {
        LinkedList<String> outgoingDependencies = readColumns(r.readLine());
        outgoingDependencies.removeFirst();

        for (String line = r.readLine(); line != null; line = r.readLine()) {
            LinkedList<String> columnValues = readColumns(line);
            String dependentPackage = columnValues.removeFirst();
            
            for (int i = 0; i < columnValues.size(); i++) {
                if (NO_DEP_ALLOWED.equals(columnValues.get(i))) {
                    addInvalidOutgoingDependencyPattern(dependentPackage, outgoingDependencies.get(i));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String violationReport(Collection<JavaPackage> packages) {
        Set<String> violations = new HashSet<String>();
        
        for (JavaPackage dependentPackage : packages) {
            for (InvalidOutgoingDependencyPattern pattern : patternsForDependencPackage(dependentPackage)) {
                for (JavaPackage outgoingDependency : (Collection<JavaPackage>)dependentPackage.getEfferents()) {
                    if (pattern.outgoingDependencyPatternMatch(outgoingDependency)) {
                        violations.add("Violation: " + pattern.getDependentPackagePattern() + " is dependent on " + outgoingDependency.getName() + "\n");
                    }
                }
            }
        }
        
        return violations.toString();
    }
    
    private List<InvalidOutgoingDependencyPattern> patternsForDependencPackage(JavaPackage dependentPackage) {
        List<InvalidOutgoingDependencyPattern> patterns = new LinkedList<InvalidOutgoingDependencyPattern>();
        for (InvalidOutgoingDependencyPattern pattern : invalidDependencies) {
            if (pattern.dependentPackagePatternMatch(dependentPackage)) {
                patterns.add(pattern);
            }
        }
        return patterns;
    }

    private void addInvalidOutgoingDependencyPattern(String dependentPackage, String outgoingDependency) {
        invalidDependencies.add(new InvalidOutgoingDependencyPattern(dependentPackage, outgoingDependency));
    }

    private LinkedList<String> readColumns(String line) {
        LinkedList<String> columnValues = new LinkedList<String>();
        StringTokenizer st = new StringTokenizer(line, COL_SEPARATOR);
        while (st.hasMoreTokens()) {
            String columnValue = st.nextToken();
            columnValues.add(columnValue.trim());
        }
        return columnValues;
    }
    
    public List<InvalidOutgoingDependencyPattern> getInvalidDependencies() {
        return invalidDependencies;
    }

    public static class InvalidOutgoingDependencyPattern {
        private final String dependentPackagePattern;   
        private final String outgoingDependencyPattern; 
        
        /**
         * @param dependentPackagePattern like "ch.zuehlke" or "ch.zuehlke.*"
         * @param outgoingDependencyPattern like "ch.zuehlke" or "ch.zuehlke.*"
         */
        public InvalidOutgoingDependencyPattern(String dependentPackagePattern, String outgoingDependencyPattern) {
            this.dependentPackagePattern = dependentPackagePattern;
            this.outgoingDependencyPattern = outgoingDependencyPattern;
        }

        public boolean dependentPackagePatternMatch(JavaPackage dependentPackage) {
            return match(dependentPackagePattern, dependentPackage);
        }

        public boolean outgoingDependencyPatternMatch(JavaPackage outgoingDependency) {
            return match(outgoingDependencyPattern, outgoingDependency);
        }

        public String getDependentPackagePattern() {
            return dependentPackagePattern;
        }

        public String getOutgoingDependencyPattern() {
            return outgoingDependencyPattern;
        }
        
        public static boolean match(String pattern, JavaPackage javaPackage) {
            if (pattern.endsWith(".*")) {
                return javaPackage.getName().startsWith(pattern.substring(0, pattern.length() - 1))
                        || javaPackage.getName().equals(pattern.substring(0, pattern.length() - 2));
            }
            else {
                return javaPackage.getName().equals(pattern);
            }
        }
    }
}
