/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import com.drm.facade.services.ApplicationUserService;
import com.drm.facade.services.ApplicationsService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.UserService;
import com.drm.model.entities.ApplicationUser;
import com.drm.model.entities.Applications;
import com.drm.model.entities.User;
import com.drm.utils.Logger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mohammed.ayad
 */
@Stateless
@Local(ApplicationUserService.class)
public class ApplicationUserServiceImpl extends AbstractService implements ApplicationUserService {

    private static final Logger logger = Logger.getLogger(ApplicationUserServiceImpl.class);
    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;
    @EJB
    private UserService userService;
    @EJB
    private ApplicationsService applicationsService;

    @PostConstruct
    private void init() {
        logger.debug("ApplicationUserServiceImpl has been initilized");
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<String> getAllAvaiableUserNames() {
        List<String> avaliableUserNames = userService.getAllUserNames();
        return avaliableUserNames;
    }

    @Override
    public List<String> getAllAvaiableApplicationCodes() {
        List<String> avaliableApplicationsCode = applicationsService.getAllApplicationCode();
        return avaliableApplicationsCode;
    }

    @Override
    public List<ApplicationUser> getAllApplicationUser() {
        List<ApplicationUser> ApplicationUserList = getNamedQueryResult(ApplicationUser.NAMED_QUERY_FIND_ALL_APPLICATION_USER, null);
        return ApplicationUserList;
    }

    @Override
    public void updateApplicationUserInfo(ApplicationUser applicationUser, String updatedUserName, String updatedApplicationCode) {
        User user = userService.getUserByUserName(updatedUserName).get(0);
        Applications application = applicationsService.getApplicationByApplicationCode(updatedApplicationCode).get(0);
        applicationUser.setUserID(user);
        applicationUser.setApplicationsID(application);
        applicationUser.setUpdateDate(new Date());
        update(applicationUser);
    }

    @Override
    public void addNewApplicationUser(ApplicationUser newApplicationUser, String selectedUserName, String selectedApplicationCode) {
        User user = userService.getUserByUserName(selectedUserName).get(0);
        Applications application = applicationsService.getApplicationByApplicationCode(selectedApplicationCode).get(0);
        newApplicationUser.setUserID(user);
        newApplicationUser.setApplicationsID(application);
        newApplicationUser.setCreateDate(new Date());
        newApplicationUser.setUpdateDate(new Date());
        newApplicationUser.setEnable(1);
        insert(newApplicationUser);
    }

    @Override
    public boolean isApplicationUserExistBefore(String... params) {
        List<ApplicationUser> ApplicationUserList = getNamedQueryResult(ApplicationUser.NAMED_QUERY_IS_APPLICATION_USER_EXIST_BEFORE, params);
        if (ApplicationUserList.size() > 0) {
            return true;
        } else {
            return false;
        }

    }
}
