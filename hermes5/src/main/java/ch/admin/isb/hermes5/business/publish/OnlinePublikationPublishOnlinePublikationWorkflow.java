/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.onlinepublikation.MenuItem;
import ch.admin.isb.hermes5.business.rendering.onlinepublikation.OnlinePublikationMenuRenderer;
import ch.admin.isb.hermes5.business.search.IndexWriterWrapper;
import ch.admin.isb.hermes5.business.search.SearchEngine;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.translation.TranslationRepositoryWrapper;
import ch.admin.isb.hermes5.business.util.FutureUtil;
import ch.admin.isb.hermes5.business.word.WordDocumentCustomizer;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.PerformanceLogged;

public class OnlinePublikationPublishOnlinePublikationWorkflow {

    @Inject
    SearchEngine searchEngine;

    @Inject
    S3 s3;

    @Inject
    OnlinePublikationMenuGenerator onlinePublikationMenuGenerator;

    @Inject
    OnlinePublikationMenuRenderer onlinePublikationMenuRenderer;

    @Inject
    WordDocumentCustomizer wordDocumentCustomizer;

    @Inject
    TranslationRepository translationRepository;

    @Inject
    FutureUtil futureUtil;
    
    @Inject
    OnlinePublikationPublishMethodElementWorkflow onlinePublikationPublishMethodElementWorkflow;

    @PerformanceLogged
    public void publishOnlinePublikation(final String modelIdentifier, List<String> langs,
            PublishContainer hermesWebsite) throws UnsupportedEncodingException {
        List<MenuItem> menu = onlinePublikationMenuGenerator.generateMenu(hermesWebsite);

        List<AbstractMethodenElement> elementsToPublish = hermesWebsite.getElementsToPublish();
        TranslationRepositoryWrapper translationRepositoryWrapper = new TranslationRepositoryWrapper(modelIdentifier,
                translationRepository, langs);
        List<Future<Void>> onlinePublikationFutures = new ArrayList<Future<Void>>();
        for (String lang : langs) {

            LocalizationEngine localizationEngine = new LocalizationEngine(translationRepositoryWrapper,
                    modelIdentifier, lang);

            // top menu used for search result page
            List<MenuItem> topMenu = onlinePublikationMenuGenerator.trimToTopMenu(menu);
            byte[] topMenuBytes = onlinePublikationMenuRenderer.renderTopMenu(topMenu, localizationEngine).getBytes(
                    "UTF-8");

            onlinePublikationFutures.add(s3.addPublishedFile(modelIdentifier, lang + "/menu.html", topMenuBytes));

            IndexWriterWrapper iwriter = searchEngine.startIndexing(modelIdentifier, localizationEngine);

            for (AbstractMethodenElement abstractMethodenElement : elementsToPublish) {
                OnlinePublikationPublishMethodElementResult renderMethodElement = onlinePublikationPublishMethodElementWorkflow
                        .renderMethodElement(hermesWebsite, menu, elementsToPublish, lang, abstractMethodenElement,
                                localizationEngine);
                if (renderMethodElement != null) {
                    iwriter.addDocumentFromModelElement(abstractMethodenElement,
                            renderMethodElement.getOnlySearchableContent());
                    byte[] bytes = renderMethodElement.getFinalHtml().getBytes("UTF-8");
                    onlinePublikationFutures.add(s3.addPublishedFile(modelIdentifier, lang + "/"
                            + abstractMethodenElement.getName() + ".html", bytes));
                }

            }

            for (Image image : translationRepository.getImages(modelIdentifier)) {
                String url = image.getUrl(lang);
                byte[] readFile = s3.readFile(modelIdentifier, lang, url);
                onlinePublikationFutures.add(s3.addPublishedFile(modelIdentifier, lang + "/" + url, readFile));
            }

            for (Document document : translationRepository.getDocuments(modelIdentifier)) {
                String url = document.getUrl(lang);
                byte[] readFile = s3.readFile(modelIdentifier, lang, url);
                // add default logo
                readFile = wordDocumentCustomizer.adjustDocumentWithUserData(url, readFile, new SzenarioUserData());
                iwriter.addDocumentFromDocument(document, readFile);
                onlinePublikationFutures.add(s3.addPublishedFile(modelIdentifier, lang + "/" + url, readFile));
            }

            onlinePublikationFutures.add(searchEngine.finishIndexing(iwriter));
        }

        futureUtil.waitForFuturesToComplete(onlinePublikationFutures);
    }
 

}
