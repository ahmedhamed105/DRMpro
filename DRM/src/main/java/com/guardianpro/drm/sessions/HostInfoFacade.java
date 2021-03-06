/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guardianpro.drm.sessions;

import com.guardianpro.drm.entities.HostInfo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class HostInfoFacade extends AbstractFacade<HostInfo> {

    @PersistenceContext(unitName = "com.guardianpro_DRM_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HostInfoFacade() {
        super(HostInfo.class);
    }
    
    
    public HostInfo ip_find(String ip){
      Query hostip = em.createNamedQuery("HostInfo.findByHIp");
        hostip.setParameter("hIp", ip);
        try {
                HostInfo  ips = (HostInfo) hostip.getSingleResult();
                return ips;        
        } catch (Exception e) {
            return null;
        }
    
    
    
    }
    
}
