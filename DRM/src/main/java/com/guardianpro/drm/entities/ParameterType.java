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
@Table(name = "parameter_type", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParameterType.findAll", query = "SELECT p FROM ParameterType p")
    , @NamedQuery(name = "ParameterType.findById", query = "SELECT p FROM ParameterType p WHERE p.id = :id")
    , @NamedQuery(name = "ParameterType.findByType", query = "SELECT p FROM ParameterType p WHERE p.type = :type")
    , @NamedQuery(name = "ParameterType.findByCreateDate", query = "SELECT p FROM ParameterType p WHERE p.createDate = :createDate")
    , @NamedQuery(name = "ParameterType.findByUpdateDate", query = "SELECT p FROM ParameterType p WHERE p.updateDate = :updateDate")
    , @NamedQuery(name = "ParameterType.findByXMLheader", query = "SELECT p FROM ParameterType p WHERE p.xMLheader = :xMLheader")})
public class ParameterType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "type", nullable = false, length = 45)
    private String type;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "XML_header", nullable = false, length = 150)
    private String xMLheader;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parametertypeID")
    private Collection<ParameterGroup> parameterGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parametertypeID")
    private Collection<GroupSechema> groupSechemaCollection;

    public ParameterType() {
    }

    public ParameterType(Integer id) {
        this.id = id;
    }

    public ParameterType(Integer id, String type, Date createDate, Date updateDate, String xMLheader) {
        this.id = id;
        this.type = type;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.xMLheader = xMLheader;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getXMLheader() {
        return xMLheader;
    }

    public void setXMLheader(String xMLheader) {
        this.xMLheader = xMLheader;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ParameterGroup> getParameterGroupCollection() {
        return parameterGroupCollection;
    }

    public void setParameterGroupCollection(Collection<ParameterGroup> parameterGroupCollection) {
        this.parameterGroupCollection = parameterGroupCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<GroupSechema> getGroupSechemaCollection() {
        return groupSechemaCollection;
    }

    public void setGroupSechemaCollection(Collection<GroupSechema> groupSechemaCollection) {
        this.groupSechemaCollection = groupSechemaCollection;
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
        if (!(object instanceof ParameterType)) {
            return false;
        }
        ParameterType other = (ParameterType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.ParameterType[ id=" + id + " ]";
    }
    
}
