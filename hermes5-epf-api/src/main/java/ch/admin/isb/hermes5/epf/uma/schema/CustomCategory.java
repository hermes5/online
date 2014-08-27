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
 * A category introduced by a method content author to structure any number of method Content Elements of any subtype based on user-defined criteria.  Because Content Categories (and therefore Custom Categories, too) are Content Elements themselves, Custom Categories can be used to recursively categorize Content Categories as well.  Custom Categories can also be nested with any Content Category.  Custom categories can be used to categorize content based on the user's criteria as well as to define whole tree-structures of nested categories allowing the user to systematically navigate and browse method content and processes based on these categories.  For example, one could create a custom category to logically organize content relevant for the user's development organization departments; e.g. a "Testing" category that groups together all roles, work products, tasks, and guidance element relevant to testing.  Another example would be categories that express licensing levels of the content grouping freely distributable method content versus content that represent intellectual property and requires a license to be purchased to be able to use it.
 * 
 * <p>Java-Klasse für CustomCategory complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CustomCategory">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentCategory">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="CategorizedElement" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SubCategory" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomCategory", propOrder = {
    "categorizedElementOrSubCategory"
})
public class CustomCategory
    extends ContentCategory
{

    @XmlElementRefs({
        @XmlElementRef(name = "CategorizedElement", type = JAXBElement.class),
        @XmlElementRef(name = "SubCategory", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> categorizedElementOrSubCategory;

    /**
     * Gets the value of the categorizedElementOrSubCategory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categorizedElementOrSubCategory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategorizedElementOrSubCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getCategorizedElementOrSubCategory() {
        if (categorizedElementOrSubCategory == null) {
            categorizedElementOrSubCategory = new ArrayList<JAXBElement<String>>();
        }
        return this.categorizedElementOrSubCategory;
    }

    /**
     * Sets the value of the categorizedElementOrSubCategory property.
     * 
     * @param categorizedElementOrSubCategory
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCategorizedElementOrSubCategory(List<JAXBElement<String>> categorizedElementOrSubCategory) {
        this.categorizedElementOrSubCategory = categorizedElementOrSubCategory;
    }

}
