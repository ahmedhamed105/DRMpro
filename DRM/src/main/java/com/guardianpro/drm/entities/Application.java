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
@Table(name = "application", catalog = "guardianpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findById", query = "SELECT a FROM Application a WHERE a.id = :id"),
    @NamedQuery(name = "Application.findByAppName", query = "SELECT a FROM Application a WHERE a.appName = :appName"),
    @NamedQuery(name = "Application.findByAppDir", query = "SELECT a FROM Application a WHERE a.appDir = :appDir"),
    @NamedQuery(name = "Application.findByCreateDate", query = "SELECT a FROM Application a WHERE a.createDate = :createDate"),
    @NamedQuery(name = "Application.findByUpdateDate", query = "SELECT a FROM Application a WHERE a.updateDate = :updateDate"),
    @NamedQuery(name = "Application.findByFilename", query = "SELECT a FROM Application a WHERE a.filename = :filename"),
    @NamedQuery(name = "Application.findByAppSize", query = "SELECT a FROM Application a WHERE a.appSize = :appSize"),
    @NamedQuery(name = "Application.findByAPPlength", query = "SELECT a FROM Application a WHERE a.aPPlength = :aPPlength")})
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "APP_NAME", nullable = false, length = 45)
    private String appName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "APP_DIR", nullable = false, length = 150)
    private String appDir;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "File_name", nullable = false, length = 100)
    private String filename;
    @Column(name = "APP_SIZE")
    private Integer appSize;
    @Column(name = "APP_length")
    private Integer aPPlength;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "applicationID")
    private Collection<ApplicationHasGroup> applicationHasGroupCollection;

    public Application() {
    }

    public Application(Integer id) {
        this.id = id;
    }

    public Application(Integer id, String appName, String appDir, Date createDate, Date updateDate, String filename) {
        this.id = id;
        this.appName = appName;
        this.appDir = appDir;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDir() {
        return appDir;
    }

    public void setAppDir(String appDir) {
        this.appDir = appDir;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getAppSize() {
        return appSize;
    }

    public void setAppSize(Integer appSize) {
        this.appSize = appSize;
    }

    public Integer getAPPlength() {
        return aPPlength;
    }

    public void setAPPlength(Integer aPPlength) {
        this.aPPlength = aPPlength;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ApplicationHasGroup> getApplicationHasGroupCollection() {
        return applicationHasGroupCollection;
    }

    public void setApplicationHasGroupCollection(Collection<ApplicationHasGroup> applicationHasGroupCollection) {
        this.applicationHasGroupCollection = applicationHasGroupCollection;
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
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.Application[ id=" + id + " ]";
    }
    
}
