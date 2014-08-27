/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/

package ch.admin.isb.hermes5.epf.uma.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * An abstract Method Element that generalizes all descriptions of work within the Unified Method Architecture.  This package introduces two concrete types of Work Definitions: Task and Step.  Work Definitions can contain sets of pre- and post-conditions defining constraints that need to be valid before the described work can begin or before it can be declared as finished.  Note that general ownedRules can be used to define additional constraints and rules for Work Definitions.
 * Work Definitions represent behavioral descriptions for doing work.  These behavioral descriptions are not bound to one specific classifier, but represent an arbitrary definition of work.  For example, a Work Definition could represent work that is being performed by a specific Role (e.g. a Role performing a specific Task or Steps of a Task), by many Roles working in close collaboration (many Roles all working together on the same interdisciplinary Task), or complex work that is performed throughout the lifecycle (e.g. a process defining a breakdown structure for organizing larger composite units of work performed by many Roles working in collaboration).
 * 
 * <p>Java-Klasse für WorkDefinition complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WorkDefinition">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodElement">
 *       &lt;sequence>
 *         &lt;element name="Precondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Postcondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkDefinition", propOrder = {
    "precondition",
    "postcondition"
})
public class WorkDefinition
    extends MethodElement
{

    @XmlElement(name = "Precondition")
    protected String precondition;
    @XmlElement(name = "Postcondition")
    protected String postcondition;

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

}
