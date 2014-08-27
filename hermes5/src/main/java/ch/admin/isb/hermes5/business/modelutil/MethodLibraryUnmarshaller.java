/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelutil;

import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;

@ApplicationScoped
public class MethodLibraryUnmarshaller implements Serializable {

    private static final long serialVersionUID = 1L;
    private JAXBContext jc;

    private JAXBContext getJC() {
        if (jc == null) {
            try {
                jc = JAXBContext.newInstance(MethodLibrary.class.getPackage().getName());
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return jc;
    }

    @SuppressWarnings("unchecked")
    public MethodLibrary unmarshalMethodLibrary(InputStream inputStream) {
        try {
            Unmarshaller u = getJC().createUnmarshaller();
            JAXBElement<MethodLibrary> doc = (JAXBElement<MethodLibrary>) u.unmarshal(inputStream);
            return doc.getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
