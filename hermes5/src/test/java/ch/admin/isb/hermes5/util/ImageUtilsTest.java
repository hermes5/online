/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.util;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

public class ImageUtilsTest {

    private ImageUtils imageUtil;

    @Before
    public void setUp() throws Exception {
        imageUtil = new ImageUtils();
        imageUtil.imageScaler = new ImageScaler();
    }

    @Test
    public void testScaleByWidth() {
        byte[] gifStream = getResourceAsStream("logo.jpg");
        BufferedImage bufferedImage = imageUtil.toBufferedImage(gifStream);
        BufferedImage makeSmallerToMax = imageUtil.makeSmallerToMax(bufferedImage, 100, 90);
        assertEquals(100, makeSmallerToMax.getWidth());
        assertEquals((int)(10300.0/241), makeSmallerToMax.getHeight());
    }
    @Test
    public void testScaleByHeight() {
        byte[] gifStream = getResourceAsStream("logo.jpg");
        BufferedImage bufferedImage = imageUtil.toBufferedImage(gifStream);
        BufferedImage makeSmallerToMax = imageUtil.makeSmallerToMax(bufferedImage, 200, 50);
        assertEquals(50, makeSmallerToMax.getHeight());
        assertEquals((int)(50.0*bufferedImage.getWidth()/bufferedImage.getHeight())+1 /*RUNDUNGSFEHLER*/, makeSmallerToMax.getWidth());
    }

    @Test
    public void testToPNG() {
        byte[] gifStream = getResourceAsStream("logo.jpg");
        BufferedImage bufferedImage = imageUtil.toBufferedImage(gifStream);
        byte[] png = imageUtil.toPNGByteArray(bufferedImage);
        assertNotNull(png);
        assertFalse(Arrays.equals(gifStream, png));
    }
    
    private byte[] getResourceAsStream(String string) {
        InputStream resource = ImageUtilsTest.class.getResourceAsStream(string);
        assertNotNull(resource);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(resource);
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
