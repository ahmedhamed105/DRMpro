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
@Table(name = "trx_type_msg", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrxTypeMsg.findAll", query = "SELECT t FROM TrxTypeMsg t")
    , @NamedQuery(name = "TrxTypeMsg.findById", query = "SELECT t FROM TrxTypeMsg t WHERE t.id = :id")
    , @NamedQuery(name = "TrxTypeMsg.findByTtype", query = "SELECT t FROM TrxTypeMsg t WHERE t.ttype = :ttype")
    , @NamedQuery(name = "TrxTypeMsg.findByCreateDate", query = "SELECT t FROM TrxTypeMsg t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TrxTypeMsg.findByUpdateDate", query = "SELECT t FROM TrxTypeMsg t WHERE t.updateDate = :updateDate")})
public class TrxTypeMsg implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 450)
    @Column(name = "T_type", nullable = false, length = 450)
    private String ttype;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tRXtypemsgID")
    private Collection<Trx> trxCollection;

    public TrxTypeMsg() {
    }

    public TrxTypeMsg(Integer id) {
        this.id = id;
    }

    public TrxTypeMsg(Integer id, String ttype, Date createDate, Date updateDate) {
        this.id = id;
        this.ttype = ttype;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTtype() {
        return ttype;
    }

    public void setTtype(String ttype) {
        this.ttype = ttype;
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

    @XmlTransient
    @JsonIgnore
    public Collection<Trx> getTrxCollection() {
        return trxCollection;
    }

    public void setTrxCollection(Collection<Trx> trxCollection) {
        this.trxCollection = trxCollection;
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
        if (!(object instanceof TrxTypeMsg)) {
            return false;
        }
        TrxTypeMsg other = (TrxTypeMsg) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.TrxTypeMsg[ id=" + id + " ]";
    }
    
}
