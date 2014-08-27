/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2012.09.24 um 02:57:09 PM CEST 
//


package ch.admin.isb.hermes5.print.api;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ch.admin.isb.hermes5.print.api package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Book_QNAME = new QName("http://www.hermes.admin.ch/print/hermes5", "book");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ch.admin.isb.hermes5.print.api
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Subsubsection }
     * 
     */
    public Subsubsection createSubsubsection() {
        return new Subsubsection();
    }

    /**
     * Create an instance of {@link Chapter }
     * 
     */
    public Chapter createChapter() {
        return new Chapter();
    }

    /**
     * Create an instance of {@link Section }
     * 
     */
    public Section createSection() {
        return new Section();
    }

    /**
     * Create an instance of {@link Subsection }
     * 
     */
    public Subsection createSubsection() {
        return new Subsection();
    }

    /**
     * Create an instance of {@link BookType }
     * 
     */
    public BookType createBookType() {
        return new BookType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.hermes.admin.ch/print/hermes5", name = "book")
    public JAXBElement<BookType> createBook(BookType value) {
        return new JAXBElement<BookType>(_Book_QNAME, BookType.class, null, value);
    }

}
