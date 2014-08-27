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
 * A special Activity that describes a structure for particular types of development projects.  To perform such a development project a Processes would be 'instantiated' and adapted for the specific situation.  Process is an abstract class and this meta-model defines different special types of Processes for different process management applications and different situations of process reuse.  Every Process comprises of and is the top-level element of an n-level breakdown structure using the Nesting association defined on Activity.
 * Core Method Content provides step-by-step explanations, describing how very specific development goals are achieved independent of the placement of these steps within a development lifecycle.  Processes take these method elements and relate them into semi-ordered sequences that are customized to specific types of projects.  Thus, a process is a set of partially ordered work descriptions intended to reach a higher development goal, such as the release of a specific software system.  A process and the process meta-model structure defined in this specification focuses on the lifecycle and the sequencing of work in breakdown structures.  To achieve this it uses the Descriptor concept referencing method content and allowing defining time-specific customizations of the referenced content (e.g. defining a focus on different steps of the same Task and providing input Work Products in different states within the different Phases of a process lifecycle in which the same Task is performed).
 * 
 * <p>Java-Klasse für Process complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Process">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Activity">
 *       &lt;sequence>
 *         &lt;element name="IncludesPattern" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DefaultContext" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValidContext" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="diagramURI" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Process", propOrder = {
    "includesPattern",
    "defaultContext",
    "validContext"
})
public class Process
    extends Activity
{

    @XmlElement(name = "IncludesPattern")
    protected List<String> includesPattern;
    @XmlElement(name = "DefaultContext")
    protected String defaultContext;
    @XmlElement(name = "ValidContext")
    protected List<String> validContext;
    @XmlAttribute(name = "diagramURI")
    protected String diagramURI;

    /**
     * Gets the value of the includesPattern property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includesPattern property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludesPattern().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIncludesPattern() {
        if (includesPattern == null) {
            includesPattern = new ArrayList<String>();
        }
        return this.includesPattern;
    }

    /**
     * Ruft den Wert der defaultContext-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultContext() {
        return defaultContext;
    }

    /**
     * Legt den Wert der defaultContext-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultContext(String value) {
        this.defaultContext = value;
    }

    /**
     * Gets the value of the validContext property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the validContext property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValidContext().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getValidContext() {
        if (validContext == null) {
            validContext = new ArrayList<String>();
        }
        return this.validContext;
    }

    /**
     * Ruft den Wert der diagramURI-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagramURI() {
        return diagramURI;
    }

    /**
     * Legt den Wert der diagramURI-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagramURI(String value) {
        this.diagramURI = value;
    }

    /**
     * Sets the value of the includesPattern property.
     * 
     * @param includesPattern
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncludesPattern(List<String> includesPattern) {
        this.includesPattern = includesPattern;
    }

    /**
     * Sets the value of the validContext property.
     * 
     * @param validContext
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidContext(List<String> validContext) {
        this.validContext = validContext;
    }

}
