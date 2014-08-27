/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.publish;

import static ch.admin.isb.hermes5.domain.MethodElementBuilder.customCategory;
import static ch.admin.isb.hermes5.domain.SzenarioBuilder.ergebnis;
import static ch.admin.isb.hermes5.domain.SzenarioBuilder.modul;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.ejb.AsyncResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.rendering.customelement.CustomElementPostProcessor;
import ch.admin.isb.hermes5.business.rendering.onlinepublikation.MenuItem;
import ch.admin.isb.hermes5.business.rendering.onlinepublikation.OnlinePublikationMenuRenderer;
import ch.admin.isb.hermes5.business.rendering.onlinepublikation.OnlinePublikationRenderer;
import ch.admin.isb.hermes5.business.rendering.onlinepublikation.OnlinePublikationRendererRepository;
import ch.admin.isb.hermes5.business.rendering.postprocessor.ImageLinkPostProcessor;
import ch.admin.isb.hermes5.business.rendering.postprocessor.InternalLinkPostProcessor;
import ch.admin.isb.hermes5.business.search.IndexWriterWrapper;
import ch.admin.isb.hermes5.business.search.SearchEngine;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.business.util.FutureUtil;
import ch.admin.isb.hermes5.business.word.WordDocumentCustomizer;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Document;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.Kategorie;
import ch.admin.isb.hermes5.domain.Phase;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.ZipUtil;

public class OnlinePublikationPublishWorkflowTest {

    private static final String MODEL = "model";
    private OnlinePublikationPublishWorkflow onlinePublikationPublishWorkflow;
    private S3 s3Mock = mock(S3.class);

    @Before
    public void setUp() throws Exception {
        onlinePublikationPublishWorkflow = new OnlinePublikationPublishWorkflow();
        onlinePublikationPublishWorkflow.onlinePublikationPublishOnlinePublikationWorkflow = new OnlinePublikationPublishOnlinePublikationWorkflow();
        onlinePublikationPublishWorkflow.onlinePublikationPublishSampleSzenarienWorkflow = new OnlinePublikationPublishSampleSzenarienWorkflow();
        onlinePublikationPublishWorkflow.onlinePublikationPublishOnlinePublikationWorkflow.futureUtil = new FutureUtil();
        onlinePublikationPublishWorkflow.onlinePublikationPublishSampleSzenarienWorkflow.futureUtil = new FutureUtil();
        OnlinePublikationPublishOnlinePublikationWorkflow onlinePublikationPublishOnlinePublikationWorkflow = onlinePublikationPublishWorkflow.onlinePublikationPublishOnlinePublikationWorkflow;
        onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow = new OnlinePublikationPublishMethodElementWorkflow();
        onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.internalLinkPostProcessor = mock(InternalLinkPostProcessor.class);
        onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.imageLinkPostProcessor = mock(ImageLinkPostProcessor.class);
        onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.customElementPostProcessor = mock(CustomElementPostProcessor.class);
        onlinePublikationPublishWorkflow.modelRepository = mock(ModelRepository.class);
        OnlinePublikationMenuGenerator menuGenerator = mock(OnlinePublikationMenuGenerator.class);
        onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationMenuGenerator = menuGenerator;
        onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.onlinePublikationRendererRepository = mock(OnlinePublikationRendererRepository.class);
        onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.onlinePublikationMenuGenerator = menuGenerator;
        onlinePublikationPublishOnlinePublikationWorkflow.s3 = s3Mock;
        onlinePublikationPublishWorkflow.onlinePublikationPublishSampleSzenarienWorkflow.s3 = s3Mock;
        onlinePublikationPublishOnlinePublikationWorkflow.translationRepository = mock(TranslationRepository.class);
        onlinePublikationPublishOnlinePublikationWorkflow.wordDocumentCustomizer = mock(WordDocumentCustomizer.class);
        onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationMenuRenderer = mock(OnlinePublikationMenuRenderer.class);
        onlinePublikationPublishWorkflow.onlinePublikationPublishSampleSzenarienWorkflow.zipUtil = new ZipUtil();
        onlinePublikationPublishOnlinePublikationWorkflow.searchEngine = mock(SearchEngine.class);
        IndexWriterWrapper mock = mock(IndexWriterWrapper.class);
        when(
                onlinePublikationPublishOnlinePublikationWorkflow.searchEngine.startIndexing(anyString(),
                        any(LocalizationEngine.class))).thenReturn(mock);

        when(
                onlinePublikationPublishOnlinePublikationWorkflow.searchEngine
                        .finishIndexing(any(IndexWriterWrapper.class))).thenReturn(new AsyncResult<Void>(null));

        when(s3Mock.addPublishedFile(anyString(), anyString(), (byte[]) any())).thenReturn(new AsyncResult<Void>(null));
        when(s3Mock.addSampleSzenarioFile(anyString(), anyString(), (byte[]) any())).thenReturn(
                new AsyncResult<Void>(null));

        when(
                onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.onlinePublikationRendererRepository
                        .lookupOnlinePublikationRenderer(any(AbstractMethodenElement.class))).thenReturn(
                new OnlinePublikationRenderer() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public String renderModelElement(AbstractMethodenElement abstractMethodenElement,
                            List<MenuItem> adjustedMenu, LocalizationEngine localizationEngine,
                            PublishContainer hermesWebsite, boolean onlySearchableContent) {
                        return abstractMethodenElement.getId();
                    }

                    @Override
                    public boolean isResponsibleFor(AbstractMethodenElement methodElement) {
                        return true;
                    }
                });

