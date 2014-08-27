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
 * The root generalization for all UMA Method Elements.  Defines a common set of attributes inherited by all UMA Method Elements.  Method Element itself is derived from Packageable Element from the UML 2.0 Infrastructure.
 * 
 * <p>Java-Klasse für MethodElement complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MethodElement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}PackageableElement">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="OwnedRule" type="{http://www.eclipse.org/epf/uma/1.0.6}Constraint"/>
 *         &lt;element name="MethodElementProperty" type="{http://www.eclipse.org/epf/uma/1.0.6}MethodElementProperty"/>
 *       &lt;/choice>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="briefDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="suppressed" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="orderingGuide" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="presentationName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodElement", propOrder = {
    "ownedRuleOrMethodElementProperty"
})
public class MethodElement
    extends PackageableElement
{

    @XmlElements({
        @XmlElement(name = "OwnedRule", type = Constraint.class),
        @XmlElement(name = "MethodElementProperty", type = MethodElementProperty.class)
    })
    protected List<PackageableElement> ownedRuleOrMethodElementProperty;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "briefDescription")
    protected String briefDescription;
    @XmlAttribute(name = "suppressed")
    protected Boolean suppressed;
    @XmlAttribute(name = "orderingGuide")
    protected String orderingGuide;
    @XmlAttribute(name = "presentationName")
    protected String presentationName;

    /**
     * Gets the value of the ownedRuleOrMethodElementProperty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ownedRuleOrMethodElementProperty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOwnedRuleOrMethodElementProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Constraint }
     * {@link MethodElementProperty }
     * 
     * 
     */
    public List<PackageableElement> getOwnedRuleOrMethodElementProperty() {
        if (ownedRuleOrMethodElementProperty == null) {
            ownedRuleOrMethodElementProperty = new ArrayList<PackageableElement>();
        }
        return this.ownedRuleOrMethodElementProperty;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Ruft den Wert der briefDescription-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBriefDescription() {
        return briefDescription;
    }

    /**
     * Legt den Wert der briefDescription-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBriefDescription(String value) {
        this.briefDescription = value;
    }

    /**
     * Ruft den Wert der suppressed-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuppressed() {
        return suppressed;
    }

    /**
     * Legt den Wert der suppressed-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuppressed(Boolean value) {
        this.suppressed = value;
    }

    /**
     * Ruft den Wert der orderingGuide-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderingGuide() {
        return orderingGuide;
    }

    /**
     * Legt den Wert der orderingGuide-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderingGuide(String value) {
        this.orderingGuide = value;
    }

    /**
     * Ruft den Wert der presentationName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresentationName() {
        return presentationName;
    }

    /**
     * Legt den Wert der presentationName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresentationName(String value) {
        this.presentationName = value;
    }

    /**
     * Sets the value of the ownedRuleOrMethodElementProperty property.
     * 
     * @param ownedRuleOrMethodElementProperty
     *     allowed object is
     *     {@link Constraint }
     *     {@link MethodElementProperty }
     *     
     */
    public void setOwnedRuleOrMethodElementProperty(List<PackageableElement> ownedRuleOrMethodElementProperty) {
        this.ownedRuleOrMethodElementProperty = ownedRuleOrMethodElementProperty;
    }

}
