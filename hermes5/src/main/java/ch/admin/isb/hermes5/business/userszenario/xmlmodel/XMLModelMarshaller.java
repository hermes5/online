/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.xmlmodel;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.model.api.ModelType;
import ch.admin.isb.hermes5.model.api.ObjectFactory;

import com.sun.xml.txw2.output.CDataXMLStreamWriter;

public class XMLModelMarshaller {
    private static final Logger logger = LoggerFactory.getLogger(XMLModelMarshaller.class);

    public   String marshal(ModelType model) {
        JAXBContext jaxbContext;
        StringWriter st = new StringWriter();
        try {
            jaxbContext = JAXBContext.newInstance("ch.admin.isb.hermes5.model.api");
            Marshaller marshaller = jaxbContext.createMarshaller();
            JAXBElement<ModelType> modelElement = (new ObjectFactory()).createModel(model);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.hermes.admin.ch/model/hermes5/v1/model_schema.xsd");
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter streamWriter = xof.createXMLStreamWriter(st);
            CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);
            marshaller.marshal(modelElement, cdataStreamWriter);
            cdataStreamWriter.flush();
            cdataStreamWriter.close();
        } catch (JAXBException e) {
            logger.error("error marshalling model xml: " + e.getStackTrace());
        } catch (XMLStreamException e) {
            logger.error("error marshalling model xml: " + e.getStackTrace());
        }

        return st.toString();
    }

}
