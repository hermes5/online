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
 * A special Descriptor that represents a Work Product in the context of one specific Activity.  Every breakdown structure can define different relationships of Work Product Descriptors to Task Descriptors and Role Descriptors.  Therefore one Work Product can be represented by many Work Product Descriptors each within the context of an Activity with its own set of relationships.
 * 
 * <p>Java-Klasse für WorkProductDescriptor complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WorkProductDescriptor">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Descriptor">
 *       &lt;sequence>
 *         &lt;element name="WorkProduct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResponsibleRole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="ExternalInputTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="ImpactedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Impacts" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="MandatoryInputTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="OptionalInputTo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="OutputFrom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="DeliverableParts" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="activityEntryState" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="activityExitState" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkProductDescriptor", propOrder = {
    "workProduct",
    "responsibleRole",
    "externalInputToOrImpactedByOrImpacts"
})
public class WorkProductDescriptor
    extends Descriptor
{

    @XmlElement(name = "WorkProduct")
    protected String workProduct;
    @XmlElement(name = "ResponsibleRole")
    protected String responsibleRole;
    @XmlElementRefs({
        @XmlElementRef(name = "DeliverableParts", type = JAXBElement.class),
        @XmlElementRef(name = "ExternalInputTo", type = JAXBElement.class),
        @XmlElementRef(name = "MandatoryInputTo", type = JAXBElement.class),
        @XmlElementRef(name = "OutputFrom", type = JAXBElement.class),
        @XmlElementRef(name = "Impacts", type = JAXBElement.class),
        @XmlElementRef(name = "OptionalInputTo", type = JAXBElement.class),
        @XmlElementRef(name = "ImpactedBy", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> externalInputToOrImpactedByOrImpacts;
    @XmlAttribute(name = "activityEntryState")
    protected String activityEntryState;
    @XmlAttribute(name = "activityExitState")
    protected String activityExitState;

    /**
     * Ruft den Wert der workProduct-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkProduct() {
        return workProduct;
    }

    /**
     * Legt den Wert der workProduct-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkProduct(String value) {
        this.workProduct = value;
    }

    /**
     * Ruft den Wert der responsibleRole-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsibleRole() {
        return responsibleRole;
    }

    /**
     * Legt den Wert der responsibleRole-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsibleRole(String value) {
        this.responsibleRole = value;
    }

    /**
     * Gets the value of the externalInputToOrImpactedByOrImpacts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalInputToOrImpactedByOrImpacts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalInputToOrImpactedByOrImpacts().add(newItem);
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
    public List<JAXBElement<String>> getExternalInputToOrImpactedByOrImpacts() {
        if (externalInputToOrImpactedByOrImpacts == null) {
            externalInputToOrImpactedByOrImpacts = new ArrayList<JAXBElement<String>>();
        }
        return this.externalInputToOrImpactedByOrImpacts;
    }

    /**
     * Ruft den Wert der activityEntryState-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityEntryState() {
        return activityEntryState;
    }

    /**
     * Legt den Wert der activityEntryState-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityEntryState(String value) {
        this.activityEntryState = value;
    }

    /**
     * Ruft den Wert der activityExitState-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityExitState() {
        return activityExitState;
    }

    /**
     * Legt den Wert der activityExitState-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityExitState(String value) {
        this.activityExitState = value;
    }

    /**
     * Sets the value of the externalInputToOrImpactedByOrImpacts property.
     * 
     * @param externalInputToOrImpactedByOrImpacts
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
    public void setExternalInputToOrImpactedByOrImpacts(List<JAXBElement<String>> externalInputToOrImpactedByOrImpacts) {
        this.externalInputToOrImpactedByOrImpacts = externalInputToOrImpactedByOrImpacts;
    }

}
