/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.db.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.EPFModelBuilder;

public class EPFModelDAOTest extends AbstractDAOTest {

    private EPFModelDAO dao;
    private EPFModel epfModel;

    @Before
    public void setUp() throws Exception {
        dao = new EPFModelDAO();
        dao.em = getEntityManager();
        beginTransaction();
        epfModel = EPFModelBuilder.epfModel("i1");
        persist(epfModel);
        persist(EPFModelBuilder.epfModel("i2"));
        commitTransaction();
    }

    @Test
    public void test() {
        beginTransaction();
        EPFModel modelByIdentifier = dao.getModelByIdentifier("i1");
        assertEquals("title_i1", modelByIdentifier.getTitle());
        commitTransaction();
    }

}
