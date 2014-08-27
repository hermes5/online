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
 * An abstract generalization of Method Elements for which external presentation names as well as content descriptions have been defined, such as Roles or Work Products.  Presentation Name and Content Descriptions are typically localized using a resource allocation mechanism for its String type attributes.
 * This abstraction represents all elements in the Method Content as well as Process space for which concrete textual descriptions are defined in the form of documenting attributes grouped in a matching Content Description instance.  Describable Elements are intended to be published in method or process publications (similar to the IBM Rational Unified Process web).  Describable Element defines that the element it represents will have content 'attached' to it.  Content Description is the abstraction for the actual places in which the content is being represented.  This separation allows a distinction between core method model elements describing the structure of the model from the actual description container providing, for example, the documentation of the content element in different alternatives languages, audiences, licensing levels, etc.
 * 
 * <p>Java-Klasse für DescribableElement complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="DescribableElement">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodElement">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="Presentation" type="{http://www.eclipse.org/epf/uma/1.0.6}ContentDescription"/>
 *         &lt;/choice>
 *         &lt;element name="Fulfill" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="shapeicon" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="nodeicon" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isAbstract" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescribableElement", propOrder = {
    "presentation",
    "fulfill"
})
public class DescribableElement
    extends MethodElement
{

    @XmlElement(name = "Presentation")
    protected ContentDescription presentation;
    @XmlElement(name = "Fulfill")
    protected List<String> fulfill;
    @XmlAttribute(name = "shapeicon")
    protected String shapeicon;
    @XmlAttribute(name = "nodeicon")
    protected String nodeicon;
    @XmlAttribute(name = "isAbstract")
    protected Boolean isAbstract;

    /**
     * Ruft den Wert der presentation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ContentDescription }
     *     
     */
    public ContentDescription getPresentation() {
        return presentation;
    }

    /**
     * Legt den Wert der presentation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ContentDescription }
     *     
     */
    public void setPresentation(ContentDescription value) {
        this.presentation = value;
    }

    /**
     * Gets the value of the fulfill property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fulfill property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFulfill().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFulfill() {
        if (fulfill == null) {
            fulfill = new ArrayList<String>();
        }
        return this.fulfill;
    }

    /**
     * Ruft den Wert der shapeicon-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShapeicon() {
        return shapeicon;
    }

    /**
     * Legt den Wert der shapeicon-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShapeicon(String value) {
        this.shapeicon = value;
    }

    /**
     * Ruft den Wert der nodeicon-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeicon() {
        return nodeicon;
    }

    /**
     * Legt den Wert der nodeicon-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeicon(String value) {
        this.nodeicon = value;
    }

    /**
     * Ruft den Wert der isAbstract-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAbstract() {
        return isAbstract;
    }

    /**
     * Legt den Wert der isAbstract-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAbstract(Boolean value) {
        this.isAbstract = value;
    }

    /**
     * Sets the value of the fulfill property.
     * 
     * @param fulfill
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFulfill(List<String> fulfill) {
        this.fulfill = fulfill;
    }

}
