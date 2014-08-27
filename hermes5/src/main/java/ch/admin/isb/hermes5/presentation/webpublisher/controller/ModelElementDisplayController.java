/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.presentation.common.ImageUrlUtil;
import ch.admin.isb.hermes5.presentation.common.MessagesUtil;
import ch.admin.isb.hermes5.presentation.common.SelectableRow;

@Named
@SessionScoped
public class ModelElementDisplayController extends AbstractViewController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    WebPublisherFacade webPublisherFacade;
    @Inject
    ImageUrlUtil imageUrlUtil;

    @Inject
    MessagesUtil messagesUtil;

    private ModelElement modelElement;
    List<SelectableRow<TranslateableText>> translateableTexts;
    private String lang;

    public String getTextDe(TranslateableText t) {
        return imageUrlUtil.adaptImageUrls(t.getTextDe(), t.getModelIdentifier(), "de");
    }

    public String getOtherText(TranslateableText t) {
        if ("fr".equals(lang)) {
            return imageUrlUtil.adaptImageUrls(t.getTextFr(), t.getModelIdentifier(), lang);
        }
        if ("it".equals(lang)) {
            return imageUrlUtil.adaptImageUrls(t.getTextIt(), t.getModelIdentifier(), lang);
        }
        if ("en".equals(lang)) {
            return imageUrlUtil.adaptImageUrls(t.getTextEn(), t.getModelIdentifier(), lang);
        }
        throw new IllegalStateException("unknown lang: " + lang);
    }

    public List<SelectableRow<TranslateableText>> getTexts() {
        return translateableTexts;
    }

    private List<TranslateableText> getSelectedTranslateableTexts() {
        List<TranslateableText> selected = new ArrayList<TranslateableText>();
        for (SelectableRow<TranslateableText> text : translateableTexts) {
            if (text.isSelected()) {
                selected.add(text.getData());
            }
        }
        return selected;
    }

    public String getTitle() {
        return modelElement.getTypeName() + ": " + getPresentationName();
    }

    private String getPresentationName() {
        for (SelectableRow<TranslateableText> text : translateableTexts) {
            if (text.getData().getElementIdentifier().equals(text.getData().getRootElementIdentifier())
                    && "presentationName".equals(text.getData().getTextIdentifier())) {
                return text.getData().getTextDe();
            }
        }
        return modelElement.getName();
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String deleteTexts() {
        List<TranslateableText> selectedTexts = getSelectedTranslateableTexts();
        if (selectedTexts.isEmpty()) {
            messagesUtil.addGlobalErrorMessage("Bitte mindestens ein Modell auswählen");
        }
        for (TranslateableText text : selectedTexts) {
            EPFModel model = webPublisherFacade.getModel(text.getModelIdentifier());
            if (model.isPublished()) {
                messagesUtil.addGlobalErrorMessage("Texte des publizierten Modells können nicht gelöscht werden");
                return null;
            }
        }
        for (TranslateableText text : selectedTexts) {
            webPublisherFacade.deleteTranslateableText(text.getModelIdentifier(), text.getElementIdentifier(),
                    text.getTextIdentifier(), lang);
        }
        TranslateableText first = translateableTexts.get(0).getData();
        loadTexts(first.getModelIdentifier(), first.getRootElementIdentifier());
        return null;
    }

    public String display(String modelIdentifier, String rootElementIdentifier) {
        this.lang = "fr";
        loadTexts(modelIdentifier, rootElementIdentifier);
        return getIdentifier();
    }

    private void loadTexts(String modelIdentifier, String rootElementIdentifier) {
        translateableTexts = new ArrayList<SelectableRow<TranslateableText>>();
        modelElement = webPublisherFacade.getModelElement(modelIdentifier, rootElementIdentifier);
        List<TranslateableText> tempTexts = modelElement.getTexts();
        for (TranslateableText text : tempTexts) {
            translateableTexts.add(new SelectableRow<TranslateableText>(text));
        }
    }

    @Override
    public String getIdentifier() {
        return "modelelement-display";
    }

}
