/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.TranslationDocument;
import ch.admin.isb.hermes5.persistence.db.dao.ImageDAO;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@RequestScoped
public class ImageImportHandler extends AbstractTranslationDocumentImportHandler {


    private static final long serialVersionUID = 1L;

    @Inject
    ImageDAO imageDAO;

    @Inject
    @SystemProperty(value = "imageTypes", fallback = "png, jpg, jpeg")
    ConfigurationProperty imageTypes;


    @Override
    public boolean isResponsible(String filename) {
        String[] urlParts = filename.toLowerCase().split("\\.");
        return imageTypes.getStringValue().contains(urlParts[urlParts.length - 1]);
    }

    

    protected void mergeTranslationDocument(TranslationDocument image) {
        imageDAO.merge((Image) image);
    }

    protected Image readTranslationDocument(String filename, String publishedModelIdentifier) {
        Image publishedImage = imageDAO.getImage(publishedModelIdentifier, filename);
        return publishedImage;
    }

    protected Image createTranslationDocument(String modelIdentifier, String filename) {
        Image image = new Image();
        image.setUrlDe(filename);
        image.setModelIdentifier(modelIdentifier);
        return image;
    }

    
    

}
