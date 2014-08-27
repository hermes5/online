/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.io.Serializable;
import java.util.List;

public class ModelElement implements TranslationEntity, Serializable{

    private static final long serialVersionUID = 1L;

    private final String typ;
    private final String name;
    private final Status statusFr;
    private final Status statusEn;
    private final Status statusIt;
    private final List<TranslateableText> texts;
    private String rootElementIdentifier;

    public ModelElement(String typ, String name, Status statusFr, Status statusIt, Status statusEn,
            List<TranslateableText> texts) {
        this.typ = typ;
        this.name = name;
        this.statusFr = statusFr;
        this.statusEn = statusEn;
        this.statusIt = statusIt;
        this.texts = texts;
        rootElementIdentifier = texts.get(0).getRootElementIdentifier();
    }

    public String getRootElementIdentifier() {
        return rootElementIdentifier;
    }

    @Override
    public String toString() {
        return "ModelElement [typ=" + typ + ", name=" + name + "]";
    }

    public String getTyp() {
        return typ;
    }

    public String getName() {
        return name;
    }

    public Status getStatusFr() {
        return statusFr;
    }

    public Status getStatusEn() {
        return statusEn;
    }

    public Status getStatusIt() {
        return statusIt;
    }

    public List<TranslateableText> getTexts() {
        return texts;
    }

    @Override
    public TranslationEntityType getType() {
        return TranslationEntityType.TEXT_ELEMENT;
    }

    @Override
    public String getTypeName() {
        return getTyp();
    }

}
