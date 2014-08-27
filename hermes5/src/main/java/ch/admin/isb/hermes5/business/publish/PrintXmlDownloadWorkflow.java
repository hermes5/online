/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.rendering.postprocessor.InternalLinkPostProcessor;
import ch.admin.isb.hermes5.business.rendering.printxml.PrintXmlRendererRepository;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.print.api.BookType;
import ch.admin.isb.hermes5.print.api.ObjectFactory;

import com.sun.xml.txw2.output.CDataXMLStreamWriter;

public class PrintXmlDownloadWorkflow implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(PrintXmlDownloadWorkflow.class);

    @Inject
    ModelRepository modelRepository;
    
    @Inject
    InternalLinkPostProcessor internalLinkPostProcessor;

    @Inject
    PrintXmlRendererRepository printXmlRendererRepository;

    public String generatePrintXml(String modelIdentifier, LocalizationEngine localizationEngine ) {
        BookType book = new BookType();
        PublishContainer hermesHandbuch = modelRepository.getHermesHandbuch(modelIdentifier);
        List<AbstractMethodenElement> elementsToPublish = hermesHandbuch.getElementsToPublish();
        
        AbstractMethodenElement publishingRoot = hermesHandbuch.getPublishingRoot();
        printXmlRendererRepository.lookupPrintXmlRenderer(publishingRoot).renderPrintXml(publishingRoot, book,
                localizationEngine, hermesHandbuch);
        String xmlString = marshal(book);

        return internalLinkPostProcessor.adjustInternalLinksForXML(xmlString, elementsToPublish);
    }

    private String marshal(BookType book) {
        JAXBContext jaxbContext;
        StringWriter st = new StringWriter();
        try {
            jaxbContext = JAXBContext.newInstance("ch.admin.isb.hermes5.print.api");
            Marshaller marshaller = jaxbContext.createMarshaller();
            JAXBElement<BookType> bookElement = (new ObjectFactory()).createBook(book);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter streamWriter = xof.createXMLStreamWriter(st);
            CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);
            marshaller.marshal(bookElement, cdataStreamWriter);
            cdataStreamWriter.flush();
            cdataStreamWriter.close();
        } catch (JAXBException e) {
            logger.error("error marshalling print xml: " + e.getStackTrace());
        } catch (XMLStreamException e) {
            logger.error("error marshalling print xml: " + e.getStackTrace());
        }

        return st.toString();
    }

}
