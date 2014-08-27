/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.modelrepository;

import static ch.admin.isb.hermes5.domain.Status.*;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.business.modelutil.MethodLibraryUnmarshaller;
import ch.admin.isb.hermes5.business.modelutil.MethodLibraryVisitorDriver;
import ch.admin.isb.hermes5.business.translation.TranslationRepository;
import ch.admin.isb.hermes5.domain.EPFModel;
import ch.admin.isb.hermes5.domain.Modul;
import ch.admin.isb.hermes5.domain.PublishContainer;
import ch.admin.isb.hermes5.domain.Rolle;
import ch.admin.isb.hermes5.domain.Status;
import ch.admin.isb.hermes5.domain.Szenario;
import ch.admin.isb.hermes5.epf.uma.schema.CustomCategory;
import ch.admin.isb.hermes5.epf.uma.schema.DeliveryProcess;
import ch.admin.isb.hermes5.epf.uma.schema.Discipline;
import ch.admin.isb.hermes5.epf.uma.schema.MethodConfiguration;
import ch.admin.isb.hermes5.epf.uma.schema.MethodElement;
import ch.admin.isb.hermes5.epf.uma.schema.MethodLibrary;
import ch.admin.isb.hermes5.epf.uma.schema.MethodPlugin;
import ch.admin.isb.hermes5.epf.uma.schema.Phase;
import ch.admin.isb.hermes5.epf.uma.schema.Role;
import ch.admin.isb.hermes5.epf.uma.schema.RoleSet;
import ch.admin.isb.hermes5.persistence.db.dao.EPFModelDAO;
import ch.admin.isb.hermes5.persistence.s3.S3;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.DateUtil;
import ch.admin.isb.hermes5.util.SystemProperty;

