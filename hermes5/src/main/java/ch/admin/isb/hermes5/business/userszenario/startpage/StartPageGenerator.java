/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.startpage;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungRenderingContainer;
import ch.admin.isb.hermes5.business.rendering.anwenderloesung.AnwenderloesungStartPageRenderer;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilderUtils;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.ImageUtils;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

public class StartPageGenerator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    AnwenderloesungStartPageRenderer anwenderloesungStartPageRenderer;

    @Inject
    ImageUtils imageUtils;

    @Inject
    ZipOutputBuilderUtils zipOutputBuilderUtils;

    @Inject
    @SystemProperty(value = "aw_logo_max_width", fallback = "241")
    ConfigurationProperty logoMaxWidth;
    @Inject
    @SystemProperty(value = "aw_logo_max_height", fallback = "103")
    ConfigurationProperty logoMaxHeight;

    public void addStartPage(AnwenderloesungRenderingContainer container, ZipOutputBuilder zipBuilder,
            LocalizationEngine localizationEngine) {
        String fileContent = anwenderloesungStartPageRenderer.renderStartPage(container, localizationEngine);
        byte[] writeStartPage = StringUtil.getBytes(fileContent);
        zipBuilder.addFile("start_" + localizationEngine.getLanguage() + ".html", writeStartPage);
    }

    public void addCommonResources(ZipOutputBuilder zipBuilder, SzenarioUserData szenarioUserData) {
        String logoTarget = "resources/logo.png";
        if (szenarioUserData.getLogo() != null) {
            BufferedImage logo = imageUtils.toBufferedImage(szenarioUserData.getLogo());
            if (logo != null) {
                BufferedImage smallerToMax = imageUtils.makeSmallerToMax(logo, logoMaxWidth.getIntegerValue(),
                        logoMaxHeight.getIntegerValue());
                zipBuilder.addFile(logoTarget, imageUtils.toPNGByteArray(smallerToMax));
            } 

        } else {
            zipOutputBuilderUtils.addResource(this, zipBuilder, "logo_bund.png", logoTarget);
        }
        zipOutputBuilderUtils.addResource(this, zipBuilder, "HERMES_Internet_Manual.png",
                "resources/HERMES_Internet_Manual.png");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "Bulletpoint_Hermes.png",
                "resources/Bulletpoint_Hermes.png");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "service_image.png", "resources/service_image.png");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "style.css", "resources/style.css");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "style_print.css", "resources/style_print.css");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "h5-font.eot", "resources/h5-font.eot");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "h5-font.svg", "resources/h5-font.svg");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "h5-font.ttf", "resources/h5-font.ttf");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "h5-font.woff", "resources/h5-font.woff");
        zipOutputBuilderUtils.addResource(this, zipBuilder, "ico_extern.gif", "resources/ico_extern.gif");
    }

}
