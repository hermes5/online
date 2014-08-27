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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * A Describable Element that represents an abstract generalization for all elements that are considered to be and managed as Method Content.
 * Content Elements represents reusable Method Content that is supposed to be managed in Content Packages.  The separation of Content Element from Process Element allows to clearly distinguish between pure method content from content that is represented in processes.
 * 
 * <p>Java-Klasse für ContentElement complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ContentElement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}DescribableElement">
 *       &lt;sequence>
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
@XmlType(name = "ContentElement", propOrder = {
    "checklistOrConceptOrExample"
})
public class ContentElement
    extends DescribableElement
{

    @XmlElementRefs({
        @XmlElementRef(name = "ReusableAsset", type = JAXBElement.class),
        @XmlElementRef(name = "Whitepaper", type = JAXBElement.class),
        @XmlElementRef(name = "Checklist", type = JAXBElement.class),
        @XmlElementRef(name = "Example", type = JAXBElement.class),
        @XmlElementRef(name = "SupportingMaterial", type = JAXBElement.class),
        @XmlElementRef(name = "Guideline", type = JAXBElement.class),
        @XmlElementRef(name = "Concept", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> checklistOrConceptOrExample;
    @XmlAttribute(name = "variabilityType")
    protected VariabilityType variabilityType;
    @XmlAttribute(name = "variabilityBasedOnElement")
    protected String variabilityBasedOnElement;

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
