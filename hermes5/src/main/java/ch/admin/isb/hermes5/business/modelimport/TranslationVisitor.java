/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.modelutil.MethodElementUtil;
import ch.admin.isb.hermes5.business.modelutil.ModelVisitor;
import ch.admin.isb.hermes5.business.modelutil.SimpleFieldComparator;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.epf.uma.schema.Checklist;
import ch.admin.isb.hermes5.epf.uma.schema.ContentCategory;
import ch.admin.isb.hermes5.epf.uma.schema.ContentPackage;
import ch.admin.isb.hermes5.epf.uma.schema.DeliveryProcess;
import ch.admin.isb.hermes5.epf.uma.schema.DescriptorDescription;
import ch.admin.isb.hermes5.epf.uma.schema.Example;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;
import ch.admin.isb.hermes5.epf.uma.schema.Phase;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.SupportingMaterial;
import ch.admin.isb.hermes5.epf.uma.schema.Task;
import ch.admin.isb.hermes5.epf.uma.schema.TaskDescriptor;
import ch.admin.isb.hermes5.epf.uma.schema.WorkProduct;
import ch.admin.isb.hermes5.util.ReflectionUtil;
import ch.admin.isb.hermes5.util.Stack;
import ch.admin.isb.hermes5.util.StringUtil;

@RequestScoped
public class TranslationVisitor implements ModelVisitor, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(TranslationVisitor.class);

    private static final long serialVersionUID = 1L;

    private final Map<Class<? extends MethodElement>, String> classFileMapping = new HashMap<Class<? extends MethodElement>, String>();
    @SuppressWarnings("unchecked")
    private List<Class<? extends MethodElement>> ignoreList = Arrays.asList(ContentPackage.class, Example.class, DescriptorDescription.class, TaskDescriptor.class, DeliveryProcess.class);

    private String modelIdentifier;
    private List<TranslateableText> translateableTexts;
    private ImportResult importResult;
    private Stack<String> fileTypeStack;
    private Stack<String> fileNameStack;
    private Stack<String> rootElementStack;

    @Inject
    SimpleFieldComparator fieldComparator;

    @Inject
    MethodElementUtil methodElementUtil;

    @Inject
    TranslationMerge translationMerge;

    public void init(String modelIdentifier) {
        this.modelIdentifier = modelIdentifier;
        translateableTexts = new ArrayList<TranslateableText>();
        fileTypeStack = new Stack<String>();
        importResult = new ImportResult();
        fileNameStack = new Stack<String>();
        rootElementStack = new Stack<String>();
        classFileMapping.put(MethodLibrary.class, "Uebrige");
        classFileMapping.put(Role.class, "Rolle");
        classFileMapping.put(WorkProduct.class, "Ergebnis");
        classFileMapping.put(ContentCategory.class, "Kategorie");
        classFileMapping.put(Task.class, "Aufgabe");
        classFileMapping.put(Checklist.class, "Checkliste");
        classFileMapping.put(SupportingMaterial.class, "SupportingMaterial");
        classFileMapping.put(Phase.class, "Phase");
        classFileMapping.put(MethodConfiguration.class, "Szenario");
    }

    @Override
    public void visitStart(MethodElement element, MethodElement parent) {

        if (ignoreList.contains(element.getClass())) {
            return;
        }

        pushFileStackIfNecessary(element);

        if (!methodElementUtil.isCreatedByReference(element)) {

            List<Field> fields = ReflectionUtil.getFieldsWithType(element.getClass(), String.class, "id", "name",
                    "task", "role", "tool", "workProduct", /* "attachment", */"superActivity", "defaultContext");
            Collections.sort(fields, fieldComparator);
            for (Field field : fields) {
                String textIdentifier = field.getName();
                try {
                    field.setAccessible(true);
                    Object object = field.get(element);
                    if (object != null) {
                        String text = String.valueOf(object);
                        if ("attachment".equals(textIdentifier)) {
                            String[] split = text.split("\\|");
                            for (String attachment : split) {
                                if (StringUtil.isWebAttachmentUrl(attachment)) {
                                    addTranslateableText(element, "attachment_" + attachment.hashCode(), attachment);
                                }
                            }
                        } else if (StringUtil.isNotBlank(text)) {
                            addTranslateableText(element, textIdentifier, text);
                        }
                    }
                } catch (Exception e) {
                    importResult.addError(element.getId() + "/" + textIdentifier + ": " + e.getMessage());
                    logger.error("Exception on translation visitor", e);
                }
            }
        }

    }

    private void addTranslateableText(MethodElement element, String textIdentifier, String text) {
        TranslateableText translateableText = new TranslateableText(modelIdentifier,
                element.getClass().getSimpleName(), rootElementStack.top(), fileTypeStack.top(), fileNameStack.top(),
                element.getId(), textIdentifier, text);
        translateableTexts.add(translationMerge.mergeWithPublished(translateableText));
        importResult.addSuccessResult(translateableText.getFileType() + " " + translateableText.getFileName() + " "
                + translateableText.getTextIdentifier());
    }

    private void pushFileStackIfNecessary(MethodElement element) {
        Class<? extends MethodElement> matchClass = getMatchClass(element.getClass());
        if (matchClass != null) {
            fileTypeStack.push(getFileType(matchClass));
            fileNameStack.push(element.getName());
            rootElementStack.push(element.getId());
        }

    }

    @SuppressWarnings("unchecked")
    private Class<? extends MethodElement> getMatchClass(Class<? extends MethodElement> clazz) {
        if (classFileMapping.containsKey(clazz)) {
            return clazz;
        }
        Class<?> superclass = clazz.getSuperclass();
        if (MethodElement.class.isAssignableFrom(superclass)) {
            return getMatchClass((Class<? extends MethodElement>) superclass);
        }
        return null;
    }

    @Override
    public void visitEnd(MethodElement element, MethodElement parent) {
        popFileStackIfNecessary(element);
    }

    private void popFileStackIfNecessary(MethodElement element) {
        Class<? extends MethodElement> clazz = getMatchClass(element.getClass());
        if (clazz != null) {
            popAndCheck(getFileType(clazz), fileTypeStack);
            popAndCheck(element.getName(), fileNameStack);
            popAndCheck(element.getId(), rootElementStack);
        }
    }

    private void popAndCheck(String file, Stack<String> stack) {
        String fileType = stack.pop();
        if (!file.equals(fileType)) {
            throw new IllegalStateException("expected " + fileType + " but was " + file);
        }
    }

    private String getFileType(Class<? extends MethodElement> clazz) {
        return classFileMapping.get(clazz);
    }

    public List<TranslateableText> getTranslateableTexts() {
        return translateableTexts;
    }

    public ImportResult getImportResult() {
        return this.importResult;
    }
}
