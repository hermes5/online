/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.db.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslateableText;

public class TranslateableTextDAO implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TranslateableTextDAO.class);

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "hermes5PU")
    EntityManager em;

    public TranslateableText merge(TranslateableText document) {
        return em.merge(document);
    }

    public List<TranslateableText> getTexts(String modelIdentifier) {
        return em.createNamedQuery("getTranslateableTexts", TranslateableText.class)
                .setParameter("modelIdentifier", modelIdentifier).getResultList();
    }

    public TranslateableText deleteTranslateableText(String modelIdentifier, String elementIdentifier, String textIdentifier,
            String lang) {
        TranslateableText text = getSpecificText(modelIdentifier, elementIdentifier, textIdentifier);
        if ("fr".equals(lang)) {
            text.setTextFr(null);
            text.setStatusFr(Status.UNVOLLSTAENDIG);
        } else if ("it".equals(lang)) {
            text.setTextIt(null);
            text.setStatusIt(Status.UNVOLLSTAENDIG);
        } else if ("en".equals(lang)) {
            text.setTextEn(null);
            text.setStatusEn(Status.UNVOLLSTAENDIG);
        }
        return merge(text);
    }

    public List<Status> getTextElementStati(String modelIdentifier, String lang) {
        return em.createNamedQuery("getTextelementStati_" + lang, Status.class)
                .setParameter("modelIdentifier", modelIdentifier).getResultList();
    }

    public TranslateableText getSpecificText(String modelIdentifier, String elementIdentifier, String textIdentifier) {
        List<TranslateableText> resultList = em
                .createNamedQuery("getSpecificTranslateableText", TranslateableText.class)
                .setParameter("modelIdentifier", modelIdentifier).setParameter("elementIdentifier", elementIdentifier)
                .setParameter("textIdentifier", textIdentifier).getResultList();
        if (resultList.size() > 1) {
            logger.error("Expected just one result but was " + resultList.size() + " " + modelIdentifier + " "
                    + elementIdentifier + " " + textIdentifier);
        }
        if (resultList.isEmpty()) {
            logger.debug("Unable to find a text for " + modelIdentifier + " " + elementIdentifier + " " + textIdentifier);
            return null;
        }
        return resultList.get(0);
    }

}
