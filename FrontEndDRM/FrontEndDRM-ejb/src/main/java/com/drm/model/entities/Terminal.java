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
 * @author mohammed.ayad
 */
@Entity
@Table(catalog = "drmpro", schema = "",name = "terminal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terminal.findAll", query = "SELECT t FROM Terminal t")
    , @NamedQuery(name = "Terminal.findById", query = "SELECT t FROM Terminal t WHERE t.id = :id")
         , @NamedQuery(name = "Terminal.findByTidd", query = "SELECT t FROM Terminal t WHERE t.tid =?1")
    , @NamedQuery(name = "Terminal.findByTid", query = "SELECT t FROM Terminal t WHERE t.tid = :tid")
    , @NamedQuery(name = "Terminal.findByOwnerName", query = "SELECT t FROM Terminal t WHERE t.ownerName = :ownerName")
    , @NamedQuery(name = "Terminal.findByMerchantName", query = "SELECT t FROM Terminal t WHERE t.merchantName = :merchantName")
    , @NamedQuery(name = "Terminal.findByShopName", query = "SELECT t FROM Terminal t WHERE t.shopName = :shopName")
    , @NamedQuery(name = "Terminal.findByAddress", query = "SELECT t FROM Terminal t WHERE t.address = :address")
    , @NamedQuery(name = "Terminal.findByContactperson", query = "SELECT t FROM Terminal t WHERE t.contactperson = :contactperson")
    , @NamedQuery(name = "Terminal.findByTelNo", query = "SELECT t FROM Terminal t WHERE t.telNo = :telNo")
    , @NamedQuery(name = "Terminal.findByOfficeContact", query = "SELECT t FROM Terminal t WHERE t.officeContact = :officeContact")
    , @NamedQuery(name = "Terminal.findByOfficeTelNo", query = "SELECT t FROM Terminal t WHERE t.officeTelNo = :officeTelNo")
    , @NamedQuery(name = "Terminal.findByPOSSerialNo", query = "SELECT t FROM Terminal t WHERE t.pOSSerialNo = :pOSSerialNo")
    , @NamedQuery(name = "Terminal.findByCreateDate", query = "SELECT t FROM Terminal t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "Terminal.findByUpdateDate", query = "SELECT t FROM Terminal t WHERE t.updateDate = :updateDate")
    , @NamedQuery(name = "Terminal.findByScheduleStart", query = "SELECT t FROM Terminal t WHERE t.scheduleStart = :scheduleStart")
    , @NamedQuery(name = "Terminal.findByScheduleEnd", query = "SELECT t FROM Terminal t WHERE t.scheduleEnd = :scheduleEnd")})
public class Terminal extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "terminalId")
    private Collection<TransactionHistory> transactionHistoryCollection;
    
    public static final String NAMED_QUERY_FIND_ALL_BY_Terminal_tid = "Terminal.findByTidd";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TID")
    private String tid;
    @Size(max = 500)
    @Column(name = "Owner_Name")
    private String ownerName;
    @Size(max = 500)
    @Column(name = "Merchant_Name")
    private String merchantName;
    @Size(max = 500)
    @Column(name = "Shop_Name")
    private String shopName;
    @Size(max = 500)
    @Column(name = "Address")
    private String address;
    @Size(max = 500)
    @Column(name = "Contact_person")
    private String contactperson;
    @Size(max = 50)
    @Column(name = "Tel_No")
    private String telNo;
    @Size(max = 45)
    @Column(name = "officeContact")
    private String officeContact;
    @Size(max = 45)
    @Column(name = "officeTelNo")
    private String officeTelNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "POS_SerialNo")
    private String pOSSerialNo;
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
    @Column(name = "schedule_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleStart;
    @Column(name = "schedule_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleEnd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "terminalID")
    private Collection<Trx> trxCollection;

    public Terminal() {
    }

    public Terminal(Integer id) {
        this.id = id;
    }

    public Terminal(Integer id, String tid, String pOSSerialNo, Date createDate, Date updateDate) {
        this.id = id;
        this.tid = tid;
        this.pOSSerialNo = pOSSerialNo;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getOfficeContact() {
        return officeContact;
    }

    public void setOfficeContact(String officeContact) {
        this.officeContact = officeContact;
    }

    public String getOfficeTelNo() {
        return officeTelNo;
    }

    public void setOfficeTelNo(String officeTelNo) {
        this.officeTelNo = officeTelNo;
    }

    public String getPOSSerialNo() {
        return pOSSerialNo;
    }

    public void setPOSSerialNo(String pOSSerialNo) {
        this.pOSSerialNo = pOSSerialNo;
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

    public Date getScheduleStart() {
        return scheduleStart;
    }

    public void setScheduleStart(Date scheduleStart) {
        this.scheduleStart = scheduleStart;
    }

    public Date getScheduleEnd() {
        return scheduleEnd;
    }

    public void setScheduleEnd(Date scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }

    

    @XmlTransient
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
        if (!(object instanceof Terminal)) {
            return false;
        }
        Terminal other = (Terminal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.drm.model.entities.Terminal[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TransactionHistory> getTransactionHistoryCollection() {
        return transactionHistoryCollection;
    }

    public void setTransactionHistoryCollection(Collection<TransactionHistory> transactionHistoryCollection) {
        this.transactionHistoryCollection = transactionHistoryCollection;
    }
    
}
