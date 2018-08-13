/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.SecurityService;
import com.drm.facade.services.UserService;
import com.drm.model.entities.User;
import com.drm.model.entities.UserPassword;
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
@Local(UserService.class)
public class UserServiceImpl extends AbstractService implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
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
    public List<User> searchUsers(String jpql) {
        List<User> userList = getResult(jpql, null);
        return userList;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = getNamedQueryResult(User.NAMED_QUERY_FIND_ALL_USERS, null);
        return userList;

    }

    @Override
    public boolean isUserNameExistBefore(String userName) {
        List<User> queryResult = getNamedQueryResult(User.NAMED_QUERY_USER_FIND_USER_BY_USERNAME, userName);
        if (queryResult.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addNewUser(User user, String password) {
        String hashedPassword = securityService.addUserPassword(password);
        UserPassword newPassword = new UserPassword();
        newPassword.setPassword(hashedPassword);
        newPassword.setCreateDate(new Date());
        newPassword.setUpdateDate(new Date());
        user.setUserPasswordID(newPassword);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        logger.debug(user);
        insert(user);
    }

    @Override
    public void updateUserInfo(User user, String updatedPassword) throws Exception {
        if (updatedPassword != null && !updatedPassword.equalsIgnoreCase("")) {
            String hashedPassword = securityService.addUserPassword(updatedPassword);
            UserPassword newPassword = user.getUserPasswordID();
            newPassword.setPassword(hashedPassword);
            newPassword.setUpdateDate(new Date());
            user.setUserPasswordID(newPassword);
        }
        user.setUpdateDate(new Date());
        logger.debug(user);
        update(user);
    }

    @Override
    public List<String> getAllUserNames() {
        List<String> userNameList = getNamedQueryColumnResult(User.NAMED_QUERY_FIND_ALL_USER_NAMES, null);
        return userNameList;
    }

    @Override
    public List<User> getUserByUserName(String userName) {
        List<User> queryResult = getNamedQueryResult(User.NAMED_QUERY_USER_FIND_USER_BY_USERNAME, userName);
        return queryResult;

    }

}
