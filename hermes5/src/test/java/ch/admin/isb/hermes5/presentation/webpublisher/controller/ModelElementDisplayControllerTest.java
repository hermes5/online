/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.presentation.webpublisher.controller;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.business.service.WebPublisherFacade;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.ModelElement;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.presentation.common.ImageUrlUtil;
import ch.admin.isb.hermes5.presentation.common.SelectableRow;


public class ModelElementDisplayControllerTest {

    private ModelElementDisplayController modelElementDisplayController;
    
    @Before
    public void setUp() throws Exception {
        modelElementDisplayController=new ModelElementDisplayController();
        modelElementDisplayController.webPublisherFacade=mock(WebPublisherFacade.class);
        modelElementDisplayController.imageUrlUtil=mock(ImageUrlUtil.class);
    }

    @Test
    public void testGetOtherText() {
       TranslateableText text=mock(TranslateableText.class);
       modelElementDisplayController.setLang("it");
       when(text.getTextIt()).thenReturn("text_it");
       when(modelElementDisplayController.imageUrlUtil.adaptImageUrls(eq("text_it"),anyString(),eq("it"))).thenReturn("adapted_text_it");
       Assert.assertEquals("adapted_text_it", modelElementDisplayController.getOtherText(text));
    }


    @Test
    public void testDeleteTexts() {
        modelElementDisplayController.setLang("fr");
       TranslateableText text=mock(TranslateableText.class);
       when(text.getElementIdentifier()).thenReturn("element");
       when(text.getModelIdentifier()).thenReturn("model_identifier");
       when(text.getTextIdentifier()).thenReturn("text_identifier");
       EPFModel model=mock(EPFModel.class);
       ModelElement modelElement=mock(ModelElement.class);
       when(model.isPublished()).thenReturn(false);
       when(modelElementDisplayController.webPublisherFacade.getModel(anyString())).thenReturn(model);
       when(modelElementDisplayController.webPublisherFacade.getModelElement(anyString(), anyString())).thenReturn(modelElement);
       SelectableRow<TranslateableText> row=new SelectableRow<TranslateableText>(text);
       row.setSelected(true);
       modelElementDisplayController.translateableTexts = new ArrayList<SelectableRow<TranslateableText>>();
       modelElementDisplayController.translateableTexts.add(row);
       modelElementDisplayController.deleteTexts();
       verify(modelElementDisplayController.webPublisherFacade).deleteTranslateableText("model_identifier", "element", "text_identifier", "fr");
    }

}
