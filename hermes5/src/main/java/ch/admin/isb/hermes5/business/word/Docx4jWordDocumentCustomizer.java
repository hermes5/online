/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.business.word;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.io.SaveToZipFile;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.JaxbXmlPartXPathAware;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.Parts;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;

import ch.admin.isb.hermes5.domain.SzenarioUserData;
import ch.admin.isb.hermes5.util.ConfigurationProperty;
import ch.admin.isb.hermes5.util.IOUtil;
import ch.admin.isb.hermes5.util.PerformanceLogged;
import ch.admin.isb.hermes5.util.SystemProperty;

@ApplicationScoped
public class Docx4jWordDocumentCustomizer {

    @Inject
    @SystemProperty(value = "template_logo_width", fallback = "3200")
    ConfigurationProperty logoWidth;
    @Inject
    @SystemProperty(value = "template_logo_bund_classpath", fallback = "Logo_BV_sw.png")
    ConfigurationProperty logoBundClasspath;


    @PerformanceLogged
    public byte[] adjustDocumentWithUserData(InputStream is, SzenarioUserData szenarioUserData, HashMap<String, String> replaceMap, String keyLogo) {
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);

            if (szenarioUserData.getLogo() != null) {
                replaceLogo(wordMLPackage, szenarioUserData.getLogo(), szenarioUserData.getLogoFilename(), keyLogo);
            } else {
                InputStream logoBund = getClass().getResourceAsStream(logoBundClasspath.getStringValue());
                replaceLogo(wordMLPackage, IOUtil.readToByteArray(logoBund), "logo.png", keyLogo);
            }
            
            replaceTexts(szenarioUserData, wordMLPackage.getMainDocumentPart(), replaceMap);

            Collection<Part> values = wordMLPackage.getParts().getParts().values();
            for (Part part : values) {
                if (part instanceof HeaderPart) {
                    replaceTexts(szenarioUserData, (HeaderPart) part, replaceMap);
                }
            }
            SaveToZipFile saver = new SaveToZipFile(wordMLPackage);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            saver.save(outputStream);
            byte[] byteArray = outputStream.toByteArray();
            return byteArray;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void replaceTexts(SzenarioUserData szenarioUserData, JaxbXmlPartXPathAware documentPart, HashMap<String, String> replaceMap)
            throws JAXBException {
        String xml = XmlUtils.marshaltoString(documentPart.getJaxbElement(), true);

        Object obj = XmlUtils.unmarshallFromTemplate(xml, replaceMap);
        documentPart.setJaxbElement(obj);
    }

    
    private void replaceLogo(WordprocessingMLPackage wordMLPackage, byte[] logo, String logoFilename, String keyLogo) throws Exception {
        HeaderPart headerPart = getHeaderPartWithContent(wordMLPackage.getParts(), keyLogoVariable(keyLogo));
        if (headerPart != null) {
            Tc tcWithContent = getTcWithContent(headerPart, keyLogoVariable(keyLogo));
            Object checkForContent = checkForContent(tcWithContent.getContent(), keyLogoVariable(keyLogo));
            int indexOf = tcWithContent.getContent().indexOf(checkForContent);
            tcWithContent.getContent().remove(checkForContent);
            tcWithContent.getContent().add(indexOf,
                    newImage(wordMLPackage, headerPart, logo, logoFilename, logoFilename, 1, 2));
            
        }
    }

    private String keyLogoVariable(String keyLogo) {
        // To avoid strange eclipse warning
        StringBuilder sb = new StringBuilder("$");
        sb.append("{").append(keyLogo).append("}");
        return sb.toString();
    }

    private HeaderPart getHeaderPartWithContent(Parts parts, String stringContent) {
        for (Entry<PartName, Part> entry : parts.getParts().entrySet()) {
            if (entry.getValue() instanceof HeaderPart) {
                HeaderPart headerPart = (HeaderPart) entry.getValue();
                if (getTcWithContent(headerPart, stringContent) != null) {
                    return headerPart;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private Tc getTcWithContent(HeaderPart headerPart, String stringContent) {
        List<Object> content = headerPart.getContent();
        for (Object object : content) {
            if (object instanceof JAXBElement) {
                if (((JAXBElement) object).getValue() instanceof Tbl) {
                    Tbl tbl = (Tbl) ((JAXBElement) object).getValue();
                    for (Object o : tbl.getContent()) {
                        if (o instanceof Tr) {
                            Tr tr = (Tr) o;
                            for (Object trContent : tr.getContent()) {
                                if (trContent instanceof JAXBElement) {
                                    if (((JAXBElement) trContent).getValue() instanceof Tc) {
                                        Tc tc = (Tc) ((JAXBElement) trContent).getValue();
                                        if (checkForContent(tc.getContent(), stringContent) != null) {
                                            return tc;
                                        }

                                    }

                                }
                            }

                        }
                    }
                }

            }
        }
        return null;
    }

    private Object checkForContent(List<Object> content, String contentString) {
        for (Object object : content) {
            if (object.toString().contains(contentString)) {
                return object;
            }
        }
        return null;
    }

    private org.docx4j.wml.P newImage(WordprocessingMLPackage wordMLPackage, Part sourcePart, byte[] bytes,
            String filenameHint, String altText, int id1, int id2) throws Exception {

        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, sourcePart, bytes);

        Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, logoWidth.getLongValue(), false);

        // Now add the inline in w:p/w:r/w:drawing
        org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
        org.docx4j.wml.P p = factory.createP();
        org.docx4j.wml.R run = factory.createR();
        p.getContent().add(run);
        org.docx4j.wml.Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);

        return p;

    }
    

}
