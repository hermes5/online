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
 * A special Process that describes a reusable cluster of doing work in common process areas.  Capabilities Patterns express and communicate process knowledge for a key area of interest such as a Discipline and can be directly used by process practitioner to guide his work.  They are also used as building blocks to assemble Delivery Processes or larger Capability Patterns ensuring optimal reuse and application of the key practices they express.
 * A Capability Pattern is a special Process that describes a reusable cluster of doing work in a general process area that provides a consistent development approach to common problems.  Examples for Capability Pattern could be 'use case-based requirements management', 'use case analysis', or 'unit testing'. Typically but not necessarily, Capability Patterns have the scope of one discipline providing a breakdown of reusable complex Activities, relationships to the Roles which perform Tasks within these Activities, as well as to the Work Products that are used and produced.  A capability pattern does not relate to any specific phase or iteration of a development lifecycle, and should not imply any.  In other words, a pattern should be designed in a way that it is applicable anywhere in a Delivery Process.  This enables its Activities to be flexibly assigned to whatever phases there are in the Delivery Process to which it is being applied.  It is a good practice to design a Capability Pattern to produce one or more generic Deliverables.  The typical configuration is that each Activity in the Capability Pattern produces one Deliverable, and the last Task Descriptor in the Activity explicitly outputs just this Deliverable.  This enables the process engineer to select Patterns or just Activities by deciding which Deliverables are required.  It also offers a simple integration approach: an Activity from a capability pattern is linked to the Phase or Iteration which is required to produce the Activity's Deliverable.  Key applications areas of / areas of reuse for Capability Patterns are:
 * - To serve as building blocks for assembling Delivery Processes or larger Capability Patterns.  Normally developing a Delivery Process is not done from scratch but by systematically applying and binding patterns.  In addition to the standard pattern application of 'copy-and-modify', which allows the process engineer to individually customize the pattern's content to the particular situation it is applied for, the Plugin meta-model package (Section 6.1) introduces even more sophisticated inheritance relationships that support dynamic binding of patterns (i.e. the pattern is referenced and not copied).  This unique new way of reusing process knowledge allows to factor out commonly reoccurring Activities into patterns and to apply them over and over again for a process.  When the pattern is being revised or updated, all changes will be automatically reflected in all pattern application in all processes because of the dynamic binding.
 * - To support direct execution in a development project that does not work following a well-defined process, but works based on loosely connected process fragments of best practices in a flexible manner (e.g. Agile Development).
 * - To support process education by describing knowledge for a key area such as best practices on how to perform the work for a Discipline (e.g. Requirements Management), for a specific development technique (aspect-oriented development), or a specific technical area (e.g. relational database design), which is used for education and teaching.
 * 
 * <p>Java-Klasse für CapabilityPattern complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="CapabilityPattern">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.eclipse.org/epf/uma/1.0.6}Process">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CapabilityPattern")
public class CapabilityPattern
    extends Process
{


}
