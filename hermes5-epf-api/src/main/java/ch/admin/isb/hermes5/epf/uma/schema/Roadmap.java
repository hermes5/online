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
import javax.xml.bind.annotation.XmlType;


/**
 * A specific type of Guidance which is only related to Activates and therefore has been added by this package to the list of Guidance Types rather than listed in the Guidance Types package.  A Roadmap represents a linear walkthrough of an Activity, typically a Process.
 * An instance of a Roadmap represents important documentation for the Activity or Process it is related to.  Often a complex Activity such as a Process can be much easier understood by providing a walkthrough with a linear thread of a typical instantiation of this Activity.  In addition to making the process practitioner understand how work in the process is being performed, a Roadmap provides additional information about how Activities and Tasks relate to each other over time.  Roadmaps are also used to show how specific aspects are distributed over a whole process providing a kind of filter on the process for this information.
 * 
 * <p>Java-Klasse für Roadmap complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Roadmap">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Guidance">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Roadmap")
public class Roadmap
    extends Guidance
{


}
