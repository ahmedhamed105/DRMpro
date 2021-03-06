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
@Table(name = "user", catalog = "drmpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id")
    , @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName")
    , @NamedQuery(name = "User.findByMiddlename", query = "SELECT u FROM User u WHERE u.middlename = :middlename")
    , @NamedQuery(name = "User.findByLastname", query = "SELECT u FROM User u WHERE u.lastname = :lastname")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
    , @NamedQuery(name = "User.findByCreateDate", query = "SELECT u FROM User u WHERE u.createDate = :createDate")
    , @NamedQuery(name = "User.findByUpdateDate", query = "SELECT u FROM User u WHERE u.updateDate = :updateDate")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "First_Name", nullable = false, length = 45)
    private String firstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Middle_name", nullable = false, length = 45)
    private String middlename;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Last_name", nullable = false, length = 45)
    private String lastname;
    @Size(max = 45)
    @Column(name = "Username", length = 45)
    private String username;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Reports> reportsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<BirthData> birthDataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<TokeanGo> tokeanGoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<ParameterGroup> parameterGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<LoginWay> loginWayCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<TerminalParserLog> terminalParserLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<PhoneData> phoneDataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<PasswordHistory> passwordHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<TerminalTemplate> terminalTemplateCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<ConfigParmeter> configParmeterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<AccessoryGroup> accessoryGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<TrxFieldsValues> trxFieldsValuesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<WebsiteDataHasUser> websiteDataHasUserCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<GroupsHasUser> groupsHasUserCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<NationalId> nationalIdCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<EmailLog> emailLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<LogScreen> logScreenCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<ConfigEmail> configEmailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<EmailSmsSend> emailSmsSendCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<ApplicationUser> applicationUserCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Audit> auditCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<TerminalGroup> terminalGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<DrmParameter> drmParameterCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<UserStatus> userStatusCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<SocialData> socialDataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<ProfileData> profileDataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<ActionBar> actionBarCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<ApplicationGroup> applicationGroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<Menu> menuCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private Collection<FtpMessages> ftpMessagesCollection;
    @JoinColumn(name = "User_Password_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private UserPassword userPasswordID;

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

    @XmlTransient
    @JsonIgnore
    public Collection<Reports> getReportsCollection() {
        return reportsCollection;
    }

    public void setReportsCollection(Collection<Reports> reportsCollection) {
        this.reportsCollection = reportsCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<BirthData> getBirthDataCollection() {
        return birthDataCollection;
    }

    public void setBirthDataCollection(Collection<BirthData> birthDataCollection) {
        this.birthDataCollection = birthDataCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<TokeanGo> getTokeanGoCollection() {
        return tokeanGoCollection;
    }

    public void setTokeanGoCollection(Collection<TokeanGo> tokeanGoCollection) {
        this.tokeanGoCollection = tokeanGoCollection;
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
    public Collection<LoginWay> getLoginWayCollection() {
        return loginWayCollection;
    }

    public void setLoginWayCollection(Collection<LoginWay> loginWayCollection) {
        this.loginWayCollection = loginWayCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<TerminalParserLog> getTerminalParserLogCollection() {
        return terminalParserLogCollection;
    }

    public void setTerminalParserLogCollection(Collection<TerminalParserLog> terminalParserLogCollection) {
        this.terminalParserLogCollection = terminalParserLogCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<PhoneData> getPhoneDataCollection() {
        return phoneDataCollection;
    }

    public void setPhoneDataCollection(Collection<PhoneData> phoneDataCollection) {
        this.phoneDataCollection = phoneDataCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<PasswordHistory> getPasswordHistoryCollection() {
        return passwordHistoryCollection;
    }

    public void setPasswordHistoryCollection(Collection<PasswordHistory> passwordHistoryCollection) {
        this.passwordHistoryCollection = passwordHistoryCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<TerminalTemplate> getTerminalTemplateCollection() {
        return terminalTemplateCollection;
    }

    public void setTerminalTemplateCollection(Collection<TerminalTemplate> terminalTemplateCollection) {
        this.terminalTemplateCollection = terminalTemplateCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ConfigParmeter> getConfigParmeterCollection() {
        return configParmeterCollection;
    }

    public void setConfigParmeterCollection(Collection<ConfigParmeter> configParmeterCollection) {
        this.configParmeterCollection = configParmeterCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<AccessoryGroup> getAccessoryGroupCollection() {
        return accessoryGroupCollection;
    }

    public void setAccessoryGroupCollection(Collection<AccessoryGroup> accessoryGroupCollection) {
        this.accessoryGroupCollection = accessoryGroupCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<TrxFieldsValues> getTrxFieldsValuesCollection() {
        return trxFieldsValuesCollection;
    }

    public void setTrxFieldsValuesCollection(Collection<TrxFieldsValues> trxFieldsValuesCollection) {
        this.trxFieldsValuesCollection = trxFieldsValuesCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<WebsiteDataHasUser> getWebsiteDataHasUserCollection() {
        return websiteDataHasUserCollection;
    }

    public void setWebsiteDataHasUserCollection(Collection<WebsiteDataHasUser> websiteDataHasUserCollection) {
        this.websiteDataHasUserCollection = websiteDataHasUserCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<GroupsHasUser> getGroupsHasUserCollection() {
        return groupsHasUserCollection;
    }

    public void setGroupsHasUserCollection(Collection<GroupsHasUser> groupsHasUserCollection) {
        this.groupsHasUserCollection = groupsHasUserCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<NationalId> getNationalIdCollection() {
        return nationalIdCollection;
    }

    public void setNationalIdCollection(Collection<NationalId> nationalIdCollection) {
        this.nationalIdCollection = nationalIdCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<EmailLog> getEmailLogCollection() {
        return emailLogCollection;
    }

    public void setEmailLogCollection(Collection<EmailLog> emailLogCollection) {
        this.emailLogCollection = emailLogCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<LogScreen> getLogScreenCollection() {
        return logScreenCollection;
    }

    public void setLogScreenCollection(Collection<LogScreen> logScreenCollection) {
        this.logScreenCollection = logScreenCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ConfigEmail> getConfigEmailCollection() {
        return configEmailCollection;
    }

    public void setConfigEmailCollection(Collection<ConfigEmail> configEmailCollection) {
        this.configEmailCollection = configEmailCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<EmailSmsSend> getEmailSmsSendCollection() {
        return emailSmsSendCollection;
    }

    public void setEmailSmsSendCollection(Collection<EmailSmsSend> emailSmsSendCollection) {
        this.emailSmsSendCollection = emailSmsSendCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ApplicationUser> getApplicationUserCollection() {
        return applicationUserCollection;
    }

    public void setApplicationUserCollection(Collection<ApplicationUser> applicationUserCollection) {
        this.applicationUserCollection = applicationUserCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Audit> getAuditCollection() {
        return auditCollection;
    }

    public void setAuditCollection(Collection<Audit> auditCollection) {
        this.auditCollection = auditCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<TerminalGroup> getTerminalGroupCollection() {
        return terminalGroupCollection;
    }

    public void setTerminalGroupCollection(Collection<TerminalGroup> terminalGroupCollection) {
        this.terminalGroupCollection = terminalGroupCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<DrmParameter> getDrmParameterCollection() {
        return drmParameterCollection;
    }

    public void setDrmParameterCollection(Collection<DrmParameter> drmParameterCollection) {
        this.drmParameterCollection = drmParameterCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<UserStatus> getUserStatusCollection() {
        return userStatusCollection;
    }

    public void setUserStatusCollection(Collection<UserStatus> userStatusCollection) {
        this.userStatusCollection = userStatusCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<SocialData> getSocialDataCollection() {
        return socialDataCollection;
    }

    public void setSocialDataCollection(Collection<SocialData> socialDataCollection) {
        this.socialDataCollection = socialDataCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ProfileData> getProfileDataCollection() {
        return profileDataCollection;
    }

    public void setProfileDataCollection(Collection<ProfileData> profileDataCollection) {
        this.profileDataCollection = profileDataCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ActionBar> getActionBarCollection() {
        return actionBarCollection;
    }

    public void setActionBarCollection(Collection<ActionBar> actionBarCollection) {
        this.actionBarCollection = actionBarCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ApplicationGroup> getApplicationGroupCollection() {
        return applicationGroupCollection;
    }

    public void setApplicationGroupCollection(Collection<ApplicationGroup> applicationGroupCollection) {
        this.applicationGroupCollection = applicationGroupCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Menu> getMenuCollection() {
        return menuCollection;
    }

    public void setMenuCollection(Collection<Menu> menuCollection) {
        this.menuCollection = menuCollection;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<FtpMessages> getFtpMessagesCollection() {
        return ftpMessagesCollection;
    }

    public void setFtpMessagesCollection(Collection<FtpMessages> ftpMessagesCollection) {
        this.ftpMessagesCollection = ftpMessagesCollection;
    }

    public UserPassword getUserPasswordID() {
        return userPasswordID;
    }

    public void setUserPasswordID(UserPassword userPasswordID) {
        this.userPasswordID = userPasswordID;
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
        return "com.guardianpro.drm.entities.User[ id=" + id + " ]";
    }
    
}
