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
@Table(name = "tgroup_has_accesory", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TgroupHasAccesory.findAll", query = "SELECT t FROM TgroupHasAccesory t")
    , @NamedQuery(name = "TgroupHasAccesory.findById", query = "SELECT t FROM TgroupHasAccesory t WHERE t.id = :id")
    , @NamedQuery(name = "TgroupHasAccesory.findByAaccessory", query = "SELECT t FROM TgroupHasAccesory t WHERE t.aaccessory = :aaccessory")
    , @NamedQuery(name = "TgroupHasAccesory.findByCreateDate", query = "SELECT t FROM TgroupHasAccesory t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TgroupHasAccesory.findByUpdateDate", query = "SELECT t FROM TgroupHasAccesory t WHERE t.updateDate = :updateDate")
    , @NamedQuery(name = "TgroupHasAccesory.findByXMLupdate", query = "SELECT t FROM TgroupHasAccesory t WHERE t.xMLupdate = :xMLupdate")})
public class TgroupHasAccesory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Size(max = 500)
    @Column(name = "A_accessory", length = 500)
    private String aaccessory;
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
    @Column(name = "XML_update", nullable = false)
    private int xMLupdate;
    @JoinColumn(name = "Terminal_Group_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private TerminalGroup terminalGroupID;
    @JoinColumn(name = "accessory_Group_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private AccessoryGroup accessoryGroupID;

    public TgroupHasAccesory() {
    }

    public TgroupHasAccesory(Integer id) {
        this.id = id;
    }

    public TgroupHasAccesory(Integer id, Date createDate, Date updateDate, int xMLupdate) {
        this.id = id;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.xMLupdate = xMLupdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAaccessory() {
        return aaccessory;
    }

    public void setAaccessory(String aaccessory) {
        this.aaccessory = aaccessory;
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

    public int getXMLupdate() {
        return xMLupdate;
    }

    public void setXMLupdate(int xMLupdate) {
        this.xMLupdate = xMLupdate;
    }

    public TerminalGroup getTerminalGroupID() {
        return terminalGroupID;
    }

    public void setTerminalGroupID(TerminalGroup terminalGroupID) {
        this.terminalGroupID = terminalGroupID;
    }

    public AccessoryGroup getAccessoryGroupID() {
        return accessoryGroupID;
    }

    public void setAccessoryGroupID(AccessoryGroup accessoryGroupID) {
        this.accessoryGroupID = accessoryGroupID;
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
        if (!(object instanceof TgroupHasAccesory)) {
            return false;
        }
        TgroupHasAccesory other = (TgroupHasAccesory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.TgroupHasAccesory[ id=" + id + " ]";
    }
    
}
