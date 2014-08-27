/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.util.List;
import java.util.Map;

import ch.admin.isb.hermes5.epf.uma.schema.DescribableElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;

public abstract class AbstractMethodenElement {

    public abstract DescribableElement getElement();

    public abstract String getName();

    /**
     * references to all other elements in the method library via index. allows for easy resolving references
     */
    private final Map<String, MethodElement> methodLibraryIndex;

    public Map<String, MethodElement> getMethodLibraryIndex() {
        return methodLibraryIndex;
    }

    public AbstractMethodenElement(Map<String, MethodElement> methodLibraryIndex) {
        this.methodLibraryIndex = methodLibraryIndex;
    }

    public String getId() {
        return getElement().getId();
    }

    protected MethodElement resolveMethodLibraryReference(String id) {
        return methodLibraryIndex.get(id);
    }

    public Localizable getMainDescription() {
        if (getElement().getPresentation() == null) {
            return null;
        }
        return new DefaultLocalizable(getElement().getPresentation(), "mainDescription");
    }

    public Localizable getBriefDescription() {
        return new DefaultLocalizable(getElement(), "briefDescription");
    }

    public Localizable getPresentationName() {
        return new DefaultLocalizable(getElement(), "presentationName");
    }

    protected void addIfNotContained(List<RelationshipTableRecord> result, RelationshipTableRecord record) {
        if (!contains(result, record)) {
            result.add(record);
        }

    }

    private boolean contains(List<RelationshipTableRecord> result, RelationshipTableRecord record) {
        for (RelationshipTableRecord existing : result) {
            if (equals(existing.getModul(), record.getModul()) && equals(existing.getAufgabe(), record.getAufgabe())
                    && equals(existing.getVerantwortlichFuerAufgabe(), record.getVerantwortlichFuerAufgabe())
                    && equals(existing.getErgebnis(), record.getErgebnis())
                    && equals(existing.getVerantwortlichFuerErgebnis(), record.getVerantwortlichFuerErgebnis())) {
                return true;
            }
        }
        return false;
    }

 
    private boolean equals(List<? extends AbstractMethodenElement> o1, List<? extends AbstractMethodenElement> o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 == null || o2 == null || o1.size() != o2.size()) {
            return false;
        }
        for (int i = 0; i < o1.size(); i++) {
            if(! equals(o1.get(i), o2.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean equals(AbstractMethodenElement o1, AbstractMethodenElement o2) {
        if (o1 == null && o2 == null) {
            return true;
        }
        if (o1 != null && o2 != null && o1.getId() != null) {
            return o1.getId().equals(o2.getId());
        }
        return false;
    }
}
