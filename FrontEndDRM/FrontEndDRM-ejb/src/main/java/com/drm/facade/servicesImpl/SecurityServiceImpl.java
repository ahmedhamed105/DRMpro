package com.drm.facade.servicesImpl;

import com.drm.exceptions.PasswordException;
import com.drm.facade.services.AbstractService;
import com.drm.facade.services.AbstractService;
import com.drm.facade.services.SecurityService;
import com.drm.facade.services.SecurityService;
import com.drm.model.entities.User;
import com.drm.model.entities.UserPassword;
import com.drm.utils.DrmUtils;
import com.drm.utils.Logger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(SecurityService.class)
public class SecurityServiceImpl extends AbstractService implements
        SecurityService {

    private static final Logger logger = Logger.getLogger(SecurityServiceImpl.class);

    @PostConstruct
    private void init() {
        logger.debug("SecurityService has been initilized");

    }

    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {

        return entityManager;
    }

    @Override
    public LoginData validateUserLogin(String submittedUsername,
            String submittedPassword, String checksumKey,
            String submittedHashSum) {
        logger.debug("validateUserLogin " + submittedUsername);
        LoginData loginData = new LoginData();
        List<User> results = getUserByUsername(submittedUsername);
        if (results != null && !results.isEmpty()) {// user found
            logger.debug("user is found with this user name " + submittedUsername);
            User userEntity = null;
            for (User user : results) {
                userEntity = user;
                break;
            }
            String expectedHashedSum = DrmUtils.getHash(submittedPassword + submittedUsername);
            logger.debug("expectedHashedSum " + expectedHashedSum);
            String expectedHashedValue = DrmUtils.getHash(userEntity.getUserPasswordID().getPassword() + checksumKey);
            logger.debug("expectedHashedValue " + expectedHashedValue);
            if (expectedHashedValue.equals(submittedPassword)
                    && expectedHashedSum.equals(submittedHashSum)) {
                logger.debug("password matched successfully");
                loginData.setUser(userEntity);
                loginData.setLoginStatus(LOGIN_STATUS.SUCCESS_LOGIN);
                logger.debug("LOGIN_STATUS " + LOGIN_STATUS.SUCCESS_LOGIN);
            } else {
                logger.debug("password not matched successfully");
                loginData.setLoginStatus(LOGIN_STATUS.LOGIN_FAILED);
                logger.debug("LOGIN_STATUS " + LOGIN_STATUS.LOGIN_FAILED);

            }
        } else {
            logger.debug("user not found with this user name " + submittedUsername);
            loginData.setLoginStatus(LOGIN_STATUS.USER_NOTFOUND);
        }

        return loginData;

    }

    @Override
    public List<User> getUserByUsername(String username) {
        logger.debug("getUserByUsername " + username);

        List<User> results = getNamedQueryResult(
                User.NAMED_QUERY_USER_FIND_USER_BY_USERNAME, username);

        return results;
    }

    @Override
    public String addUserPassword(String password) {
        String hashedPassword =hashingPassword(password);
        return hashedPassword;

    }

    private String hashingPassword(String newPassword) {
        logger.debug("hashingPassword ", newPassword);
        String hashedPassword = DrmUtils.getHash(newPassword);
        logger.debug("password "+newPassword+" hashed Password"+hashedPassword);
        return hashedPassword;
//        UserPassword password = new UserPassword();
//        password.setPassword(hashedPassword);
//        password.setCreateDate(new Date());
//        password.setUpdateDate(new Date());
//        user.setUserPasswordID(password);
    }
}
