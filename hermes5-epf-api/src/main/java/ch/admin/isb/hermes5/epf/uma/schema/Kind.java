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
 * <p>Java-Klasse für Kind complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Kind">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}ContentElement">
 *       &lt;sequence>
 *         &lt;element name="ApplicableMetaClassInfo" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kind", propOrder = {
    "applicableMetaClassInfo"
})
public class Kind
    extends ContentElement
{

    @XmlElement(name = "ApplicableMetaClassInfo")
    protected List<String> applicableMetaClassInfo;

    /**
     * Gets the value of the applicableMetaClassInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicableMetaClassInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicableMetaClassInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getApplicableMetaClassInfo() {
        if (applicableMetaClassInfo == null) {
            applicableMetaClassInfo = new ArrayList<String>();
        }
        return this.applicableMetaClassInfo;
    }

    /**
     * Sets the value of the applicableMetaClassInfo property.
     * 
     * @param applicableMetaClassInfo
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicableMetaClassInfo(List<String> applicableMetaClassInfo) {
        this.applicableMetaClassInfo = applicableMetaClassInfo;
    }

}
