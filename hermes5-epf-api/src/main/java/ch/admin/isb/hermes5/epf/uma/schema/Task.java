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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * A Content Element that describes work being performed by Roles.  It defines one default performing Role as well as many additional performers.  A Task is associated to input and output work products.  Inputs are differentiated in mandatory versus optional inputs.  The associations to Work Products are not instantiatable/variable-like parameters.  They rather express (hyper-)links to the descriptions of the work products types that are related to the Task as inputs and outputs.  In other words, these associations are not intended to be used to capture which concrete instances will be passed when instantiating the method in a project.  All of the Task's default associations can be overridden in an actual process definition.
 * A Task describes an assignable unit of work.  Every Task is assigned to specific Roles.  The granularity of a Task is generally a few hours to a few days.  It usually affects one or only a small number of work products. A Task is used as an element of defining a process. Tasks are further used for planning and tracking progress; therefore, if they are defined too fine-grained, they will be neglected, and if they are too large, progress would have to be expressed in terms of a Task's parts (e.g. Steps, which is not recommended). 
 * A Task has a clear purpose in which the performing roles achieve a well defined goal.  It provides complete step-by-step explanations of doing all the work that needs to be done to achieve this goal.  This description is complete, independent of when in a process lifecycle the work would actually be done.  It therefore does not describe when you do what work at what point of time, but describes all the work that gets done throughout the development lifecycle that contributes to the achievement of this goal.  When the Task is being applied in a process then this process application (defined as Task Descriptor) provides the information of which pieces of the Task will actually be performed at any particular point in time. This assumes that the Task will be performed in the process over and over again, but each time with a slightly different emphasis on different steps or aspects of the task description. 
 * For example, a Task such as "Develop Use Case Model" describes all the work that needs to be done to develop a complete use case model. This would comprise of the identification and naming of use cases and actors, the writing of a brief description, the modeling of use cases and their relationships in diagrams, the detailed description of a basic flow, the detailed description of alternatives flows, performing of walkthroughs workshops and reviews, etc.  All of these parts contribute to the development goal of developing the use case model, but the parts will be performed at different points in time in a process.  Identification, naming, and brief descriptions would be performed early in a typical development process versus the writing of detailed alternative flows which would be performed much later.  All these parts or steps within the same Task define the "method" of Developing a Use Case Model.  Applying such a method in a lifecycle (i.e. in a process) is defining which steps are done when going from one iteration to the next.
 * 
 * <p>Java-Klasse für Task complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Task">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentElement">
 *       &lt;sequence>
 *         &lt;element name="Precondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Postcondition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PerformedBy" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="MandatoryInput" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Output" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="AdditionallyPerformedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="OptionalInput" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Estimate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="EstimationConsiderations" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="ToolMentor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Task", propOrder = {
    "precondition",
    "postcondition",
    "performedBy",
    "mandatoryInputOrOutputOrAdditionallyPerformedBy"
})
public class Task
    extends ContentElement
{

    @XmlElement(name = "Precondition")
    protected String precondition;
    @XmlElement(name = "Postcondition")
    protected String postcondition;
    @XmlElement(name = "PerformedBy")
    protected List<String> performedBy;
    @XmlElementRefs({
        @XmlElementRef(name = "Output", type = JAXBElement.class),
        @XmlElementRef(name = "Estimate", type = JAXBElement.class),
        @XmlElementRef(name = "OptionalInput", type = JAXBElement.class),
        @XmlElementRef(name = "EstimationConsiderations", type = JAXBElement.class),
        @XmlElementRef(name = "AdditionallyPerformedBy", type = JAXBElement.class),
        @XmlElementRef(name = "MandatoryInput", type = JAXBElement.class),
        @XmlElementRef(name = "ToolMentor", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> mandatoryInputOrOutputOrAdditionallyPerformedBy;

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

    /**
     * Gets the value of the performedBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the performedBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerformedBy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPerformedBy() {
        if (performedBy == null) {
            performedBy = new ArrayList<String>();
        }
        return this.performedBy;
    }

    /**
     * Gets the value of the mandatoryInputOrOutputOrAdditionallyPerformedBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mandatoryInputOrOutputOrAdditionallyPerformedBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMandatoryInputOrOutputOrAdditionallyPerformedBy().add(newItem);
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
    public List<JAXBElement<String>> getMandatoryInputOrOutputOrAdditionallyPerformedBy() {
        if (mandatoryInputOrOutputOrAdditionallyPerformedBy == null) {
            mandatoryInputOrOutputOrAdditionallyPerformedBy = new ArrayList<JAXBElement<String>>();
        }
        return this.mandatoryInputOrOutputOrAdditionallyPerformedBy;
    }

    /**
     * Sets the value of the performedBy property.
     * 
     * @param performedBy
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerformedBy(List<String> performedBy) {
        this.performedBy = performedBy;
    }

    /**
     * Sets the value of the mandatoryInputOrOutputOrAdditionallyPerformedBy property.
     * 
     * @param mandatoryInputOrOutputOrAdditionallyPerformedBy
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
    public void setMandatoryInputOrOutputOrAdditionallyPerformedBy(List<JAXBElement<String>> mandatoryInputOrOutputOrAdditionallyPerformedBy) {
        this.mandatoryInputOrOutputOrAdditionallyPerformedBy = mandatoryInputOrOutputOrAdditionallyPerformedBy;
    }

}
