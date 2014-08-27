/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/

package ch.admin.isb.hermes5.epf.uma.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A generalized Work Product Description that is used to store the textual description for an Artifact.
 * 
 * <p>Java-Klasse für ArtifactDescription complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ArtifactDescription">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}WorkProductDescription">
 *       &lt;sequence>
 *         &lt;element name="BriefOutline" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RepresentationOptions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Representation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Notation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArtifactDescription", propOrder = {
    "briefOutline",
    "representationOptions",
    "representation",
    "notation"
})
public class ArtifactDescription
    extends WorkProductDescription
{

    @XmlElement(name = "BriefOutline")
    protected String briefOutline;
    @XmlElement(name = "RepresentationOptions")
    protected String representationOptions;
    @XmlElement(name = "Representation")
    protected String representation;
    @XmlElement(name = "Notation")
    protected String notation;

    /**
     * Ruft den Wert der briefOutline-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBriefOutline() {
        return briefOutline;
    }

    /**
     * Legt den Wert der briefOutline-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBriefOutline(String value) {
        this.briefOutline = value;
    }

    /**
     * Ruft den Wert der representationOptions-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepresentationOptions() {
        return representationOptions;
    }

    /**
     * Legt den Wert der representationOptions-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepresentationOptions(String value) {
        this.representationOptions = value;
    }

    /**
     * Ruft den Wert der representation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepresentation() {
        return representation;
    }

    /**
     * Legt den Wert der representation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepresentation(String value) {
        this.representation = value;
    }

    /**
     * Ruft den Wert der notation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotation() {
        return notation;
    }

    /**
     * Legt den Wert der notation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotation(String value) {
        this.notation = value;
    }

}
