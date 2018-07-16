/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

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
 * @author ahmed.elemam
 */
@Entity
@Table(name = "tokean_go", catalog = "guardianpro", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TokeanGo.findAll", query = "SELECT t FROM TokeanGo t"),
    @NamedQuery(name = "TokeanGo.findById", query = "SELECT t FROM TokeanGo t WHERE t.id = :id"),
    @NamedQuery(name = "TokeanGo.findByTokean", query = "SELECT t FROM TokeanGo t WHERE t.tokean = :tokean"),
    @NamedQuery(name = "TokeanGo.findByCreateDate", query = "SELECT t FROM TokeanGo t WHERE t.createDate = :createDate"),
    @NamedQuery(name = "TokeanGo.findByUpdateDate", query = "SELECT t FROM TokeanGo t WHERE t.updateDate = :updateDate")})
public class TokeanGo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "Tokean", nullable = false, length = 500)
    private String tokean;
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
    @JoinColumn(name = "Login_prev_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private LoginPrev loginprevID;
    @JoinColumn(name = "User_ID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private User userID;

    public TokeanGo() {
    }

    public TokeanGo(Integer id) {
        this.id = id;
    }

    public TokeanGo(Integer id, String tokean, Date createDate, Date updateDate) {
        this.id = id;
        this.tokean = tokean;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTokean() {
        return tokean;
    }

    public void setTokean(String tokean) {
        this.tokean = tokean;
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

    public LoginPrev getLoginprevID() {
        return loginprevID;
    }

    public void setLoginprevID(LoginPrev loginprevID) {
        this.loginprevID = loginprevID;
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
        if (!(object instanceof TokeanGo)) {
            return false;
        }
        TokeanGo other = (TokeanGo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.TokeanGo[ id=" + id + " ]";
    }
    
}
