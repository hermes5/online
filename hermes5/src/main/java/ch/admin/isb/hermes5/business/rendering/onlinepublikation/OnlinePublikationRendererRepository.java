/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.onlinepublikation;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;

public class OnlinePublikationRendererRepository {
    private static final Logger logger = LoggerFactory.getLogger(OnlinePublikationRendererRepository.class);
    @Inject @Any
    Instance<OnlinePublikationRenderer> onlinePublikationRenderers;
    
    public OnlinePublikationRenderer  lookupOnlinePublikationRenderer(AbstractMethodenElement methodElement) {
        for (OnlinePublikationRenderer printXmlRenderer : onlinePublikationRenderers) {
            if(printXmlRenderer.isResponsibleFor(methodElement)) {
                return printXmlRenderer;
            }
        }
        logger.warn("No onlinepublication renderer found for "+methodElement);
        return null;
    }
    
    

}

