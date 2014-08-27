/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.db.dao;

import static ch.admin.isb.hermes5.domain.Status.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.Image;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.util.AssertUtils;
import ch.admin.isb.hermes5.util.ReflectionTestHelper;


public class ImageDAOTest extends AbstractDAOTest {

    private ImageDAO dao;
    private ReflectionTestHelper helper = new ReflectionTestHelper();
    private Image i1 = new Image();
    private Image i2 = new Image();
    private Image i3 = new Image();

    @Before
    public void setUp() throws Exception {
        dao = new ImageDAO();
        dao.em = getEntityManager();
        beginTransaction();
        helper.fillInSetter(i1);
        helper.fillInSetter(i2);
        helper.fillInSetter(i3);
        i1.setId(null);
        i2.setId(null);
        i3.setId(null);
        i1.setModelIdentifier("m1");
        i1.setUrlDe("urlDe1");
        i1.setUrlFr("urlFr1");
        i1.setStatusFr(UNVOLLSTAENDIG);
        i1.setStatusIt(UNVOLLSTAENDIG);
        i1.setStatusEn(UNVOLLSTAENDIG);
        i2.setModelIdentifier("m2");
        i3.setModelIdentifier("m1");
        i3.setUrlDe("urlDe3");
        i3.setStatusFr(UNVOLLSTAENDIG);
        i3.setStatusIt(IN_ARBEIT);
        i3.setStatusEn(FREIGEGEBEN);
        persist(i1, i2, i3);
        commitTransaction();
    }
    @Test
    public void testGetImage() {
        Image image = dao.getImage("m1","urlDe1");
        assertEquals("urlFr1", image.getUrlFr());
    }
    
    @Test
    public void getStatusFr() {
        List<Status> textElementStati = dao.getImageStati("m1", "fr");
        assertEquals(textElementStati+"", 1, textElementStati.size());
        assertEquals(UNVOLLSTAENDIG, textElementStati.get(0));
    }
    @Test
    public void getStatusIt() {
        List<Status> textElementStati = dao.getImageStati("m1", "it");
        assertEquals(textElementStati+"", 2, textElementStati.size());
        AssertUtils.assertContains(textElementStati, UNVOLLSTAENDIG, IN_ARBEIT);
    }
    @Test
    public void getStatusEn() {
        List<Status> textElementStati = dao.getImageStati("m1", "en");
        assertEquals(2, textElementStati.size());
        AssertUtils.assertContains(textElementStati, UNVOLLSTAENDIG, FREIGEGEBEN);
    }

}
