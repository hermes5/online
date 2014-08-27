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

import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslationDocument;

public class ImageDAO implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ImageDAO.class);
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "hermes5PU")
    EntityManager em;

    public TranslationDocument merge(Image image) {
        return em.merge(image);
    }

    public List<Image> getImages(String modelIdentifier) {
        return em.createNamedQuery("getImages", Image.class).setParameter("modelIdentifier", modelIdentifier)
                .getResultList();
    }

    public Image getImage(long id) {
        return em.find(Image.class, id);
    }

    public List<Status> getImageStati(String modelIdentifier, String lang) {
        return em.createNamedQuery("getImageStati_" + lang, Status.class)
                .setParameter("modelIdentifier", modelIdentifier).getResultList();
    }

    public Image getImage(String modelIdentifier, String urlDe) {
        List<Image> resultList = em.createNamedQuery("getImage", Image.class)
                .setParameter("modelIdentifier", modelIdentifier).setParameter("urlDe", urlDe).getResultList();
        if (resultList.size() > 1) {
            logger.warn("Expected just one result but was " + resultList.size() + " " + modelIdentifier + " " + urlDe);
        }
        if (resultList.isEmpty()) {
            if(logger.isDebugEnabled()) {
                logger.debug("Unable to find an image for " + modelIdentifier + " " + urlDe);
            }
            return null;
        }
        return resultList.get(0);
    }
}
