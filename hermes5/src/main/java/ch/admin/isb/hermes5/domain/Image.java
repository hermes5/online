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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "getImages", query = "select i FROM Image i WHERE i.modelIdentifier = :modelIdentifier"),
        @NamedQuery(name = "getImage", query = "select i FROM Image i WHERE i.modelIdentifier = :modelIdentifier and i.urlDe = :urlDe"),
        @NamedQuery(name = "getImageStati_fr",
                query = "SELECT distinct i.statusFr FROM Image i WHERE i.modelIdentifier= :modelIdentifier"),
        @NamedQuery(name = "getImageStati_it",
                query = "SELECT distinct i.statusIt FROM Image i WHERE i.modelIdentifier= :modelIdentifier"),
        @NamedQuery(name = "getImageStati_en",
                query = "SELECT distinct i.statusEn FROM Image i WHERE i.modelIdentifier= :modelIdentifier") })
@Entity
@Table(name = "IMAGE")
public class Image implements Serializable, TranslationDocument {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_IDENTIFIER")
    private String modelIdentifier;

    @Column(name = "URL_DE")
    private String urlDe;

    @Column(name = "URL_FR")
    private String urlFr;

    @Column(name = "URL_IT")
    private String urlIt;

    @Column(name = "URL_EN")
    private String urlEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_FR")
    private Status statusFr;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_IT")
    private Status statusIt;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_EN")
    private Status statusEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUrlDe() {
        return urlDe;
    }

    public void setUrlDe(String urlDe) {
        this.urlDe = urlDe;
    }

    public String getUrlFr() {
        return urlFr;
    }

    public void setUrlFr(String urlFr) {
        this.urlFr = urlFr;
    }

    public String getUrlIt() {
        return urlIt;
    }

    public void setUrlIt(String urlIt) {
        this.urlIt = urlIt;
    }

    public String getUrlEn() {
        return urlEn;
    }

    public void setUrlEn(String urlEn) {
        this.urlEn = urlEn;
    }

    @Override
    public Status getStatusFr() {
        return statusFr;
    }

    public void setStatusFr(Status statusFr) {
        this.statusFr = statusFr;
    }

    @Override
    public Status getStatusIt() {
        return statusIt;
    }

    public void setStatusIt(Status statusIt) {
        this.statusIt = statusIt;
    }

    @Override
    public Status getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(Status sttatusEn) {
        this.statusEn = sttatusEn;
    }

    @Override
    public String getName() {
        String[] elements = urlDe.split("\\/");
        return elements[elements.length - 1];
    }

    @Override
    public TranslationEntityType getType() {
        return TranslationEntityType.IMAGE;
    }

    public String getModelIdentifier() {
        return modelIdentifier;
    }

    public void setModelIdentifier(String modelIdentifier) {
        this.modelIdentifier = modelIdentifier;
    }

    @Override
    public String getTypeName() {
        return getType().nameDe();
    }

    public String getUrl(String language) {
        String url = getUrlDe();
        if (language.equals("fr")) {
            url = getUrlFr();
        }
        if (language.equals("it")) {
            url = getUrlIt();
        }
        if (language.equals("en")) {
            url = getUrlEn();
        }
        return url;
    }

}
