/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.ApplicationUser;
import com.guardianpro.drm.entities.HostInfo;
import com.guardianpro.drm.entities.LoginQuery;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class LoginQueryFacade extends AbstractFacade<LoginQuery> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginQueryFacade() {
        super(LoginQuery.class);
    }
       public LoginQuery  appuser_find(ApplicationUser Appuser){
      Query hostip = em.createNamedQuery("LoginQuery.findByappuser");
        hostip.setParameter("id", Appuser);
        try {
                LoginQuery  ips = (LoginQuery) hostip.getSingleResult();
                return ips;        
        } catch (Exception e) {
            return null;
        }
    
    
    
    }
    
}
