/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.DrmParameter;
import com.guardianpro.drm.entities.HostInfo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class DrmParameterFacade extends AbstractFacade<DrmParameter> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DrmParameterFacade() {
        super(DrmParameter.class);
    }
    
    
    public DrmParameter para_find(String parametername){
      Query parag = em.createNamedQuery("DrmParameter.findByParametername");
        parag.setParameter("parametername", parametername);
        try {
                DrmParameter  para = (DrmParameter) parag.getSingleResult();
                return para;        
        } catch (Exception e) {
            return null;
        }
    
    
    
    }
    
}
