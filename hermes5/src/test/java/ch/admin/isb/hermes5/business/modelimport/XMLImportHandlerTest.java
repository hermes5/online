/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;
import ch.admin.isb.hermes5.util.ResourceUtils;

public class XMLImportHandlerTest {

    private XMLImportHandler xmlImportHandler;

    @Before
    public void setUp() {
        xmlImportHandler = new XMLImportHandler();
        xmlImportHandler.methodLibraryUnmarshaller = mock(MethodLibraryUnmarshaller.class);
        xmlImportHandler.methodLibraryVisitorDriver = mock(MethodLibraryVisitorDriver.class);
        xmlImportHandler.visitor = mock(TranslationVisitor.class);
        xmlImportHandler.modelRepository = mock(ModelRepository.class);
        
    }

    @Test
    public void testIsResponsible_xml() {
        assertTrue(xmlImportHandler.isResponsible("aa/bb/filename.xml"));
    }

    @Test
    public void testIsResponsible_XML() {
        assertTrue(xmlImportHandler.isResponsible("AA/BB/FILENAME.XML"));
    }

    @Test
    public void testIsResponsible_Xml() {
        assertTrue(xmlImportHandler.isResponsible("aa/bb/filename.Xml"));
    }

    @Test
    public void testIsResponsible_png() {
        assertFalse(xmlImportHandler.isResponsible("aa/bb/filename.png"));
    }

    @Test
    public void testIsResponsible_xmlt() {
        assertFalse(xmlImportHandler.isResponsible("aa/bb/filename.xmlt"));
    }

    @Test
    public void testHandleImport() throws Exception {
        MethodLibrary ml = new MethodLibrary();
        when(xmlImportHandler.methodLibraryUnmarshaller.unmarshalMethodLibrary(any(InputStream.class))).thenReturn(ml);
        InputStream resourceAsStream = getClass().getResourceAsStream("export.xml");
        byte[] content = ResourceUtils.loadResource(resourceAsStream);
        xmlImportHandler.handleImport("modelIdentifier", content,  "export.xml");
        verify(xmlImportHandler.methodLibraryVisitorDriver).visit(any(MethodLibrary.class), eq(xmlImportHandler.visitor));
    }

}
