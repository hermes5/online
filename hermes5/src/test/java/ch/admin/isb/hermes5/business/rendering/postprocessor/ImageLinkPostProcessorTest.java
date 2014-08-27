/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.postprocessor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.util.ResourceUtils;
import ch.admin.isb.hermes5.util.StringUtil;

public class ImageLinkPostProcessorTest {

    private ImageLinkPostProcessor imageLinkPostProcessor;

    @Before
    public void setUp() {
        imageLinkPostProcessor = new ImageLinkPostProcessor();
    }

    @Test
    public void testAdjustImageLinksNoImage() {
        String content = "content without link but with src and img in content";
        assertEquals(content, imageLinkPostProcessor.adjustImageLinks(content));
    }

    @Test
    public void testAdjustImageImpressum() {
        String content = "<img style=\"WIDTH: 83px; HEIGHT: 43px\" alt=\"\" \nsrc=\"hermes.core/guidances/supportingmaterials/resources/FSC_Staempfli_Mix_sw_quer_de.jpeg\" width=\"592\" height=\"256\" />Gedruckt auf FSC-zertifiziertem Papier";
        assertTrue(content,
                imageLinkPostProcessor.adjustImageLinks(content).contains("src=\"content/hermes.core/guidance"));
        assertTrue(content, imageLinkPostProcessor.adjustImageLinks(content).contains("WIDTH: 83px; HEIGHT: 43px"));
    }

    @Test
    public void testAdjustImageLinksSingleImage() {
        assertEquals("content with <img src=\"content/abc.jpg\"> in it",
                imageLinkPostProcessor.adjustImageLinks("content with <img src=\"abc.jpg\"> in it"));
    }
    

    // Issue HERMES-165
    @Test
    public void testAdjustImageLinksWithWronglyPlacesSpace() {
        assertEquals("content with <img src=\"content/abc.jpg\"> in it",
                imageLinkPostProcessor.adjustImageLinks("content with <img src=\" abc.jpg\"> in it"));
    }

    @Test
    public void testAdjustImageLinksTwoImage() {
        String actual = imageLinkPostProcessor
                .adjustImageLinks("content with <img src=\"abc.jpg\"> <img src=\"cde.jpg\"> in it");
        assertEquals("content with <img src=\"content/abc.jpg\"> <img src=\"content/cde.jpg\"> in it", actual);
    }

    @Test
    public void testAdjustImageLinksWithExternalLinkToLargeImage() {
        String actual = imageLinkPostProcessor
                .adjustImageLinks("content with <a id=\"id1\" href=\"hermes.core/abc.jpg\" target=\"_blank\">  <img src=\"abc.jpg\"></a> in it");
        assertEquals(
                "content with <a id=\"id1\" href=\"content/hermes.core/abc.jpg\" target=\"_blank\">  <img src=\"content/abc.jpg\"></a> in it",
                actual);
    }

    @Test
    public void testAdjustImageLinksLeaveNormalLinksUntouched() {
        String actual = imageLinkPostProcessor
                .adjustImageLinks("content with <a href=\"www.target.com\" target=\"_blank\">Normal Link</a> in it");
        assertEquals("content with <a href=\"www.target.com\" target=\"_blank\">Normal Link</a> in it", actual);
    }

    @Test
    public void testAdjustImageLinksLeaveExternalLinksOnImagesUntouched() {
        String actual = imageLinkPostProcessor
                .adjustImageLinks("content with <a href=\"www.target.com\" target=\"_blank\"><img src=\"abc.jpg\"></a> in it");
        assertEquals(
                "content with <a href=\"www.target.com\" target=\"_blank\"><img src=\"content/abc.jpg\"></a> in it",
                actual);
    }

    @Test
    public void testAdjustImageLinksLeaveExternalLinksAfterImagesUntouched() {
        String actual = imageLinkPostProcessor
                .adjustImageLinks("content with <a id=\"id1\" href=\"hermes.core/abc.jpg\" target=\"_blank\">  <img src=\"abc.jpg\"></a> Text <a href=\"www.target.com\">Test</a> in it");
        assertEquals(
                "content with <a id=\"id1\" href=\"content/hermes.core/abc.jpg\" target=\"_blank\">  <img src=\"content/abc.jpg\"></a> Text <a href=\"www.target.com\">Test</a> in it",
                actual);
    }

    @Test
    public void testAdjustImagesLinksRealExample() {
        String actual = imageLinkPostProcessor
                .adjustImageLinks("Text Before <a href=\"hermes.core/guidances/supportingmaterials/resources/hermes_szenarien_09und10_de.png\" target=\"_blank\">\n<img alt=\"\" src=\"hermes.core/guidances/supportingmaterials/resources/hermes_szenarien_09und10_de.png\" width=\"468\" height=\"326\">\n</a> Text After");
        assertEquals(
                "Text Before <a href=\"content/hermes.core/guidances/supportingmaterials/resources/hermes_szenarien_09und10_de.png\" target=\"_blank\">\n<img alt=\"\" src=\"content/hermes.core/guidances/supportingmaterials/resources/hermes_szenarien_09und10_de.png\" width=\"468\" height=\"326\">\n</a> Text After",
                actual);
    }

    @Test
    public void testAdjustImages1000MatchRealExample() {

        String content = StringUtil.fromBytes(ResourceUtils.loadResource(getClass().getResourceAsStream(
                "supportingmaterial_szenario_it_individualanwendung.html")));

        long before = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            imageLinkPostProcessor.adjustImageLinks(content);
        }

        long time = System.currentTimeMillis() - before;
        assertTrue("Time taken: " + time, time < 10000);
    }

}
