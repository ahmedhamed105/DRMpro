/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.HostInfo;
import com.guardianpro.drm.entities.LoginPrev;
import com.guardianpro.drm.entities.Terminal;
import com.guardianpro.drm.entities.TokeanGo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class TokeanGoFacade extends AbstractFacade<TokeanGo> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TokeanGoFacade() {
        super(TokeanGo.class);
    }
    
    
    
       public TokeanGo preterm_find(LoginPrev pre,Terminal term){
      Query hostip = em.createNamedQuery("TokeanGo.findBypretreminal");
        hostip.setParameter("id", pre);
        hostip.setParameter("id1", term);
        try {
                TokeanGo  ips = (TokeanGo) hostip.getSingleResult();
                return ips;        
        } catch (Exception e) {
            return null;
        }
    
    
    
    }
    
}
