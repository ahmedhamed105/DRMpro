/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.model.entities;

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
 * @author mohammed.ayad
 */
@Entity
@Table(catalog = "drmpro", schema = "",name = "transaction_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionHistory.findAll", query = "SELECT t FROM TransactionHistory t")
    , @NamedQuery(name = "TransactionHistory.findById", query = "SELECT t FROM TransactionHistory t WHERE t.id = :id")
    , @NamedQuery(name = "TransactionHistory.findByFieldName", query = "SELECT t FROM TransactionHistory t WHERE t.fieldName = :fieldName")
    , @NamedQuery(name = "TransactionHistory.findByFieldDescription", query = "SELECT t FROM TransactionHistory t WHERE t.fieldDescription = :fieldDescription")
    , @NamedQuery(name = "TransactionHistory.findByFieldType", query = "SELECT t FROM TransactionHistory t WHERE t.fieldType = :fieldType")
    , @NamedQuery(name = "TransactionHistory.findByFieldPattern", query = "SELECT t FROM TransactionHistory t WHERE t.fieldPattern = :fieldPattern")
    , @NamedQuery(name = "TransactionHistory.findByFieldValue", query = "SELECT t FROM TransactionHistory t WHERE t.fieldValue = :fieldValue")
    , @NamedQuery(name = "TransactionHistory.findByTransactionType", query = "SELECT t FROM TransactionHistory t WHERE t.transactionType = :transactionType")
    , @NamedQuery(name = "TransactionHistory.findByTransactionNumber", query = "SELECT t FROM TransactionHistory t WHERE t.transactionNumber = :transactionNumber")
    , @NamedQuery(name = "TransactionHistory.findByCreateDate", query = "SELECT t FROM TransactionHistory t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TransactionHistory.findByUpdateDate", query = "SELECT t FROM TransactionHistory t WHERE t.updateDate = :updateDate")})
public class TransactionHistory extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "field_name")
    private String fieldName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "field_description")
    private String fieldDescription;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "field_type")
    private String fieldType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "field_pattern")
    private String fieldPattern;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "field_value")
    private String fieldValue;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "transaction_type")
    private String transactionType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "transaction_number")
    private String transactionNumber;
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
    @JoinColumn(name = "terminal_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Terminal terminalId;
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User userId;

    public TransactionHistory() {
    }

    public TransactionHistory(Integer id) {
        this.id = id;
    }

    public TransactionHistory(Integer id, String fieldName, String fieldDescription, String fieldType, String fieldPattern, String fieldValue, String transactionType, String transactionNumber, Date createDate, Date updateDate) {
        this.id = id;
        this.fieldName = fieldName;
        this.fieldDescription = fieldDescription;
        this.fieldType = fieldType;
        this.fieldPattern = fieldPattern;
        this.fieldValue = fieldValue;
        this.transactionType = transactionType;
        this.transactionNumber = transactionNumber;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldPattern() {
        return fieldPattern;
    }

    public void setFieldPattern(String fieldPattern) {
        this.fieldPattern = fieldPattern;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
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

    public Terminal getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(Terminal terminalId) {
        this.terminalId = terminalId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof TransactionHistory)) {
            return false;
        }
        TransactionHistory other = (TransactionHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.drm.model.entities.TransactionHistory[ id=" + id + " ]";
    }
    
}
