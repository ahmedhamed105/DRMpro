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
@Table(name = "host_info", catalog = "GuardianPro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HostInfo.findAll", query = "SELECT h FROM HostInfo h")
    , @NamedQuery(name = "HostInfo.findById", query = "SELECT h FROM HostInfo h WHERE h.id = :id")
    , @NamedQuery(name = "HostInfo.findByHIp", query = "SELECT h FROM HostInfo h WHERE h.hIp = :hIp")
    , @NamedQuery(name = "HostInfo.findByHHost", query = "SELECT h FROM HostInfo h WHERE h.hHost = :hHost")
    , @NamedQuery(name = "HostInfo.findByHPort", query = "SELECT h FROM HostInfo h WHERE h.hPort = :hPort")
    , @NamedQuery(name = "HostInfo.findByHUser", query = "SELECT h FROM HostInfo h WHERE h.hUser = :hUser")
    , @NamedQuery(name = "HostInfo.findByCreateDate", query = "SELECT h FROM HostInfo h WHERE h.createDate = :createDate")
    , @NamedQuery(name = "HostInfo.findByUpdateDate", query = "SELECT h FROM HostInfo h WHERE h.updateDate = :updateDate")
    , @NamedQuery(name = "HostInfo.findByRequestcount", query = "SELECT h FROM HostInfo h WHERE h.requestcount = :requestcount")})
public class HostInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "h_ip", nullable = false, length = 45)
    private String hIp;
    @Size(max = 450)
    @Column(name = "h_host", length = 450)
    private String hHost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "h_port", nullable = false)
    private int hPort;
    @Size(max = 45)
    @Column(name = "h_user", length = 45)
    private String hUser;
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
    @Column(name = "Request_count", nullable = false)
    private int requestcount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hostinfoID")
    private Collection<LoginPrev> loginPrevCollection;

    public HostInfo() {
    }

    public HostInfo(Integer id) {
        this.id = id;
    }

    public HostInfo(Integer id, String hIp, int hPort, Date createDate, Date updateDate, int requestcount) {
        this.id = id;
        this.hIp = hIp;
        this.hPort = hPort;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.requestcount = requestcount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getHPort() {
        return hPort;
    }

    public void setHPort(int hPort) {
        this.hPort = hPort;
    }

    public String getHUser() {
        return hUser;
    }

    public void setHUser(String hUser) {
        this.hUser = hUser;
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

    public int getRequestcount() {
        return requestcount;
    }

    public void setRequestcount(int requestcount) {
        this.requestcount = requestcount;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<LoginPrev> getLoginPrevCollection() {
        return loginPrevCollection;
    }

    public void setLoginPrevCollection(Collection<LoginPrev> loginPrevCollection) {
        this.loginPrevCollection = loginPrevCollection;
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
        if (!(object instanceof HostInfo)) {
            return false;
        }
        HostInfo other = (HostInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.HostInfo[ id=" + id + " ]";
    }
    
}
