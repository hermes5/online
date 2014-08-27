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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A Work Product that provides a description and definition for packaging other Work Products, and may be delivered to an internal or external party.  Therefore, a Deliverable aggregates other Work Products. Therefore, a Deliverable aggregates other Work Products.  A Deliverable is used to pre-define typical or recommended content in the form or work products that would be packaged for delivery.  The actual packaging of the Deliverable in an actual process or even project could be a modification of this recommendation.  Deliverables are used to represent an output from a process that has value, material or otherwise, to a client, customer or other stakeholder. 
 * 
 * <p>Java-Klasse für Deliverable complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Deliverable">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}WorkProduct">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="DeliveredWorkProduct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Deliverable", propOrder = {
    "deliveredWorkProduct"
})
public class Deliverable
    extends WorkProduct
{

    @XmlElement(name = "DeliveredWorkProduct")
    protected List<String> deliveredWorkProduct;

    /**
     * Gets the value of the deliveredWorkProduct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deliveredWorkProduct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeliveredWorkProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDeliveredWorkProduct() {
        if (deliveredWorkProduct == null) {
            deliveredWorkProduct = new ArrayList<String>();
        }
        return this.deliveredWorkProduct;
    }

    /**
     * Sets the value of the deliveredWorkProduct property.
     * 
     * @param deliveredWorkProduct
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveredWorkProduct(List<String> deliveredWorkProduct) {
        this.deliveredWorkProduct = deliveredWorkProduct;
    }

}
