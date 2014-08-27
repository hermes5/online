/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelutil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.util.Logged;
import ch.admin.isb.hermes5.util.ReflectionUtil;

public class MethodLibraryVisitorDriver implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    ComplexFieldComparator fieldComparator;

    public void visit(MethodElement element, ModelVisitor... visitor) {
        this.visit(element, null, visitor);
    }

    @Logged
    public void visit(MethodElement element, MethodElement parent, ModelVisitor... visitors) {
        for (ModelVisitor modelVisitor : visitors) {
            modelVisitor.visitStart(element, parent);
        }
        Class<? extends MethodElement> class1 = element.getClass();
        List<Field> fields = ReflectionUtil.getFieldsInGivenPackageCollectionsOrObject(class1);
        Collections.sort(fields, fieldComparator);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object object = field.get(element);
                if (object != null) {
                    if (object instanceof MethodElement) {
                        visit((MethodElement) object, element, visitors);
                    } else if (object instanceof Collection) {
                        @SuppressWarnings("unchecked")
                        Collection<Object> o = (Collection<Object>) object;
                        for (Object collectionMember : o) {
                            if (collectionMember != null && collectionMember instanceof MethodElement) {
                                visit((MethodElement) collectionMember, element, visitors);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for (ModelVisitor modelVisitor : visitors) {
            modelVisitor.visitEnd(element, parent);
        }
    }

}
