/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.Applications;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class ApplicationsFacade extends AbstractFacade<Applications> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApplicationsFacade() {
        super(Applications.class);
    }
    
    
      public  Applications APP_find(String APP){      
       Query ter = em.createNamedQuery("Applications.findByApplicationsCode");
        ter.setParameter("applicationsCode", APP);
        try {
                Applications  status = (Applications) ter.getSingleResult();     
                return status;
        } catch (Exception e) {
            return null;
        }
    }
    
}
