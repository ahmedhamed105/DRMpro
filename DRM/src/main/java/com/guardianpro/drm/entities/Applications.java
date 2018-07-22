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
 * @author ahmed.elemam
 */
@Entity
@Table(name = "applications", catalog = "guardianpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Applications.findAll", query = "SELECT a FROM Applications a"),
    @NamedQuery(name = "Applications.findById", query = "SELECT a FROM Applications a WHERE a.id = :id"),
    @NamedQuery(name = "Applications.findByApplicationsCode", query = "SELECT a FROM Applications a WHERE a.applicationsCode = :applicationsCode"),
    @NamedQuery(name = "Applications.findByApplicationDesc", query = "SELECT a FROM Applications a WHERE a.applicationDesc = :applicationDesc")})
public class Applications implements Serializable {

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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Applications_Code", nullable = false, length = 45)
    private String applicationsCode;
    @Size(max = 45)
    @Column(name = "Application_Desc", length = 45)
    private String applicationDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationsID")
    private Collection<ApplicationUser> applicationUserCollection;

    public Applications() {
    }

    public Applications(Integer id) {
        this.id = id;
    }

    public Applications(Integer id, String applicationsCode) {
        this.id = id;
        this.applicationsCode = applicationsCode;
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

    @XmlTransient
    @JsonIgnore
    public Collection<ApplicationUser> getApplicationUserCollection() {
        return applicationUserCollection;
    }

    public void setApplicationUserCollection(Collection<ApplicationUser> applicationUserCollection) {
        this.applicationUserCollection = applicationUserCollection;
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
        return "com.guardianpro.drm.entities.Applications[ id=" + id + " ]";
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
    
}
