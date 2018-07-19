/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.DrmParameter;
import com.guardianpro.drm.entities.HostInfo;
import com.guardianpro.drm.entities.LoginPrev;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class LoginPrevFacade extends AbstractFacade<LoginPrev> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginPrevFacade() {
        super(LoginPrev.class);
    }
    
    
       public LoginPrev host_find(HostInfo host){
      Query parag = em.createNamedQuery("LoginPrev.findByhost");
        parag.setParameter("id", host);
        try {
                LoginPrev  para = (LoginPrev) parag.getSingleResult();
                return para;        
        } catch (Exception e) {
            return null;
        }
    
    
    
    }
    
}
