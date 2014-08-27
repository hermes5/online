/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.anwenderloesung;

import java.util.Map;

import javax.inject.Inject;

import ch.admin.isb.hermes5.business.rendering.customelement.RelationshipTablePreprocessor;
import ch.admin.isb.hermes5.business.translation.LocalizationEngine;
import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.domain.Aufgabe;

public class AnwenderloesungAufgabeRenderer extends AnwenderloesungModelElementRenderer {

    private static final long serialVersionUID = 1L;

    @Inject
    RelationshipTablePreprocessor relationshipTablePreprocessor;

    @Override
    public boolean isResponsible(AbstractMethodenElement modelElement) {
        return modelElement instanceof Aufgabe;
    }

    @Override
    public String renderModelElement(AbstractMethodenElement modelElement, LocalizationEngine localizationEngine,
            AnwenderloesungRenderingContainer container) {
        Map<String, Object> ctx = initContext();

        ctx.put("c", container);
        ctx.put("me", modelElement);
        ctx.put("l", localizationEngine);
        ctx.put("lang", localizationEngine.getLanguage());

        ctx.put("titleKey", "al_doc_type_aufgabe");

        ctx.put("relationshipTableRecords", relationshipTablePreprocessor.preprocess(
                ((Aufgabe) modelElement).getRelationshipTableRecords(), localizationEngine));
        return velocityAdapter.mergeTemplates(ctx, "al_header.vm", "aufgabe.vm", "al_footer.vm");
    }
}
