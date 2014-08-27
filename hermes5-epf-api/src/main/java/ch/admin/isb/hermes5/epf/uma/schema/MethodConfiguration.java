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
 * A collection of selected Method Models and MethodPackages. A configuration can be exported into its own standalone library when it includes the full transitive closure of all elements all other elements depend on.
 * 
 * <p>Java-Klasse für MethodConfiguration complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="MethodConfiguration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodUnit">
 *       &lt;sequence>
 *         &lt;element name="BaseConfiguration" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MethodPluginSelection" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MethodPackageSelection" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DefaultView" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProcessView" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SubtractedCategory" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AddedCategory" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MethodConfiguration", propOrder = {
    "baseConfiguration",
    "methodPluginSelection",
    "methodPackageSelection",
    "defaultView",
    "processView",
    "subtractedCategory",
    "addedCategory"
})
public class MethodConfiguration
    extends MethodUnit
{

    @XmlElement(name = "BaseConfiguration")
    protected List<String> baseConfiguration;
    @XmlElement(name = "MethodPluginSelection")
    protected List<String> methodPluginSelection;
    @XmlElement(name = "MethodPackageSelection")
    protected List<String> methodPackageSelection;
    @XmlElement(name = "DefaultView")
    protected String defaultView;
    @XmlElement(name = "ProcessView")
    protected List<String> processView;
    @XmlElement(name = "SubtractedCategory")
    protected List<String> subtractedCategory;
    @XmlElement(name = "AddedCategory")
    protected List<String> addedCategory;

    /**
     * Gets the value of the baseConfiguration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the baseConfiguration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBaseConfiguration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getBaseConfiguration() {
        if (baseConfiguration == null) {
            baseConfiguration = new ArrayList<String>();
        }
        return this.baseConfiguration;
    }

    /**
     * Gets the value of the methodPluginSelection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the methodPluginSelection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethodPluginSelection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMethodPluginSelection() {
        if (methodPluginSelection == null) {
            methodPluginSelection = new ArrayList<String>();
        }
        return this.methodPluginSelection;
    }

    /**
     * Gets the value of the methodPackageSelection property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the methodPackageSelection property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethodPackageSelection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMethodPackageSelection() {
        if (methodPackageSelection == null) {
            methodPackageSelection = new ArrayList<String>();
        }
        return this.methodPackageSelection;
    }

    /**
     * Ruft den Wert der defaultView-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultView() {
        return defaultView;
    }

    /**
     * Legt den Wert der defaultView-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultView(String value) {
        this.defaultView = value;
    }

    /**
     * Gets the value of the processView property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the processView property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProcessView().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getProcessView() {
        if (processView == null) {
            processView = new ArrayList<String>();
        }
        return this.processView;
    }

    /**
     * Gets the value of the subtractedCategory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subtractedCategory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubtractedCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSubtractedCategory() {
        if (subtractedCategory == null) {
            subtractedCategory = new ArrayList<String>();
        }
        return this.subtractedCategory;
    }

    /**
     * Gets the value of the addedCategory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addedCategory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddedCategory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAddedCategory() {
        if (addedCategory == null) {
            addedCategory = new ArrayList<String>();
        }
        return this.addedCategory;
    }

    /**
     * Sets the value of the baseConfiguration property.
     * 
     * @param baseConfiguration
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseConfiguration(List<String> baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
    }

    /**
     * Sets the value of the methodPluginSelection property.
     * 
     * @param methodPluginSelection
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodPluginSelection(List<String> methodPluginSelection) {
        this.methodPluginSelection = methodPluginSelection;
    }

    /**
     * Sets the value of the methodPackageSelection property.
     * 
     * @param methodPackageSelection
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodPackageSelection(List<String> methodPackageSelection) {
        this.methodPackageSelection = methodPackageSelection;
    }

    /**
     * Sets the value of the processView property.
     * 
     * @param processView
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessView(List<String> processView) {
        this.processView = processView;
    }

    /**
     * Sets the value of the subtractedCategory property.
     * 
     * @param subtractedCategory
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubtractedCategory(List<String> subtractedCategory) {
        this.subtractedCategory = subtractedCategory;
    }

    /**
     * Sets the value of the addedCategory property.
     * 
     * @param addedCategory
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddedCategory(List<String> addedCategory) {
        this.addedCategory = addedCategory;
    }

}
