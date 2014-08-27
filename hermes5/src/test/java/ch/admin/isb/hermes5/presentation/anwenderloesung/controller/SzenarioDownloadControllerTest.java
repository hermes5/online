/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import static ch.admin.isb.hermes5.domain.EPFModelBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.StreamedContent;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.ReflectionUtil;

public class SzenarioDownloadControllerTest {

    private SzenarioDownloadController controller;

    @Before
    public void setUp() throws Exception {
        controller = new SzenarioDownloadController();
        controller.anwenderloesungFacade = mock(AnwenderloesungFacade.class);
        controller.localeController = mock(LocaleController.class);
        when(controller.anwenderloesungFacade.getPublishedModel()).thenReturn(epfModel("model_id"));
        controller.szenarioWizardContext = new SzenarioWizardContext();
        controller.szenarioWizardContext.init();
        controller.szenarioEigeneElementeController = mock(SzenarioEigeneElementeController.class);
        controller.facesContext = mock(FacesContext.class);
        ExternalContext externalContext = mock(ExternalContext.class);
        HashMap<String, String> rhm = new HashMap<String, String>();
        rhm.put("host", "myhost");
        when(externalContext.getRequestHeaderMap()).thenReturn(rhm);
        when(controller.facesContext.getExternalContext()).thenReturn(externalContext);
        when(controller.localeController.getLanguage()).thenReturn("fr");
        controller.init();
    }

    @Test
    public void testGetIdentifier() {
        assertEquals("szenario-download", controller.getIdentifier());
    }

   

    @Test
    public void testInit() {
        assertFalse(controller.isRenderDownloadDialog());
        assertFalse(controller.isRenderPublishedOnlineDialog());
    }

    @Test
    public void testDisplay() {
        assertEquals("szenario-download", controller.display());

        assertFalse(controller.isRenderDownloadDialog());
        assertFalse(controller.isRenderPublishedOnlineDialog());
    }

    

    @Test
    public void testGoBack() {
        when(controller.szenarioEigeneElementeController.display()).thenReturn("eigene-elemente");
        assertEquals("eigene-elemente?faces-redirect=true", controller.goBack());
    }

    @Test
    public void testGetLanguages() {
        assertArrayEquals(new String[] { "fr" }, controller.getLanguages());
    }

    @Test
    public void testSetLanguages() {
        controller.setLanguages(new String[] { "fr", "it" });
        assertArrayEquals(new String[] { "fr", "it" }, controller.getLanguages());
    }

    @Test
    public void testGetInitialComponents() {
        assertArrayEquals(new String[] { "projektstrukturplan", "dokumentation", "ergebnisvorlagen" },
                controller.getComponents());
    }

    @Test
    public void testSetComponents() {
        controller.setComponents(new String[] { "dokumentation", "ergebnisvorlagen" });
        assertArrayEquals(new String[] { "dokumentation", "ergebnisvorlagen" }, controller.getComponents());
    }

    @Test
    public void testDownload() {
        when(
                controller.anwenderloesungFacade.generateDownloadZip(eq("model_id"), any(Szenario.class),
                        any(SzenarioUserData.class), eq(true), eq(true), eq(true),eq(false),  eq(Arrays.asList("fr"))))
                .thenReturn("zip".getBytes());
        controller.download();
        assertTrue(controller.isRenderDownloadDialog());
        assertFalse(controller.isRenderPublishedOnlineDialog());
        assertArrayEquals("zip".getBytes(), IOUtil.readToByteArray(controller.getFile().getStream()));
        assertNull(controller.getPublishedUrl());
        assertEquals("Hermes.zip", controller.getFile().getName());
    }

