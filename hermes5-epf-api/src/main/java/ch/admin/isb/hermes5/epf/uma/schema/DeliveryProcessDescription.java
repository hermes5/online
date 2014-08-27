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
 * A generalized Process Description that is used to store the textual description for a Delivery Process.
 * 
 * <p>Java-Klasse für DeliveryProcessDescription complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="DeliveryProcessDescription">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ProcessDescription">
 *       &lt;sequence>
 *         &lt;element name="Scale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProjectCharacteristics" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RiskLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EstimatingTechnique" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProjectMemberExpertise" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeOfContract" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeliveryProcessDescription", propOrder = {
    "scale",
    "projectCharacteristics",
    "riskLevel",
    "estimatingTechnique",
    "projectMemberExpertise",
    "typeOfContract"
})
public class DeliveryProcessDescription
    extends ProcessDescription
{

    @XmlElement(name = "Scale")
    protected String scale;
    @XmlElement(name = "ProjectCharacteristics")
    protected String projectCharacteristics;
    @XmlElement(name = "RiskLevel")
    protected String riskLevel;
    @XmlElement(name = "EstimatingTechnique")
    protected String estimatingTechnique;
    @XmlElement(name = "ProjectMemberExpertise")
    protected String projectMemberExpertise;
    @XmlElement(name = "TypeOfContract")
    protected String typeOfContract;

    /**
     * Ruft den Wert der scale-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScale() {
        return scale;
    }

    /**
     * Legt den Wert der scale-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScale(String value) {
        this.scale = value;
    }

    /**
     * Ruft den Wert der projectCharacteristics-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectCharacteristics() {
        return projectCharacteristics;
    }

    /**
     * Legt den Wert der projectCharacteristics-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectCharacteristics(String value) {
        this.projectCharacteristics = value;
    }

    /**
     * Ruft den Wert der riskLevel-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * Legt den Wert der riskLevel-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskLevel(String value) {
        this.riskLevel = value;
    }

    /**
     * Ruft den Wert der estimatingTechnique-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstimatingTechnique() {
        return estimatingTechnique;
    }

    /**
     * Legt den Wert der estimatingTechnique-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimatingTechnique(String value) {
        this.estimatingTechnique = value;
    }

    /**
     * Ruft den Wert der projectMemberExpertise-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectMemberExpertise() {
        return projectMemberExpertise;
    }

    /**
     * Legt den Wert der projectMemberExpertise-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectMemberExpertise(String value) {
        this.projectMemberExpertise = value;
    }

    /**
     * Ruft den Wert der typeOfContract-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeOfContract() {
        return typeOfContract;
    }

    /**
     * Legt den Wert der typeOfContract-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeOfContract(String value) {
        this.typeOfContract = value;
    }

}
