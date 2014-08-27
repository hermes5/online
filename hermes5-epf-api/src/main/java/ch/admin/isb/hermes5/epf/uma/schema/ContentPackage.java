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
 * A special Method Package that contains Content Elements and Content Elements, only.  Examples for Content Element are Artifacts, Tasks, Roles, etc.  A key separation of concerns in UMA is the distinction between Method Content and Process.  This separation is enforced by special package types, which do not allow the mixing of method content with processes.
 * 
 * <p>Java-Klasse für ContentPackage complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ContentPackage">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}MethodPackage">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="ContentElement" type="{http://www.eclipse.org/epf/uma/1.0.6}ContentElement"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContentPackage", propOrder = {
    "contentElement"
})
public class ContentPackage
    extends MethodPackage
{

    @XmlElement(name = "ContentElement")
    protected List<ContentElement> contentElement;

    /**
     * Gets the value of the contentElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contentElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContentElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContentElement }
     * 
     * 
     */
    public List<ContentElement> getContentElement() {
        if (contentElement == null) {
            contentElement = new ArrayList<ContentElement>();
        }
        return this.contentElement;
    }

    /**
     * Sets the value of the contentElement property.
     * 
     * @param contentElement
     *     allowed object is
     *     {@link ContentElement }
     *     
     */
    public void setContentElement(List<ContentElement> contentElement) {
        this.contentElement = contentElement;
    }

}