@ApplicationScoped
public class ModelRepository implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ModelRepository.class);
    private static final Logger cacheLogger = LoggerFactory.getLogger("CacheLog");

    private static final long serialVersionUID = 1L;

    @Inject
    @SystemProperty(value = "epf_model.publish_plugin", fallback = "hermes.publish")
    ConfigurationProperty publishPlugin;
    @Inject
    @SystemProperty(value = "epf_model.hermes_handbuch", fallback = "hermes_handbuch")
    ConfigurationProperty hermesHandbuch;
    @Inject
    @SystemProperty(value = "epf_model.hermes_website", fallback = "hermes_verstehen")
    ConfigurationProperty hermesWebsite;

    @Inject
    EPFModelDAO epfModelDAO;
    @Inject
    DateUtil dateUtil;

    @Inject
    TranslationRepository translationRepository;

    @Inject
    MethodLibraryUnmarshaller methodLibraryUnmarshaller;

    @Inject
    ElementExtractorVisitor elementExtractorVisitor;
    @Inject
    ElementIndexVisitor elementIndexVisitor;

    @Inject
    SzenarioAssembler szenarioAssembler;

    @Inject
    ModulAssembler modulAssembler;

    @Inject
    RollenAssembler rollenAssembler;

    @Inject
    PublishContainerAssembler publishContainerAssembler;

    @Inject
    MethodLibraryVisitorDriver methodLibraryVisitorDriver;

    @Inject
    S3 s3;

    @Resource(mappedName = "java:jboss/infinispan/epfmodel")
    CacheContainer cacheContainer;
    private Cache<String, MethodLibrary> cache;

    @PostConstruct
    public void init() {
        cache = cacheContainer.getCache();
    }

    public EPFModel saveNewModel(EPFModel model) {
        return epfModelDAO.merge(model);
    }

    public List<EPFModel> getEPFModels() {
        return epfModelDAO.getEPFModels();
    }

    public void deleteModel(String modelIdentifier) {
        EPFModel model = getModelByIdentifier(modelIdentifier);
        s3.deleteModelContent(modelIdentifier);
        model.setDeleted(true);
        epfModelDAO.merge(model);
    }

    public EPFModel getModelByIdentifier(String identifier) {
        return epfModelDAO.getModelByIdentifier(identifier);
    }

    public EPFModel updateStatusAndUpdateDate(String modelIdentifier) {
        EPFModel model = getModelByIdentifier(modelIdentifier);
        model.setStatusFr(calculateStatus(translationRepository.getTranslationEntityStati(modelIdentifier, "fr")));
        model.setStatusIt(calculateStatus(translationRepository.getTranslationEntityStati(modelIdentifier, "it")));
        model.setStatusEn(calculateStatus(translationRepository.getTranslationEntityStati(modelIdentifier, "en")));
        return epfModelDAO.merge(model);
    }

    private Status calculateStatus(List<Status> translationEntityStati) {
        if (translationEntityStati.contains(IN_ARBEIT)) {
            return IN_ARBEIT;
        }
        if (translationEntityStati.contains(UNVOLLSTAENDIG)) {
            return UNVOLLSTAENDIG;
        }
        if (translationEntityStati.contains(FREIGEGEBEN)) {
            return FREIGEGEBEN;
        }
        return UNVOLLSTAENDIG;
    }

    public EPFModel publish(String modelIdentifier) {
        unpublishPublishedModel();
        EPFModel model = getModelByIdentifier(modelIdentifier);
        model.setLastPublication(dateUtil.currentDate());
        model.setPublished(true);
        return epfModelDAO.mergeWithoutUpdateDateChange(model);
    }

    private void unpublishPublishedModel() {
        EPFModel model = epfModelDAO.getPublishedModel();
        if (model != null) {
            model.setPublished(false);
            epfModelDAO.mergeWithoutUpdateDateChange(model);
        }
    }

    public PublishContainer getHermesWebsite(String modelIdentifier) {
        return getPublishContainer(modelIdentifier, hermesWebsite.getStringValue());
    }

    public PublishContainer getHermesHandbuch(String modelIdentifier) {
        return getPublishContainer(modelIdentifier, hermesHandbuch.getStringValue());
    }

    /**
     * @param modelIdentifier
     * @param customCategoryName
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private PublishContainer getPublishContainer(String modelIdentifier, String customCategoryName) {
        elementExtractorVisitor.init(MethodPlugin.class, Discipline.class, Role.class, RoleSet.class,
                DeliveryProcess.class, MethodConfiguration.class);
        methodLibraryVisitorDriver.visit(getMethodLibrary(modelIdentifier), elementExtractorVisitor,
                elementIndexVisitor);
        Map<String, MethodElement> index = elementIndexVisitor.getResult();

        List<Discipline> disciplines = (List) elementExtractorVisitor.getResult(Discipline.class);
        List<Role> roles = (List) elementExtractorVisitor.getResult(Role.class);
        List<RoleSet> rolesets = (List) elementExtractorVisitor.getResult(RoleSet.class);
        List<DeliveryProcess> deliveryProcesses = (List) elementExtractorVisitor.getResult(DeliveryProcess.class);
        List<MethodConfiguration> configurations = (List) elementExtractorVisitor.getResult(MethodConfiguration.class);
        List<Rolle> rollen = rollenAssembler.assembleRollen(index, roles, rolesets);
        List<Modul> modules = modulAssembler.assembleModules(index, disciplines, rollen);

        List<MethodElement> plugins = elementExtractorVisitor.getResult(MethodPlugin.class);
        MethodPlugin hermesPublish = (MethodPlugin) getElementWithName(plugins, publishPlugin.getStringValue());
        if (hermesPublish == null) {
            throw new IllegalStateException("Unable to find method plugin" + publishPlugin.getStringValue());
        }

        elementExtractorVisitor.init(CustomCategory.class);
        methodLibraryVisitorDriver.visit(hermesPublish, elementExtractorVisitor);
        List<MethodElement> customCategories = elementExtractorVisitor.getResult(CustomCategory.class);
        CustomCategory customCategory = (CustomCategory) getElementWithName(customCategories, customCategoryName);
        if (customCategory == null) {
            throw new IllegalStateException("Unable to find custom category " + customCategoryName);
        }
        List<Szenario> szenarien = buildSzenarien(deliveryProcesses, disciplines, rollen, configurations, index);
        return publishContainerAssembler.assemblePublishContainer(customCategory, index, modules, rollen, szenarien,
                getPhasen(deliveryProcesses));
    }

    private List<Phase> getPhasen(List<DeliveryProcess> deliveryProcesses) {
        List<Phase> result = new ArrayList<Phase>();
        for (Object object : deliveryProcesses.get(0).getBreakdownElementOrRoadmap()) {
            if (object instanceof Phase) {
                result.add((Phase) object);
            }
        }
        return result;
    }

    private MethodElement getElementWithName(List<MethodElement> result, String name) {
        for (MethodElement methodElement : result) {
            if (methodElement.getName().equals(name)) {
                return methodElement;
            }
        }
        return null;
    }

    public EPFModel getPublishedModel() {
        return epfModelDAO.getPublishedModel();
    }

    /**
     * Returns a list of modules with aufgaben and ergebnisse, no roles are considered
     */
    @SuppressWarnings("unchecked")
    public List<Modul> getModulesWithErgebnisse(String modelIdentifier) {
        MethodLibrary unmarshalMethodLibrary = getMethodLibrary(modelIdentifier);
        elementExtractorVisitor.init(Discipline.class);
        methodLibraryVisitorDriver.visit(unmarshalMethodLibrary, elementExtractorVisitor, elementIndexVisitor);
        @SuppressWarnings("rawtypes")
        List<Discipline> disciplines = (List) elementExtractorVisitor.getResult(Discipline.class);
        Map<String, MethodElement> index = elementIndexVisitor.getResult();
        return modulAssembler.assembleModules(index, disciplines, new ArrayList<Rolle>());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Szenario> getSzenarien(String modelIdentifier) {
        MethodLibrary unmarshalMethodLibrary = getMethodLibrary(modelIdentifier);
        elementExtractorVisitor.init(DeliveryProcess.class, Discipline.class, Role.class, RoleSet.class,
                MethodConfiguration.class);
        methodLibraryVisitorDriver.visit(unmarshalMethodLibrary, elementExtractorVisitor, elementIndexVisitor);
        List<DeliveryProcess> deliveryProcesses = (List) elementExtractorVisitor.getResult(DeliveryProcess.class);
        List<Discipline> disciplines = (List) elementExtractorVisitor.getResult(Discipline.class);
        List<Role> roles = (List) elementExtractorVisitor.getResult(Role.class);
        List<RoleSet> rolesets = (List) elementExtractorVisitor.getResult(RoleSet.class);
        List<MethodConfiguration> configurations = (List) elementExtractorVisitor.getResult(MethodConfiguration.class);
        Map<String, MethodElement> index = elementIndexVisitor.getResult();
        List<Rolle> rollen = rollenAssembler.assembleRollen(index, roles, rolesets);
        List<Szenario> result = buildSzenarien(deliveryProcesses, disciplines, rollen, configurations, index);
        return result;
    }

    private List<Szenario> buildSzenarien(List<DeliveryProcess> deliveryProcesses, List<Discipline> disciplines,
            List<Rolle> rollen, List<MethodConfiguration> configurations, Map<String, MethodElement> index) {
        if (deliveryProcesses.size() != 1) {
            throw new IllegalStateException("Exactly one delivery process (masterszenario) expected "
                    + deliveryProcesses);
        }
        List<Szenario> result = new ArrayList<Szenario>();
        for (MethodConfiguration configuration : configurations) {
            Szenario buildSzenario = szenarioAssembler.buildScenario(deliveryProcesses.get(0), configuration, index,
                    disciplines, rollen);
            result.add(buildSzenario);
        }
        return result;
    }

    private MethodLibrary getMethodLibrary(String modelIdentifier) {
        if (cache.containsKey(modelIdentifier)) {
            try {
                MethodLibrary methodLibrary = cache.get(modelIdentifier);
                cacheLogger.debug("got from epfmodel cache " + modelIdentifier);
                return methodLibrary;
            } catch (Exception e) {
                logger.warn("Error on getting " + modelIdentifier + " from cache " + e.getMessage());
                removeFromCache(modelIdentifier);
            }
        }
        byte[] readFile = s3.readFile(modelIdentifier, "de", getModelByIdentifier(modelIdentifier).getUrlDe());
        MethodLibrary unmarshalMethodLibrary = methodLibraryUnmarshaller
                .unmarshalMethodLibrary(new ByteArrayInputStream(readFile));
        try {
            cache.put(modelIdentifier, unmarshalMethodLibrary);
            cacheLogger.debug("put to epfmodel cache " + modelIdentifier);
        } catch (Exception e) {
            logger.warn("Error on put " + modelIdentifier + " to cache " + e.getMessage());
            removeFromCache(modelIdentifier);
        }
        return unmarshalMethodLibrary;
    }

    private void removeFromCache(String modelIdentifier) {
        try {
            cache.remove(modelIdentifier);
            cacheLogger.debug("remove from epfmodel cache " + modelIdentifier);
        } catch (Exception e2) {
            logger.warn("Error on removing" + modelIdentifier + " from cache " + e2.getMessage());
        }
    }

    public EPFModel updateUrlDe(String modelIdentifier, String urlDe) {
        EPFModel model = epfModelDAO.getModelByIdentifier(modelIdentifier);
        model.setUrlDe(urlDe);
        return epfModelDAO.merge(model);
    }

}
