/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.presentation.common.ImageUrlUtil;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;

public class ImageUrlUtilTest {

    private ImageUrlUtil imageUrlUtil;

    @Before
    public void setUp() throws Exception {
        imageUrlUtil = new ImageUrlUtil();
        new ReflectionTestHelper().updateField(imageUrlUtil, "s3PublicBucketUrl",
                Hardcoded.configuration("http://localhost:8080/s3mock?url="));
    }

    @Test
    public void testAdaptImageUrlsNull() {
        String adaptImageUrls = imageUrlUtil.adaptImageUrls(null, "20120824105805", "fr");
        assertNull(adaptImageUrls);
    }

    @Test
    public void testAdaptImageUrls() {
        String text = "Die Abbildung zeigt die Struktur der Projekt- und \nStammorganisation mit ihren Rollengruppen und Rollen.<img alt=\"Rollenmodell\" src=\"hermes.core/guidances/supportingmaterials/resources/rollenmodell_1.jpg\" width=\"600\" height=\"457\">Die folgende Grafik zeigt als Beispiel ein Projekt mit mehreren Teilprojekten.";
        String adaptImageUrls = imageUrlUtil.adaptImageUrls(text, "20120824105805", "fr");
        String expected = "Die Abbildung zeigt die Struktur der Projekt- und \nStammorganisation mit ihren Rollengruppen und Rollen.<img alt=\"Rollenmodell\" src=\"http://localhost:8080/s3mock?url=/20120824105805/fr/hermes.core/guidances/supportingmaterials/resources/rollenmodell_1.jpg\" width=\"600\" height=\"457\">Die folgende Grafik zeigt als Beispiel ein Projekt mit mehreren Teilprojekten.";
        assertEquals(expected, adaptImageUrls);
    }

    @Test
    public void testAdaptImageUrlsTwice() {
        String text = "Rollen.<img alt=\"Rollenmodell\" src=\"hermes.core/guidances/supportingmaterials/resources/rollenmodell_1.jpg\" width=\"600\" height=\"457\">Die <img alt=\"Rollenmodell\" src=\"hermes.core/guidances/supportingmaterials/resources/rollenmodell_2.jpg\" width=\"600\" height=\"457\">";
        String adaptImageUrls = imageUrlUtil.adaptImageUrls(text, "20120824105805", "fr");
        String expected = "Rollen.<img alt=\"Rollenmodell\" src=\"http://localhost:8080/s3mock?url=/20120824105805/fr/hermes.core/guidances/supportingmaterials/resources/rollenmodell_1.jpg\" width=\"600\" height=\"457\">Die <img alt=\"Rollenmodell\" src=\"http://localhost:8080/s3mock?url=/20120824105805/fr/hermes.core/guidances/supportingmaterials/resources/rollenmodell_2.jpg\" width=\"600\" height=\"457\">";
        assertEquals(expected, adaptImageUrls);
    }

}