        when(
                onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.internalLinkPostProcessor
                        .adjustInternalLinks(any(AbstractMethodenElement.class), anyString(),
                                anyListOf(AbstractMethodenElement.class))).thenAnswer(new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return String.valueOf(invocation.getArguments()[0]);
            }
        });
        when(
                onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.imageLinkPostProcessor
                        .adjustImageLinks(anyString())).thenAnswer(new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return String.valueOf(invocation.getArguments()[0]);
            }
        });
        when(
                onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationPublishMethodElementWorkflow.customElementPostProcessor
                        .renderCustomElements(anyString(), any(PublishContainer.class), any(LocalizationEngine.class)))
                .thenAnswer(new Answer<String>() {

                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return String.valueOf(invocation.getArguments()[0]);
                    }
                });

        EPFModel modelToPublish = new EPFModel();
        modelToPublish.setStatusFr(Status.FREIGEGEBEN);
        when(onlinePublikationPublishWorkflow.modelRepository.getModelByIdentifier(MODEL)).thenReturn(modelToPublish);
        HashMap<String, MethodElement> index = new HashMap<String, MethodElement>();
        Kategorie root = new Kategorie(customCategory("root"), index);
        List<AbstractMethodenElement> elementsToPublish = Arrays.asList(modul("modul1"), ergebnis("ergebnis1"));
        when(onlinePublikationPublishWorkflow.modelRepository.getHermesWebsite(MODEL)).thenReturn(
                new PublishContainer(root, elementsToPublish, new ArrayList<Phase>(), new ArrayList<Szenario>()));
        when(
                onlinePublikationPublishOnlinePublikationWorkflow.wordDocumentCustomizer.adjustDocumentWithUserData(
                        anyString(), (byte[]) anyObject(), any(SzenarioUserData.class))).thenAnswer(
                new Answer<byte[]>() {

                    @Override
                    public byte[] answer(InvocationOnMock invocation) throws Throwable {
                        return (byte[]) invocation.getArguments()[1];
                    }

                });

        when(
                onlinePublikationPublishOnlinePublikationWorkflow.onlinePublikationMenuRenderer.renderTopMenu(
                        anyListOf(MenuItem.class), any(LocalizationEngine.class))).thenReturn("menustring");
    }

    @Test
    public void testPublish() {
        EPFModel expected = new EPFModel();
        when(onlinePublikationPublishWorkflow.modelRepository.publish(MODEL)).thenReturn(expected);
        EPFModel publish = onlinePublikationPublishWorkflow.publish(MODEL);
        assertEquals(expected, publish);
        verify(s3Mock, times(2 * (2 + 1))).addPublishedFile(eq(MODEL), anyString(), (byte[]) anyObject());
    }

    @Test
    public void testPublishWithImages() {
        List<Image> images = new ArrayList<Image>();
        images.add(image("im1", s3Mock));
        images.add(image("im2", s3Mock));
        when(
                onlinePublikationPublishWorkflow.onlinePublikationPublishOnlinePublikationWorkflow.translationRepository
                        .getImages(MODEL)).thenReturn(images);
        EPFModel expected = new EPFModel();
        when(onlinePublikationPublishWorkflow.modelRepository.publish(MODEL)).thenReturn(expected);
        EPFModel publish = onlinePublikationPublishWorkflow.publish(MODEL);
        assertEquals(expected, publish);
        verify(s3Mock, times(2 * (2 + 2 + 1))).addPublishedFile(eq(MODEL), anyString(), (byte[]) anyObject());
    }

    @Test
    public void testPublishWithDocuments() {
        List<Document> docs = new ArrayList<Document>();
        docs.add(doc("doc1", s3Mock));
        docs.add(doc("doc2", s3Mock));
        when(
                onlinePublikationPublishWorkflow.onlinePublikationPublishOnlinePublikationWorkflow.translationRepository
                        .getDocuments(MODEL)).thenReturn(docs);
        EPFModel expected = new EPFModel();
        when(onlinePublikationPublishWorkflow.modelRepository.publish(MODEL)).thenReturn(expected);
        EPFModel publish = onlinePublikationPublishWorkflow.publish(MODEL);
        assertEquals(expected, publish);
        verify(s3Mock, times(2 * (2 + 2 + 1))).addPublishedFile(eq(MODEL), anyString(), (byte[]) anyObject());
    }

    @Test
    public void testPublishWithAdjustedDocuments() {
        List<Document> docs = new ArrayList<Document>();
        docs.add(doc("doc1", s3Mock));
        when(
                onlinePublikationPublishWorkflow.onlinePublikationPublishOnlinePublikationWorkflow.translationRepository
                        .getDocuments(MODEL)).thenReturn(docs);
        EPFModel expected = new EPFModel();
        when(onlinePublikationPublishWorkflow.modelRepository.publish(MODEL)).thenReturn(expected);
        when(
                onlinePublikationPublishWorkflow.onlinePublikationPublishOnlinePublikationWorkflow.wordDocumentCustomizer
                        .adjustDocumentWithUserData(eq("doc1_de"), eq("doc1_de".getBytes()),
                                any(SzenarioUserData.class))).thenReturn("doc1_de_adjusted".getBytes());
        EPFModel publish = onlinePublikationPublishWorkflow.publish(MODEL);
        assertEquals(expected, publish);
        verify(
                onlinePublikationPublishWorkflow.onlinePublikationPublishOnlinePublikationWorkflow.wordDocumentCustomizer)
                .adjustDocumentWithUserData(eq("doc1_de"), eq("doc1_de".getBytes()), any(SzenarioUserData.class));

        verify(s3Mock).addPublishedFile(eq(MODEL), eq("de/doc1_de"), eq("doc1_de_adjusted".getBytes()));
        verify(s3Mock).addPublishedFile(eq(MODEL), eq("fr/doc1_fr"), eq("doc1_fr".getBytes()));
    }

    private Image image(String string, S3 s3) {
        Image image = new Image();
        String urlDE = string + "_de";
        image.setUrlDe(urlDE);
        String urlFR = string + "_fr";
        image.setUrlFr(urlFR);
        when(s3.readFile(MODEL, "de", urlDE)).thenReturn(urlDE.getBytes());
        when(s3.readFile(MODEL, "fr", urlFR)).thenReturn(urlFR.getBytes());
        return image;
    }

    private Document doc(String string, S3 s3) {
        Document doc = new Document();
        String urlDE = string + "_de";
        doc.setUrlDe(urlDE);
        String urlFR = string + "_fr";
        doc.setUrlFr(urlFR);
        when(s3.readFile(MODEL, "de", urlDE)).thenReturn(urlDE.getBytes());
        when(s3.readFile(MODEL, "fr", urlFR)).thenReturn(urlFR.getBytes());
        return doc;
    }

}
