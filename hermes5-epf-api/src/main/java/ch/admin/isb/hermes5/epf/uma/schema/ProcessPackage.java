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
 * A special Method Package that contains Process Elements, only.
 * A key separation of concerns in UMA is the distinction between Method Content and Process.  This separation is enforced by special package types, which do not allow the mixing of method content with processes.
 * 
 * <p>Java-Klasse für ProcessPackage complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ProcessPackage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodPackage">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="ProcessElement" type="{http://www.eclipse.org/epf/uma/1.0.6}ProcessElement"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessPackage", propOrder = {
    "processElement"
})
public class ProcessPackage
    extends MethodPackage
{

    @XmlElement(name = "ProcessElement")
    protected List<ProcessElement> processElement;

    /**
     * Gets the value of the processElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcessElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProcessElement }
     * 
     * 
     */
    public List<ProcessElement> getProcessElement() {
        if (processElement == null) {
            processElement = new ArrayList<ProcessElement>();
        }
        return this.processElement;
    }

    /**
     * Sets the value of the processElement property.
     * 
     * @param processElement
     *     allowed object is
     *     {@link ProcessElement }
     *     
     */
    public void setProcessElement(List<ProcessElement> processElement) {
        this.processElement = processElement;
    }

}
