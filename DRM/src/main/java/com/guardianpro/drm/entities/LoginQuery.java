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
 * @author ahmedhamed
 */
@Entity
@Table(name = "login_query", catalog = "GuardianPro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginQuery.findAll", query = "SELECT l FROM LoginQuery l")
    , @NamedQuery(name = "LoginQuery.findById", query = "SELECT l FROM LoginQuery l WHERE l.id = :id")
    , @NamedQuery(name = "LoginQuery.findByLogin", query = "SELECT l FROM LoginQuery l WHERE l.login = :login")
    , @NamedQuery(name = "LoginQuery.findByTokean", query = "SELECT l FROM LoginQuery l WHERE l.tokean = :tokean")
    , @NamedQuery(name = "LoginQuery.findByExpiretime", query = "SELECT l FROM LoginQuery l WHERE l.expiretime = :expiretime")
    , @NamedQuery(name = "LoginQuery.findByCreateDate", query = "SELECT l FROM LoginQuery l WHERE l.createDate = :createDate")
    , @NamedQuery(name = "LoginQuery.findByUpdateDate", query = "SELECT l FROM LoginQuery l WHERE l.updateDate = :updateDate")
    , @NamedQuery(name = "LoginQuery.findByUserlock", query = "SELECT l FROM LoginQuery l WHERE l.userlock = :userlock")
    , @NamedQuery(name = "LoginQuery.findByUseradmin", query = "SELECT l FROM LoginQuery l WHERE l.useradmin = :useradmin")
    , @NamedQuery(name = "LoginQuery.findByErrorcount", query = "SELECT l FROM LoginQuery l WHERE l.errorcount = :errorcount")})
public class LoginQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Login", nullable = false)
    private int login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Tokean", nullable = false, length = 45)
    private String tokean;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Expire_time", nullable = false, length = 45)
    private String expiretime;
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
    @Column(name = "User_lock", nullable = false)
    private int userlock;
    @Basic(optional = false)
    @NotNull
    @Column(name = "User_admin", nullable = false)
    private int useradmin;
    @Column(name = "Error_count")
    private Integer errorcount;
    @JoinColumn(name = "Application_user_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private ApplicationUser applicationuserID;

    public LoginQuery() {
    }

    public LoginQuery(Integer id) {
        this.id = id;
    }

    public LoginQuery(Integer id, int login, String tokean, String expiretime, Date createDate, Date updateDate, int userlock, int useradmin) {
        this.id = id;
        this.login = login;
        this.tokean = tokean;
        this.expiretime = expiretime;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.userlock = userlock;
        this.useradmin = useradmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public String getTokean() {
        return tokean;
    }

    public void setTokean(String tokean) {
        this.tokean = tokean;
    }

    public String getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime;
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

    public int getUserlock() {
        return userlock;
    }

    public void setUserlock(int userlock) {
        this.userlock = userlock;
    }

    public int getUseradmin() {
        return useradmin;
    }

    public void setUseradmin(int useradmin) {
        this.useradmin = useradmin;
    }

    public Integer getErrorcount() {
        return errorcount;
    }

    public void setErrorcount(Integer errorcount) {
        this.errorcount = errorcount;
    }

    public ApplicationUser getApplicationuserID() {
        return applicationuserID;
    }

    public void setApplicationuserID(ApplicationUser applicationuserID) {
        this.applicationuserID = applicationuserID;
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
        if (!(object instanceof LoginQuery)) {
            return false;
        }
        LoginQuery other = (LoginQuery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.LoginQuery[ id=" + id + " ]";
    }
    
}
