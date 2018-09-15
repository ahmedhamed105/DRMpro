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
@Table(name = "field_type", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FieldType.findAll", query = "SELECT f FROM FieldType f")
    , @NamedQuery(name = "FieldType.findById", query = "SELECT f FROM FieldType f WHERE f.id = :id")
    , @NamedQuery(name = "FieldType.findByFtype", query = "SELECT f FROM FieldType f WHERE f.ftype = :ftype")
    , @NamedQuery(name = "FieldType.findByFpaterren", query = "SELECT f FROM FieldType f WHERE f.fpaterren = :fpaterren")
    , @NamedQuery(name = "FieldType.findByCreateDate", query = "SELECT f FROM FieldType f WHERE f.createDate = :createDate")
    , @NamedQuery(name = "FieldType.findByUpdateDate", query = "SELECT f FROM FieldType f WHERE f.updateDate = :updateDate")})
public class FieldType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "F_type", nullable = false, length = 50)
    private String ftype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "F_paterren", nullable = false, length = 500)
    private String fpaterren;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fieldtypeID")
    private Collection<TrxFields> trxFieldsCollection;

    public FieldType() {
    }

    public FieldType(Integer id) {
        this.id = id;
    }

    public FieldType(Integer id, String ftype, String fpaterren, Date createDate, Date updateDate) {
        this.id = id;
        this.ftype = ftype;
        this.fpaterren = fpaterren;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getFpaterren() {
        return fpaterren;
    }

    public void setFpaterren(String fpaterren) {
        this.fpaterren = fpaterren;
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
    public Collection<TrxFields> getTrxFieldsCollection() {
        return trxFieldsCollection;
    }

    public void setTrxFieldsCollection(Collection<TrxFields> trxFieldsCollection) {
        this.trxFieldsCollection = trxFieldsCollection;
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
        if (!(object instanceof FieldType)) {
            return false;
        }
        FieldType other = (FieldType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.FieldType[ id=" + id + " ]";
    }
    
}
