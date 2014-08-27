/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.search.SearchEngine;
import ch.admin.isb.hermes5.business.search.SearchResult;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.PerformanceLogged;

@Stateless
public class OnlinePublikationFacade {

    @Inject
    ModelRepository modelRepository;

    @Inject
    SearchEngine searchEngine;

    @Inject
    S3 s3;

    public EPFModel getPublishedModel() {
        return modelRepository.getPublishedModel();
    }

    public byte[] getPublishedFile(String modelIdentifier, String lang, String url) {
        return s3.readPublishedFile(modelIdentifier, lang, url);
    }
    @PerformanceLogged
    public List<SearchResult> search(String searchInput, String lang) {
        return searchEngine.search(searchInput, getPublishedModel().getIdentifier(), lang);
    }

}
