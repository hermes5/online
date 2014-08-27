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
 * A Method Library is a physical container for Method Plugins and Method Configuration definitions.  All Method Elements are stored in a Method Library.
 * 
 * <p>Java-Klasse für MethodLibrary complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MethodLibrary">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodUnit">
 *       &lt;sequence>
 *         &lt;element name="MethodPlugin" type="{http://www.eclipse.org/epf/uma/1.0.6}MethodPlugin" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MethodConfiguration" type="{http://www.eclipse.org/epf/uma/1.0.6}MethodConfiguration" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="tool" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodLibrary", propOrder = {
    "methodPlugin",
    "methodConfiguration"
})
public class MethodLibrary
    extends MethodUnit
{

    @XmlElement(name = "MethodPlugin")
    protected List<MethodPlugin> methodPlugin;
    @XmlElement(name = "MethodConfiguration")
    protected List<MethodConfiguration> methodConfiguration;
    @XmlAttribute(name = "tool")
    protected String tool;

    /**
     * Gets the value of the methodPlugin property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the methodPlugin property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethodPlugin().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MethodPlugin }
     * 
     * 
     */
    public List<MethodPlugin> getMethodPlugin() {
        if (methodPlugin == null) {
            methodPlugin = new ArrayList<MethodPlugin>();
        }
        return this.methodPlugin;
    }

    /**
     * Gets the value of the methodConfiguration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the methodConfiguration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethodConfiguration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MethodConfiguration }
     * 
     * 
     */
    public List<MethodConfiguration> getMethodConfiguration() {
        if (methodConfiguration == null) {
            methodConfiguration = new ArrayList<MethodConfiguration>();
        }
        return this.methodConfiguration;
    }

    /**
     * Ruft den Wert der tool-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTool() {
        return tool;
    }

    /**
     * Legt den Wert der tool-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTool(String value) {
        this.tool = value;
    }

    /**
     * Sets the value of the methodPlugin property.
     * 
     * @param methodPlugin
     *     allowed object is
     *     {@link MethodPlugin }
     *     
     */
    public void setMethodPlugin(List<MethodPlugin> methodPlugin) {
        this.methodPlugin = methodPlugin;
    }

    /**
     * Sets the value of the methodConfiguration property.
     * 
     * @param methodConfiguration
     *     allowed object is
     *     {@link MethodConfiguration }
     *     
     */
    public void setMethodConfiguration(List<MethodConfiguration> methodConfiguration) {
        this.methodConfiguration = methodConfiguration;
    }

}
