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
 * A generalized Method Element that is used to store the textual description for a Content Element.  It defines standard attributes applicable for all Content Element types.  Specific Content Element sub-types can define their own matching Content Description sub-types. 
 * 
 * <p>Java-Klasse für ContentDescription complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ContentDescription">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodUnit">
 *       &lt;sequence>
 *         &lt;element name="MainDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="KeyConsiderations" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Section" type="{http://www.eclipse.org/epf/uma/1.0.6}Section" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="externalId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContentDescription", propOrder = {
    "mainDescription",
    "keyConsiderations",
    "section"
})
public class ContentDescription
    extends MethodUnit
{

    @XmlElement(name = "MainDescription")
    protected String mainDescription;
    @XmlElement(name = "KeyConsiderations")
    protected String keyConsiderations;
    @XmlElement(name = "Section")
    protected List<Section> section;
    @XmlAttribute(name = "externalId")
    protected String externalId;

    /**
     * Ruft den Wert der mainDescription-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainDescription() {
        return mainDescription;
    }

    /**
     * Legt den Wert der mainDescription-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainDescription(String value) {
        this.mainDescription = value;
    }

    /**
     * Ruft den Wert der keyConsiderations-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyConsiderations() {
        return keyConsiderations;
    }

    /**
     * Legt den Wert der keyConsiderations-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyConsiderations(String value) {
        this.keyConsiderations = value;
    }

    /**
     * Gets the value of the section property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the section property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSection().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section }
     * 
     * 
     */
    public List<Section> getSection() {
        if (section == null) {
            section = new ArrayList<Section>();
        }
        return this.section;
    }

    /**
     * Ruft den Wert der externalId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Legt den Wert der externalId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalId(String value) {
        this.externalId = value;
    }

    /**
     * Sets the value of the section property.
     * 
     * @param section
     *     allowed object is
     *     {@link Section }
     *     
     */
    public void setSection(List<Section> section) {
        this.section = section;
    }

}
