/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.HostInfo;
import com.guardianpro.drm.entities.LoginPrev;
import com.guardianpro.drm.entities.TrxValues;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmedhamed
 */
@Stateless
public class TrxValuesFacade extends AbstractFacade<TrxValues> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrxValuesFacade() {
        super(TrxValues.class);
    }
    
    
     public TrxValues value_find(String value){
      Query parag = em.createNamedQuery("TrxValues.findByFValue");
        parag.setParameter("fValue", value);
        try {
                TrxValues  para = (TrxValues) parag.getSingleResult();
                return para;        
        } catch (Exception e) {
            return null;
        }
    
    
    
    }
    
}
