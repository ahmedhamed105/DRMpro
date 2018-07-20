/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.PasswordHistory;
import com.guardianpro.drm.entities.ProfileData;
import com.guardianpro.drm.entities.User;
import com.guardianpro.drm.entities.UserStatus;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    
    
        public  List<User> search_username(String Username){
    
            
       Query user_username = em.createNamedQuery("User.findByUsername");
        user_username.setParameter("username", Username);
        try {
                List<User>  users = user_username.getResultList();     
                return users;
        } catch (Exception e) {
            return null;
        }
    }
        
        
 
    public  int user_status(User User1){      
       Query user_status = em.createNamedQuery("UserStatus.findByuser");
        user_status.setParameter("id", User1);
        try {
                UserStatus  status = (UserStatus) user_status.getSingleResult();     
                return status.getId();
        } catch (Exception e) {
            return 0;
        }
    }
        
 
    public  User password_username(String Username){      
       Query password_username = em.createNamedQuery("User.findByUsername");
        password_username.setParameter("username", Username);
        try {
                User users = (User) password_username.getSingleResult();     
                return users;
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     *
     * @param Username
     * @return
     */
  
    public  Collection<PasswordHistory> passwordHistory_username(String Username){      
       Query passwordHistory_username = em.createNamedQuery("User.findByUsername");
        passwordHistory_username.setParameter("username", Username);
        try {
                User  users = (User) passwordHistory_username.getSingleResult();     
                return users.getPasswordHistoryCollection();
        } catch (Exception e) {
            return null;
        }
    }

   
     public  Collection<ProfileData> userProfile_username(String Username){      
       Query userProfile_username = em.createNamedQuery("User.findByUsername");
        userProfile_username.setParameter("username", Username);
        try {
                User user = (User) userProfile_username.getSingleResult();    
                Query userProfiles = em.createNamedQuery("ProfileData.findByUserID");
                userProfiles.setParameter("userID", user.getId());
                return userProfiles.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
     
     
    public boolean user_find(String user) {
  
       Query para_find = em.createNamedQuery("User.findByUsername");
        para_find.setParameter("username", user);
        try {
            User  Users = (User) para_find.getSingleResult(); 
         //   System.out.println("ahmed hamed  "+inputtype.getType());
         if(Users==null){
         return false;
         }else{
         return true;
         }
                
        } catch (Exception e) {
            return false;
        }
    }
    
}
