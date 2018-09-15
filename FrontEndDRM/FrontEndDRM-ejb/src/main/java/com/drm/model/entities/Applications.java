/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.model.entities;

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

/**
 *
 * @author mohammed.ayad
 */
@Entity
@Table(catalog = "drmpro", schema = "",name = "applications")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Applications.findAll", query = "SELECT a FROM Applications a")
    , @NamedQuery(name = "Applications.findById", query = "SELECT a FROM Applications a WHERE a.id = :id")
    , @NamedQuery(name = Applications.NAMED_QUERY_FIND_APPLICATION_BY_APPLICATION_CODE, query = "SELECT a FROM Applications a WHERE a.applicationsCode =?1")
    , @NamedQuery(name = "Applications.findByApplicationDesc", query = "SELECT a FROM Applications a WHERE a.applicationDesc = :applicationDesc")
    , @NamedQuery(name = "Applications.findByCreateDate", query = "SELECT a FROM Applications a WHERE a.createDate = :createDate")
    , @NamedQuery(name = "Applications.findByUpdateDate", query = "SELECT a FROM Applications a WHERE a.updateDate = :updateDate")
    ,@NamedQuery(name = Applications.NAMED_QUERY_FIND_ALL_APPLICATION_CODES, query = "SELECT a.applicationsCode FROM Applications a")})
public class Applications extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationsID")
    private Collection<ApplicationUser> applicationUserCollection;
    public static final String NAMED_QUERY_FIND_ALL_APPLICATIONS = "Applications.findAll";
    public static final String NAMED_QUERY_FIND_ALL_APPLICATION_CODES = "Applications.findAllApplicationCodes";
    public static final String NAMED_QUERY_FIND_APPLICATION_BY_APPLICATION_CODE = "Applications.findByApplicationsCode";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Applications_Code")
    private String applicationsCode;
    @Size(max = 45)
    @Column(name = "Application_Desc")
    private String applicationDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public Applications() {
    }

    public Applications(Integer id) {
        this.id = id;
    }

    public Applications(Integer id, String applicationsCode, Date createDate, Date updateDate) {
        this.id = id;
        this.applicationsCode = applicationsCode;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationsCode() {
        return applicationsCode;
    }

    public void setApplicationsCode(String applicationsCode) {
        this.applicationsCode = applicationsCode;
    }

    public String getApplicationDesc() {
        return applicationDesc;
    }

    public void setApplicationDesc(String applicationDesc) {
        this.applicationDesc = applicationDesc;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Applications)) {
            return false;
        }
        Applications other = (Applications) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.drm.model.entities.Applications[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ApplicationUser> getApplicationUserCollection() {
        return applicationUserCollection;
    }

    public void setApplicationUserCollection(Collection<ApplicationUser> applicationUserCollection) {
        this.applicationUserCollection = applicationUserCollection;
    }

}
