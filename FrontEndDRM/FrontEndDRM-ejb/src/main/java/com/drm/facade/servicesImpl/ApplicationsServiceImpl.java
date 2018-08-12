/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import com.drm.facade.services.ApplicationsService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.model.entities.Applications;
import com.drm.utils.Logger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mohammed.ayad
 */
@Stateless
@Local(ApplicationsService.class)
public class ApplicationsServiceImpl extends AbstractService implements ApplicationsService {

    private static final Logger logger = Logger.getLogger(ApplicationsServiceImpl.class);
    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;

    @PostConstruct
    private void init() {
        logger.debug("ApplicationsServiceImpl has been initilized");
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Applications> getAllApplications() {
        List<Applications> ApplicationsList = getNamedQueryResult(Applications.NAMED_QUERY_FIND_ALL_APPLICATIONS, null);
        return ApplicationsList;
    }

    @Override
    public void addNewApplication(Applications application) {
        application.setCreateDate(new Date());
        application.setUpdateDate(new Date());
        insert(application);
    }

    @Override
    public void updateApplicationInfo(Applications application) {
        application.setUpdateDate(new Date());
        update(application);
    }

}
