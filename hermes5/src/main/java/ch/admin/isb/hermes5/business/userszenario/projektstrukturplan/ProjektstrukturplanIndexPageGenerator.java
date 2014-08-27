/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.projektstrukturplan;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungProjektstrukturplanIndexPageRenderer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.util.StringUtil;

public class ProjektstrukturplanIndexPageGenerator {

    @Inject
    AnwenderloesungProjektstrukturplanIndexPageRenderer anwenderloesungProjektstrukturplanIndexPageRenderer;

    public void addProjektstrukturPlanIndexPage(String root, AnwenderloesungRenderingContainer container,
                    ZipOutputBuilder zipBuilder, LocalizationEngine localizationEngine, boolean showMpxLink,
                    boolean showXmlLink) {

        String fileContent = anwenderloesungProjektstrukturplanIndexPageRenderer.renderProjektstrukturplanIndexPage(
                localizationEngine, container, showMpxLink, showXmlLink);

        byte[] projektstrukturplanIndex = StringUtil.getBytes(fileContent);
        zipBuilder.addFile(root + "/" + localizationEngine.getLanguage() + "/index.html", projektstrukturplanIndex);
    }
}
