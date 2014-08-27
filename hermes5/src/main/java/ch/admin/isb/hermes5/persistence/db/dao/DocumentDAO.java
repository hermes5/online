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

import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Status;


public class DocumentDAO implements Serializable{

    private static final Logger logger = LoggerFactory.getLogger(DocumentDAO.class);
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext(unitName = "hermes5PU")
    EntityManager em;

    public Document merge(Document document) {
        return em.merge(document);
    }

    public List<Document> getDocuments(String modelIdentifier) {
        return em.createNamedQuery("getDocuments", Document.class).setParameter("modelIdentifier", modelIdentifier).getResultList();
    }

    public Document getDocument(long id) {
        return em.find(Document.class, id);
    }

    public List<Status> getDocumentStati(String modelIdentifier, String lang) {
        return em.createNamedQuery("getDocumentStati_" + lang, Status.class)
                .setParameter("modelIdentifier", modelIdentifier).getResultList();
    }
    public Document getDocument(String modelIdentifier, String urlDe) {
        List<Document> resultList = em.createNamedQuery("getDocument", Document.class)
                .setParameter("modelIdentifier", modelIdentifier).setParameter("urlDe", urlDe).getResultList();
        if (resultList.size() > 1) {
            logger.warn("Expected just one result but was " + resultList.size() + " " + modelIdentifier + " " + urlDe);
        }
        if (resultList.isEmpty()) {
            if(logger.isDebugEnabled()) {
                logger.warn("Unable to find a document for " + modelIdentifier + " " + urlDe);
            }
            return null;
        }
        return resultList.get(0);
    }
}
