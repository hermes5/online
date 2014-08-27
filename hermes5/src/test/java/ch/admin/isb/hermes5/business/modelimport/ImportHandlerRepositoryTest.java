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

import java.util.List;

import org.junit.Test;

import ch.admin.isb.hermes5.domain.ImportResult;
import ch.admin.isb.hermes5.util.MockInstance;


public class ImportHandlerRepositoryTest {

    
    public class MockImportHandler implements ModelImportHandler {
        private static final long serialVersionUID = 1L;
        
        private boolean responsible;
        
        public MockImportHandler(boolean responsible) {
            this.responsible = responsible;
        }
        public boolean isResponsible(String filename) {
            return responsible;
        }
        @Override
        public ImportResult handleImport(String id, byte[] content, String filename) {
            return new ImportResult();
        }

    }

    private ModelImportHandlerRepository importHandlerRepository;
    private ModelImportHandler handlerA = new MockImportHandler(true);
    private ModelImportHandler handlerB = new MockImportHandler(false);
    private ModelImportHandler handlerC = new MockImportHandler(true);

    
    @Test
    public void testPng() {
        importHandlerRepository = new ModelImportHandlerRepository();
        importHandlerRepository.importHandlers =  new MockInstance<ModelImportHandler>(handlerA, handlerB, handlerC);
        String filename = "import.png";
       List<ModelImportHandler>  result = importHandlerRepository.lookupImportHandler(filename);
       assertNotNull(result);
       assertEquals(2, result.size());
       assertTrue(result.contains(handlerA));
       assertTrue(result.contains(handlerC));
       assertFalse(result.contains(handlerB));
    }

}
