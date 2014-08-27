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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A Work Product that provides a description and definition for tangible work product types. Artifacts may be composed of other artifacts. For example, a model artifact can be composed of model elements, which are also artifacts.
 * Artifacts are tangible work products consumed, produced, or modified by Tasks.  It may serve as a basis for defining reusable assets.  Roles use Artifacts to perform Tasks and produce Artifacts in the course of performing Tasks.  Artifacts are the responsibility of a single Role, making responsibility easy to identify and understand, and promoting the idea that every piece of information produced in the method requires the appropriate set of skills. Even though one role might "own" a specific type of Artifacts, other roles can still use the Artifacts; perhaps even update them if the Role has been given permission to do so.
 * 
 * <p>Java-Klasse für Artifact complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Artifact">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}WorkProduct">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="ContainedArtifact" type="{http://www.eclipse.org/epf/uma/1.0.6}Artifact"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Artifact", propOrder = {
    "containedArtifact"
})
public class Artifact
    extends WorkProduct
{

    @XmlElement(name = "ContainedArtifact")
    protected List<Artifact> containedArtifact;

    /**
     * Gets the value of the containedArtifact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the containedArtifact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContainedArtifact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Artifact }
     * 
     * 
     */
    public List<Artifact> getContainedArtifact() {
        if (containedArtifact == null) {
            containedArtifact = new ArrayList<Artifact>();
        }
        return this.containedArtifact;
    }

    /**
     * Sets the value of the containedArtifact property.
     * 
     * @param containedArtifact
     *     allowed object is
     *     {@link Artifact }
     *     
     */
    public void setContainedArtifact(List<Artifact> containedArtifact) {
        this.containedArtifact = containedArtifact;
    }

}
