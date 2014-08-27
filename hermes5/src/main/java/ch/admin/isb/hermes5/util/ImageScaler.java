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
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.enterprise.context.ApplicationScoped;
/**
 * src http://frickelblog.wordpress.com/2009/06/08/fast-image-scaling-in-java/
 *
 */
@ApplicationScoped
public class ImageScaler {


    public BufferedImage scaleImage(BufferedImage img, Dimension d) {
        img = scaleByHalf(img, d);
        img = scaleExact(img, d);
        return img;
    }

    private BufferedImage scaleByHalf(BufferedImage img, Dimension d) {
        int w = img.getWidth();
        int h = img.getHeight();
        float factor = getBinFactor(w, h, d);

        // make new size
        w *= factor;
        h *= factor;
        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        return scaled;
    }

    private BufferedImage scaleExact(BufferedImage img, Dimension d) {
        float factor = getFactor(img.getWidth(), img.getHeight(), d);

        // create the image
        int w = (int) (img.getWidth() * factor);
        int h = (int) (img.getHeight() * factor);
        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        return scaled;
    }

    private float getBinFactor(int width, int height, Dimension dim) {
        float factor = 1;
        float target = getFactor(width, height, dim);
        if (target <= 1) {
            while (factor / 2 > target) {
                factor /= 2;
            }
        } else {
            while (factor * 2 < target) {
                factor *= 2;
            }
        }
        return factor;
    }

    private float getFactor(int width, int height, Dimension dim) {
        float sx = dim.width / (float) width;
        float sy = dim.height / (float) height;
        return Math.max(sx, sy);
    }
}