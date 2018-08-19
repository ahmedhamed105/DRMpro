/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.DrmParametersService;
import com.drm.model.entities.DrmParameter;
import com.drm.model.entities.User;
import com.drm.utils.Logger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mohammed.ayad
 */
@Stateless
@Local(DrmParametersService.class)
public class DrmParametersServiceImpl extends AbstractService implements DrmParametersService {

    private static final Logger logger = Logger.getLogger(DrmParametersServiceImpl.class);

    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;

    @PostConstruct
    private void init() {
        logger.debug("DrmParametersServiceImpl has been initilized");
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<DrmParameter> getAllDrmParameters() {
        List<DrmParameter> drmParameterList = getNamedQueryResult(DrmParameter.NAMED_QUERY_FIND_ALL_DRM_PARAMETER, null);
        return drmParameterList;
    }

    @Override
    public void updateParameterInfo(DrmParameter updateParameter) {
        updateParameter.setUpdateDate(new Date());
        update(updateParameter);
    }

    @Override
    public boolean isParameterNameExistBefore(String parameterName) {
        List<DrmParameter> parameterList = getNamedQueryResult(DrmParameter.NAMED_QUERY_FIND_ALL_BY_PARAMETER_NAME, parameterName);
        if (parameterList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addNewParameter(DrmParameter newParameter, User user) {
        newParameter.setCreateDate(new Date());
        newParameter.setUpdateDate(new Date());
        newParameter.setUserID(user);
        insert(newParameter);
    }
}
