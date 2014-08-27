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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * Comprises of a list of interface specifications (similar to operation declarations) that express inputs and outputs for a process component.  These interface specifications are expressed using Task Descriptors which are not linked to Tasks that are related to Work Product Descriptors as well as optional a Role Descriptor.
 * 
 * <p>Java-Klasse für ProcessComponentInterface complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ProcessComponentInterface">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}BreakdownElement">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="InterfaceSpecification" type="{http://www.eclipse.org/epf/uma/1.0.6}TaskDescriptor"/>
 *         &lt;element name="InterfaceIO" type="{http://www.eclipse.org/epf/uma/1.0.6}WorkProductDescriptor"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessComponentInterface", propOrder = {
    "interfaceSpecificationOrInterfaceIO"
})
public class ProcessComponentInterface
    extends BreakdownElement
{

    @XmlElements({
        @XmlElement(name = "InterfaceSpecification", type = TaskDescriptor.class),
        @XmlElement(name = "InterfaceIO", type = WorkProductDescriptor.class)
    })
    protected List<BreakdownElement> interfaceSpecificationOrInterfaceIO;

    /**
     * Gets the value of the interfaceSpecificationOrInterfaceIO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interfaceSpecificationOrInterfaceIO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInterfaceSpecificationOrInterfaceIO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaskDescriptor }
     * {@link WorkProductDescriptor }
     * 
     * 
     */
    public List<BreakdownElement> getInterfaceSpecificationOrInterfaceIO() {
        if (interfaceSpecificationOrInterfaceIO == null) {
            interfaceSpecificationOrInterfaceIO = new ArrayList<BreakdownElement>();
        }
        return this.interfaceSpecificationOrInterfaceIO;
    }

    /**
     * Sets the value of the interfaceSpecificationOrInterfaceIO property.
     * 
     * @param interfaceSpecificationOrInterfaceIO
     *     allowed object is
     *     {@link TaskDescriptor }
     *     {@link WorkProductDescriptor }
     *     
     */
    public void setInterfaceSpecificationOrInterfaceIO(List<BreakdownElement> interfaceSpecificationOrInterfaceIO) {
        this.interfaceSpecificationOrInterfaceIO = interfaceSpecificationOrInterfaceIO;
    }

}
