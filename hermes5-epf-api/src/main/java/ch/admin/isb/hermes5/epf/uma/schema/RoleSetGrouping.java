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
 * Role Sets can be categorized into Role Set Groupings.  For example, different methods might define similar Role Sets, which however need to be distinguished from each other on a global scale.  Thus, Role Set Groupings allow distinguishing, for example, Software Services Manager Role Sets from Software Development Organization Manager Role Sets.
 * 
 * <p>Java-Klasse für RoleSetGrouping complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="RoleSetGrouping">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentCategory">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="RoleSet" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoleSetGrouping", propOrder = {
    "roleSet"
})
public class RoleSetGrouping
    extends ContentCategory
{

    @XmlElement(name = "RoleSet")
    protected List<String> roleSet;

    /**
     * Gets the value of the roleSet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleSet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleSet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRoleSet() {
        if (roleSet == null) {
            roleSet = new ArrayList<String>();
        }
        return this.roleSet;
    }

    /**
     * Sets the value of the roleSet property.
     * 
     * @param roleSet
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleSet(List<String> roleSet) {
        this.roleSet = roleSet;
    }

}
