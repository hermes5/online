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
 * A second category for work products, which in contrast to Domain is more presentation oriented.  A work product can have many Work Product Types.  Examples, for a Work Product Type is "Class Diagram", which categorizes the Artifacts Analysis Model, Design Model, User Experience Model, or "Specification", which categorizes requirements specifications that define a system with a well-defined system boundary, such as use case or functional requirements specification.  A Work Product can be categorized to be of many Work Product Types.  For example, a use case model can be categorized as a Specification as well as Diagram Work Product Type.
 * 
 * <p>Java-Klasse für WorkProductType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WorkProductType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentCategory">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="WorkProduct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkProductType", propOrder = {
    "workProduct"
})
public class WorkProductType
    extends ContentCategory
{

    @XmlElement(name = "WorkProduct")
    protected List<String> workProduct;

    /**
     * Gets the value of the workProduct property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the workProduct property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkProduct().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getWorkProduct() {
        if (workProduct == null) {
            workProduct = new ArrayList<String>();
        }
        return this.workProduct;
    }

    /**
     * Sets the value of the workProduct property.
     * 
     * @param workProduct
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkProduct(List<String> workProduct) {
        this.workProduct = workProduct;
    }

}
