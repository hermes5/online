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
 * An abstract class which provides a generalization for the content element types Artifact, Outcome, and Deliverable.  The meta-model class Work Product actually represents work product types, i.e. an instance of Work Product is a description of a specific type of work product and not an individual work product instance.  However, for simplicity reasons and because of low risk of misinterpretation we did not append the word 'type' to every meta-class.
 * A work product is an abstraction for descriptions of content elements that are used to define anything used, produced, or modified by a task.
 * 
 * <p>Java-Klasse für WorkProduct complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="WorkProduct">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentElement">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Estimate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EstimationConsiderations" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Report" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Template" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ToolMentor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkProduct", propOrder = {
    "estimateOrEstimationConsiderationsOrReport"
})
public class WorkProduct
    extends ContentElement
{

    @XmlElementRefs({
        @XmlElementRef(name = "ToolMentor", type = JAXBElement.class),
        @XmlElementRef(name = "Template", type = JAXBElement.class),
        @XmlElementRef(name = "Report", type = JAXBElement.class),
        @XmlElementRef(name = "EstimationConsiderations", type = JAXBElement.class),
        @XmlElementRef(name = "Estimate", type = JAXBElement.class)
    })
    protected List<JAXBElement<String>> estimateOrEstimationConsiderationsOrReport;

    /**
     * Gets the value of the estimateOrEstimationConsiderationsOrReport property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the estimateOrEstimationConsiderationsOrReport property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEstimateOrEstimationConsiderationsOrReport().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getEstimateOrEstimationConsiderationsOrReport() {
        if (estimateOrEstimationConsiderationsOrReport == null) {
            estimateOrEstimationConsiderationsOrReport = new ArrayList<JAXBElement<String>>();
        }
        return this.estimateOrEstimationConsiderationsOrReport;
    }

    /**
     * Sets the value of the estimateOrEstimationConsiderationsOrReport property.
     * 
     * @param estimateOrEstimationConsiderationsOrReport
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEstimateOrEstimationConsiderationsOrReport(List<JAXBElement<String>> estimateOrEstimationConsiderationsOrReport) {
        this.estimateOrEstimationConsiderationsOrReport = estimateOrEstimationConsiderationsOrReport;
    }

}
