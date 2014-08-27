/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.translationimport;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.persistence.db.dao.ImageDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;

public class ImageImportHandlerTest {

    private ImageImportHandler imageImportHandler;

    @Before
    public void setUp() throws Exception {
        imageImportHandler = new ImageImportHandler();
        imageImportHandler.imageDAO = mock(ImageDAO.class);
        imageImportHandler.s3 = mock(S3.class);
    }

    @Test
    public void testHandleImport() {
        String url="MethodLibrary_20120828021719/Bilder/hermes_core_guidances_supportingmaterials_resources_H5_Masterfolien_jpg/fr/H5_Masterfolien.jpg";
        List<String> langs=new ArrayList<String>();
        langs.add("fr");
        Image image=new Image();
        image.setUrlDe("hermes.core/guidances/supportingmaterials/resources/H5_Masterfolien.jpg");
        List<Image> images=new ArrayList<Image>(); 
        images.add(image);
        when(imageImportHandler.imageDAO.getImages(anyString())).thenReturn(images);
        imageImportHandler.handleImport("model_identifier", "content".getBytes(), langs, url);
        verify(imageImportHandler.imageDAO).getImages("model_identifier");
        verify(imageImportHandler.s3).
                addFile((InputStream)any(), anyLong(), eq("model_identifier"), eq("fr"), eq("hermes.core/guidances/supportingmaterials/resources/H5_Masterfolien.jpg"));
    }
}
