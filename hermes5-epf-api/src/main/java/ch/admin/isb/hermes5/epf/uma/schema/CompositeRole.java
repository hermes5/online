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
 * A special Role Descriptor that relates to more then one Role.  It represents a grouping of Roles with the main purpose of simplification, i.e. reducing the number of roles for a process.
 * A Composite Role is a grouping of Roles that can be used in an Activity or Process to reduce the number of Roles.  A typical application would be a process for a small team in which a standard set of roles from the method content would be all performed by one or more resource.  By using Composite Role the process would suggest a typical clustering of Roles to Resources.  A Composite Role could perform all Tasks defined for the Roles it refers to.
 * 
 * <p>Java-Klasse für CompositeRole complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CompositeRole">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}RoleDescriptor">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="AggregatedRole" type="{http://www.eclipse.org/epf/uma/1.0.6}Role"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompositeRole", propOrder = {
    "aggregatedRole"
})
public class CompositeRole
    extends RoleDescriptor
{

    @XmlElement(name = "AggregatedRole")
    protected List<Role> aggregatedRole;

    /**
     * Gets the value of the aggregatedRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aggregatedRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAggregatedRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Role }
     * 
     * 
     */
    public List<Role> getAggregatedRole() {
        if (aggregatedRole == null) {
            aggregatedRole = new ArrayList<Role>();
        }
        return this.aggregatedRole;
    }

    /**
     * Sets the value of the aggregatedRole property.
     * 
     * @param aggregatedRole
     *     allowed object is
     *     {@link Role }
     *     
     */
    public void setAggregatedRole(List<Role> aggregatedRole) {
        this.aggregatedRole = aggregatedRole;
    }

}
