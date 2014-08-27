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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * An abstract class for packaging Method Elements.  All Method Elements shall be located in exactly one of Method Package's concrete specializations (e.g. Content Package).   Method Package defines common properties for all of its specializations. Elements are organized in Method Packages to structure large scale of method content and processes as well as to define a mechanism for reuse.  Method Elements from one package can reuse element from other packages by defining a reusedPackages link.  For example, a work product defined in one package can be used as an input for Tasks defined in other packages.  By reusing it from one common place (i.e. the package in which it has been defined) ensures that no redundant definitions of the same elements are required.  Also maintenance of method content is greatly improved as changes can be performed in only one place.  Note, that other packages will introduce more specializations of Method Package, e.g. Process Package and Process Component.
 * 
 * <p>Java-Klasse für MethodPackage complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MethodPackage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodElement">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="ReusedPackage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MethodPackage" type="{http://www.eclipse.org/epf/uma/1.0.6}MethodPackage"/>
 *       &lt;/choice>
 *       &lt;attribute name="global" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodPackage", propOrder = {
    "reusedPackageOrMethodPackage"
})
public class MethodPackage
    extends MethodElement
{

    @XmlElements({
        @XmlElement(name = "ReusedPackage", type = String.class),
        @XmlElement(name = "MethodPackage", type = MethodPackage.class)
    })
    protected List<Object> reusedPackageOrMethodPackage;
    @XmlAttribute(name = "global")
    protected Boolean global;

    /**
     * Gets the value of the reusedPackageOrMethodPackage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reusedPackageOrMethodPackage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReusedPackageOrMethodPackage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * {@link MethodPackage }
     * 
     * 
     */
    public List<Object> getReusedPackageOrMethodPackage() {
        if (reusedPackageOrMethodPackage == null) {
            reusedPackageOrMethodPackage = new ArrayList<Object>();
        }
        return this.reusedPackageOrMethodPackage;
    }

    /**
     * Ruft den Wert der global-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGlobal() {
        return global;
    }

    /**
     * Legt den Wert der global-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGlobal(Boolean value) {
        this.global = value;
    }

    /**
     * Sets the value of the reusedPackageOrMethodPackage property.
     * 
     * @param reusedPackageOrMethodPackage
     *     allowed object is
     *     {@link String }
     *     {@link MethodPackage }
     *     
     */
    public void setReusedPackageOrMethodPackage(List<Object> reusedPackageOrMethodPackage) {
        this.reusedPackageOrMethodPackage = reusedPackageOrMethodPackage;
    }

}
