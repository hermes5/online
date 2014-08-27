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
 * A categorization of work (i.e. Tasks for Method Content), based upon similarity of concerns and cooperation of work effort.
 * A discipline is a collection of Tasks that are related to a major 'area of concern' within the overall project. The grouping of Tasks into disciplines is mainly an aid to understanding the project from a 'traditional' waterfall perspective. However, typically, for example, it is more common to perform certain requirements activities in close coordination with analysis and design activities. Separating these activities into separate disciplines makes the activities easier to comprehend.
 * 
 * <p>Java-Klasse für Discipline complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Discipline">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentCategory">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Task" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SubDiscipline" type="{http://www.eclipse.org/epf/uma/1.0.6}Discipline"/>
 *         &lt;element name="ReferenceWorkflow" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Discipline", propOrder = {
    "taskOrSubDisciplineOrReferenceWorkflow"
})
public class Discipline
    extends ContentCategory
{

    @XmlElementRefs({
        @XmlElementRef(name = "Task", type = JAXBElement.class),
        @XmlElementRef(name = "SubDiscipline", type = JAXBElement.class),
        @XmlElementRef(name = "ReferenceWorkflow", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> taskOrSubDisciplineOrReferenceWorkflow;

    /**
     * Gets the value of the taskOrSubDisciplineOrReferenceWorkflow property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taskOrSubDisciplineOrReferenceWorkflow property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTaskOrSubDisciplineOrReferenceWorkflow().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Discipline }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getTaskOrSubDisciplineOrReferenceWorkflow() {
        if (taskOrSubDisciplineOrReferenceWorkflow == null) {
            taskOrSubDisciplineOrReferenceWorkflow = new ArrayList<JAXBElement<?>>();
        }
        return this.taskOrSubDisciplineOrReferenceWorkflow;
    }

    /**
     * Sets the value of the taskOrSubDisciplineOrReferenceWorkflow property.
     * 
     * @param taskOrSubDisciplineOrReferenceWorkflow
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Discipline }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTaskOrSubDisciplineOrReferenceWorkflow(List<JAXBElement<?>> taskOrSubDisciplineOrReferenceWorkflow) {
        this.taskOrSubDisciplineOrReferenceWorkflow = taskOrSubDisciplineOrReferenceWorkflow;
    }

}
