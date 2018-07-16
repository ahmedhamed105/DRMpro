/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

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

/**
 *
 * @author ahmed.elemam
 */
@Entity
@Table(name = "trx", catalog = "guardianpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trx.findAll", query = "SELECT t FROM Trx t"),
    @NamedQuery(name = "Trx.findById", query = "SELECT t FROM Trx t WHERE t.id = :id"),
    @NamedQuery(name = "Trx.findByTRXnumber", query = "SELECT t FROM Trx t WHERE t.tRXnumber = :tRXnumber"),
    @NamedQuery(name = "Trx.findByTid", query = "SELECT t FROM Trx t WHERE t.tid = :tid"),
    @NamedQuery(name = "Trx.findByCreateDate", query = "SELECT t FROM Trx t WHERE t.createDate = :createDate"),
    @NamedQuery(name = "Trx.findByUpdateDate", query = "SELECT t FROM Trx t WHERE t.updateDate = :updateDate")})
public class Trx implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "TRX_number", nullable = false, length = 150)
    private String tRXnumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TID", nullable = false, length = 50)
    private String tid;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trxId")
    private Collection<TrxFieldsValues> trxFieldsValuesCollection;
    @JoinColumn(name = "TRX_type_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private TrxType tRXtypeID;

    public Trx() {
    }

    public Trx(Integer id) {
        this.id = id;
    }

    public Trx(Integer id, String tRXnumber, String tid, Date createDate, Date updateDate) {
        this.id = id;
        this.tRXnumber = tRXnumber;
        this.tid = tid;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTRXnumber() {
        return tRXnumber;
    }

    public void setTRXnumber(String tRXnumber) {
        this.tRXnumber = tRXnumber;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
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

    public TrxType getTRXtypeID() {
        return tRXtypeID;
    }

    public void setTRXtypeID(TrxType tRXtypeID) {
        this.tRXtypeID = tRXtypeID;
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
        if (!(object instanceof Trx)) {
            return false;
        }
        Trx other = (Trx) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Trx[ id=" + id + " ]";
    }
    
}