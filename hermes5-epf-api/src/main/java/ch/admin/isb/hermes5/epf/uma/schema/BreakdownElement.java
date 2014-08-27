/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/

package ch.admin.isb.hermes5.epf.uma.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * An abstract generalization for any type of Method Element that is part of a breakdown structure.  It defines a set of properties available to all of its specializations.
 * 
 * <p>Java-Klasse für BreakdownElement complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="BreakdownElement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ProcessElement">
 *       &lt;sequence>
 *         &lt;element name="PresentedAfter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PresentedBefore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlanningData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SuperActivity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="Checklist" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Concept" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Example" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Guideline" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="ReusableAsset" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="SupportingMaterial" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Whitepaper" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="prefix" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isPlanned" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="hasMultipleOccurrences" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isOptional" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BreakdownElement", propOrder = {
    "presentedAfter",
    "presentedBefore",
    "planningData",
    "superActivity",
    "checklistOrConceptOrExample"
})
public class BreakdownElement
    extends ProcessElement
{

    @XmlElement(name = "PresentedAfter")
    protected String presentedAfter;
    @XmlElement(name = "PresentedBefore")
    protected String presentedBefore;
    @XmlElement(name = "PlanningData")
    protected String planningData;
    @XmlElement(name = "SuperActivity")
    protected String superActivity;
    @XmlElementRefs({
        @XmlElementRef(name = "SupportingMaterial", type = JAXBElement.class),
        @XmlElementRef(name = "Guideline", type = JAXBElement.class),
        @XmlElementRef(name = "Example", type = JAXBElement.class),
        @XmlElementRef(name = "Checklist", type = JAXBElement.class),
        @XmlElementRef(name = "Whitepaper", type = JAXBElement.class),
        @XmlElementRef(name = "Concept", type = JAXBElement.class),
        @XmlElementRef(name = "ReusableAsset", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> checklistOrConceptOrExample;
    @XmlAttribute(name = "prefix")
    protected String prefix;
    @XmlAttribute(name = "isPlanned")
    protected Boolean isPlanned;
    @XmlAttribute(name = "hasMultipleOccurrences")
    protected Boolean hasMultipleOccurrences;
    @XmlAttribute(name = "isOptional")
    protected Boolean isOptional;

    /**
     * Ruft den Wert der presentedAfter-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresentedAfter() {
        return presentedAfter;
    }

    /**
     * Legt den Wert der presentedAfter-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresentedAfter(String value) {
        this.presentedAfter = value;
    }

    /**
     * Ruft den Wert der presentedBefore-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresentedBefore() {
        return presentedBefore;
    }

    /**
     * Legt den Wert der presentedBefore-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresentedBefore(String value) {
        this.presentedBefore = value;
    }

    /**
     * Ruft den Wert der planningData-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanningData() {
        return planningData;
    }

    /**
     * Legt den Wert der planningData-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanningData(String value) {
        this.planningData = value;
    }

    /**
     * Ruft den Wert der superActivity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuperActivity() {
        return superActivity;
    }

    /**
     * Legt den Wert der superActivity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuperActivity(String value) {
        this.superActivity = value;
    }

    /**
     * Gets the value of the checklistOrConceptOrExample property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the checklistOrConceptOrExample property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChecklistOrConceptOrExample().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getChecklistOrConceptOrExample() {
        if (checklistOrConceptOrExample == null) {
            checklistOrConceptOrExample = new ArrayList<JAXBElement<String>>();
        }
        return this.checklistOrConceptOrExample;
    }

    /**
     * Ruft den Wert der prefix-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Legt den Wert der prefix-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

    /**
     * Ruft den Wert der isPlanned-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPlanned() {
        return isPlanned;
    }

    /**
     * Legt den Wert der isPlanned-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPlanned(Boolean value) {
        this.isPlanned = value;
    }

    /**
     * Ruft den Wert der hasMultipleOccurrences-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasMultipleOccurrences() {
        return hasMultipleOccurrences;
    }

    /**
     * Legt den Wert der hasMultipleOccurrences-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasMultipleOccurrences(Boolean value) {
        this.hasMultipleOccurrences = value;
    }

    /**
     * Ruft den Wert der isOptional-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOptional() {
        return isOptional;
    }

    /**
     * Legt den Wert der isOptional-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOptional(Boolean value) {
        this.isOptional = value;
    }

    /**
     * Sets the value of the checklistOrConceptOrExample property.
     * 
     * @param checklistOrConceptOrExample
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setChecklistOrConceptOrExample(List<JAXBElement<String>> checklistOrConceptOrExample) {
        this.checklistOrConceptOrExample = checklistOrConceptOrExample;
    }

}
