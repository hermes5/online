/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.rendering.postprocessor;

import static ch.admin.isb.hermes5.domain.SzenarioBuilder.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ch.admin.isb.hermes5.domain.AbstractMethodenElement;
import ch.admin.isb.hermes5.util.Hardcoded;

public class InternalLinkPostProcessorTest {

    private InternalLinkPostProcessor internalLinkPostProcessor;

    String fileContent = "Der <a class=\"elementLink\" href=\"./../../hermes.core/roles/projektleiter_60B26E82.html\""
            + " guid=\"_69LMMLlIEeGS_sZhKwrDPg\">Projektleiter</a>&nbsp;erteilt <a class=\"elementLink\""
            + " href=\"./../../hermes.core/workproducts/arbeitsauftrag_43E8676.html\" guid=\"_Jmf8ENJuEeGLu81XaCUa0A\">Arbeitsauftrag</a>";

    @Before
    public void setUp() throws Exception {
        internalLinkPostProcessor = new InternalLinkPostProcessor();
        Hardcoded.enableDefaults(internalLinkPostProcessor);
    }

    @Test
    public void testAdjustInternalLinksOnceSecondIsIgnored() {
        List<AbstractMethodenElement> elementsToWrite = Arrays.asList((AbstractMethodenElement) rolle("projektleiter",
                "_69LMMLlIEeGS_sZhKwrDPg"));
        String adjustInternalLinks = internalLinkPostProcessor.adjustInternalLinks(ergebnis("ergebnis"), fileContent,
                elementsToWrite);
        assertEquals(
                "Der  <a href=\"rolle_projektleiter.html\" class=\"elementLink\">Projektleiter</a> &nbsp;erteilt  Arbeitsauftrag ",
                adjustInternalLinks);
    }

    @Test
    public void testAdjustInternalLinksTwice() {
        List<AbstractMethodenElement> elementsToWrite = Arrays.asList(
                (AbstractMethodenElement) rolle("projektleiter", "_69LMMLlIEeGS_sZhKwrDPg"),
                rolle("arbeitsauftrag", "_Jmf8ENJuEeGLu81XaCUa0A"));
        String adjustInternalLinks = internalLinkPostProcessor.adjustInternalLinks(ergebnis("ergebnis"), fileContent,
                elementsToWrite);
        assertEquals(
                "Der  <a href=\"rolle_projektleiter.html\" class=\"elementLink\">Projektleiter</a> &nbsp;erteilt  <a href=\"rolle_arbeitsauftrag.html\" class=\"elementLink\">Arbeitsauftrag</a> ",
                adjustInternalLinks);
    }

    @Test
    public void testAdjustInternalLinksForXml() {
        List<AbstractMethodenElement> elementsToWrite = Arrays.asList((AbstractMethodenElement) ergebnisWithId(
                "projektleiter", "_69LMMLlIEeGS_sZhKwrDPg"));
        String adjustInternalLinks = internalLinkPostProcessor.adjustInternalLinksForXML(fileContent, elementsToWrite);
        assertEquals("Der <i>Projektleiter</i>&nbsp;erteilt Arbeitsauftrag", adjustInternalLinks);
    }

    @Test
    public void testAdjustInternalLinksTwiceForXml() {
        List<AbstractMethodenElement> elementsToWrite = Arrays.asList(
                (AbstractMethodenElement) ergebnisWithId("projektleiter", "_69LMMLlIEeGS_sZhKwrDPg"),
                ergebnisWithId("arbeitsauftrag", "_Jmf8ENJuEeGLu81XaCUa0A"));
        String adjustInternalLinks = internalLinkPostProcessor.adjustInternalLinksForXML(fileContent, elementsToWrite);
        assertEquals("Der <i>Projektleiter</i>&nbsp;erteilt <i>Arbeitsauftrag</i>", adjustInternalLinks);
    }

    @Test
    public void testAdjustInternalLinksNoSubstitueRolle() {
        List<AbstractMethodenElement> elementsToWrite = Arrays.asList(
                rolle("projektleiter", "_69LMMLlIEeGS_sZhKwrDPg"),
                ergebnisWithId("arbeitsauftrag", "_Jmf8ENJuEeGLu81XaCUa0A"));
        String adjustInternalLinks = internalLinkPostProcessor.adjustInternalLinksForXML(fileContent, elementsToWrite);
        assertEquals("Der Projektleiter&nbsp;erteilt <i>Arbeitsauftrag</i>", adjustInternalLinks);
    }

    @Test
    public void testAdjustInternalLinksNoSubstitueRolleAndErgebnis() {
        internalLinkPostProcessor.noXMLSubstituteClasses = Hardcoded
                .configuration("ch.admin.isb.hermes5.domain.Rolle ch.admin.isb.hermes5.domain.Ergebnis");

        List<AbstractMethodenElement> elementsToWrite = Arrays.asList(
                rolle("projektleiter", "_69LMMLlIEeGS_sZhKwrDPg"),
                ergebnisWithId("arbeitsauftrag", "_Jmf8ENJuEeGLu81XaCUa0A"));
        String adjustInternalLinks = internalLinkPostProcessor.adjustInternalLinksForXML(fileContent, elementsToWrite);
        assertEquals("Der Projektleiter&nbsp;erteilt Arbeitsauftrag", adjustInternalLinks);
    }

}
