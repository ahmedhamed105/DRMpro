/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.Trx;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ahmedhamed
 */
@Stateless
public class TrxFacade extends AbstractFacade<Trx> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TrxFacade() {
        super(Trx.class);
    }
    
}
