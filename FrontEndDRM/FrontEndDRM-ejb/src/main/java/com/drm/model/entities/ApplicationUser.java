/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.model.entities;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mohammed.ayad
 */
@Entity
@Table(name = "application_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = ApplicationUser.NAMED_QUERY_FIND_ALL_APPLICATION_USER, query = "SELECT a FROM ApplicationUser a")
    , @NamedQuery(name = "ApplicationUser.findById", query = "SELECT a FROM ApplicationUser a WHERE a.id = :id")
    , @NamedQuery(name = "ApplicationUser.findByCreateDate", query = "SELECT a FROM ApplicationUser a WHERE a.createDate = :createDate")
    , @NamedQuery(name = "ApplicationUser.findByUpdateDate", query = "SELECT a FROM ApplicationUser a WHERE a.updateDate = :updateDate")
    , @NamedQuery(name = "ApplicationUser.findByEnable", query = "SELECT a FROM ApplicationUser a WHERE a.enable = :enable")
    , @NamedQuery(name = ApplicationUser.NAMED_QUERY_IS_APPLICATION_USER_EXIST_BEFORE, query = "SELECT a FROM ApplicationUser a WHERE a.userID.username=?1 and a.applicationsID.applicationsCode=?2")})
public class ApplicationUser extends AbstractEntity {

    public static final String NAMED_QUERY_FIND_ALL_APPLICATION_USER = "ApplicationUser.findAll";
    public static final String NAMED_QUERY_IS_APPLICATION_USER_EXIST_BEFORE = "ApplicationUser.isExistBefore";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "Enable")
    private int enable;
    @JoinColumn(name = "Applications_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Applications applicationsID;
    @JoinColumn(name = "User_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User userID;

    public ApplicationUser() {
    }

    public ApplicationUser(Integer id) {
        this.id = id;
    }

    public ApplicationUser(Integer id, Date createDate, Date updateDate, int enable) {
        this.id = id;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.enable = enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public Applications getApplicationsID() {
        return applicationsID;
    }

    public void setApplicationsID(Applications applicationsID) {
        this.applicationsID = applicationsID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
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
        if (!(object instanceof ApplicationUser)) {
            return false;
        }
        ApplicationUser other = (ApplicationUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.drm.model.entities.ApplicationUser[ id=" + id + " ]";
    }

}
