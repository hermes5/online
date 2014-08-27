/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationexport;

import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.word.TranslationWordAdapter;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.domain.TranslationEntity.TranslationEntityType;

public class TranslationExportWorkflowTextElementProcessor implements TranslationExportWorkflowProcessor {

    @Inject
    TranslationWordAdapter wordAdapter;
    
    @Inject
    TranslationRepository translationRepository;

    @Override
    public String path(String modelIdentifier, TranslationEntity translationEntity, String lang) {
        ModelElement modelElement = (ModelElement) translationEntity;
        return "MethodLibrary_" + modelIdentifier + "/Textelemente/" + modelElement.getTyp() + "/"
        + modelElement.getName() + "_" + lang + ".docx";
    }

    @Override
    public byte[] processEntity(TranslationEntity translationEntity, String lang) {
        ModelElement modelElement = (ModelElement) translationEntity;
        return wordAdapter.write(modelElement.getTexts(), lang);
    }

    @Override
    public void markAsInArbeit(TranslationEntity translationEntity, List<String> langs) {
        translationRepository.markModelElementAsInArbeit((ModelElement) translationEntity, langs);
    }


    @Override
    public boolean isResponsible(TranslationEntity translationEntity) {
        return translationEntity.getType() == TranslationEntityType.TEXT_ELEMENT;
    }

}
