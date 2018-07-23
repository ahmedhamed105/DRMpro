package com.drm.facade.servicesImpl;

import com.drm.exceptions.PasswordException;
import com.drm.facade.services.AbstractService;
import com.drm.facade.services.AbstractService;
import com.drm.facade.services.SecurityService;
import com.drm.facade.services.SecurityService;
import com.drm.model.entities.User;
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

        // TODO user not found
        // TODO user Locked
        // TODO user status is valid
        // TODO no of retries
        // TODO password invalid
        // TODO increment password fail record
        // TODO handle lifetime
        // TODO password expire
        LoginData loginData = new LoginData();
        List<User> results = getUserByUsername(submittedUsername);
        if (results != null && !results.isEmpty()) {// user found
            logger.debug("user is found with this user name " + submittedUsername);
            User userEntity = null;
            for (User user : results) {
                userEntity = user;
                break;
            }

            // if (userEntity.getFailRetries() < noOfRetires) {// no of
            // retires
            // not
            // exceeded
            String expectedHashedSum = DrmUtils.getHash(submittedPassword + submittedUsername);
            logger.debug("expectedHashedSum " + expectedHashedSum);
            String expectedHashedValue = DrmUtils.getHash(userEntity.getUserPasswordID().getPassword() + checksumKey);
            logger.debug("expectedHashedValue " + expectedHashedValue);
            if (expectedHashedValue.equals(submittedPassword)
                    && expectedHashedSum.equals(submittedHashSum)) {// submitted
                logger.debug("password matched successfully");
                // password
                // matches
                // the
                // user
                // password
                Calendar calendar = Calendar.getInstance();
                // Date passwordLifeDate = userEntity
                // .getPasswordLifeDate();
                Date today = calendar.getTime();
                boolean lifeTimeNotExpired = true;
                /*
		 * passwordLifeDate ==
		 * null ||
		 * passwordLifeDate
                 * .after(today)
                 */
                ;

                // not expired
                loginData.setUser(userEntity);
                loginData.setLoginStatus(LOGIN_STATUS.SUCCESS_LOGIN);
                logger.debug("LOGIN_STATUS "+LOGIN_STATUS.SUCCESS_LOGIN);
            } else {
                logger.debug("password not matched successfully");
                loginData.setLoginStatus(LOGIN_STATUS.LOGIN_FAILED);
                logger.debug("LOGIN_STATUS "+LOGIN_STATUS.LOGIN_FAILED);

            }
        } else {
            logger.debug("user not found with this user name " + submittedUsername);
            loginData.setLoginStatus(LOGIN_STATUS.USER_NOTFOUND);
        }

        return loginData;

    }

    @Override
    public void validatePassword(User user, String password) throws PasswordException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getUserByUsername(String username) {
        logger.debug("getUserByUsername " + username);

        List<User> results = getNamedQueryResult(
                User.NAMED_QUERY_USER_FIND_USER_BY_USERNAME, username);

        return results;
    }

    @Override
    public User changeUserPassword(User user, String password) throws PasswordException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User changeMyPassword(User user, String oldPassword, String newPassword) throws PasswordException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void archiveOldPassword(String oldPassword, Integer usrId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User addUserPassword(User user, String password) throws PasswordException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int autoDisableAndDeleteUsers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
