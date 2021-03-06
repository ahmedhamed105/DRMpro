/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author ahmedhamed
 */
@Entity
@Table(name = "website_data", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WebsiteData.findAll", query = "SELECT w FROM WebsiteData w")
    , @NamedQuery(name = "WebsiteData.findById", query = "SELECT w FROM WebsiteData w WHERE w.id = :id")
    , @NamedQuery(name = "WebsiteData.findByWebSite", query = "SELECT w FROM WebsiteData w WHERE w.webSite = :webSite")
    , @NamedQuery(name = "WebsiteData.findByWebDesc", query = "SELECT w FROM WebsiteData w WHERE w.webDesc = :webDesc")
    , @NamedQuery(name = "WebsiteData.findByCreateDate", query = "SELECT w FROM WebsiteData w WHERE w.createDate = :createDate")
    , @NamedQuery(name = "WebsiteData.findByUpdateDate", query = "SELECT w FROM WebsiteData w WHERE w.updateDate = :updateDate")})
public class WebsiteData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "web_site", nullable = false, length = 300)
    private String webSite;
    @Size(max = 300)
    @Column(name = "Web_Desc", length = 300)
    private String webDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "update_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "websiteDataID")
    private Collection<WebsiteDataHasUser> websiteDataHasUserCollection;

    public WebsiteData() {
    }

    public WebsiteData(Integer id) {
        this.id = id;
    }

    public WebsiteData(Integer id, String webSite, Date createDate, Date updateDate) {
        this.id = id;
        this.webSite = webSite;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getWebDesc() {
        return webDesc;
    }

    public void setWebDesc(String webDesc) {
        this.webDesc = webDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<WebsiteDataHasUser> getWebsiteDataHasUserCollection() {
        return websiteDataHasUserCollection;
    }

    public void setWebsiteDataHasUserCollection(Collection<WebsiteDataHasUser> websiteDataHasUserCollection) {
        this.websiteDataHasUserCollection = websiteDataHasUserCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebsiteData)) {
            return false;
        }
        WebsiteData other = (WebsiteData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.WebsiteData[ id=" + id + " ]";
    }
    
}
