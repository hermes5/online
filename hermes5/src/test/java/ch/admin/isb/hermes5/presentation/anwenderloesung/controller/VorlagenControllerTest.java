/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.anwenderloesung.controller;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import ch.admin.isb.hermes5.business.service.AnwenderloesungFacade;
import ch.admin.isb.hermes5.domain.DefaultLocalizable;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Localizable;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.presentation.common.SelectableRow;

public class VorlagenControllerTest {

    private static final String MODEL_IDENTIFIER_2 = "model2";
    private static final List<Localizable> WEB_LINK_LIST = Arrays.asList((Localizable) new DefaultLocalizable("id",
            "<a href=\"http://www.duckduckgo.com\">The Duck</a>"));
    private static final String MODEL_IDENTIFIER_1 = "MODEL1";
    private static final Modul MODUL1 = modul("m1");
    private static final Modul MODUL2 = modul("m2");
    private static final List<Modul> MODUL_LIST = Arrays.asList(MODUL1, MODUL2);
    private static final Document DOCUMENT1 = new Document();
    private static final Document DOCUMENT2 = new Document();
    private static final Document DOCUMENT3 = new Document();
    private static final List<Document> DOCUMENT_LIST = Arrays.asList(DOCUMENT1, DOCUMENT2, DOCUMENT3);
    private static final byte[] ZIP_FILE = "zipfile".getBytes();

    private VorlagenController vorlagenController;

    @Before
    public void init() {
        vorlagenController = new VorlagenController();

        EPFModel model = mock(EPFModel.class);
        when(model.getIdentifier()).thenReturn(MODEL_IDENTIFIER_1);

        vorlagenController.anwenderloesungFacade = mock(AnwenderloesungFacade.class);
        when(vorlagenController.anwenderloesungFacade.getModulesWithErgebnisse(anyString())).thenReturn(MODUL_LIST);
        when(vorlagenController.anwenderloesungFacade.getDocumentsOfModule(anyString(), any(Modul.class))).thenReturn(
                DOCUMENT_LIST);
        when(vorlagenController.anwenderloesungFacade.getWebLinksOfModule(anyString(), any(Modul.class))).thenReturn(
                WEB_LINK_LIST);
        when(vorlagenController.anwenderloesungFacade.getPublishedModel()).thenReturn(model);
        when(vorlagenController.anwenderloesungFacade.generateVorlagenZip(anyListOf(Document.class), anyString()))
                .thenReturn(ZIP_FILE);

        vorlagenController.localizer = LocalizerBuilder.getLocalizerDe();
        vorlagenController.presentationNameComparator = new PresentationNameComparator();
        vorlagenController.presentationNameComparator.localizer = LocalizerBuilder.getLocalizerDe();
        vorlagenController.localeController = mock(LocaleController.class);
        when(vorlagenController.localeController.getLanguage()).thenReturn("de");
    }

    @Test
    public void testInitWithFirstTime() {
        vorlagenController.vorlagenControllerSession = mock(VorlagenControllerSession.class);
        when(vorlagenController.vorlagenControllerSession.getModelIdentifier()).thenReturn(null);
        vorlagenController.init();

        verify(vorlagenController.vorlagenControllerSession).init(eq(MODEL_IDENTIFIER_1),
                Matchers.<List<SelectableRow<Modul>>> any(),
                Matchers.<Map<Modul, List<SelectableRow<Document>>>> any(),
                Matchers.<Map<Modul, List<Localizable>>> any());
    }

    @Test
    public void testInitWithChangedModel() {
        vorlagenController.vorlagenControllerSession = mock(VorlagenControllerSession.class);
        when(vorlagenController.vorlagenControllerSession.getModelIdentifier()).thenReturn(MODEL_IDENTIFIER_2);
        vorlagenController.init();

        verify(vorlagenController.vorlagenControllerSession).init(eq(MODEL_IDENTIFIER_1),
                Matchers.<List<SelectableRow<Modul>>> any(),
                Matchers.<Map<Modul, List<SelectableRow<Document>>>> any(),
                Matchers.<Map<Modul, List<Localizable>>> any());
    }

    @Test
    public void testInitWithExistingModel() {
        vorlagenController.vorlagenControllerSession = mock(VorlagenControllerSession.class);
        when(vorlagenController.vorlagenControllerSession.getModelIdentifier()).thenReturn(MODEL_IDENTIFIER_1);
        vorlagenController.init();

        verify(vorlagenController.vorlagenControllerSession, never()).init(eq(MODEL_IDENTIFIER_1),
                Matchers.<List<SelectableRow<Modul>>> any(),
                Matchers.<Map<Modul, List<SelectableRow<Document>>>> any(),
                Matchers.<Map<Modul, List<Localizable>>> any());
    }

    @Test
    public void testInitialLoad() {
        VorlagenControllerSession vcs = new VorlagenControllerSession();
        vorlagenController.vorlagenControllerSession = vcs;
        vorlagenController.initialLoad();

        assertEquals(MODUL_LIST.size(), vcs.getModules().size());
        assertTrue(vcs.getDocuments().keySet().containsAll(MODUL_LIST));
        assertTrue(vcs.getWebLinks().keySet().containsAll(MODUL_LIST));
        assertTrue(vcs.getWebLinks().get(MODUL1).containsAll(WEB_LINK_LIST));
        assertTrue(vcs.getWebLinks().get(MODUL2).containsAll(WEB_LINK_LIST));
    }

    @Test
    public void testDownloadSelected() {
        VorlagenControllerSession vcs = new VorlagenControllerSession();
        vorlagenController.vorlagenControllerSession = vcs;
        vorlagenController.init();

        List<SelectableRow<Document>> documents = vorlagenController.vorlagenControllerSession.getDocuments().get(
                MODUL1);

        List<Document> selected = new ArrayList<Document>();
        selected.add(documents.get(0).getData());
        documents.get(0).setSelected(true);
        selected.add(documents.get(2).getData());
        documents.get(2).setSelected(true);

        vorlagenController.downloadSelected();

        verify(vorlagenController.anwenderloesungFacade).generateVorlagenZip(eq(selected), eq("de"));
        assertEquals(ZIP_FILE, vcs.getZipFile());
        assertTrue(vcs.isRenderDownloadDialog());
    }

    @Test
    public void testGetWebLinks() {
        VorlagenControllerSession vcs = new VorlagenControllerSession();
        vorlagenController.vorlagenControllerSession = vcs;
        vorlagenController.init();

        List<String> webLinks = vorlagenController.getWebLinks(MODUL1);
        assertTrue(webLinks.contains("<a href=\"http://www.duckduckgo.com\" target=\"_blank\">The Duck</a>"));
    }
}
