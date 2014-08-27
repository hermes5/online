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
 * A special Descriptor that represents a Role in the context of one specific Activity.  Every breakdown structure can define different relationships of Role Descriptors to Task Descriptors and Work Product Descriptors.  Therefore one Role can be represented by many Role Descriptors each within the context of an Activity with its own set of relationships.
 * 
 * <p>Java-Klasse für RoleDescriptor complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="RoleDescriptor">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Descriptor">
 *       &lt;sequence>
 *         &lt;element name="Role" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResponsibleFor" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoleDescriptor", propOrder = {
    "role",
    "responsibleFor"
})
public class RoleDescriptor
    extends Descriptor
{

    @XmlElement(name = "Role")
    protected String role;
    @XmlElement(name = "ResponsibleFor")
    protected List<String> responsibleFor;

    /**
     * Ruft den Wert der role-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Legt den Wert der role-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the responsibleFor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responsibleFor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponsibleFor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getResponsibleFor() {
        if (responsibleFor == null) {
            responsibleFor = new ArrayList<String>();
        }
        return this.responsibleFor;
    }

    /**
     * Sets the value of the responsibleFor property.
     * 
     * @param responsibleFor
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsibleFor(List<String> responsibleFor) {
        this.responsibleFor = responsibleFor;
    }

}
