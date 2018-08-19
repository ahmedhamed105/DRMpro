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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mohammed.ayad
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = User.NAMED_QUERY_FIND_ALL_USERS, query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
    , @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "User.findByMiddlename", query = "SELECT u FROM User u WHERE u.middlename = :middlename")
    , @NamedQuery(name = "User.findByLastname", query = "SELECT u FROM User u WHERE u.lastname = :lastname")
    , @NamedQuery(name = User.NAMED_QUERY_USER_FIND_USER_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username =?1")
    , @NamedQuery(name = "User.findByCreateDate", query = "SELECT u FROM User u WHERE u.createDate = :createDate")
    , @NamedQuery(name = "User.findByUpdateDate", query = "SELECT u FROM User u WHERE u.updateDate = :updateDate")
    ,@NamedQuery(name = User.NAMED_QUERY_FIND_ALL_USER_NAMES, query = "SELECT u.username FROM User u")})
public class User extends AbstractEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<DrmParameter> drmParameterCollection;

    public static final String NAMED_QUERY_USER_FIND_USER_BY_USERNAME = "User.findUserByUsername";
    public static final String NAMED_QUERY_FIND_ALL_USERS = "User.findAll";
    public static final String NAMED_QUERY_FIND_ALL_USER_NAMES = "User.findAllUserNames";

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<ApplicationUser> applicationUserCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Reports> reportsCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
//    @Size(min = 1, max = 45)
    @Column(name = "First_Name")
    private String firstName;
    @Basic(optional = false)
    @NotNull
//    @Size(min = 1, max = 45)
    @Column(name = "Middle_name")
    private String middlename;
    @Basic(optional = false)
    @NotNull
//    @Size(min = 1, max = 45)
    @Column(name = "Last_name")
    private String lastname;
//    @Size(max = 45)
    @Column(name = "Username")
    private String username;
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
    @JoinColumn(name = "User_Password_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private UserPassword userPasswordID;
    @Transient
    private Date loginTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<TerminalTemplate> terminalTemplateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<TrxFieldsValues> trxFieldsValuesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Menu> menuCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Audit> auditCollection;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String firstName, String middlename, String lastname, Date createDate, Date updateDate) {
        this.id = id;
        this.firstName = firstName;
        this.middlename = middlename;
        this.lastname = lastname;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public UserPassword getUserPasswordID() {
        return userPasswordID;
    }

    public void setUserPasswordID(UserPassword userPasswordID) {
        this.userPasswordID = userPasswordID;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.drm.model.entities.User[ id=" + id + " ][ username=" + username + " ]"
                + "[ firstName=" + firstName + " ][ middlename=" + middlename + " ]"
                + "[ lastname=" + lastname + " ][ getPassword=" + userPasswordID.getPassword() + " ]";
    }

    @XmlTransient
    public Collection<Audit> getAuditCollection() {
        return auditCollection;
    }

    public void setAuditCollection(Collection<Audit> auditCollection) {
        this.auditCollection = auditCollection;
    }

    @XmlTransient
    public Collection<Menu> getMenuCollection() {
        return menuCollection;
    }

    public void setMenuCollection(Collection<Menu> menuCollection) {
        this.menuCollection = menuCollection;
    }

    @XmlTransient
    public Collection<TerminalTemplate> getTerminalTemplateCollection() {
        return terminalTemplateCollection;
    }

    public void setTerminalTemplateCollection(Collection<TerminalTemplate> terminalTemplateCollection) {
        this.terminalTemplateCollection = terminalTemplateCollection;
    }

    @XmlTransient
    public Collection<TrxFieldsValues> getTrxFieldsValuesCollection() {
        return trxFieldsValuesCollection;
    }

    public void setTrxFieldsValuesCollection(Collection<TrxFieldsValues> trxFieldsValuesCollection) {
        this.trxFieldsValuesCollection = trxFieldsValuesCollection;
    }

    @XmlTransient
    public Collection<Reports> getReportsCollection() {
        return reportsCollection;
    }

    public void setReportsCollection(Collection<Reports> reportsCollection) {
        this.reportsCollection = reportsCollection;
    }

    @XmlTransient
    public Collection<ApplicationUser> getApplicationUserCollection() {
        return applicationUserCollection;
    }

    public void setApplicationUserCollection(Collection<ApplicationUser> applicationUserCollection) {
        this.applicationUserCollection = applicationUserCollection;
    }

    @XmlTransient
    public Collection<DrmParameter> getDrmParameterCollection() {
        return drmParameterCollection;
    }

    public void setDrmParameterCollection(Collection<DrmParameter> drmParameterCollection) {
        this.drmParameterCollection = drmParameterCollection;
    }

}
