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
 * A special Process that is prepared for instantiation by a project planning tool.  Typically, it is created based on a Process such as a Delivery Process as a whole (e.g. in case of a waterfall-based development approach) or in parts (e.g. in case of an iterative development approach).
 * A Process Planning Template represents a partially finished plan for a concrete project.  It uses the same information structures as all other Process Types to represent templates for project plans.  However, certain planning decisions have already been applied to the template as well as information has been removed and/or reformatted to be ready for export to a specific planning tool.  Examples for such decisions are: a template has been created to represent a plan for a particular Iteration in an iterative development project, which fr example distinguishes early from late iterations in the Elaboration phase of a project; if the targeted planning tool cannot represent input and output of Task, then these have been removed from the structure; certain repetitions have been already applied, e.g. stating that a cycle of specific Task grouped in an Activity have to be repeated n-times; etc.
 * 
 * <p>Java-Klasse für ProcessPlanningTemplate complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ProcessPlanningTemplate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Process">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="BaseProcess" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessPlanningTemplate", propOrder = {
    "baseProcess"
})
public class ProcessPlanningTemplate
    extends Process
{

    @XmlElement(name = "BaseProcess")
    protected List<String> baseProcess;

    /**
     * Gets the value of the baseProcess property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseProcess property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseProcess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getBaseProcess() {
        if (baseProcess == null) {
            baseProcess = new ArrayList<String>();
        }
        return this.baseProcess;
    }

    /**
     * Sets the value of the baseProcess property.
     * 
     * @param baseProcess
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseProcess(List<String> baseProcess) {
        this.baseProcess = baseProcess;
    }

}
