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
import javax.xml.bind.annotation.XmlType;


/**
 * A special Breakdown Element that provides specific properties for Breakdown Elements that represent or refer to Work Definitions.  For example its subclass Activity defines work as it is also a subclass of Work Definition.  Its subclass Task Descriptor does not define work by itself, but refers to a Work Definition and therefore can have the same common properties and Work Breakdown Element has.
 * 
 * <p>Java-Klasse für WorkBreakdownElement complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WorkBreakdownElement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}BreakdownElement">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Predecessor" type="{http://www.eclipse.org/epf/uma/1.0.6}WorkOrder"/>
 *       &lt;/choice>
 *       &lt;attribute name="isRepeatable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isOngoing" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isEventDriven" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkBreakdownElement", propOrder = {
    "predecessor"
})
public class WorkBreakdownElement
    extends BreakdownElement
{

    @XmlElement(name = "Predecessor")
    protected List<WorkOrder> predecessor;
    @XmlAttribute(name = "isRepeatable")
    protected Boolean isRepeatable;
    @XmlAttribute(name = "isOngoing")
    protected Boolean isOngoing;
    @XmlAttribute(name = "isEventDriven")
    protected Boolean isEventDriven;

    /**
     * Gets the value of the predecessor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the predecessor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPredecessor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WorkOrder }
     * 
     * 
     */
    public List<WorkOrder> getPredecessor() {
        if (predecessor == null) {
            predecessor = new ArrayList<WorkOrder>();
        }
        return this.predecessor;
    }

    /**
     * Ruft den Wert der isRepeatable-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsRepeatable() {
        return isRepeatable;
    }

    /**
     * Legt den Wert der isRepeatable-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRepeatable(Boolean value) {
        this.isRepeatable = value;
    }

    /**
     * Ruft den Wert der isOngoing-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOngoing() {
        return isOngoing;
    }

    /**
     * Legt den Wert der isOngoing-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOngoing(Boolean value) {
        this.isOngoing = value;
    }

    /**
     * Ruft den Wert der isEventDriven-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsEventDriven() {
        return isEventDriven;
    }

    /**
     * Legt den Wert der isEventDriven-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEventDriven(Boolean value) {
        this.isEventDriven = value;
    }

    /**
     * Sets the value of the predecessor property.
     * 
     * @param predecessor
     *     allowed object is
     *     {@link WorkOrder }
     *     
     */
    public void setPredecessor(List<WorkOrder> predecessor) {
        this.predecessor = predecessor;
    }

}
