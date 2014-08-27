/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import static ch.admin.isb.hermes5.domain.Status.*;
import static ch.admin.isb.hermes5.domain.TranslateableTextBuilder.*;

import java.util.List;

public class ModelElementBuilder  {

    public static ModelElement modelElementName1FrInArbeit() {
        
        
        return modelElement( BuilderUtils.<TranslateableText>list(translateableText()), "typ1", "name1", IN_ARBEIT, UNVOLLSTAENDIG, UNVOLLSTAENDIG);
    }

    public static ModelElement modelElement(List<TranslateableText> texts1, String typ, String name, Status statusFr,
            Status statusIt, Status statusEn) {
        ModelElement modelElement1 = new ModelElement(typ, name, statusFr, statusIt, statusEn,
                texts1);
        return modelElement1;
    }

    public static ModelElement modelElementName2Unvollstaendig() {
        return modelElement( BuilderUtils.<TranslateableText>list(translateableText()), "typ2", "name2", UNVOLLSTAENDIG, UNVOLLSTAENDIG, UNVOLLSTAENDIG);
    }
    public static ModelElement modelElementName3FrInArbeitItFreigegeben() {
        return modelElement( BuilderUtils.<TranslateableText>list(translateableText()), "typ3", "name3", IN_ARBEIT, FREIGEGEBEN, UNVOLLSTAENDIG);
    }

   
}
