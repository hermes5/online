/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.xmlmodel;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.model.api.ModelType;
import ch.admin.isb.hermes5.util.ZipUtil;

public class XMLModelGeneratorTestUtil {

    public ModelType readFirstFileToModelType(ZipOutputBuilder zipBuilder) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipBuilder.getResult()));
            ZipEntry nextEntry;
            List<String> names = new ArrayList<String>();
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                names.add(nextEntry.getName());
                byte[] byteArray = new ZipUtil().readZipEntry(zipInputStream);
                return unmarshall(byteArray);
            }
            throw new RuntimeException("no entry");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String readFileToString(String path, ZipOutputBuilder zipBuilder) {
        List<String> names = new ArrayList<String>();
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipBuilder.getResult()));
            ZipEntry nextEntry;
            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                if (nextEntry.getName().equals(path)) {
                    byte[] readZipEntry = new ZipUtil().readZipEntry(zipInputStream);
                    zipInputStream.close();
                    return fromBytes(readZipEntry);
                } else {
                    names.add(nextEntry.getName());
                }
                
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("File at path "+path+" not found. Available files "+ Arrays.toString(names.toArray())); 
    }

    public ModelType unmarshall(byte[] byteArray) {
        try {
            Unmarshaller u = JAXBContext.newInstance(ModelType.class.getPackage().getName()).createUnmarshaller();
            @SuppressWarnings("unchecked")
            JAXBElement<ModelType> doc = (JAXBElement<ModelType>) u.unmarshal(new ByteArrayInputStream(byteArray));
            return doc.getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String readFirstFileToString(ZipOutputBuilder zipBuilder) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipBuilder.getResult()));
            ZipEntry nextEntry;
            List<String> names = new ArrayList<String>();

            while ((nextEntry = zipInputStream.getNextEntry()) != null) {
                names.add(nextEntry.getName());
                byte[] byteArray = new ZipUtil().readZipEntry(zipInputStream);
                return fromBytes(byteArray);
            }

            throw new RuntimeException("no entry");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
