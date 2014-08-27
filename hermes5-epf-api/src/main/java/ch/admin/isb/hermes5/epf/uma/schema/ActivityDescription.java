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
 * A generalized Breakdown Element Description that is used to store the textual description for an Activity.
 * 
 * <p>Java-Klasse für ActivityDescription complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ActivityDescription">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}BreakdownElementDescription">
 *       &lt;sequence>
 *         &lt;element name="Alternatives" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HowToStaff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActivityDescription", propOrder = {
    "alternatives",
    "howToStaff",
    "purpose"
})
public class ActivityDescription
    extends BreakdownElementDescription
{

    @XmlElement(name = "Alternatives")
    protected String alternatives;
    @XmlElement(name = "HowToStaff")
    protected String howToStaff;
    @XmlElement(name = "Purpose")
    protected String purpose;

    /**
     * Ruft den Wert der alternatives-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternatives() {
        return alternatives;
    }

    /**
     * Legt den Wert der alternatives-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternatives(String value) {
        this.alternatives = value;
    }

    /**
     * Ruft den Wert der howToStaff-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHowToStaff() {
        return howToStaff;
    }

    /**
     * Legt den Wert der howToStaff-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHowToStaff(String value) {
        this.howToStaff = value;
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

}
