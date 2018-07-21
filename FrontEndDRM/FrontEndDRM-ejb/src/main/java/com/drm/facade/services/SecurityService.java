package com.drm.facade.services;

import com.drm.exceptions.PasswordException;
import com.drm.model.entities.User;
import java.util.List;

import javax.ejb.Local;

@Local
public interface SecurityService extends DataService {

    /**
     * This method will validate the given password and if it is not valid, it
     * will throw exception describing the problem
     *
     * @param password
     */
    public void validatePassword(User user, String password)
            throws PasswordException;

    LoginData validateUserLogin(String submittedUsername,
            String submittedPassword, String checksumKey,
            String submittedHashSum);

    List<User> getUserByUsername(String username);

    enum LOGIN_STATUS {
        SUCCESS_LOGIN, FORCE_CHANGE_PASSWORD, PASSWORD_EXPIRED, LOGIN_FAILED, USER_LOCKED, PASSWORD_LIFETIME_EXPIRED, USER_NOTFOUND, USER_INACTIVE, NO_OF_RETRIES_REACHED
    }

    class LoginData {

        private User user;
        private LOGIN_STATUS loginStatus;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public LOGIN_STATUS getLoginStatus() {
            return loginStatus;
        }

        public void setLoginStatus(LOGIN_STATUS loginStatus) {
            this.loginStatus = loginStatus;
        }

    }

    /**
     * Admin User changes an user password
     *
     * @param user
     * @param password
     * @return
     * @throws PasswordException
     */
    User changeUserPassword(User user, String password)
            throws PasswordException;

    /**
     * User changes his password
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     * @return
     * @throws PasswordException
     */
    User changeMyPassword(User user, String oldPassword, String newPassword)
            throws PasswordException;

    void archiveOldPassword(String oldPassword, Integer usrId);

    User addUserPassword(User user, String password) throws PasswordException;

//    public List<ActivityLogDO> getAuditLogs(String tableName, Object recordId);
//    public List<ActivityLogDO> getAuditLogs(
//            Class<? extends AbstractEntity> entityClass, Object recordId);
    public int autoDisableAndDeleteUsers();

//    public Role getRole(Integer id);
//    public List<ActivityLogDO> getSystemLogs(
//            String tableName, Object entityId);
}