    @Test
    public void testPublishOnline() {
        when(
                controller.anwenderloesungFacade.generateDownloadZip(eq("model_id"), any(Szenario.class),
                        any(SzenarioUserData.class), eq(true), eq(true), eq(true),eq(false),  eq(Arrays.asList("fr"))))
                .thenReturn("zip".getBytes());
        when(controller.anwenderloesungFacade.publishOnline((byte[]) anyObject(), eq("fr"))).thenReturn("zip.zip");
        controller.publishOnline();
        assertFalse(controller.isRenderDownloadDialog());
        assertTrue(controller.isRenderPublishedOnlineDialog());
        assertArrayEquals("zip".getBytes(), IOUtil.readToByteArray(controller.getFile().getStream()));
        assertEquals("http://myhost/zip.zip", controller.getPublishedUrl());
    }

    @Test
    public void testPublishOnlineXML() {
        controller.setComponents(new String[] { "xmlmodel" });
        controller.publishOnline();

        verify(controller.anwenderloesungFacade).generateDownloadZip(eq("model_id"), any(Szenario.class),
                any(SzenarioUserData.class), eq(false), eq(false), eq(false), eq(true), eq(Arrays.asList("fr")));
    }

    @Test
    public void testPublishOnlineDocumentationIsSelected() {
        controller.setComponents(new String[] { "dokumentation" });
        controller.publishOnline();

        verify(controller.anwenderloesungFacade).generateDownloadZip(eq("model_id"), any(Szenario.class),
                any(SzenarioUserData.class), eq(false), eq(true), eq(false), eq(false), eq(Arrays.asList("fr")));
    }

    @Test
    public void testPublishOnlineProjektstrukturplanIsSelected() {
        controller.setComponents(new String[] { "projektstrukturplan" });
        controller.publishOnline();

        verify(controller.anwenderloesungFacade).generateDownloadZip(eq("model_id"), any(Szenario.class),
                any(SzenarioUserData.class), eq(true), eq(false), eq(false), eq(false), eq(Arrays.asList("fr")));
    }
    
    @Test
    public void testPublishOnlineVorlagenIsSelected() {
        controller.setComponents(new String[] { "ergebnisvorlagen" });
        controller.publishOnline();

        verify(controller.anwenderloesungFacade).generateDownloadZip(eq("model_id"), any(Szenario.class),
                any(SzenarioUserData.class), eq(false), eq(false), eq(true), eq(false), eq(Arrays.asList("fr")));
    }

    @Test
    public void testDownloadDialogClose() {
        when(
                controller.anwenderloesungFacade.generateDownloadZip(eq("model_id"), any(Szenario.class),
                        any(SzenarioUserData.class), eq(true), eq(true), eq(true),eq(true),  eq(Arrays.asList("fr"))))
                .thenReturn("zip".getBytes());
        controller.download();
        assertTrue(controller.isRenderDownloadDialog());
        assertNull( controller.downloadDialogClose());
        assertFalse(controller.isRenderDownloadDialog());
        assertFalse(controller.isRenderPublishedOnlineDialog());
    }

    @Test
    public void testPublishOnlineDialogClose() {
        when(
                controller.anwenderloesungFacade.generateDownloadZip(eq("model_id"), any(Szenario.class),
                        any(SzenarioUserData.class), eq(true), eq(true), eq(true),eq(true),  eq(Arrays.asList("fr"))))
                .thenReturn("zip".getBytes());
        when(controller.anwenderloesungFacade.publishOnline((byte[]) anyObject(), eq("fr"))).thenReturn("zip.zip");
        controller.publishOnline();
        assertTrue(controller.isRenderPublishedOnlineDialog());
        assertNull( controller.publishOnlineDialogClose());
        assertFalse(controller.isRenderDownloadDialog());
        assertFalse(controller.isRenderPublishedOnlineDialog());
    }

    @Test
    public void testGetFile() {
        ReflectionUtil.updateField(controller, "zipFile", "zipContent".getBytes());
        StreamedContent file = controller.getFile();
        assertEquals("Hermes.zip", file.getName());
        assertEquals("application/zip", file.getContentType());
        assertNotNull(file.getStream());
    }

    @Test
    public void testGetFile_zipFileNull() {
        try {
            StreamedContent file = controller.getFile();
            assertNull(file);
        } catch (NullPointerException ex) {
            fail("Getter threw null pointer exception!");
        }
    }
}
