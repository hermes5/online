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
 * A generalized Work Product Description that is used to store the textual description for a Deliverable.
 * 
 * <p>Java-Klasse für DeliverableDescription complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="DeliverableDescription">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}WorkProductDescription">
 *       &lt;sequence>
 *         &lt;element name="ExternalDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PackagingGuidance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliverableDescription", propOrder = {
    "externalDescription",
    "packagingGuidance"
})
public class DeliverableDescription
    extends WorkProductDescription
{

    @XmlElement(name = "ExternalDescription")
    protected String externalDescription;
    @XmlElement(name = "PackagingGuidance")
    protected String packagingGuidance;

    /**
     * Ruft den Wert der externalDescription-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalDescription() {
        return externalDescription;
    }

    /**
     * Legt den Wert der externalDescription-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalDescription(String value) {
        this.externalDescription = value;
    }

    /**
     * Ruft den Wert der packagingGuidance-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackagingGuidance() {
        return packagingGuidance;
    }

    /**
     * Legt den Wert der packagingGuidance-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackagingGuidance(String value) {
        this.packagingGuidance = value;
    }

}
