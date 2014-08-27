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
 * A refineable hierarchy grouping related work products.  In other words, Domains can be further divided into sub-domains, with work product elements to be categorized only at the leaf-level of this hierarchy.
 * Domain is a logical grouping of work products that have an affinity to each other based on resources, timing, or relationship.  A Domain may be divided into subdomains.  For example, GS Method uses six predefined Domains for Work Products: Application, Architecture, Business, Engagement, Operations and Organization.
 * 
 * <p>Java-Klasse für Domain complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Domain">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentCategory">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="WorkProduct" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Subdomain" type="{http://www.eclipse.org/epf/uma/1.0.6}Domain"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Domain", propOrder = {
    "workProductOrSubdomain"
})
public class Domain
    extends ContentCategory
{

    @XmlElements({
        @XmlElement(name = "WorkProduct", type = String.class),
        @XmlElement(name = "Subdomain", type = Domain.class)
    })
    protected List<Object> workProductOrSubdomain;

    /**
     * Gets the value of the workProductOrSubdomain property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the workProductOrSubdomain property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWorkProductOrSubdomain().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link Domain }
     * 
     * 
     */
    public List<Object> getWorkProductOrSubdomain() {
        if (workProductOrSubdomain == null) {
            workProductOrSubdomain = new ArrayList<Object>();
        }
        return this.workProductOrSubdomain;
    }

    /**
     * Sets the value of the workProductOrSubdomain property.
     * 
     * @param workProductOrSubdomain
     *     allowed object is
     *     {@link String }
     *     {@link Domain }
     *     
     */
    public void setWorkProductOrSubdomain(List<Object> workProductOrSubdomain) {
        this.workProductOrSubdomain = workProductOrSubdomain;
    }

}
