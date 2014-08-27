/*----------------------------------------------------------------------------------------------
 * Copyright 2014 Federal IT Steering Unit FITSU Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *---------------------------------------------------------------------------------------------*/
/**
 * 
 */
package ch.admin.isb.hermes5.domain;

import java.io.Serializable;
import java.util.Date;

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
@NamedQuery(
        name="getAllEPFModels",
        query="select m FROM EPFModel m WHERE m.deleted=false ORDER BY m.id DESC"
),
@NamedQuery(
        name="getEPFByIdentifier",
        query="select m FROM EPFModel m WHERE m.identifier = :modelIdentifier"
),
@NamedQuery(
        name="getPublishedEPF",
        query="select m FROM EPFModel m WHERE m.published = true"
)})

@Entity
@Table(name = "EPF_MODEL")
public class EPFModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "DELETED")
	private boolean deleted;

	@Column(name = "IDENTIFIER")
	private String identifier;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "VERSION")
	private String version;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_FR")
	private Status statusFr;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_IT")
	private Status statusIt;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS_EN")
	private Status statusEn;
	
	@Column(name = "LAST_PUBLICATION")
	private Date lastPublication;
	
	@Column(name = "LAST_CHANGE")
	private Date lastChange;
	
	@Column(name = "PUBLISHED")
    private boolean published;
	@Column(name = "URL_DE")
    private String urlDe;

	
    public void setUrlDe(String urlDe) {
        this.urlDe = urlDe;
    }

    public Long getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getTitle() {
		return title;
	}

	public String getVersion() {
		return version;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public Status getStatusFr() {
		return statusFr;
	}

	public void setStatusFr(Status statusFr) {
		this.statusFr = statusFr;
	}

	public Status getStatusIt() {
		return statusIt;
	}

	public void setStatusIt(Status statusIt) {
		this.statusIt = statusIt;
	}

	public Status getStatusEn() {
		return statusEn;
	}

	public void setStatusEn(Status statusEn) {
		this.statusEn = statusEn;
	}

	public Date getLastPublication() {
		return lastPublication;
	}

	public void setLastPublication(Date lastPublication) {
		this.lastPublication = lastPublication;
	}

	public Date getLastChange() {
		return lastChange;
	}

	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getUrlDe() {
        return urlDe;
    }

}
