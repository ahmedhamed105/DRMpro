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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahmedhamed
 */
@Entity
@Table(name = "login_history", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginHistory.findAll", query = "SELECT l FROM LoginHistory l")
    , @NamedQuery(name = "LoginHistory.findById", query = "SELECT l FROM LoginHistory l WHERE l.id = :id")
    , @NamedQuery(name = "LoginHistory.findByLoginfailed", query = "SELECT l FROM LoginHistory l WHERE l.loginfailed = :loginfailed")
    , @NamedQuery(name = "LoginHistory.findByLoginsucess", query = "SELECT l FROM LoginHistory l WHERE l.loginsucess = :loginsucess")
    , @NamedQuery(name = "LoginHistory.findByHIp", query = "SELECT l FROM LoginHistory l WHERE l.hIp = :hIp")
    , @NamedQuery(name = "LoginHistory.findByHHost", query = "SELECT l FROM LoginHistory l WHERE l.hHost = :hHost")
    , @NamedQuery(name = "LoginHistory.findByHPort", query = "SELECT l FROM LoginHistory l WHERE l.hPort = :hPort")
    , @NamedQuery(name = "LoginHistory.findByHUser", query = "SELECT l FROM LoginHistory l WHERE l.hUser = :hUser")
    , @NamedQuery(name = "LoginHistory.findByFailedSucess", query = "SELECT l FROM LoginHistory l WHERE l.failedSucess = :failedSucess")
    , @NamedQuery(name = "LoginHistory.findByLocknot", query = "SELECT l FROM LoginHistory l WHERE l.locknot = :locknot")
    , @NamedQuery(name = "LoginHistory.findByErrorCode", query = "SELECT l FROM LoginHistory l WHERE l.errorCode = :errorCode")})
public class LoginHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "Login_failed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginfailed;
    @Column(name = "Login_sucess")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginsucess;
    @Size(max = 45)
    @Column(name = "h_ip", length = 45)
    private String hIp;
    @Size(max = 450)
    @Column(name = "h_host", length = 450)
    private String hHost;
    @Column(name = "h_port")
    private Integer hPort;
    @Size(max = 45)
    @Column(name = "h_user", length = 45)
    private String hUser;
    @Column(name = "failed_sucess")
    private Integer failedSucess;
    @Size(max = 45)
    @Column(name = "Lock_not", length = 45)
    private String locknot;
    @Column(name = "error_code")
    private Integer errorCode;
    @JoinColumn(name = "Login_prev_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private LoginPrev loginprevID;

    public LoginHistory() {
    }

    public LoginHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLoginfailed() {
        return loginfailed;
    }

    public void setLoginfailed(Date loginfailed) {
        this.loginfailed = loginfailed;
    }

    public Date getLoginsucess() {
        return loginsucess;
    }

    public void setLoginsucess(Date loginsucess) {
        this.loginsucess = loginsucess;
    }

    public String getHIp() {
        return hIp;
    }

    public void setHIp(String hIp) {
        this.hIp = hIp;
    }

    public String getHHost() {
        return hHost;
    }

    public void setHHost(String hHost) {
        this.hHost = hHost;
    }

    public Integer getHPort() {
        return hPort;
    }

    public void setHPort(Integer hPort) {
        this.hPort = hPort;
    }

    public String getHUser() {
        return hUser;
    }

    public void setHUser(String hUser) {
        this.hUser = hUser;
    }

    public Integer getFailedSucess() {
        return failedSucess;
    }

    public void setFailedSucess(Integer failedSucess) {
        this.failedSucess = failedSucess;
    }

    public String getLocknot() {
        return locknot;
    }

    public void setLocknot(String locknot) {
        this.locknot = locknot;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public LoginPrev getLoginprevID() {
        return loginprevID;
    }

    public void setLoginprevID(LoginPrev loginprevID) {
        this.loginprevID = loginprevID;
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
        if (!(object instanceof LoginHistory)) {
            return false;
        }
        LoginHistory other = (LoginHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.LoginHistory[ id=" + id + " ]";
    }
    
}
