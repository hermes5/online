/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    @Inject
    ImageScaler imageScaler;

    public BufferedImage makeSmallerToMax(BufferedImage img, int maxWidth, int maxHeight) {
        if (img.getHeight() > maxHeight
                && (img.getHeight() / (double) maxHeight) > (img.getWidth() / (double) maxWidth)) {
            // scale by height
            int scaledWidth = (int) (img.getWidth() * ((double) maxHeight) / img.getHeight());
            return imageScaler.scaleImage(img, new Dimension(scaledWidth, maxHeight));
        }
        if (img.getWidth() > maxWidth) {
            // scale by width
            int scaledHeight = (int) (img.getHeight() * ((double) maxWidth) / img.getWidth());
            return imageScaler.scaleImage(img, new Dimension(maxWidth, scaledHeight));
        }

        return img;
    }

    public byte[] toPNGByteArray(BufferedImage bufferedImage) {
        return toByteArray(bufferedImage, "png");
    }
    private byte[] toByteArray(BufferedImage bufferedImage, String filetype) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, filetype, output);
            return output.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public BufferedImage toBufferedImage(byte[] byteArray) {
        try {
            return ImageIO.read(new ByteArrayInputStream(byteArray));
        } catch (Exception e) {
            logger.warn("Can't convert to buffered image (JPEG with CYMK colorspace?)", e);
            return null;
        }
    }

}