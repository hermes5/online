/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.postprocessor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.SystemProperty;

public class InternalLinkPostProcessor implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(InternalLinkPostProcessor.class);

    @Inject
    @SystemProperty(value="internalLink.linkClass", fallback="<a class=\"elementLink\"")
    ConfigurationProperty linkClass;
    @Inject
    @SystemProperty(value="internalLink.linkEndTag", fallback="</a>")
    ConfigurationProperty linkEndTag;
    
    @Inject
    @SystemProperty(value="internalLink.xmlSubstituteStart", fallback="<i>")
    ConfigurationProperty xmlSubstituteStart;
    @Inject
    @SystemProperty(value="internalLink.xmlSubstituteEnd", fallback="</i>")
    ConfigurationProperty xmlSubstituteEnd;

    // separated by whitespace
    @Inject
    @SystemProperty(value="internalLink.noXMLSubstituteClasses", fallback="ch.admin.isb.hermes5.domain.Rolle")
    ConfigurationProperty noXMLSubstituteClasses;
    
    public String adjustInternalLinks(AbstractMethodenElement modelElement, String fileContent,
            List<AbstractMethodenElement> elementsToWrite) {
        String result = "";
        int linkBegin = fileContent.indexOf(linkClass.getStringValue());
        if (linkBegin > 0) {
            result = result + fileContent.substring(0, linkBegin);
            String remains = fileContent.substring(linkBegin);
            int linkEnd = remains.indexOf(linkEndTag.getStringValue());
            result += adjustLink(modelElement, remains.substring(0, linkEnd + linkEndTag.getStringValue().length()), elementsToWrite);
            result += adjustInternalLinks(modelElement, remains.substring(linkEnd + linkEndTag.getStringValue().length()),
                    elementsToWrite);
            return result;
        }
        return fileContent;
    }

    public String adjustInternalLinksForXML(String fileContent, List<AbstractMethodenElement> elementsToWrite) {
        String result = "";
        int linkBegin = fileContent.indexOf(linkClass.getStringValue());
        if (linkBegin > 0) {
            result = result + fileContent.substring(0, linkBegin);
            String remains = fileContent.substring(linkBegin);
            int linkEnd = remains.indexOf(linkEndTag.getStringValue());
            result += adjustLinkForXML(remains.substring(0, linkEnd + linkEndTag.getStringValue().length()), elementsToWrite);
            result += adjustInternalLinksForXML(remains.substring(linkEnd + linkEndTag.getStringValue().length()), elementsToWrite);
            return result;
        }
        return fileContent;
    }

    private String adjustLinkForXML(String link, List<AbstractMethodenElement> elementsToWrite) {
        String text = extractText(link);
        String guid = extractGuid(link);
        if (guid != null) {
            AbstractMethodenElement ref = getRef(null, guid, elementsToWrite, text);
            if (ref != null && isSubstituted(ref)) {
                return xmlSubstituteStart.getStringValue()+ text + xmlSubstituteEnd.getStringValue();
            }
        }
        return text;
    }

    private boolean isSubstituted(AbstractMethodenElement ref) {
        List<String> split = Arrays.asList(noXMLSubstituteClasses.getStringValue().split(" "));
        return ! split.contains(ref.getClass().getCanonicalName());
    }

    private String adjustLink(AbstractMethodenElement modelElement, String link,
            List<AbstractMethodenElement> elementsToWrite) {
        String text = extractText(link);
        String guid = extractGuid(link);
        if (guid != null) {
            AbstractMethodenElement ref = getRef(modelElement, guid, elementsToWrite, text);
            if (ref != null) {
                logger.debug("linking to " + ref.getName());
                return " <a href=\"" + ref.getName() + ".html\" class=\"elementLink\">" + text + linkEndTag.getStringValue() + " ";
            }
        }
        return " " + text + " ";
    }

    private AbstractMethodenElement getRef(AbstractMethodenElement modelElement, String guid,
            List<AbstractMethodenElement> elementsToWrite, String text) {
        for (AbstractMethodenElement abstractMethodenElement : elementsToWrite) {
            if (abstractMethodenElement.getId().equals(guid)) {
                return abstractMethodenElement;
            }
        }
        if (modelElement != null) {
            logger.warn("No element with id " + guid + " found (" + text + ") in " + modelElement.getName()
                    + ". No internal link will be generated");
        }
        return null;
    }

    private String extractGuid(String link) {
        try {
            String guidAttribute = "guid=\"";
            int startGuid = link.indexOf(guidAttribute) + guidAttribute.length();
            int endGuid = link.substring(startGuid).indexOf("\"");
            return link.substring(startGuid, startGuid + endGuid);
        } catch (Exception e) {
            logger.warn("Unable to extract guid ", e);
            return null;
        }
    }

    private String extractText(String link) {
        int startText = link.indexOf(">") + 1;
        int endText = link.length() - linkEndTag.getStringValue().length();
        return link.substring(startText, endText);
    }
}
