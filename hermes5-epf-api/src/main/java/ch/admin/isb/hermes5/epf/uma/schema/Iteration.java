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
 * A special Activity which prescribes pre-defined values for its instances for the attributes prefix ('Iteration') and isRepeatable ('True').  It has been included into the meta-model for convenience and to provide a special stereotype, because it represents a very commonly used Activity type.
 * Iteration groups a set of nested Activities that are repeated more than once.  It represents an important structuring element to organize work in repetitive cycles.  The concept of Iteration can be associated with different rules in different methods.  For example, the IBM Rational Unified Process method framework (RUP) defines a rule that Iterations are not allowed to span across Phases.  In contrast IBM Global Services Method (GSMethod) based method frameworks this rule does not apply and Iteration can be defined which nest Phases.  Rules like these, which play an important role for each individual method and are therefore not enforced by this meta-model.  Instead, process authors are expected to follow and check these rules manually.  (Note: Any Breakdown Element can be repeated; however, Iterations has been introduced as a special meta-model concept, because of its important role for many methods.)
 * 
 * <p>Java-Klasse für Iteration complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Iteration">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Activity">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Iteration")
public class Iteration
    extends Activity
{


}
