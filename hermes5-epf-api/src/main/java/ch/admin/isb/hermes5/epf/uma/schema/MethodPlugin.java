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
import javax.xml.bind.annotation.XmlType;


/**
 * A special Method Unit that represents a physical container for Method Packages.  It defines a granularity level for the modularization and organization of method content and processes.  A Method Plugin can extend many other Method Plugins and it can be extended by many Method Plugins.  It can also be used stand-alone, i.e. with no Extension relationship to other plug-ins.
 * Method Plugin conceptually represents a unit for configuration, modularization, extension, packaging, and deployment of method content and processes.  A Process Engineer shall design his Plugins and allocate his content to these Plugins with requirements for extensibility, modularity, reuse, and maintainability in mind.
 * Special extensibility mechanisms defined for the meta-classes Variability Element and Process Contribution allow Plugin content to directly contribute new content, replace existing content, or to cross-reference to any Content Element or Process within another Plugin that it extends.  Similar to UML 2.0's 'package merge' mechanism transformation interpretations, interpreting these Method Plugin mechanisms results into new extended Method Content and Processes.
 * 
 * <p>Java-Klasse für MethodPlugin complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MethodPlugin">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodUnit">
 *       &lt;sequence>
 *         &lt;element name="ReferencedMethodPlugin" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MethodPackage" type="{http://www.eclipse.org/epf/uma/1.0.6}MethodPackage" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="userChangeable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="supporting" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodPlugin", propOrder = {
    "referencedMethodPlugin",
    "methodPackage"
})
public class MethodPlugin
    extends MethodUnit
{

    @XmlElement(name = "ReferencedMethodPlugin")
    protected List<String> referencedMethodPlugin;
    @XmlElement(name = "MethodPackage")
    protected List<MethodPackage> methodPackage;
    @XmlAttribute(name = "userChangeable")
    protected Boolean userChangeable;
    @XmlAttribute(name = "supporting")
    protected Boolean supporting;

    /**
     * Gets the value of the referencedMethodPlugin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referencedMethodPlugin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferencedMethodPlugin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getReferencedMethodPlugin() {
        if (referencedMethodPlugin == null) {
            referencedMethodPlugin = new ArrayList<String>();
        }
        return this.referencedMethodPlugin;
    }

    /**
     * Gets the value of the methodPackage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the methodPackage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethodPackage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MethodPackage }
     * 
     * 
     */
    public List<MethodPackage> getMethodPackage() {
        if (methodPackage == null) {
            methodPackage = new ArrayList<MethodPackage>();
        }
        return this.methodPackage;
    }

    /**
     * Ruft den Wert der userChangeable-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUserChangeable() {
        return userChangeable;
    }

    /**
     * Legt den Wert der userChangeable-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUserChangeable(Boolean value) {
        this.userChangeable = value;
    }

    /**
     * Ruft den Wert der supporting-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSupporting() {
        return supporting;
    }

    /**
     * Legt den Wert der supporting-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSupporting(Boolean value) {
        this.supporting = value;
    }

    /**
     * Sets the value of the referencedMethodPlugin property.
     * 
     * @param referencedMethodPlugin
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferencedMethodPlugin(List<String> referencedMethodPlugin) {
        this.referencedMethodPlugin = referencedMethodPlugin;
    }

    /**
     * Sets the value of the methodPackage property.
     * 
     * @param methodPackage
     *     allowed object is
     *     {@link MethodPackage }
     *     
     */
    public void setMethodPackage(List<MethodPackage> methodPackage) {
        this.methodPackage = methodPackage;
    }

}
