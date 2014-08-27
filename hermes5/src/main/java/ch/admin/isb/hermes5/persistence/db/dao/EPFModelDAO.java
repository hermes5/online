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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.util.DateUtil;

public class EPFModelDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "hermes5PU")
    EntityManager em;

    @Inject
    DateUtil dateUtil;

    public EPFModel merge(EPFModel model) {
        model.setLastChange(dateUtil.currentDate());
        return em.merge(model);
    }

    public List<EPFModel> getEPFModels() {
        return em.createNamedQuery("getAllEPFModels", EPFModel.class).getResultList();
    }

    public EPFModel getModelByIdentifier(String identifier) {
        return (EPFModel) em.createNamedQuery("getEPFByIdentifier").setParameter("modelIdentifier", identifier)
                .getResultList().get(0);
    }

    public EPFModel getPublishedModel() {
        List<EPFModel> models = em.createNamedQuery("getPublishedEPF", EPFModel.class).getResultList();
        return models.isEmpty() ? null : models.get(0);
    }

    public EPFModel mergeWithoutUpdateDateChange(EPFModel model) {
        return em.merge(model);
    }

    
}
