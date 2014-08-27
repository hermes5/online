/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementPostProcessor;
import ch.admin.isb.hermes5.business.rendering.onlinepublikation.MenuItem;
import ch.admin.isb.hermes5.business.rendering.onlinepublikation.OnlinePublikationRenderer;
import ch.admin.isb.hermes5.business.rendering.onlinepublikation.OnlinePublikationRendererRepository;
import ch.admin.isb.hermes5.business.rendering.postprocessor.ImageLinkPostProcessor;
import ch.admin.isb.hermes5.business.rendering.postprocessor.InternalLinkPostProcessor;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.PublishContainer;

public class OnlinePublikationPublishMethodElementWorkflow {

    @Inject
    OnlinePublikationMenuGenerator onlinePublikationMenuGenerator;

    @Inject
    OnlinePublikationRendererRepository onlinePublikationRendererRepository;

    @Inject
    InternalLinkPostProcessor internalLinkPostProcessor;

    @Inject
    ImageLinkPostProcessor imageLinkPostProcessor;

    @Inject
    CustomElementPostProcessor customElementPostProcessor;

    public OnlinePublikationPublishMethodElementResult renderMethodElement(PublishContainer hermesWebsite,
            List<MenuItem> menu, List<AbstractMethodenElement> elementsToPublish, String lang,
            AbstractMethodenElement abstractMethodenElement, LocalizationEngine localizationEngine) {
        List<MenuItem> adjustedMenu = onlinePublikationMenuGenerator.adjustMenuForItem(menu, abstractMethodenElement,
                localizationEngine);
        OnlinePublikationRenderer renderer = onlinePublikationRendererRepository
                .lookupOnlinePublikationRenderer(abstractMethodenElement);
        if (renderer != null) {
            String finalHtml = render(hermesWebsite, elementsToPublish, abstractMethodenElement, localizationEngine,
                    adjustedMenu, renderer, false);
            String onlySearchableContent = render(hermesWebsite, elementsToPublish, abstractMethodenElement,
                    localizationEngine, adjustedMenu, renderer, true);
            return new OnlinePublikationPublishMethodElementResult(finalHtml, onlySearchableContent);
        }
        return null;
    }


    private String render(PublishContainer hermesWebsite, List<AbstractMethodenElement> elementsToPublish,
            AbstractMethodenElement abstractMethodenElement, LocalizationEngine localizationEngine,
            List<MenuItem> adjustedMenu, OnlinePublikationRenderer renderer, boolean onlySearchableContent) {
        String buildModelElementForOnlinePublikation = renderer.renderModelElement(abstractMethodenElement,
                adjustedMenu, localizationEngine, hermesWebsite, onlySearchableContent);
        String adjustInternalLinks = internalLinkPostProcessor.adjustInternalLinks(abstractMethodenElement,
                buildModelElementForOnlinePublikation, elementsToPublish);
        String adjustImageLinks = imageLinkPostProcessor.adjustImageLinks(adjustInternalLinks);
        String finalHtml = customElementPostProcessor.renderCustomElements(adjustImageLinks, hermesWebsite,
                localizationEngine);
        return finalHtml;
    }

}
