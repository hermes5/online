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
 * A generalized Content Description that is used to store the textual description for a Work Product.
 * 
 * <p>Java-Klasse für WorkProductDescription complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WorkProductDescription">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentDescription">
 *       &lt;sequence>
 *         &lt;element name="ImpactOfNotHaving" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReasonsForNotNeeding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkProductDescription", propOrder = {
    "impactOfNotHaving",
    "purpose",
    "reasonsForNotNeeding"
})
public class WorkProductDescription
    extends ContentDescription
{

    @XmlElement(name = "ImpactOfNotHaving")
    protected String impactOfNotHaving;
    @XmlElement(name = "Purpose")
    protected String purpose;
    @XmlElement(name = "ReasonsForNotNeeding")
    protected String reasonsForNotNeeding;

    /**
     * Ruft den Wert der impactOfNotHaving-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpactOfNotHaving() {
        return impactOfNotHaving;
    }

    /**
     * Legt den Wert der impactOfNotHaving-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpactOfNotHaving(String value) {
        this.impactOfNotHaving = value;
    }

    /**
     * Ruft den Wert der purpose-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Legt den Wert der purpose-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Ruft den Wert der reasonsForNotNeeding-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonsForNotNeeding() {
        return reasonsForNotNeeding;
    }

    /**
     * Legt den Wert der reasonsForNotNeeding-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonsForNotNeeding(String value) {
        this.reasonsForNotNeeding = value;
    }

}
