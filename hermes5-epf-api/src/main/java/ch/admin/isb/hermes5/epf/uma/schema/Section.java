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
import javax.xml.bind.annotation.XmlType;


/**
 * A special Method Element that represents structural subsections of a Content Description's sectionDescription attribute.  It is used for either large scale documentation of Content Elements organized into sections as well as to flexibly add new Sections to Content Elements using contribution variability added to the Section concept for Method Plug-ins.
 * 
 * <p>Java-Klasse für Section complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Section">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodElement">
 *       &lt;sequence>
 *         &lt;element name="SubSection" type="{http://www.eclipse.org/epf/uma/1.0.6}Section" minOccurs="0"/>
 *         &lt;element name="Predecessor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="sectionName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="variabilityType" type="{http://www.eclipse.org/epf/uma/1.0.6}VariabilityType" />
 *       &lt;attribute name="variabilityBasedOnElement" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Section", propOrder = {
    "subSection",
    "predecessor",
    "description"
})
public class Section
    extends MethodElement
{

    @XmlElement(name = "SubSection")
    protected Section subSection;
    @XmlElement(name = "Predecessor")
    protected String predecessor;
    @XmlElement(name = "Description")
    protected String description;
    @XmlAttribute(name = "sectionName")
    protected String sectionName;
    @XmlAttribute(name = "variabilityType")
    protected VariabilityType variabilityType;
    @XmlAttribute(name = "variabilityBasedOnElement")
    protected String variabilityBasedOnElement;

    /**
     * Ruft den Wert der subSection-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Section }
     *     
     */
    public Section getSubSection() {
        return subSection;
    }

    /**
     * Legt den Wert der subSection-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Section }
     *     
     */
    public void setSubSection(Section value) {
        this.subSection = value;
    }

    /**
     * Ruft den Wert der predecessor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPredecessor() {
        return predecessor;
    }

    /**
     * Legt den Wert der predecessor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPredecessor(String value) {
        this.predecessor = value;
    }

    /**
     * Ruft den Wert der description-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Legt den Wert der description-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Ruft den Wert der sectionName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * Legt den Wert der sectionName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectionName(String value) {
        this.sectionName = value;
    }

    /**
     * Ruft den Wert der variabilityType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link VariabilityType }
     *     
     */
    public VariabilityType getVariabilityType() {
        return variabilityType;
    }

    /**
     * Legt den Wert der variabilityType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link VariabilityType }
     *     
     */
    public void setVariabilityType(VariabilityType value) {
        this.variabilityType = value;
    }

    /**
     * Ruft den Wert der variabilityBasedOnElement-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariabilityBasedOnElement() {
        return variabilityBasedOnElement;
    }

    /**
     * Legt den Wert der variabilityBasedOnElement-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariabilityBasedOnElement(String value) {
        this.variabilityBasedOnElement = value;
    }

}
