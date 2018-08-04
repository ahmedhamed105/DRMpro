/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.model.entities;

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

/**
 *
 * @author mohammed.ayad
 */
@Entity
@Table(name = "trx_values")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrxValues.findAll", query = "SELECT t FROM TrxValues t")
    , @NamedQuery(name = "TrxValues.findById", query = "SELECT t FROM TrxValues t WHERE t.id = :id")
    , @NamedQuery(name = "TrxValues.findByFValue", query = "SELECT t FROM TrxValues t WHERE t.fValue = :fValue")
    , @NamedQuery(name = "TrxValues.findByFDescription", query = "SELECT t FROM TrxValues t WHERE t.fDescription = :fDescription")
    , @NamedQuery(name = "TrxValues.findByCreateDate", query = "SELECT t FROM TrxValues t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TrxValues.findByUpdateDate", query = "SELECT t FROM TrxValues t WHERE t.updateDate = :updateDate")})
public class TrxValues extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "F_Value")
    private String fValue;
    @Size(max = 500)
    @Column(name = "F_Description")
    private String fDescription;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tRXValuesID")
    private Collection<TrxFieldsValues> trxFieldsValuesCollection;

    public TrxValues() {
    }

    public TrxValues(Integer id) {
        this.id = id;
    }

    public TrxValues(Integer id, String fValue, Date createDate, Date updateDate) {
        this.id = id;
        this.fValue = fValue;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFValue() {
        return fValue;
    }

    public void setFValue(String fValue) {
        this.fValue = fValue;
    }

    public String getFDescription() {
        return fDescription;
    }

    public void setFDescription(String fDescription) {
        this.fDescription = fDescription;
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
    public Collection<TrxFieldsValues> getTrxFieldsValuesCollection() {
        return trxFieldsValuesCollection;
    }

    public void setTrxFieldsValuesCollection(Collection<TrxFieldsValues> trxFieldsValuesCollection) {
        this.trxFieldsValuesCollection = trxFieldsValuesCollection;
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
        if (!(object instanceof TrxValues)) {
            return false;
        }
        TrxValues other = (TrxValues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.drm.model.entities.TrxValues[ id=" + id + " ]";
    }
    
}
