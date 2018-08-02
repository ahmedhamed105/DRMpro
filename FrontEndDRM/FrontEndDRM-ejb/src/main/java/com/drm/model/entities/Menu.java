/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.model.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mohammed.ayad
 */
@Entity
@Table(name = "menu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
    , @NamedQuery(name = "Menu.findById", query = "SELECT m FROM Menu m WHERE m.id = :id")
    , @NamedQuery(name = "Menu.findByName", query = "SELECT m FROM Menu m WHERE m.name = :name")
    , @NamedQuery(name = "Menu.findByLink", query = "SELECT m FROM Menu m WHERE m.link = :link")
    , @NamedQuery(name = Menu.FIND_BY_MODEL_TYPE, query = "SELECT m FROM Menu m WHERE m.modelType = ?1")})
public class Menu extends AbstractEntity {

    @Column(name = "Menu_Order")
    private Integer menuOrder;

    public static final String FIND_BY_MODEL_TYPE = "Menu.findByModelType";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Link")
    private String link;
    @Size(max = 45)
    @Column(name = "Model_Type")
    private String modelType;
//    @Column(name = "Parent_ID")
//    private Integer parentID;
    @JoinColumn(name = "User_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private User userID;

    @OneToMany(mappedBy = "parentMenu", fetch = FetchType.EAGER)
    private List<Menu> childMenu;

    @JoinColumn(name = "Parent_ID", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Menu parentMenu;

    public Menu() {
    }

    public Menu(Integer id) {
        this.id = id;
    }

    public Menu(Integer id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

//    public Integer getParentID() {
//        return parentID;
//    }
//
//    public void setParentID(Integer parentID) {
//        this.parentID = parentID;
//    }

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
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.drm.model.entities.Menu[ id=" + id + " ]";
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public List<Menu> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<Menu> childMenu) {
        this.childMenu = childMenu;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

}
