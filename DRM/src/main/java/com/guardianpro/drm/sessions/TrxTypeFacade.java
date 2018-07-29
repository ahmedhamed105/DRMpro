/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.DrmParameter;
import com.guardianpro.drm.entities.TrxType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmedhamed
 */
@Stateless
public class TrxTypeFacade extends AbstractFacade<TrxType> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrxTypeFacade() {
        super(TrxType.class);
    }
    
    
     public TrxType trxtype_find(String type){
      Query parag = em.createNamedQuery("TrxType.findByTtype");
        parag.setParameter("ttype", type);
        try {
                TrxType  para = (TrxType) parag.getSingleResult();
                return para;        
        } catch (Exception e) {
            return null;
        }
    
    
    
    }
    
    
}
