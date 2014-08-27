/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.userszenario.szenario;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.util.ZipOutputBuilder;
import ch.admin.isb.hermes5.business.util.ZipOutputBuilderUtils;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.AssertUtils;
import ch.admin.isb.hermes5.util.ZipUtil;

public class AddDocumentIfNecessaryPostProcessorTest {

    private AddResourceIfNecessaryPostProcessor addResourceIfNecessaryPostProcessor;
    private final ZipUtil zipUtil = new ZipUtil();

    @Before
    public void setUp() throws Exception {
        addResourceIfNecessaryPostProcessor = new AddResourceIfNecessaryPostProcessor();
        addResourceIfNecessaryPostProcessor.zipOutputBuilderUtils = new ZipOutputBuilderUtils();
        addResourceIfNecessaryPostProcessor.s3 = mock(S3.class);
    }

    @Test
    public void testAddImage() throws IOException {
        String fileContent = "<p><img alt=\"\" src=\"hermes.core/disciplines/resources/projektfuehrung.JPG\" width=\"600\" height=\"381\"></p>";
        ZipOutputBuilder zipBuilder = new ZipOutputBuilder();

        when(
                addResourceIfNecessaryPostProcessor.s3.readFile("modelIdentifier", "de",
                        "hermes.core/disciplines/resources/projektfuehrung.JPG")).thenReturn(
                "hermes.core/disciplines/resources/projektfuehrung.JPG".getBytes());

        addResourceIfNecessaryPostProcessor.addImagesIfNecessary("doc", fileContent, zipBuilder, "de",
                "modelIdentifier");
        byte[] result = zipBuilder.getResult();
        ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(result));
        ZipEntry nextEntry;
        List<String> names = new ArrayList<String>();
        while ((nextEntry = zipInputStream.getNextEntry()) != null) {
            names.add(nextEntry.getName());
            byte[] byteArray = zipUtil.readZipEntry(zipInputStream);
            assertNotNull(byteArray);
        }
        AssertUtils.assertContains(names, "doc/de/hermes.core/disciplines/resources/projektfuehrung.JPG");
    }
}
