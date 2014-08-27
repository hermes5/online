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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * A Breakdown Element that groups Role Descriptors or Resource Definitions defining a nested hierarchy of teams and team members.
 * Work assignments and Work Product responsibilities can be different from Activity to Activity in a development project. Different phases require different staffing profiles, i.e. different skills and resources doing different types of work.  Therefore, a process needs to define such different profiles in a flexible manner.  Whereas Core Method Content defines standard responsibilities and assignments, a process express by a breakdown structures needs to be able refine and redefine these throughout its definition.  Role Descriptors, Resource Definitions, as well as Team Profiles provide the data structure necessary to achieve this flexibility and to provide a process user with the capability to define different teams and role relationships for every Activity (including Activities on any nesting-level as well as Iterations or Phases).
 * Hence, in addition to the work breakdown and work product breakdown structures defined so far, Team Profiles are used to define a third type of breakdown structure: team breakdown structures.  These are created as an Activity specific hierarchy of Team Profiles comprising of Role Descriptors and Resource Definitions.  These structures can be presented as well-known Org-Charts.  Just as with any other Breakdown Element and Descriptors, Team Profiles can be defined within the scope of any Activity in a breakdown structure.  In other words every Activity can define its own Team Profiles consisting of Activity specific Role Descriptors and Resource Definitions.  Typically, Team Profiles are defined on the level of Iterations or Phases or other higher-level Activity.
 * 
 * <p>Java-Klasse für TeamProfile complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="TeamProfile">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}BreakdownElement">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Role" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SuperTeam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SubTeam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TeamProfile", propOrder = {
    "roleOrSuperTeamOrSubTeam"
})
public class TeamProfile
    extends BreakdownElement
{

    @XmlElementRefs({
        @XmlElementRef(name = "Role", type = JAXBElement.class),
        @XmlElementRef(name = "SubTeam", type = JAXBElement.class),
        @XmlElementRef(name = "SuperTeam", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> roleOrSuperTeamOrSubTeam;

    /**
     * Gets the value of the roleOrSuperTeamOrSubTeam property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleOrSuperTeamOrSubTeam property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleOrSuperTeamOrSubTeam().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getRoleOrSuperTeamOrSubTeam() {
        if (roleOrSuperTeamOrSubTeam == null) {
            roleOrSuperTeamOrSubTeam = new ArrayList<JAXBElement<String>>();
        }
        return this.roleOrSuperTeamOrSubTeam;
    }

    /**
     * Sets the value of the roleOrSuperTeamOrSubTeam property.
     * 
     * @param roleOrSuperTeamOrSubTeam
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRoleOrSuperTeamOrSubTeam(List<JAXBElement<String>> roleOrSuperTeamOrSubTeam) {
        this.roleOrSuperTeamOrSubTeam = roleOrSuperTeamOrSubTeam;
    }

}
