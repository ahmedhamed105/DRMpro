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
@Table(name = "tgroup_has_terminal", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TgroupHasTerminal.findAll", query = "SELECT t FROM TgroupHasTerminal t")
    , @NamedQuery(name = "TgroupHasTerminal.findById", query = "SELECT t FROM TgroupHasTerminal t WHERE t.id = :id")
    , @NamedQuery(name = "TgroupHasTerminal.findByCreateDate", query = "SELECT t FROM TgroupHasTerminal t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TgroupHasTerminal.findByUpdateDate", query = "SELECT t FROM TgroupHasTerminal t WHERE t.updateDate = :updateDate")
    , @NamedQuery(name = "TgroupHasTerminal.findByXMLupdate", query = "SELECT t FROM TgroupHasTerminal t WHERE t.xMLupdate = :xMLupdate")
    , @NamedQuery(name = "TgroupHasTerminal.findByDLLname", query = "SELECT t FROM TgroupHasTerminal t WHERE t.dLLname = :dLLname")})
public class TgroupHasTerminal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
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
    @Size(max = 45)
    @Column(name = "DLL_name", length = 45)
    private String dLLname;
    @JoinColumn(name = "Terminal_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Terminal terminalID;
    @JoinColumn(name = "Terminal_Group_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private TerminalGroup terminalGroupID;

    public TgroupHasTerminal() {
    }

    public TgroupHasTerminal(Integer id) {
        this.id = id;
    }

    public TgroupHasTerminal(Integer id, Date createDate, Date updateDate, int xMLupdate) {
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

    public String getDLLname() {
        return dLLname;
    }

    public void setDLLname(String dLLname) {
        this.dLLname = dLLname;
    }

    public Terminal getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(Terminal terminalID) {
        this.terminalID = terminalID;
    }

    public TerminalGroup getTerminalGroupID() {
        return terminalGroupID;
    }

    public void setTerminalGroupID(TerminalGroup terminalGroupID) {
        this.terminalGroupID = terminalGroupID;
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
        if (!(object instanceof TgroupHasTerminal)) {
            return false;
        }
        TgroupHasTerminal other = (TgroupHasTerminal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.TgroupHasTerminal[ id=" + id + " ]";
    }
    
}
