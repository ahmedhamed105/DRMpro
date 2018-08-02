/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.TrxFields;
import com.guardianpro.drm.entities.TrxTypeMsg;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmedhamed
 */
@Stateless
public class TrxFieldsFacade extends AbstractFacade<TrxFields> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrxFieldsFacade() {
        super(TrxFields.class);
    }
    
    
    
     public TrxFields trxtype_find(String field_name){
      Query parag = em.createNamedQuery("TrxFields.findByFName");
        parag.setParameter("fName", field_name);
        try {
                TrxFields  para = (TrxFields) parag.getSingleResult();
                return para;        
        } catch (Exception e) {
            return null;
        }
    
    
    
    }
    
}
