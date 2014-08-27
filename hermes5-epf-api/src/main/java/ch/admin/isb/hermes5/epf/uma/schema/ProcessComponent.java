/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/

package ch.admin.isb.hermes5.epf.uma.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * A special Process Package that applies the principles of encapsulation.  A Process Component realizes one or more Interfaces which specify inputs and outputs of the component. There might be many components realizing the same interfaces, but using different techniques to achieve similar outputs for similar inputs.  Whereas the Component Interfaces represent component specifications (black box descriptions of the component), good candidates for component realizations can be found in Capability Patterns (white box descriptions for the component).
 * UMA supports replaceable and reusable Process Components realizing the principles of encapsulation. Certain situations in a software development project might require that concrete realizations of parts of the process remain undecided or will be decided by the executing team itself (e.g. in outsourcing situations).  UMA provides a unique component concept defining interfaces for work product input and output, allowing treating the actual definition of the work that produces the outputs as a "black box".  At any point during a project the component "realization" detailing the work can be added to the process.  The component approach also allows that different styles or techniques of doing work can be replaced with one another.  For example, a software code output of a component could be produced with a model-driven development or a code-centric technique.  The component concept encapsulates the actual work and lets the development team choose the appropriate technique and fill the component's realization with their choice of Activities that produce the required outputs.
 * 
 * <p>Java-Klasse für ProcessComponent complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ProcessComponent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ProcessPackage">
 *       &lt;sequence>
 *         &lt;element name="Copyright" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Interface" type="{http://www.eclipse.org/epf/uma/1.0.6}ProcessComponentInterface" minOccurs="0"/>
 *         &lt;element name="Process" type="{http://www.eclipse.org/epf/uma/1.0.6}Process"/>
 *       &lt;/sequence>
 *       &lt;attribute name="authors" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="changeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="changeDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessComponent", propOrder = {
    "copyright",
    "_interface",
    "process"
})
public class ProcessComponent
    extends ProcessPackage
{

    @XmlElement(name = "Copyright")
    protected String copyright;
    @XmlElement(name = "Interface")
    protected ProcessComponentInterface _interface;
    @XmlElement(name = "Process", required = true)
    protected Process process;
    @XmlAttribute(name = "authors")
    protected String authors;
    @XmlAttribute(name = "changeDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar changeDate;
    @XmlAttribute(name = "changeDescription")
    protected String changeDescription;
    @XmlAttribute(name = "version")
    protected String version;

    /**
     * Ruft den Wert der copyright-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * Legt den Wert der copyright-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCopyright(String value) {
        this.copyright = value;
    }

    /**
     * Ruft den Wert der interface-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ProcessComponentInterface }
     *     
     */
    public ProcessComponentInterface getInterface() {
        return _interface;
    }

    /**
     * Legt den Wert der interface-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessComponentInterface }
     *     
     */
    public void setInterface(ProcessComponentInterface value) {
        this._interface = value;
    }

    /**
     * Ruft den Wert der process-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Process }
     *     
     */
    public Process getProcess() {
        return process;
    }

    /**
     * Legt den Wert der process-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Process }
     *     
     */
    public void setProcess(Process value) {
        this.process = value;
    }

    /**
     * Ruft den Wert der authors-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * Legt den Wert der authors-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthors(String value) {
        this.authors = value;
    }

    /**
     * Ruft den Wert der changeDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getChangeDate() {
        return changeDate;
    }

    /**
     * Legt den Wert der changeDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setChangeDate(XMLGregorianCalendar value) {
        this.changeDate = value;
    }

    /**
     * Ruft den Wert der changeDescription-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangeDescription() {
        return changeDescription;
    }

    /**
     * Legt den Wert der changeDescription-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangeDescription(String value) {
        this.changeDescription = value;
    }

    /**
     * Ruft den Wert der version-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Legt den Wert der version-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
