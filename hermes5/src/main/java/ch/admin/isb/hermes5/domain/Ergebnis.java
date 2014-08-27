/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import static ch.admin.isb.hermes5.util.StringUtil.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.epf.uma.schema.DescribableElement;
import ch.admin.isb.hermes5.epf.uma.schema.Guidance;
import ch.admin.isb.hermes5.epf.uma.schema.GuidanceDescription;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProduct;
import ch.admin.isb.hermes5.util.JAXBUtils;
import ch.admin.isb.hermes5.util.StringUtil;

public class Ergebnis extends AbstractMethodenElement {

    private static final Logger logger = LoggerFactory.getLogger(Ergebnis.class);

    private final WorkProduct workProduct;
    private List<Rolle> verantwortlicheRollen;
    private final List<Aufgabe> aufgaben;
    private final boolean required;

    public Ergebnis(WorkProduct workProduct, Map<String, MethodElement> index, boolean required) {
        super(index);
        this.required = required;
        verantwortlicheRollen = new ArrayList<Rolle>();
        aufgaben = new ArrayList<Aufgabe>();
        this.workProduct = workProduct;
    }

    protected Ergebnis(WorkProduct workProduct, Map<String, MethodElement> index, boolean required,
            List<Rolle> verantwortlicheRollen) {
        this(workProduct, index, required);
        this.verantwortlicheRollen = verantwortlicheRollen;
    }

    public List<Aufgabe> getAufgaben() {
        return aufgaben;
    }

    public List<Modul> getModule() {
        LinkedHashSet<Modul> module = new LinkedHashSet<Modul>();
        for (Aufgabe aufgabe : aufgaben) {
            module.addAll(aufgabe.getModule());
        }
        return new ArrayList<Modul>(module);
    }

    public List<Rolle> getVerantwortlicheRollen() {
        return verantwortlicheRollen;
    }

    @Override
    public String getName() {
        return "ergebnis_" + StringUtil.replaceSpecialChars(workProduct.getName());
    }

    public List<Localizable> getWebAttachmentUrls() {
        List<Localizable> result = new ArrayList<Localizable>();
        List<String> templateReferences = getTemplateReferences();

        for (String templateId : templateReferences) {
            Guidance template = (Guidance) super.resolveMethodLibraryReference(templateId);
            if (template != null) {
                GuidanceDescription guidanceDescription = (GuidanceDescription) template.getPresentation();
                if (guidanceDescription != null) {
                    String attachment = guidanceDescription.getAttachment();
                    String[] attachmentUrls = attachment.split("\\|");
                    for (String attachmentUrl : attachmentUrls) {
                        if (isNotBlank(attachmentUrl) && StringUtil.isWebAttachmentUrl(attachmentUrl)) {
                            result.add(new DefaultLocalizable(guidanceDescription, "attachment_"
                                    + attachmentUrl.hashCode()));
                        }
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("unable to resolve guidance description with guidance-id " + templateId);
                    }
                }
            } else {
                logger.warn("unable to resolve guidance with id " + templateId);
            }
        }
        return result;
    }

    public List<String> getTemplateAttachmentUrls() {
        List<String> templateReferences = getTemplateReferences();
        List<String> result = new ArrayList<String>();

        for (String templateId : templateReferences) {
            Guidance template = (Guidance) super.resolveMethodLibraryReference(templateId);
            if (template != null) {
                GuidanceDescription guidanceDescription = (GuidanceDescription) template.getPresentation();
                if (guidanceDescription != null) {
                    String attachment = guidanceDescription.getAttachment();
                    String[] attachmentUrls = attachment.split("\\|");
                    for (String attachmentUrl : attachmentUrls) {
                        if (isNotBlank(attachmentUrl) && !isWebAttachmentUrl(attachmentUrl)) {
                            result.add(attachmentUrl);
                        }
                    }
                } else {
                    if (logger.isDebugEnabled()) {
                        logger.debug("unable to resolve guidance description with guidance-id " + templateId);
                    }
                }
            } else {
                logger.warn("unable to resolve guidance with id " + templateId);
            }
        }
        return result;
    }

    private List<String> getTemplateReferences() {
        List<String> templateReferences = JAXBUtils.getValuesWithName(workProduct.getChecklistOrConceptOrExample(),
                "Example");
        return templateReferences;
    }

    public List<RelationshipTableRecord> getRelationshipTableRecords() {
        List<RelationshipTableRecord> result = new ArrayList<RelationshipTableRecord>();
        for (Aufgabe aufgabe : getAufgaben()) {
            for (Modul modul : aufgabe.getModule()) {
                addIfNotContained(result, new RelationshipTableRecord(modul, aufgabe,
                        aufgabe.getVerantwortlicheRolle(), this, getVerantwortlicheRollen()));
            }
        }
        return result;
    }

    @Override
    public DescribableElement getElement() {
        return workProduct;
    }

    public void addVerantwortlicheRolle(Rolle rolle) {
        if (!verantwortlicheRollen.contains(rolle)) {
            verantwortlicheRollen.add(rolle);
        }
        if (!rolle.getVerantwortlichFuerErgebnis().contains(this)) {
            rolle.addVerantwortlichFuerErgebnis(this);
        }

    }

    public boolean isRequired() {
        return required;
    }

    public Localizable getPurpose() {
        if (getElement() == null || getElement().getPresentation() == null) {
            return null;
        }
        return new DefaultLocalizable(getElement().getPresentation(), "purpose");
    }

}
