/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahmed.elemam
 */
@Entity
@Table(name = "login_prev", catalog = "guardianpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginPrev.findAll", query = "SELECT l FROM LoginPrev l"),
    @NamedQuery(name = "LoginPrev.findByhost", query = "SELECT l FROM LoginPrev l WHERE l.hostinfoID = :id"),
    @NamedQuery(name = "LoginPrev.findById", query = "SELECT l FROM LoginPrev l WHERE l.id = :id"),
    @NamedQuery(name = "LoginPrev.findByAdminLock", query = "SELECT l FROM LoginPrev l WHERE l.adminLock = :adminLock"),
    @NamedQuery(name = "LoginPrev.findByLockcount", query = "SELECT l FROM LoginPrev l WHERE l.lockcount = :lockcount"),
    @NamedQuery(name = "LoginPrev.findBySerKey", query = "SELECT l FROM LoginPrev l WHERE l.serKey = :serKey"),
    @NamedQuery(name = "LoginPrev.findByCreateDate", query = "SELECT l FROM LoginPrev l WHERE l.createDate = :createDate"),
    @NamedQuery(name = "LoginPrev.findByUpdateDate", query = "SELECT l FROM LoginPrev l WHERE l.updateDate = :updateDate")})
public class LoginPrev implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Admin_Lock", nullable = false)
    private int adminLock;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Lock_count", nullable = false)
    private int lockcount;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "Ser_Key", nullable = false, length = 256)
    private String serKey;
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
    @JoinColumn(name = "Host_info_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private HostInfo hostinfoID;

    public LoginPrev() {
    }

    public LoginPrev(Integer id) {
        this.id = id;
    }

    public LoginPrev(Integer id, int adminLock, int lockcount, String serKey, Date createDate, Date updateDate) {
        this.id = id;
        this.adminLock = adminLock;
        this.lockcount = lockcount;
        this.serKey = serKey;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAdminLock() {
        return adminLock;
    }

    public void setAdminLock(int adminLock) {
        this.adminLock = adminLock;
    }

    public int getLockcount() {
        return lockcount;
    }

    public void setLockcount(int lockcount) {
        this.lockcount = lockcount;
    }

    public String getSerKey() {
        return serKey;
    }

    public void setSerKey(String serKey) {
        this.serKey = serKey;
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

    public HostInfo getHostinfoID() {
        return hostinfoID;
    }

    public void setHostinfoID(HostInfo hostinfoID) {
        this.hostinfoID = hostinfoID;
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
        if (!(object instanceof LoginPrev)) {
            return false;
        }
        LoginPrev other = (LoginPrev) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.LoginPrev[ id=" + id + " ]";
    }
    
}