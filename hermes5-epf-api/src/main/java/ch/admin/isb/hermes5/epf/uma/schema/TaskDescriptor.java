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
 * A special Descriptor that represents a proxy for a Task in the context of one specific Activity.  Every breakdown structure can define different relationships of Task Descriptors to Work Product Descriptors and Role Descriptors. Therefore one Task can be represented by many Task Descriptors each within the context of an Activity with its own set of relationships.
 * A key difference between Method Content and Process is that a Content Element such as Task describes all aspects of doing work defined around this Task.  This description is managed in steps, which are modeled as Sections of the Tasks' Content Descriptions.  When applying a Task in a Process' Activity with a Task Descriptor a Process Engineer needs to indicate that at that particular point in time in the Process definition for which the Task Descriptor has been created, only a subset of steps shall be performed.  He defines this selection using the selectedSteps association.  If he wants to add steps to a Task Descriptor, he can describe these either pragmatically in the refinedDescription attribute or 'properly' create a contributing Task to the Task the Task Descriptor refers to.
 * 
 * 
 * <p>Java-Klasse für TaskDescriptor complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="TaskDescriptor">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}WorkBreakdownElement">
 *       &lt;sequence>
 *         &lt;element name="Task" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="PerformedPrimarilyBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="AdditionallyPerformedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="AssistedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="ExternalInput" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="MandatoryInput" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="OptionalInput" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="Output" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *         &lt;element name="Step" type="{http://www.eclipse.org/epf/uma/1.0.6}Section" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="isSynchronizedWithSource" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaskDescriptor", propOrder = {
    "task",
    "performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy",
    "step"
})
public class TaskDescriptor
    extends WorkBreakdownElement
{

    @XmlElement(name = "Task")
    protected String task;
    @XmlElementRefs({
        @XmlElementRef(name = "Output", type = JAXBElement.class),
        @XmlElementRef(name = "MandatoryInput", type = JAXBElement.class),
        @XmlElementRef(name = "ExternalInput", type = JAXBElement.class),
        @XmlElementRef(name = "AssistedBy", type = JAXBElement.class),
        @XmlElementRef(name = "AdditionallyPerformedBy", type = JAXBElement.class),
        @XmlElementRef(name = "OptionalInput", type = JAXBElement.class),
        @XmlElementRef(name = "PerformedPrimarilyBy", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy;
    @XmlElement(name = "Step")
    protected List<Section> step;
    @XmlAttribute(name = "isSynchronizedWithSource")
    protected Boolean isSynchronizedWithSource;

    /**
     * Ruft den Wert der task-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTask() {
        return task;
    }

    /**
     * Legt den Wert der task-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTask(String value) {
        this.task = value;
    }

    /**
     * Gets the value of the performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy().add(newItem);
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
    public List<JAXBElement<String>> getPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy() {
        if (performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy == null) {
            performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy = new ArrayList<JAXBElement<String>>();
        }
        return this.performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy;
    }

    /**
     * Gets the value of the step property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the step property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStep().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section }
     * 
     * 
     */
    public List<Section> getStep() {
        if (step == null) {
            step = new ArrayList<Section>();
        }
        return this.step;
    }

    /**
     * Ruft den Wert der isSynchronizedWithSource-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSynchronizedWithSource() {
        return isSynchronizedWithSource;
    }

    /**
     * Legt den Wert der isSynchronizedWithSource-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSynchronizedWithSource(Boolean value) {
        this.isSynchronizedWithSource = value;
    }

    /**
     * Sets the value of the performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy property.
     * 
     * @param performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy
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
    public void setPerformedPrimarilyByOrAdditionallyPerformedByOrAssistedBy(List<JAXBElement<String>> performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy) {
        this.performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy = performedPrimarilyByOrAdditionallyPerformedByOrAssistedBy;
    }

    /**
     * Sets the value of the step property.
     * 
     * @param step
     *     allowed object is
     *     {@link Section }
     *     
     */
    public void setStep(List<Section> step) {
        this.step = step;
    }

}
