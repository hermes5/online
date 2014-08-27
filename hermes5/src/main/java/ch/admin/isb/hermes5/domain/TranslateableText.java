/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
package ch.admin.isb.hermes5.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "getTranslateableTexts",
                query = "select t FROM TranslateableText t WHERE t.modelIdentifier= :modelIdentifier", hints = { @QueryHint(
                        name = "org.hibernate.cacheable", value = "true") }),
        @NamedQuery(name = "getTextelementStati_fr",
                query = "SELECT distinct t.statusFr FROM TranslateableText t WHERE t.modelIdentifier= :modelIdentifier"),
        @NamedQuery(name = "getTextelementStati_it",
                query = "SELECT distinct t.statusIt FROM TranslateableText t WHERE t.modelIdentifier= :modelIdentifier"),
        @NamedQuery(name = "getTextelementStati_en",
                query = "SELECT distinct t.statusEn FROM TranslateableText t WHERE t.modelIdentifier= :modelIdentifier"),
        @NamedQuery(
                name = "getSpecificTranslateableText",
                query = "SELECT t FROM TranslateableText t WHERE t.modelIdentifier= :modelIdentifier AND t.elementIdentifier = :elementIdentifier"
                        + " AND t.textIdentifier = :textIdentifier", hints = { @QueryHint(
                        name = "org.hibernate.cacheable", value = "true") }) })
@Entity
@Table(name = "TRANSLATEABLE_TEXT")
@Cacheable
public class TranslateableText implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_IDENTIFIER")
    private String modelIdentifier;

    @Column(name = "ELEMENT_IDENTIFIER")
    private String elementIdentifier;

    @Column(name = "TEXT_IDENTIFIER")
    private String textIdentifier;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "ROOT_ELEMENT_IDENTIFIER")
    private String rootElementIdentifier;

    @Column(name = "ELEMENT_TYPE")
    private String elementType;

    @Column(name = "TEXT_DE")
    private String textDe;

    @Column(name = "TEXT_FR")
    private String textFr;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_FR")
    private Status statusFr;

    @Column(name = "TEXT_IT")
    private String textIt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_IT")
    private Status statusIt;

    @Column(name = "TEXT_EN")
    private String textEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_EN")
    private Status statusEn;

    public TranslateableText() {
        // used for hibernate
    }

    public TranslateableText(String modelIdentifier, String elementType, String rootElementIdentifier, String fileType,
            String fileName, String elementIdentifier, String textIdentifier, String textDe) {
        this.modelIdentifier = modelIdentifier;
        this.elementType = elementType;
        this.rootElementIdentifier = rootElementIdentifier;
        this.fileType = fileType;
        this.fileName = fileName;
        this.elementIdentifier = elementIdentifier;
        this.textIdentifier = textIdentifier;
        this.textDe = textDe;
        this.statusEn = Status.UNVOLLSTAENDIG;
        this.statusFr = Status.UNVOLLSTAENDIG;
        this.statusIt = Status.UNVOLLSTAENDIG;
    }

    public String getElementType() {
        return elementType;
    }

    public String getRootElementIdentifier() {
        return rootElementIdentifier;
    }

    @Override
    public String toString() {
        return "TranslateableText [elementIdentifier=" + elementIdentifier + ", textIdentifier=" + textIdentifier
                + ", fileType=" + fileType + ", fileName=" + fileName + ", rootElementIdentifier="
                + rootElementIdentifier + ", elementType=" + elementType + "]";
    }

    public String getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelIdentifier() {
        return modelIdentifier;
    }

    public void setModelIdentifier(String modelIdentifier) {
        this.modelIdentifier = modelIdentifier;
    }

    public String getElementIdentifier() {
        return elementIdentifier;
    }

    public String getTextIdentifier() {
        return textIdentifier;
    }

    public String getTextDe() {
        return textDe;
    }

    public String getTextFr() {
        return textFr;
    }

    public void setTextFr(String textFr) {
        this.textFr = textFr;
    }

    public Status getStatusFr() {
        return statusFr;
    }

    public void setStatusFr(Status statusFr) {
        this.statusFr = statusFr;
    }

    public String getTextIt() {
        return textIt;
    }

    public void setTextIt(String textIt) {
        this.textIt = textIt;
    }

    public Status getStatusIt() {
        return statusIt;
    }

    public void setStatusIt(Status statusIt) {
        this.statusIt = statusIt;
    }

    public String getTextEn() {
        return textEn;
    }

    public void setTextEn(String textEn) {
        this.textEn = textEn;
    }

    public Status getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(Status statusEn) {
        this.statusEn = statusEn;
    }

}
