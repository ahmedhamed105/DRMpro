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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahmedhamed
 */
@Entity
@Table(name = "logger", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logger.findAll", query = "SELECT l FROM Logger l")
    , @NamedQuery(name = "Logger.findByLoggerId", query = "SELECT l FROM Logger l WHERE l.loggerId = :loggerId")
    , @NamedQuery(name = "Logger.findByDate", query = "SELECT l FROM Logger l WHERE l.date = :date")
    , @NamedQuery(name = "Logger.findByLogger", query = "SELECT l FROM Logger l WHERE l.logger = :logger")
    , @NamedQuery(name = "Logger.findByLevel", query = "SELECT l FROM Logger l WHERE l.level = :level")
    , @NamedQuery(name = "Logger.findByMessage", query = "SELECT l FROM Logger l WHERE l.message = :message")})
public class Logger implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "logger_id", nullable = false)
    private Integer loggerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "date", nullable = false, length = 50)
    private String date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "logger", nullable = false, length = 250)
    private String logger;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "level", nullable = false, length = 45)
    private String level;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "message", nullable = false, length = 500)
    private String message;

    public Logger() {
    }

    public Logger(Integer loggerId) {
        this.loggerId = loggerId;
    }

    public Logger(Integer loggerId, String date, String logger, String level, String message) {
        this.loggerId = loggerId;
        this.date = date;
        this.logger = logger;
        this.level = level;
        this.message = message;
    }

    public Integer getLoggerId() {
        return loggerId;
    }

    public void setLoggerId(Integer loggerId) {
        this.loggerId = loggerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loggerId != null ? loggerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logger)) {
            return false;
        }
        Logger other = (Logger) object;
        if ((this.loggerId == null && other.loggerId != null) || (this.loggerId != null && !this.loggerId.equals(other.loggerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.Logger[ loggerId=" + loggerId + " ]";
    }
    
}
