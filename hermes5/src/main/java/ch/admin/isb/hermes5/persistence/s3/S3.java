/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.persistence.s3;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.persistence.SearchIndexStore;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;


@ApplicationScoped
public class S3 implements Serializable, SearchIndexStore {

    private static final String ONLINEPUBLIKATION = "onlinepublikation";
    private static final String SAMPLE_SZENARIEN = "szenarien";
    private static final String SEARCH_INDEX = "searchindex";
    public static final String USERSZENARIO = "userszenario";
    private static final Logger logger = LoggerFactory.getLogger(S3.class);
    private static final Logger cacheLogger = LoggerFactory.getLogger("CacheLog");
    private static final long serialVersionUID = 1L;

    @Inject
    S3LocalAdapter s3LocalAdapter;
    @Inject
    S3RemoteAdapter s3RemoteAdapter;

    @Inject
    @SystemProperty(value = "s3.use_local", fallback = "false")
    ConfigurationProperty useLocalS3;

    @Resource(mappedName = "java:jboss/infinispan/s3files")
    CacheContainer cacheContainer;

    private Cache<String, byte[]> cache;

    @PostConstruct
    public void init() {
        cache = cacheContainer.getCache();
    }

    public byte[] readFile(String modelIdentifier, String lang, String url) {
        return readFile(buildPath(modelIdentifier, lang, url));
    }

    public byte[] readPublishedFile(String modelIdentifier, String lang, String url) {
        return readFile(modelIdentifier + "/" + ONLINEPUBLIKATION + "/" + lang + url);
    }

    public byte[] readUserszenarioFile(String url) {
        return readFile(USERSZENARIO + url);
    }

    public byte[] readSampelszenarioFile(String modelIdentifier, String url) {
        return readFile(modelIdentifier + "/" + SAMPLE_SZENARIEN + url);
    }

    private byte[] readFile(String key) {
        if (cache.containsKey(key)) {
            try {
                byte[] bs = cache.get(key);
                cacheLogger.debug("got from s3files cache " + key);
                return bs;
            } catch (Exception e) {
                logger.warn("Error on getting " + key + " from cache " + e.getMessage());
                removeFromCache(key);
            }
        }
        byte[] file = getAdapter().readFile(key);
        if (file != null && !cache.containsKey(key)) {
            try {
                cache.put(key, file);
                cacheLogger.debug("put to s3files cache " + key);
            } catch (Exception e) {
                logger.warn("Error on putting " + key + " to cache " + e.getMessage());
                removeFromCache(key);
            }
        }
        return file;
    }

    private void removeFromCache(String key) {
        try {
            cache.remove(key);
            cacheLogger.debug("removed from s3files cache " + key);
        } catch (Exception e2) {
            logger.warn("Error on removing" + key + " from cache " + e2.getMessage());
        }
    }

    public void addFile(InputStream file, long size, String modelIdentifier, String lang, String url) {
        String path = buildPath(modelIdentifier, lang, url);
        Future<Void> addFile = addFile(file, size, path);
        try {
            addFile.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Future<Void> addFile(InputStream file, long size, String path) {
        return getAdapter().addFile(file, size, path);
    }

    public Future<Void> addPublishedFile(String modelIdentifier, String url, byte[] byteArray) {
        return addFile(new ByteArrayInputStream(byteArray), byteArray.length, modelIdentifier + "/" + ONLINEPUBLIKATION
                + "/" + url);
    }

    public Future<Void> addSampleSzenarioFile(String modelIdentifier, String url, byte[] byteArray) {
        return addFile(new ByteArrayInputStream(byteArray), byteArray.length, modelIdentifier + "/" + SAMPLE_SZENARIEN
                + "/" + url);
    }

    public Future<Void> addUserszenarioFile(String id, String url, byte[] byteArray) {
        return addFile(new ByteArrayInputStream(byteArray), byteArray.length, USERSZENARIO + "/" + id + "/" + url);
    }

    private String buildPath(String modelIdentifier, String lang, String url) {
        return modelIdentifier + "/" + lang + "/" + url;
    }

    public void deleteModelContent(String modelIdentifier) {
        deleteAndRemoveFromCache(modelIdentifier);
    }

    @Override
    public void deleteIndexFiles(String modelIdentifier, String lang) {
        deleteAndRemoveFromCache(buildSearchIndexPath(modelIdentifier, lang));
    }

    private void deleteAndRemoveFromCache(String keyPrefix) {
        List<String> keys = getAdapter().deletePath(keyPrefix);
        for (String key : keys) {
            removeFromCache(key);
        }
    }

    private String buildSearchIndexPath(String modelIdentifier, String lang) {
        return modelIdentifier + "/" + SEARCH_INDEX + "/" + lang;
    }

    private String buildSearchIndexPath(String modelIdentifier, String lang, String filename) {
        return modelIdentifier + "/" + SEARCH_INDEX + "/" + lang + "/" + filename;
    }

    private S3Adapter getAdapter() {
        if (useLocalS3.getBooleanValue()) {
            return s3LocalAdapter;
        }
        return s3RemoteAdapter;
    }

    @Override
    public void addIndexFile(String modelIdentifier, String language, String filename, byte[] filecontent) {
        String path = buildSearchIndexPath(modelIdentifier, language, filename);
        addFile(new ByteArrayInputStream(filecontent), filecontent.length, path);
    }

    @Override
    public byte[] readIndexFile(String modelIdentifier, String language, String file) {
        return getAdapter().readFile(buildSearchIndexPath(modelIdentifier, language, file));
    }

    @Override
    public List<String> listIndexFiles(String modelIdentifier, String language) {
        return getAdapter().listFiles(buildSearchIndexPath(modelIdentifier, language));
    }

}
