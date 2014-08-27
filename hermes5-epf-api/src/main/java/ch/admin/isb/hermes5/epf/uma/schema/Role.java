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
 * A Content Element that defines a set of related skills, competencies, and responsibilities.  Roles are used by Tasks to define who performs them as well as define a set of work products they are responsible for.  
 * A Role defines a set of related skills, competencies, and responsibilities of an individual or a set of individuals.  Roles are not individuals or resources.  Individual members of the development organization will wear different hats, or perform different roles. The mapping from individual to role, performed by the project manager when planning and staffing for a project, allows different individuals to act as several different roles, and for a role to be played by several individuals.
 * 
 * <p>Java-Klasse für Role complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Role">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentElement">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="ResponsibleFor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Role", propOrder = {
    "responsibleFor"
})
public class Role
    extends ContentElement
{

    @XmlElement(name = "ResponsibleFor")
    protected List<String> responsibleFor;

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
