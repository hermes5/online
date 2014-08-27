/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import static ch.admin.isb.hermes5.domain.Status.*;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.util.StringUtil;

@RequestScoped
public class TranslationMerge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    TranslationRepository translationRepository;
    @Inject
    ModelRepository modelRepository;

    private EPFModel publishedModel;

    @PostConstruct
    public void init() {
        publishedModel = modelRepository.getPublishedModel();
    }

    public TranslateableText mergeWithPublished(TranslateableText translateableText) {
        if (publishedModel != null) {
            TranslateableText published = translationRepository.getTranslateableText(publishedModel.getIdentifier(),
                    translateableText.getElementIdentifier(), translateableText.getTextIdentifier());
            if (published != null) {
                boolean textDeDidntChanged = translateableText.getTextDe().equals(published.getTextDe());
                if (StringUtil.isNotBlank(published.getTextFr())) {
                    translateableText.setTextFr(published.getTextFr());
                    translateableText.setStatusFr(textDeDidntChanged ? FREIGEGEBEN : UNVOLLSTAENDIG);
                }
                if (StringUtil.isNotBlank(published.getTextIt())) {
                    translateableText.setTextIt(published.getTextIt());
                    translateableText.setStatusIt(textDeDidntChanged ? FREIGEGEBEN : UNVOLLSTAENDIG);
                }
                if (StringUtil.isNotBlank(published.getTextEn())) {
                    translateableText.setTextEn(published.getTextEn());
                    translateableText.setStatusEn(textDeDidntChanged ? FREIGEGEBEN : UNVOLLSTAENDIG);
                }
            }
        }
        return translateableText;
    }

}
