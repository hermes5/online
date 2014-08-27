/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.s3;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.ejb.AsyncResult;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.util.Hardcoded;

public class S3Test {

    private static final byte[] BYTES = "bytes".getBytes();
    private S3 s3;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        s3 = new S3();
        s3.s3RemoteAdapter = mock(S3RemoteAdapter.class);
        s3.cacheContainer = mock(CacheContainer.class);
        @SuppressWarnings("rawtypes")
        Cache cacheMock = mock(Cache.class);
        when(s3.cacheContainer.getCache()).thenReturn(cacheMock);
        Hardcoded.enableDefaults(s3);
        s3.init();

    }

    @Test
    public void testReadFile() {
        when(s3.s3RemoteAdapter.readFile("123/fr/url")).thenReturn(BYTES);
        assertArrayEquals(BYTES, s3.readFile("123", "fr", "url"));
    }

    @Test
    public void testReadSampleFile() {
        when(s3.s3RemoteAdapter.readFile("123/szenarien/url")).thenReturn(BYTES);
        assertArrayEquals(BYTES, s3.readSampelszenarioFile("123", "/url"));
    }

    @Test
    public void testReadPublishedFile() {
        when(s3.s3RemoteAdapter.readFile("123/onlinepublikation/fr/url")).thenReturn(BYTES);
        assertArrayEquals(BYTES, s3.readPublishedFile("123", "fr", "/url"));
    }

    @Test
    public void testReadUserszenarioFile() {
        when(s3.s3RemoteAdapter.readFile("userszenario/url")).thenReturn(BYTES);
        assertArrayEquals(BYTES, s3.readUserszenarioFile("/url"));
    }

    @Test
    public void testAddFile() {
        InputStream mock = mock(InputStream.class);
        when(s3.s3RemoteAdapter.addFile(mock, 22, "123/fr/url")).thenReturn(new AsyncResult<Void>(null));
        s3.addFile(mock, 22, "123", "fr", "url");
        verify(s3.s3RemoteAdapter).addFile(mock, 22, "123/fr/url");

    }

    @Test
    public void testAddPublishedFile() {
         s3.addPublishedFile("123", "fr/url", BYTES);
        verify(s3.s3RemoteAdapter).addFile(any(ByteArrayInputStream.class), eq((long) BYTES.length),
                eq("123/onlinepublikation/fr/url"));
    }

}
