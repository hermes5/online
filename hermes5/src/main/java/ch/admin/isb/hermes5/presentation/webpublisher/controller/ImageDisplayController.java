/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.presentation.common.AbstractViewController;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.Logged;
import ch.admin.isb.hermes5.util.StringUtil;
import ch.admin.isb.hermes5.util.SystemProperty;
@Named
@SessionScoped
public class ImageDisplayController extends AbstractViewController implements Serializable {

    private static final long serialVersionUID = -7484587887208357607L;

    @Inject
    @SystemProperty(value = "s3.publicBucketUrl")
    ConfigurationProperty s3PublicBucketUrl;

    @Inject
    WebPublisherFacade webPublisherFacade;

    @Inject
    ArtifactUploadController artifactUploadController;

    @Inject
    ArtifactOverviewController artifactOverviewController;

    private Image image;

    
    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public String back() {
        return redirectTo(artifactOverviewController.display());
    }

    public String getUrlDe() {
        return getUrl(image.getUrlDe(), "de");
    }

    public boolean isImageFr() {
        return StringUtil.isNotBlank(image.getUrlFr());
    }

    public boolean isImageIt() {
        return StringUtil.isNotBlank(image.getUrlIt());
    }

    public boolean isImageEn() {
        return StringUtil.isNotBlank(image.getUrlEn());
    }

    private String getUrl(String url, String lang) {
        return s3PublicBucketUrl.getStringValue() + "/" + image.getModelIdentifier() + "/" + lang + "/" + url;
    }

    public String getUrlFr() {
        return getUrl(image.getUrlFr(), "fr");
    }

    public String getUrlEn() {
        return getUrl(image.getUrlEn(), "en");
    }

    public String getUrlIt() {
        return getUrl(image.getUrlIt(), "it");
    }

    public String deleteIt() {
        return delete("it");
    }

    public String deleteFr() {
        return delete("fr");
    }

    public String deleteEn() {
        return delete("en");
    }

    private String delete(String lang) {
        return display((Image) webPublisherFacade.addOrUpdateArtifact(image, null, null, lang));
    }

    public String display(Image image) {
        this.image = image;
        return getIdentifier();
    }

    @Override
    public String getIdentifier() {
        return "image-display";
    }

    @Logged
    public String getImageTitle() {
        int lastIndexOf = image.getUrlDe().lastIndexOf("/");
        if (lastIndexOf > 0 && lastIndexOf < image.getUrlDe().length()) {
            return image.getUrlDe().substring(lastIndexOf + 1);
        }
        return image.getUrlDe();
    }

    public String uploadFr() {
        return artifactUploadController.display(image, "fr", getIdentifier() );
    }

    public String uploadIt() {
        return artifactUploadController.display(image, "it", getIdentifier() );
    }

    public String uploadEn() {
        return artifactUploadController.display(image, "en", getIdentifier() );
    }

     
}
