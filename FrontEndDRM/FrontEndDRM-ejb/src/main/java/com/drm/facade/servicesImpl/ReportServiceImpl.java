/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.ReportService;
import com.drm.facade.services.SecurityService;
import com.drm.facade.services.UserService;
import com.drm.model.entities.Reports;
import com.drm.utils.Logger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mohammed.ayad
 */
@Stateless
@Local(UserService.class)
public class ReportServiceImpl extends AbstractService implements ReportService {

    private static final Logger logger = Logger.getLogger(ReportServiceImpl.class);
    @EJB
    private SecurityService securityService;

    @PostConstruct
    private void init() {
        logger.debug("UserService has been initilized");
    }

    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Reports getbyid(int id) {
    Query user_username = entityManager.createNamedQuery("Reports.findById");
        user_username.setParameter("id", id);
        try {
                Reports  Report = (Reports) user_username.getSingleResult();     
                return Report;
        } catch (Exception e) {
            return null;
        }
    
    }


}
