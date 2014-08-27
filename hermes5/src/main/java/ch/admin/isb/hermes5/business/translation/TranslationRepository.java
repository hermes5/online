/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translation;

import static ch.admin.isb.hermes5.domain.Status.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.domain.TranslationEntity;
import ch.admin.isb.hermes5.persistence.db.dao.DocumentDAO;
import ch.admin.isb.hermes5.persistence.db.dao.ImageDAO;
import ch.admin.isb.hermes5.persistence.db.dao.TranslateableTextDAO;
import ch.admin.isb.hermes5.util.Logged;

public class TranslationRepository implements Serializable, LocalizationEngineSupport {

    private static final long serialVersionUID = 1L;

    @Inject
    ImageDAO imageDAO;

    @Inject
    DocumentDAO documentDAO;

    @Inject
    TranslateableTextDAO translateableTextDAO;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<TranslationEntity> getTranslationEntities(String modelIdentifier) {
        List result = new ArrayList<TranslationDocument>();
        result.addAll(getModelElements(modelIdentifier));
        List documents = getDocuments(modelIdentifier);
        List images = getImages(modelIdentifier);
        result.addAll(documents);
        result.addAll(images);
        return result;
    }

    public List<Image> getImages(String modelIdentifier) {
        return imageDAO.getImages(modelIdentifier);
    }

    public List<Document> getDocuments(String modelIdentifier) {
        return documentDAO.getDocuments(modelIdentifier);
    }

    public TranslationDocument updateTranslationEntity(TranslationDocument entry, String url, String lang) {
        TranslationDocument read = read(entry);
        if ("fr".equals(lang)) {
            read.setStatusFr(calcStatus(url));
            read.setUrlFr(url);
        } else if ("it".equals(lang)) {
            read.setStatusIt(calcStatus(url));
            read.setUrlIt(url);
        } else if ("en".equals(lang)) {
            read.setStatusEn(calcStatus(url));
            read.setUrlEn(url);
        }
        return merge(read);
    }

    private TranslationDocument merge(TranslationDocument entry) {
        switch (entry.getType()) {
            case IMAGE:
                return imageDAO.merge((Image) entry);
            case DOCUMENT:
                return documentDAO.merge((Document) entry);
            default:
                return null;
        }
    }

    private Status calcStatus(String text) {
        return text != null ? Status.FREIGEGEBEN : Status.UNVOLLSTAENDIG;
    }

    private TranslationDocument read(TranslationDocument entry) {
        switch (entry.getType()) {
            case IMAGE:
                return imageDAO.getImage(((Image) entry).getId());
            case DOCUMENT:
                return documentDAO.getDocument(((Document) entry).getId());
            default:
                return null;
        }
    }

    private Map<String, List<TranslateableText>> divideByFilename(List<TranslateableText> texts) {
        Map<String, List<TranslateableText>> fileMap = new LinkedHashMap<String, List<TranslateableText>>();
        for (TranslateableText text : texts) {
            addToMap(fileMap, text, text.getFileName());
        }
        return fileMap;
    }

    private Map<String, List<TranslateableText>> divideByFileType(List<TranslateableText> texts) {
        Map<String, List<TranslateableText>> fileMap = new LinkedHashMap<String, List<TranslateableText>>();
        for (TranslateableText text : texts) {
            addToMap(fileMap, text, text.getFileType());
        }
        return fileMap;
    }

    private void addToMap(Map<String, List<TranslateableText>> fileMap, TranslateableText text, String fileName) {
        List<TranslateableText> list = !fileMap.containsKey(fileName) ? new ArrayList<TranslateableText>() : fileMap
                .get(fileName);
        list.add(text);
        fileMap.put(fileName, list);
    }

    private List<ModelElement> getModelElements(String modelIdentifier) {
        List<ModelElement> result = new ArrayList<ModelElement>();
        List<TranslateableText> texts = translateableTextDAO.getTexts(modelIdentifier);
        for (Entry<String, List<TranslateableText>> byFileType : divideByFileType(texts).entrySet()) {
            String typ = byFileType.getKey();
            for (Entry<String, List<TranslateableText>> byFileName : divideByFilename(byFileType.getValue()).entrySet()) {
                result.add(new ModelElement(typ, byFileName.getKey(), status(statusFr(byFileName.getValue())),
                        status(statusIt(byFileName.getValue())), status(statusEn(byFileName.getValue())), byFileName
                                .getValue()));
            }
        }
        return result;
    }

