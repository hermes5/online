/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.domain.TranslateableText;
import ch.admin.isb.hermes5.persistence.db.dao.TranslateableTextDAO;

@ApplicationScoped
public class XMLImportHandler implements ModelImportHandler {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(XMLImportHandler.class);

    @Inject
    MethodLibraryUnmarshaller methodLibraryUnmarshaller;
    @Inject
    MethodLibraryVisitorDriver methodLibraryVisitorDriver;
    @Inject
    TranslateableTextDAO translateableTextDAO;

    @Inject
    TranslationVisitor visitor;

    @Inject
    ModelRepository modelRepository;

    @Override
    public boolean isResponsible(String filename) {
        return filename.toLowerCase().endsWith(".xml");
    }

    @Override
    public ImportResult handleImport(String modelIdentifier, byte[] content, String filename) {
        modelRepository.updateUrlDe(modelIdentifier, filename);
        ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary ml = methodLibraryUnmarshaller
                .unmarshalMethodLibrary(new ByteArrayInputStream(content));
        visitor.init(modelIdentifier);
        methodLibraryVisitorDriver.visit(ml, visitor);
        List<TranslateableText> translateableTexts = visitor.getTranslateableTexts();
        for (TranslateableText translateableText : translateableTexts) {
            if (logger.isDebugEnabled()) {
                logger.debug(translateableText + "");
            }
            translateableTextDAO.merge(translateableText);
        }
        return visitor.getImportResult();
    }
}
