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
 * A specific type of guidance that represents a proven way or strategy of doing work to achieve a goal that has a positive impact on work product or process quality.  Practices are defined orthogonal to methods and processes.  They could summarize aspects that impact many different parts of a method or specific processes.  Examples for practices would be "Manage Risks", "Continuously verify quality", "Architecture-centric and component-based development", etc.
 * 
 * <p>Java-Klasse für Practice complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Practice">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Guidance">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="ActivityReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ContentReference" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SubPractice" type="{http://www.eclipse.org/epf/uma/1.0.6}Practice"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Practice", propOrder = {
    "activityReferenceOrContentReferenceOrSubPractice"
})
public class Practice
    extends Guidance
{

    @XmlElementRefs({
        @XmlElementRef(name = "ActivityReference", type = JAXBElement.class),
        @XmlElementRef(name = "ContentReference", type = JAXBElement.class),
        @XmlElementRef(name = "SubPractice", type = JAXBElement.class)
    })
    protected List<JAXBElement<?>> activityReferenceOrContentReferenceOrSubPractice;

    /**
     * Gets the value of the activityReferenceOrContentReferenceOrSubPractice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activityReferenceOrContentReferenceOrSubPractice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivityReferenceOrContentReferenceOrSubPractice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Practice }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getActivityReferenceOrContentReferenceOrSubPractice() {
        if (activityReferenceOrContentReferenceOrSubPractice == null) {
            activityReferenceOrContentReferenceOrSubPractice = new ArrayList<JAXBElement<?>>();
        }
        return this.activityReferenceOrContentReferenceOrSubPractice;
    }

    /**
     * Sets the value of the activityReferenceOrContentReferenceOrSubPractice property.
     * 
     * @param activityReferenceOrContentReferenceOrSubPractice
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link Practice }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setActivityReferenceOrContentReferenceOrSubPractice(List<JAXBElement<?>> activityReferenceOrContentReferenceOrSubPractice) {
        this.activityReferenceOrContentReferenceOrSubPractice = activityReferenceOrContentReferenceOrSubPractice;
    }

}
