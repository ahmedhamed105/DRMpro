/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.entities;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahmedhamed
 */
@Entity
@Table(name = "trx_fields_values", catalog = "GuardianPro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrxFieldsValues.findAll", query = "SELECT t FROM TrxFieldsValues t")
    , @NamedQuery(name = "TrxFieldsValues.findById", query = "SELECT t FROM TrxFieldsValues t WHERE t.id = :id")})
public class TrxFieldsValues implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @JoinColumn(name = "TRX_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Trx trxId;
    @JoinColumn(name = "TRX_Values_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private TrxValues tRXValuesID;
    @JoinColumn(name = "TRX_fields_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private TrxFields tRXfieldsID;
    @JoinColumn(name = "User_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private User userID;

    public TrxFieldsValues() {
    }

    public TrxFieldsValues(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Trx getTrxId() {
        return trxId;
    }

    public void setTrxId(Trx trxId) {
        this.trxId = trxId;
    }

    public TrxValues getTRXValuesID() {
        return tRXValuesID;
    }

    public void setTRXValuesID(TrxValues tRXValuesID) {
        this.tRXValuesID = tRXValuesID;
    }

    public TrxFields getTRXfieldsID() {
        return tRXfieldsID;
    }

    public void setTRXfieldsID(TrxFields tRXfieldsID) {
        this.tRXfieldsID = tRXfieldsID;
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
        if (!(object instanceof TrxFieldsValues)) {
            return false;
        }
        TrxFieldsValues other = (TrxFieldsValues) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.TrxFieldsValues[ id=" + id + " ]";
    }
    
}
