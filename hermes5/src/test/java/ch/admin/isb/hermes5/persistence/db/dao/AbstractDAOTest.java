/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

/**
 * Base class for DAO tests which run against an in-memory HSQL database. DB will be initialized with empty tables
 * (hibernate create-drop) before every test. DB will be cleaned after every test. Unfortunately, you have to maintain
 * two persistence.xml files from now on (one in src/main/... and one in src/test/...).
 */
public abstract class AbstractDAOTest {

    private EntityManager em;
    private EntityTransaction tx;

    @Before
    public final void setupEntityManager() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("inMemoryPU");
        em = emf.createEntityManager();
        tx = em.getTransaction();
    }

    @After
    public final void dropDatabase() {
            try {
                Connection connection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb"); //connectionUrl);
                PreparedStatement statement = connection.prepareStatement("SHUTDOWN");
                statement.execute();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public EntityManager getEntityManager() {
        return em;
    }

    protected void beginTransaction() {
        tx.begin();
    }

    protected void commitTransaction() {
        tx.commit();
        
    }

    protected void persist(Object...objects) {
        for (Object o : objects) {
            getEntityManager().persist(o);
        }
    }
}