    public ModelElement getModelElement(String modelIdentifier, String rootElementIdentifier) {
        List<TranslateableText> modelElementTexts = new ArrayList<TranslateableText>();
        List<TranslateableText> texts = translateableTextDAO.getTexts(modelIdentifier);
        for (TranslateableText text : texts) {
            if (text.getRootElementIdentifier().equals(rootElementIdentifier)) {
                modelElementTexts.add(text);
            }
        }

        TranslateableText firstText = modelElementTexts.get(0);
        return new ModelElement(firstText.getFileType(), firstText.getFileName(), status(statusFr(modelElementTexts)),
                status(statusIt(modelElementTexts)), status(statusEn(modelElementTexts)), modelElementTexts);
    }

    private Status status(Set<Status> stati) {
        Status aggregated = FREIGEGEBEN;
        for (Status status : stati) {
            if (status == UNVOLLSTAENDIG && aggregated == FREIGEGEBEN) {
                aggregated = UNVOLLSTAENDIG;
            } else if (status == IN_ARBEIT) {
                aggregated = IN_ARBEIT;
            }
        }
        return aggregated;
    }

    private Set<Status> statusFr(List<TranslateableText> texts) {
        Set<Status> stati = new HashSet<Status>();
        for (TranslateableText translateableText : texts) {
            stati.add(translateableText.getStatusFr());
        }
        return stati;
    }

    private Set<Status> statusIt(List<TranslateableText> texts) {
        Set<Status> stati = new HashSet<Status>();
        for (TranslateableText translateableText : texts) {
            stati.add(translateableText.getStatusIt());
        }
        return stati;
    }

    private Set<Status> statusEn(List<TranslateableText> texts) {
        Set<Status> stati = new HashSet<Status>();
        for (TranslateableText translateableText : texts) {
            stati.add(translateableText.getStatusEn());
        }
        return stati;
    }

    public void markTranslationDocumentAsInArbeit(TranslationDocument td, List<String> langsToGenerate) {
        if (langsToGenerate.contains("fr")) {
            td.setStatusFr(IN_ARBEIT);
        }
        if (langsToGenerate.contains("it")) {
            td.setStatusIt(IN_ARBEIT);
        }
        if (langsToGenerate.contains("en")) {
            td.setStatusEn(IN_ARBEIT);
        }
        merge(td);
    }

    public void markModelElementAsInArbeit(ModelElement modelElement, List<String> langsToGenerate) {
        for (TranslateableText translateableText : modelElement.getTexts()) {
            if (langsToGenerate.contains("fr")) {
                translateableText.setStatusFr(IN_ARBEIT);
            }
            if (langsToGenerate.contains("it")) {
                translateableText.setStatusIt(IN_ARBEIT);
            }
            if (langsToGenerate.contains("en")) {
                translateableText.setStatusEn(IN_ARBEIT);
            }
            translateableTextDAO.merge(translateableText);
        }

    }

    public Image getImage(long imageId) {
        return imageDAO.getImage(imageId);
    }

    public Document getDocument(long id) {
        return documentDAO.getDocument(id);
    }

    @Logged
    public List<Status> getTranslationEntityStati(String modelIdentifier, String lang) {
        Set<Status> set = new LinkedHashSet<Status>();
        set.addAll(documentDAO.getDocumentStati(modelIdentifier, lang));
        set.addAll(imageDAO.getImageStati(modelIdentifier, lang));
        set.addAll(translateableTextDAO.getTextElementStati(modelIdentifier, lang));
        return new ArrayList<Status>(set);
    }

    public TranslateableText getTranslateableText(String modelIdentifier, String elementIdentifier,
            String textIdentifier) {
        return translateableTextDAO.getSpecificText(modelIdentifier, elementIdentifier, textIdentifier);
    }

    @Override
    public String getLocalizedText(String modelIdentifier, String language, String elementIdentifier,
            String textIdentifier) {
        TranslateableText t = getTranslateableText(modelIdentifier, elementIdentifier, textIdentifier);
        if (t == null) {
            return null;
        }
        if ("fr".equals(language)) {
            return t.getTextFr();
        }
        if ("it".equals(language)) {
            return t.getTextIt();
        }
        if ("en".equals(language)) {
            return t.getTextEn();
        }
        return t.getTextDe();
    }

    @Override
    public String getDocumentUrl(String modelIdentifier, String urlDe, String language) {
        Document doc = getDocument(modelIdentifier, urlDe);
        if (doc == null) {
            return null;
        }
        return doc.getUrl(language);
    }

    public Document getDocument(String modelIdentifier, String urlDe) {
        return documentDAO.getDocument(modelIdentifier, urlDe);
    }

    public List<TranslateableText> getTexts(String modelIdentifier) {
        return translateableTextDAO.getTexts(modelIdentifier);
    }
}
