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
@Table(name = "parameter", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parameter.findAll", query = "SELECT p FROM Parameter p")
    , @NamedQuery(name = "Parameter.findById", query = "SELECT p FROM Parameter p WHERE p.id = :id")
    , @NamedQuery(name = "Parameter.findByFieldName", query = "SELECT p FROM Parameter p WHERE p.fieldName = :fieldName")
    , @NamedQuery(name = "Parameter.findByDisplayName", query = "SELECT p FROM Parameter p WHERE p.displayName = :displayName")
    , @NamedQuery(name = "Parameter.findByInputLength", query = "SELECT p FROM Parameter p WHERE p.inputLength = :inputLength")
    , @NamedQuery(name = "Parameter.findByNote", query = "SELECT p FROM Parameter p WHERE p.note = :note")
    , @NamedQuery(name = "Parameter.findByAllowNull", query = "SELECT p FROM Parameter p WHERE p.allowNull = :allowNull")
    , @NamedQuery(name = "Parameter.findByCreateDate", query = "SELECT p FROM Parameter p WHERE p.createDate = :createDate")
    , @NamedQuery(name = "Parameter.findByUpdateDate", query = "SELECT p FROM Parameter p WHERE p.updateDate = :updateDate")
    , @NamedQuery(name = "Parameter.findByDefaultvalue", query = "SELECT p FROM Parameter p WHERE p.defaultvalue = :defaultvalue")})
public class Parameter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Field_Name", nullable = false, length = 100)
    private String fieldName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Display_Name", nullable = false, length = 100)
    private String displayName;
    @Column(name = "Input_Length")
    private Integer inputLength;
    @Size(max = 500)
    @Column(name = "Note", length = 500)
    private String note;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Allow_Null", nullable = false)
    private int allowNull;
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
    @Size(max = 500)
    @Column(name = "Default_value", length = 500)
    private String defaultvalue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parameterID")
    private Collection<GroupHasParameter> groupHasParameterCollection;
    @JoinColumn(name = "Input_type_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private InputType inputtypeID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parameterID")
    private Collection<ParameterValues> parameterValuesCollection;

    public Parameter() {
    }

    public Parameter(Integer id) {
        this.id = id;
    }

    public Parameter(Integer id, String fieldName, String displayName, int allowNull, Date createDate, Date updateDate) {
        this.id = id;
        this.fieldName = fieldName;
        this.displayName = displayName;
        this.allowNull = allowNull;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getInputLength() {
        return inputLength;
    }

    public void setInputLength(Integer inputLength) {
        this.inputLength = inputLength;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getAllowNull() {
        return allowNull;
    }

    public void setAllowNull(int allowNull) {
        this.allowNull = allowNull;
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

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<GroupHasParameter> getGroupHasParameterCollection() {
        return groupHasParameterCollection;
    }

    public void setGroupHasParameterCollection(Collection<GroupHasParameter> groupHasParameterCollection) {
        this.groupHasParameterCollection = groupHasParameterCollection;
    }

    public InputType getInputtypeID() {
        return inputtypeID;
    }

    public void setInputtypeID(InputType inputtypeID) {
        this.inputtypeID = inputtypeID;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ParameterValues> getParameterValuesCollection() {
        return parameterValuesCollection;
    }

    public void setParameterValuesCollection(Collection<ParameterValues> parameterValuesCollection) {
        this.parameterValuesCollection = parameterValuesCollection;
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
        if (!(object instanceof Parameter)) {
            return false;
        }
        Parameter other = (Parameter) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guardianpro.drm.entities.Parameter[ id=" + id + " ]";
    }
    
}
