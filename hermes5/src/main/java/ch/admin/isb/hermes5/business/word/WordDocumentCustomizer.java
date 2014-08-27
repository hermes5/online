/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.word;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

@ApplicationScoped
public class WordDocumentCustomizer {

    private static final Logger logger = LoggerFactory.getLogger(WordDocumentCustomizer.class);
    private static final Logger cacheLogger = LoggerFactory.getLogger("CacheLog");

    @Resource(mappedName = "java:jboss/infinispan/adjustedDocuments")
    CacheContainer cacheContainer;

    private Cache<DocCustomizerCacheKey, byte[]> cache;

    @Inject
    Docx4jWordDocumentCustomizer docx4jWordDocumentCustomizer;

    @Inject
    HandmadeWordDocumentCustomizer handmadeWordDocumentCustomizer;

    @Inject
    @SystemProperty(value = "template_replace_key_projektname", fallback = "h5_projektname")
    ConfigurationProperty keyProjektname;
    @Inject
    @SystemProperty(value = "template_replace_key_logo", fallback = "h5_logo")
    ConfigurationProperty keyLogo;

    @Inject
    @SystemProperty(value = "template_replace_key_projektnummer", fallback = "h5_projektnummer")
    ConfigurationProperty keyProjektnummer;

    @Inject
    @SystemProperty(value = "template_replace_key_projektleiter", fallback = "h5_projektleiter")
    ConfigurationProperty keyProjektleiter;

    @Inject
    @SystemProperty(value = "template_replace_key_auftraggeber", fallback = "h5_auftraggeber")
    ConfigurationProperty keyAuftraggeber;

    @Inject
    @SystemProperty(value = "template_replace_key_firma1", fallback = "h5_firma1")
    ConfigurationProperty keyFirma1;

    @Inject
    @SystemProperty(value = "template_replace_key_firma2", fallback = "h5_firma2")
    ConfigurationProperty keyFirma2;

    @Inject
    @SystemProperty(value = "use_docx4j", fallback = "false")
    ConfigurationProperty useDocx4j;

    @PostConstruct
    public void init() {
        cache = cacheContainer.getCache();
    }

    public byte[] adjustDocumentWithUserData(String url, byte[] wordFile, SzenarioUserData szenarioUserData) {

        DocCustomizerCacheKey docCustomizerCacheKey = new DocCustomizerCacheKey(url, wordFile, szenarioUserData);

        if (cache.containsKey(docCustomizerCacheKey)) {
            try {
                byte[] bs = cache.get(docCustomizerCacheKey);
                cacheLogger.debug("got from adjustedDocuments cache " + docCustomizerCacheKey);
                return bs;
            } catch (Exception e) {
                logger.warn("Error on getting " + docCustomizerCacheKey + " from cache " + e.getMessage());
                removeFromCache(docCustomizerCacheKey);
            }
        }

        logger.debug("adjusting " + url);

        try {
            if (url != null && url.toLowerCase().trim().endsWith("dotx") || url.toLowerCase().trim().endsWith("docx")) {
                InputStream is = new ByteArrayInputStream(wordFile);
                byte[] adjustDocumentWithUserData = useDocx4j.getBooleanValue() ? docx4jWordDocumentCustomizer
                        .adjustDocumentWithUserData(is, szenarioUserData, buildReplaceMap(szenarioUserData),
                                keyLogo.getStringValue()) : handmadeWordDocumentCustomizer.adjustDocumentWithUserData(
                        is, szenarioUserData, buildReplaceMap(szenarioUserData), keyLogo.getStringValue());
                try {
                    if (!cache.containsKey(docCustomizerCacheKey)) {
                        cache.put(docCustomizerCacheKey, adjustDocumentWithUserData);
                        cacheLogger.debug("put to adjustedDocuments cache " + docCustomizerCacheKey);
                    }
                } catch (Exception e) {
                    logger.warn("Error on putting " + docCustomizerCacheKey + " to cache " + e.getMessage());
                    removeFromCache(docCustomizerCacheKey);
                }
                return adjustDocumentWithUserData;
            }

        } catch (Throwable e) {
            logger.warn("unable to adjust " + url, e);
        }

        return wordFile;
    }

    private HashMap<String, String> buildReplaceMap(SzenarioUserData szenarioUserData) {
        HashMap<String, String> mappings = new HashMap<String, String>();
        mappings.put(keyProjektleiter.getStringValue(), szenarioUserData.getProjektleiter());
        mappings.put(keyAuftraggeber.getStringValue(), szenarioUserData.getAuftraggeber());
        mappings.put(keyProjektname.getStringValue(), szenarioUserData.getProjektname());
        mappings.put(keyProjektnummer.getStringValue(), szenarioUserData.getProjektnummer());
        mappings.put(keyFirma1.getStringValue(), szenarioUserData.getFirma1());
        mappings.put(keyFirma2.getStringValue(), szenarioUserData.getFirma2());
        return mappings;
    }

    private void removeFromCache(DocCustomizerCacheKey key) {
        try {
            cache.remove(key);
            cacheLogger.debug("remove from adjustedDocuments cache " + key);
        } catch (Exception e2) {
            logger.warn("Error on removing" + key + " from cache " + e2.getMessage());
        }
    }

}
