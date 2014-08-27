/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.xmlmodel;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungXMLModelIndexPageRenderer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilderUtils;
import ch.admin.isb.hermes5.util.StringUtil;

public class XMLModelGenerator {

    private static final String MODEL_PREFIX_PATH = "model/";

    @Inject
    ZipOutputBuilderUtils zipOutputBuilderUtils;
    @Inject
    XMLModelAssembler xmlModelAssembler;
    @Inject
    AnwenderloesungXMLModelIndexPageRenderer anwenderloesungXMLModelIndexPageRenderer;

    public void addIndexPage(AnwenderloesungRenderingContainer container, ZipOutputBuilder zipBuilder,
            LocalizationEngine localizationEngine) {
        String renderXMLModelIndexPage = anwenderloesungXMLModelIndexPageRenderer.renderXMLModelIndexPage(
                container, localizationEngine);

        byte[] modelIndexPage = StringUtil.getBytes(renderXMLModelIndexPage);
        zipBuilder.addFile(MODEL_PREFIX_PATH +  localizationEngine.getLanguage() + "/index.html", modelIndexPage);

    }

    public void addXMLModelWithContent(AnwenderloesungRenderingContainer container, ZipOutputBuilder zipBuilder,
            LocalizationEngine localizationEngine) {
        String xmlString = xmlModelAssembler.assembleXML(container, localizationEngine);
        zipBuilder.addFile(
                MODEL_PREFIX_PATH +  localizationEngine.getLanguage() + "/model_"
                        + localizationEngine.getLanguage() + ".xml", getBytes(xmlString));
    }

    public void addXSDToZip(ZipOutputBuilder zipBuilder) {
        zipOutputBuilderUtils.addResource(this, zipBuilder, "/model_schema.xsd", MODEL_PREFIX_PATH
                + "schema/model_schema.xsd");
    }

}
