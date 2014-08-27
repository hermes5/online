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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * An abstract generalization for special Breakdown Elements that references one concrete Content Element.  A descriptor provides a representation of a Content Element within breakdown structures.  In addition to just referencing Content Elements it allows overriding the Content Elements structural relationships by defining its own sets of associations.
 * Descriptors are the key concept for realizing the separation of processes from method content.  A Descriptor can be characterized as a reference object for one particular Content Element, which has its own relationships and properties.  When a Descriptor is created it shall be provided with congruent copies of the relationships defined for the referenced content element.  However, a user can modify these relationships for the particular process situation for which the descriptor has been created. 
 * 
 * <p>Java-Klasse für Descriptor complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Descriptor">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}BreakdownElement">
 *       &lt;attribute name="isSynchronizedWithSource" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Descriptor")
public class Descriptor
    extends BreakdownElement
{

    @XmlAttribute(name = "isSynchronizedWithSource")
    protected Boolean isSynchronizedWithSource;

    /**
     * Ruft den Wert der isSynchronizedWithSource-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSynchronizedWithSource() {
        return isSynchronizedWithSource;
    }

    /**
     * Legt den Wert der isSynchronizedWithSource-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSynchronizedWithSource(Boolean value) {
        this.isSynchronizedWithSource = value;
    }

}
