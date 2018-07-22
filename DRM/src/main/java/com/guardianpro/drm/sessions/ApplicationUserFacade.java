/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.ApplicationUser;
import com.guardianpro.drm.entities.Applications;
import com.guardianpro.drm.entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class ApplicationUserFacade extends AbstractFacade<ApplicationUser> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApplicationUserFacade() {
        super(ApplicationUser.class);
    }
    
    
     public  ApplicationUser App_user(Applications APP,User user){      
       Query ter = em.createNamedQuery("ApplicationUser.findByuserapp");
        ter.setParameter("id", user);
        ter.setParameter("id1", APP);
        try {
                ApplicationUser  status = (ApplicationUser) ter.getSingleResult();     
                return status;
        } catch (Exception e) {
            return null;
        }
    }
    
}
