/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.Terminal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class TerminalFacade extends AbstractFacade<Terminal> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TerminalFacade() {
        super(Terminal.class);
    }
    
    
    public  Terminal Terminal_find(String tid){      
       Query ter = em.createNamedQuery("Terminal.findByTid");
        ter.setParameter("tid", tid);
        try {
                Terminal  status = (Terminal) ter.getSingleResult();     
                return status;
        } catch (Exception e) {
            return null;
        }
    }
    
}
