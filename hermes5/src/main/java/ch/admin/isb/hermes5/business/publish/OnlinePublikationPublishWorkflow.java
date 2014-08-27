/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Status;

public class OnlinePublikationPublishWorkflow {

    @Inject
    ModelRepository modelRepository;

    @Inject
    OnlinePublikationPublishOnlinePublikationWorkflow onlinePublikationPublishOnlinePublikationWorkflow;

    @Inject
    OnlinePublikationPublishSampleSzenarienWorkflow onlinePublikationPublishSampleSzenarienWorkflow;

    public EPFModel publish(final String modelIdentifier) {
        try {
            EPFModel modelByIdentifier = modelRepository.getModelByIdentifier(modelIdentifier);
            List<String> langs = getLangsToPublish(modelByIdentifier);

            PublishContainer hermesWebsite = modelRepository.getHermesWebsite(modelIdentifier);
            onlinePublikationPublishOnlinePublikationWorkflow.publishOnlinePublikation(modelIdentifier, langs,
                    hermesWebsite);

            onlinePublikationPublishSampleSzenarienWorkflow.publishSampleSzenarios(modelIdentifier, langs,
                    hermesWebsite);

            return modelRepository.publish(modelIdentifier);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getLangsToPublish(EPFModel modelByIdentifier) {
        List<String> list = new ArrayList<String>();
        list.add("de");
        if (modelByIdentifier.getStatusFr() == Status.FREIGEGEBEN) {
            list.add("fr");
        }
        if (modelByIdentifier.getStatusIt() == Status.FREIGEGEBEN) {
            list.add("it");
        }
        if (modelByIdentifier.getStatusEn() == Status.FREIGEGEBEN) {
            list.add("en");
        }
        return list;
    }
}
