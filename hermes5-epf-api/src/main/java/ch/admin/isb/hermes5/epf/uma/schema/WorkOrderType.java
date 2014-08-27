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
 * <p>Java-Klasse für WorkOrderType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="WorkOrderType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NCName">
 *     &lt;enumeration value="finishToStart"/>
 *     &lt;enumeration value="finishToFinish"/>
 *     &lt;enumeration value="startToStart"/>
 *     &lt;enumeration value="startToFinish"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "WorkOrderType")
@XmlEnum
public enum WorkOrderType {

    @XmlEnumValue("finishToStart")
    FINISH_TO_START("finishToStart"),
    @XmlEnumValue("finishToFinish")
    FINISH_TO_FINISH("finishToFinish"),
    @XmlEnumValue("startToStart")
    START_TO_START("startToStart"),
    @XmlEnumValue("startToFinish")
    START_TO_FINISH("startToFinish");
    private final String value;

    WorkOrderType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static WorkOrderType fromValue(String v) {
        for (WorkOrderType c: WorkOrderType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
