/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Iterator;

import javax.enterprise.inject.Instance;
import javax.enterprise.util.TypeLiteral;

public class MockInstance<T> implements Instance<T> {
    private final T[] instances;
    public MockInstance(T... instances) {
        this.instances = instances;
    }
    
    @Override
    public Iterator<T> iterator() {
        return Arrays.asList(instances).iterator();
    }

    @Override
    public T get() {
        return instances[0];
    }

    @Override
    public boolean isAmbiguous() {
        return false;
    }

    @Override
    public boolean isUnsatisfied() {
        return false;
    }

    @Override
    public Instance<T> select(Annotation... arg0) {
        return null;
    }

    @Override
    public <U extends T> Instance<U> select(Class<U> arg0, Annotation... arg1) {
        return null;
    }

    @Override
    public <U extends T> Instance<U> select(TypeLiteral<U> arg0, Annotation... arg1) {
        return null;
    }

    @Override
    public void destroy(T arg0) {
        
    }
    
}