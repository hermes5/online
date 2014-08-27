/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/

package ch.admin.isb.hermes5.epf.uma.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für VariabilityType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="VariabilityType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NCName">
 *     &lt;enumeration value="na"/>
 *     &lt;enumeration value="contributes"/>
 *     &lt;enumeration value="extends"/>
 *     &lt;enumeration value="replaces"/>
 *     &lt;enumeration value="localContribution"/>
 *     &lt;enumeration value="localReplacement"/>
 *     &lt;enumeration value="extendsReplaces"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VariabilityType")
@XmlEnum
public enum VariabilityType {

    @XmlEnumValue("na")
    NA("na"),
    @XmlEnumValue("contributes")
    CONTRIBUTES("contributes"),
    @XmlEnumValue("extends")
    EXTENDS("extends"),
    @XmlEnumValue("replaces")
    REPLACES("replaces"),
    @XmlEnumValue("localContribution")
    LOCAL_CONTRIBUTION("localContribution"),
    @XmlEnumValue("localReplacement")
    LOCAL_REPLACEMENT("localReplacement"),
    @XmlEnumValue("extendsReplaces")
    EXTENDS_REPLACES("extendsReplaces");
    private final String value;

    VariabilityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VariabilityType fromValue(String v) {
        for (VariabilityType c: VariabilityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
