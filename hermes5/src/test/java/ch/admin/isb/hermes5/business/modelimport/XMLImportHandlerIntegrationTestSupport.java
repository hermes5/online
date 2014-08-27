/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelimport;

import static org.mockito.Mockito.*;
import ch.admin.isb.hermes5.business.modelrepository.ModelRepository;
import ch.admin.isb.hermes5.business.modelutil.ComplexFieldComparator;
import ch.admin.isb.hermes5.business.modelutil.MethodElementUtil;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.business.modelutil.SimpleFieldComparator;
import ch.admin.isb.hermes5.persistence.db.dao.TranslateableTextDAO;
import ch.admin.isb.hermes5.util.Hardcoded;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;


public class XMLImportHandlerIntegrationTestSupport {

    public  XMLImportHandler setupXmlImportHandler() {
        XMLImportHandler xmlImportHandler = new XMLImportHandler();
        xmlImportHandler.methodLibraryUnmarshaller = new MethodLibraryUnmarshaller();
        xmlImportHandler.methodLibraryVisitorDriver = new MethodLibraryVisitorDriver();
        xmlImportHandler.visitor = new TranslationVisitor();
        xmlImportHandler.visitor.translationMerge = new TranslationMerge();
        xmlImportHandler.visitor.methodElementUtil = new MethodElementUtil();
        xmlImportHandler.modelRepository = mock(ModelRepository.class);
        ComplexFieldComparator complexFieldComparator = new ComplexFieldComparator();
        Hardcoded.enableDefaults(complexFieldComparator);
        complexFieldComparator.init();
        ReflectionTestHelper helper = new ReflectionTestHelper();
        helper.updateField(xmlImportHandler.methodLibraryVisitorDriver, "fieldComparator", complexFieldComparator);
        SimpleFieldComparator simpleFieldComparator = new SimpleFieldComparator();
        Hardcoded.enableDefaults(simpleFieldComparator);
        simpleFieldComparator.init();
        helper.updateField(xmlImportHandler.visitor, "fieldComparator", simpleFieldComparator);
        xmlImportHandler.translateableTextDAO = mock(TranslateableTextDAO.class);
        return xmlImportHandler;
    }
}
