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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "trx_fields", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrxFields.findAll", query = "SELECT t FROM TrxFields t")
    , @NamedQuery(name = "TrxFields.findById", query = "SELECT t FROM TrxFields t WHERE t.id = :id")
    , @NamedQuery(name = "TrxFields.findByFName", query = "SELECT t FROM TrxFields t WHERE t.fName = :fName")
    , @NamedQuery(name = "TrxFields.findByFDescription", query = "SELECT t FROM TrxFields t WHERE t.fDescription = :fDescription")
    , @NamedQuery(name = "TrxFields.findByCreateDate", query = "SELECT t FROM TrxFields t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TrxFields.findByUpdateDate", query = "SELECT t FROM TrxFields t WHERE t.updateDate = :updateDate")})
public class TrxFields implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "F_Name", nullable = false, length = 150)
    private String fName;
    @Size(max = 500)
    @Column(name = "F_Description", length = 500)
    private String fDescription;
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
    @JoinColumn(name = "Field_type_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private FieldType fieldtypeID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tRXfieldsID")
    private Collection<TrxFieldsValues> trxFieldsValuesCollection;

    public TrxFields() {
    }

    public TrxFields(Integer id) {
        this.id = id;
    }

    public TrxFields(Integer id, String fName, Date createDate, Date updateDate) {
        this.id = id;
        this.fName = fName;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
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

    public FieldType getFieldtypeID() {
        return fieldtypeID;
    }

    public void setFieldtypeID(FieldType fieldtypeID) {
        this.fieldtypeID = fieldtypeID;
    }

    @XmlTransient
    @JsonIgnore
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
        if (!(object instanceof TrxFields)) {
            return false;
        }
        TrxFields other = (TrxFields) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.TrxFields[ id=" + id + " ]";
    }
    
}
