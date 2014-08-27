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
 * A generalized Content Description that is used to store the textual description for a Practice.
 * 
 * <p>Java-Klasse für PracticeDescription complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="PracticeDescription">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentDescription">
 *       &lt;sequence>
 *         &lt;element name="AdditionalInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Application" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Background" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Goals" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LevelsOfAdoption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Problem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PracticeDescription", propOrder = {
    "additionalInfo",
    "application",
    "background",
    "goals",
    "levelsOfAdoption",
    "problem"
})
public class PracticeDescription
    extends ContentDescription
{

    @XmlElement(name = "AdditionalInfo")
    protected String additionalInfo;
    @XmlElement(name = "Application")
    protected String application;
    @XmlElement(name = "Background")
    protected String background;
    @XmlElement(name = "Goals")
    protected String goals;
    @XmlElement(name = "LevelsOfAdoption")
    protected String levelsOfAdoption;
    @XmlElement(name = "Problem")
    protected String problem;

    /**
     * Ruft den Wert der additionalInfo-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Legt den Wert der additionalInfo-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

    /**
     * Ruft den Wert der application-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplication() {
        return application;
    }

    /**
     * Legt den Wert der application-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplication(String value) {
        this.application = value;
    }

    /**
     * Ruft den Wert der background-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackground() {
        return background;
    }

    /**
     * Legt den Wert der background-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackground(String value) {
        this.background = value;
    }

    /**
     * Ruft den Wert der goals-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoals() {
        return goals;
    }

    /**
     * Legt den Wert der goals-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoals(String value) {
        this.goals = value;
    }

    /**
     * Ruft den Wert der levelsOfAdoption-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevelsOfAdoption() {
        return levelsOfAdoption;
    }

    /**
     * Legt den Wert der levelsOfAdoption-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevelsOfAdoption(String value) {
        this.levelsOfAdoption = value;
    }

    /**
     * Ruft den Wert der problem-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProblem() {
        return problem;
    }

    /**
     * Legt den Wert der problem-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProblem(String value) {
        this.problem = value;
    }

}
