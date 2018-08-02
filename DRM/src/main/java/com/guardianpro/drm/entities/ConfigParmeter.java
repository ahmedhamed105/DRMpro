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
@Table(name = "config_parmeter", catalog = "GuardianPro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigParmeter.findAll", query = "SELECT c FROM ConfigParmeter c")
    , @NamedQuery(name = "ConfigParmeter.findById", query = "SELECT c FROM ConfigParmeter c WHERE c.id = :id")
    , @NamedQuery(name = "ConfigParmeter.findByParameter", query = "SELECT c FROM ConfigParmeter c WHERE c.parameter = :parameter")
    , @NamedQuery(name = "ConfigParmeter.findByPValue", query = "SELECT c FROM ConfigParmeter c WHERE c.pValue = :pValue")
    , @NamedQuery(name = "ConfigParmeter.findByCreateDate", query = "SELECT c FROM ConfigParmeter c WHERE c.createDate = :createDate")
    , @NamedQuery(name = "ConfigParmeter.findByUpdateDate", query = "SELECT c FROM ConfigParmeter c WHERE c.updateDate = :updateDate")
    , @NamedQuery(name = "ConfigParmeter.findByEncryption", query = "SELECT c FROM ConfigParmeter c WHERE c.encryption = :encryption")})
public class ConfigParmeter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Parameter", nullable = false, length = 500)
    private String parameter;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "PValue", nullable = false, length = 150)
    private String pValue;
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
    @Column(name = "Encryption", nullable = false)
    private int encryption;
    @JoinColumn(name = "User_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private User userID;

    public ConfigParmeter() {
    }

    public ConfigParmeter(Integer id) {
        this.id = id;
    }

    public ConfigParmeter(Integer id, String parameter, String pValue, Date createDate, Date updateDate, int encryption) {
        this.id = id;
        this.parameter = parameter;
        this.pValue = pValue;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.encryption = encryption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getPValue() {
        return pValue;
    }

    public void setPValue(String pValue) {
        this.pValue = pValue;
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

    public int getEncryption() {
        return encryption;
    }

    public void setEncryption(int encryption) {
        this.encryption = encryption;
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
        if (!(object instanceof ConfigParmeter)) {
            return false;
        }
        ConfigParmeter other = (ConfigParmeter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.ConfigParmeter[ id=" + id + " ]";
    }
    
}
