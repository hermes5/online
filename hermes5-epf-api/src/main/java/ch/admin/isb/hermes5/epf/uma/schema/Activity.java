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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * A Work Breakdown Element and Work Definition which supports the nesting and logical grouping of related Breakdown Elements forming breakdown structures.  Although Activity is a concrete meta-class, other classes which represent breakdown structures derive from it; such as Phase, Iteration, Delivery Process, or Capability Pattern.
 * Activity represents a grouping element for other Breakdown Elements such as Activities, Descriptors, Milestones, etc.  It is not per-se a 'high-level' grouping of only work as in other meta-models, but groups any kind of Breakdown Elements.  For example, one can define valid Activities that group only Work Products Descriptors without any matching Task Descriptors.  Activities also inherit all properties from Work Breakdown Element and indirectly from Process Element; i.e. Activity is ready to have a full content description attached to it.
 * 
 * <p>Java-Klasse für Activity complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Activity">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}WorkBreakdownElement">
 *       &lt;sequence>
 *         &lt;element name="Precondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Postcondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="BreakdownElement" type="{http://www.eclipse.org/epf/uma/1.0.6}BreakdownElement"/>
 *           &lt;element name="Roadmap" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="variabilityType" type="{http://www.eclipse.org/epf/uma/1.0.6}VariabilityType" />
 *       &lt;attribute name="variabilityBasedOnElement" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IsEnactable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Activity", propOrder = {
    "precondition",
    "postcondition",
    "breakdownElementOrRoadmap"
})
public class Activity
    extends WorkBreakdownElement
{

    @XmlElement(name = "Precondition")
    protected String precondition;
    @XmlElement(name = "Postcondition")
    protected String postcondition;
    @XmlElements({
        @XmlElement(name = "BreakdownElement", type = BreakdownElement.class),
        @XmlElement(name = "Roadmap", type = String.class)
    })
    protected List<Object> breakdownElementOrRoadmap;
    @XmlAttribute(name = "variabilityType")
    protected VariabilityType variabilityType;
    @XmlAttribute(name = "variabilityBasedOnElement")
    protected String variabilityBasedOnElement;
    @XmlAttribute(name = "IsEnactable")
    protected Boolean isEnactable;

    /**
     * Ruft den Wert der precondition-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrecondition() {
        return precondition;
    }

    /**
     * Legt den Wert der precondition-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrecondition(String value) {
        this.precondition = value;
    }

    /**
     * Ruft den Wert der postcondition-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostcondition() {
        return postcondition;
    }

    /**
     * Legt den Wert der postcondition-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostcondition(String value) {
        this.postcondition = value;
    }

    /**
     * Gets the value of the breakdownElementOrRoadmap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the breakdownElementOrRoadmap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBreakdownElementOrRoadmap().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BreakdownElement }
     * {@link String }
     * 
     * 
     */
    public List<Object> getBreakdownElementOrRoadmap() {
        if (breakdownElementOrRoadmap == null) {
            breakdownElementOrRoadmap = new ArrayList<Object>();
        }
        return this.breakdownElementOrRoadmap;
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
     * Ruft den Wert der isEnactable-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsEnactable() {
        return isEnactable;
    }

    /**
     * Legt den Wert der isEnactable-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEnactable(Boolean value) {
        this.isEnactable = value;
    }

    /**
     * Sets the value of the breakdownElementOrRoadmap property.
     * 
     * @param breakdownElementOrRoadmap
     *     allowed object is
     *     {@link BreakdownElement }
     *     {@link String }
     *     
     */
    public void setBreakdownElementOrRoadmap(List<Object> breakdownElementOrRoadmap) {
        this.breakdownElementOrRoadmap = breakdownElementOrRoadmap;
    }

}
