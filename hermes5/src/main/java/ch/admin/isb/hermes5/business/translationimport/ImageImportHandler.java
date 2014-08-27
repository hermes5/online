/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationimport;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.TranslationEntity.TranslationEntityType;
import ch.admin.isb.hermes5.persistence.db.dao.ImageDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;

public class ImageImportHandler implements TranslationImportHandler {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ImageImportHandler.class);

    @Inject
    ImageDAO imageDAO;
    @Inject
    S3 s3;

    @Override
    public boolean isResponsible(String filename) {
        if (filename.contains(TranslationEntityType.IMAGE.nameDePlural())) {
            return true;
        }
        return false;
    }

    @Override
    public ImportResult handleImport(String modelIdentifier, byte[] content, List<String> languages, String url) {
        ImportResult result = new ImportResult();
        String[] urlParts = url.split("\\/");
        String imgLang = urlParts[urlParts.length - 2];
        String imageFolder = urlParts[urlParts.length - 3];
        if (languages.contains(imgLang)) {
            Image image = getImageByUrl(imageFolder, modelIdentifier);
            if (image == null) {
                result.addError("Unable to find image " + imageFolder + " in model.");
            } else {
                String urlDe = "";
                try {
                    urlDe = image.getUrlDe();
                    if (imgLang.equals("fr")) {
                        image.setUrlFr(urlDe);
                        image.setStatusFr(Status.FREIGEGEBEN);
                    } else if (imgLang.equals("it")) {
                        image.setUrlIt(urlDe);
                        image.setStatusIt(Status.FREIGEGEBEN);
                    } else if (imgLang.equals("en")) {
                        image.setUrlEn(urlDe);
                        image.setStatusEn(Status.FREIGEGEBEN);
                    } else {
                        logger.warn("Error: " + imgLang + "is not a valid language");
                        result.addError("Unknown language: " + imgLang + " in " + url);
                    }
                } catch (Exception e) {
                    result.addError("Unable to import: " + modelIdentifier + " -> " + url + ": " + e.getClass() + " "
                            + e.getMessage());
                }
                imageDAO.merge(image);
                s3.addFile(new ByteArrayInputStream(content), content.length, modelIdentifier, imgLang, urlDe);
                result.addSuccessResult(url + " -> " + imgLang);
            }
        }
        return result;
    }

    private Image getImageByUrl(String imageFolder, String modelIdentifier) {
        List<Image> images = imageDAO.getImages(modelIdentifier);
        for (Image img : images) {
            String folder = img.getUrlDe().replaceAll("\\/", "_").replaceAll("\\.", "_");
            if (folder.equals(imageFolder)) {
                return img;
            }
        }
        return null;
    }

}
