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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * A specific type of Guidance that provides sizing measures, or standards for sizing the work effort associated with performing a particular piece of work and instructions for their successful use. It may be comprised of estimation considerations and estimation metrics.
 * 
 * <p>Java-Klasse für Estimate complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Estimate">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Guidance">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="EstimationMetric" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EstimationConsiderations" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Estimate", propOrder = {
    "estimationMetricOrEstimationConsiderations"
})
public class Estimate
    extends Guidance
{

    @XmlElementRefs({
        @XmlElementRef(name = "EstimationMetric", type = JAXBElement.class),
        @XmlElementRef(name = "EstimationConsiderations", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> estimationMetricOrEstimationConsiderations;

    /**
     * Gets the value of the estimationMetricOrEstimationConsiderations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the estimationMetricOrEstimationConsiderations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEstimationMetricOrEstimationConsiderations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getEstimationMetricOrEstimationConsiderations() {
        if (estimationMetricOrEstimationConsiderations == null) {
            estimationMetricOrEstimationConsiderations = new ArrayList<JAXBElement<String>>();
        }
        return this.estimationMetricOrEstimationConsiderations;
    }

    /**
     * Sets the value of the estimationMetricOrEstimationConsiderations property.
     * 
     * @param estimationMetricOrEstimationConsiderations
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEstimationMetricOrEstimationConsiderations(List<JAXBElement<String>> estimationMetricOrEstimationConsiderations) {
        this.estimationMetricOrEstimationConsiderations = estimationMetricOrEstimationConsiderations;
    }

}
