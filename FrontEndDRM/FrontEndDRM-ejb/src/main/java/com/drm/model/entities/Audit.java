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
@Table(catalog = "drmpro", schema = "",name = "audit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audit.findAll", query = "SELECT a FROM Audit a")
    , @NamedQuery(name = "Audit.findByAuditId", query = "SELECT a FROM Audit a WHERE a.auditId = :auditId")
    , @NamedQuery(name = "Audit.findByAuditDatetime", query = "SELECT a FROM Audit a WHERE a.auditDatetime = :auditDatetime")
    , @NamedQuery(name = "Audit.findByActionResult", query = "SELECT a FROM Audit a WHERE a.actionResult = :actionResult")
    , @NamedQuery(name = "Audit.findByActionValue", query = "SELECT a FROM Audit a WHERE a.actionValue = :actionValue")
    , @NamedQuery(name = "Audit.findByAction", query = "SELECT a FROM Audit a WHERE a.action = :action")})
public class Audit extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "audit_id")
    private Integer auditId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "audit_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auditDatetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "action_result")
    private String actionResult;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "action_value")
    private String actionValue;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "action")
    private String action;
    @JoinColumn(name = "user_id", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User userId;

    public Audit() {
    }

    public Audit(Integer auditId) {
        this.auditId = auditId;
    }

    public Audit(Integer auditId, Date auditDatetime, String actionResult, String actionValue, String action) {
        this.auditId = auditId;
        this.auditDatetime = auditDatetime;
        this.actionResult = actionResult;
        this.actionValue = actionValue;
        this.action = action;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Date getAuditDatetime() {
        return auditDatetime;
    }

    public void setAuditDatetime(Date auditDatetime) {
        this.auditDatetime = auditDatetime;
    }

    public String getActionResult() {
        return actionResult;
    }

    public void setActionResult(String actionResult) {
        this.actionResult = actionResult;
    }

    public String getActionValue() {
        return actionValue;
    }

    public void setActionValue(String actionValue) {
        this.actionValue = actionValue;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
        hash += (auditId != null ? auditId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Audit)) {
            return false;
        }
        Audit other = (Audit) object;
        if ((this.auditId == null && other.auditId != null) || (this.auditId != null && !this.auditId.equals(other.auditId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.drm.model.entities.Audit[ auditId=" + auditId + " ]";
    }
    
}
