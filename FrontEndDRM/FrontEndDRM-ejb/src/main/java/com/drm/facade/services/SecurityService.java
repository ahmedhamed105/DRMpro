package com.drm.facade.services;

import com.drm.exceptions.PasswordException;
import com.drm.model.entities.User;
import java.util.List;

import javax.ejb.Local;

@Local
public interface SecurityService extends DataService {

    LoginData validateUserLogin(String submittedUsername,
            String submittedPassword, String checksumKey,
            String submittedHashSum);

    List<User> getUserByUsername(String username);

    String addUserPassword(String password);

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

}
