/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.facade.services.SecurityService;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import com.drm.model.entities.User;
import com.drm.utils.Constants;
import com.drm.utils.DrmUtils;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.drm.utils.Constants.SYSTEM_PAGES;
import com.drm.utils.Logger;
import javax.faces.application.FacesMessage;

/**
 *
 * @author mohammed.ayad
 */
@Named("loginBean")
@SessionScoped
public class LoginController extends AbstractManagedBean {

    private static final Logger logger = Logger.getLogger(LoginController.class);
    private static final String VIEW_STATE_HIDDEN_FIELD_NAME = "javax.faces.ViewState";
    private String username;
    private String password;
    private String hashsum;
    private User currentUser;
    @EJB
    private SecurityService securityService;

    public String doLogin() {
        logger.debug("doLogin user name " + username + " password " + password);
        FacesContext facesContext = JSFUtils.getFacesContext();
        if (securityService != null) {
            logger.debug("securityService not null");
            String checkSumKey = JSFUtils.getRequestParamValue(VIEW_STATE_HIDDEN_FIELD_NAME);
            logger.debug(VIEW_STATE_HIDDEN_FIELD_NAME + " " + checkSumKey + " hashsum " + hashsum);
            System.out.println("checkSumKey " + checkSumKey + " hashsum " + hashsum + " username " + username + " password " + password);
            SecurityService.LoginData loginData = securityService.validateUserLogin(username, password, checkSumKey, hashsum);
            switch (loginData.getLoginStatus()) {
                case SUCCESS_LOGIN:
                    User user = loginData.getUser();
                    logger.debug("SUCCESS_LOGIN " + user.getUsername());
                    signInUser(user);
                    logger.debug(user.getUsername() + " signed in session successfully");
                    logger.debug("send " + user.getUsername() + " to home " + SYSTEM_PAGES.HOME_PAGE_URL.getUrl());
                    return SYSTEM_PAGES.HOME_PAGE_URL.getUrl();
                case USER_NOTFOUND:
                case LOGIN_FAILED:
                    JSFUtils.addErrorMessage("Login error! Incorrect username or password.");
                    logger.debug("Login error! Incorrect username or password.");
                    break;
                default:
                    JSFUtils.addErrorMessage("Login error! Incorrect username or password.");
                    logger.debug("Login error! Incorrect username or password.");
                    break;
            }
        } else {
            FacesMessage msg = new FacesMessage(
                    "Couldn't contacting the server, please try again later",
                    "ERROR MSG");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesContext.addMessage(null, msg);
            logger.debug("securityService is null");
            return null;
        }
        logger.debug("Login error! Incorrect username or password.");
        logger.debug("send user to" + SYSTEM_PAGES.FORM_LOGIN_URL.getUrl() + " an error happened");
        return SYSTEM_PAGES.FORM_LOGIN_URL.getUrl();
    }

    private void signInUser(User user) {
        logger.debug("signInUser " + user.getUsername());
        DrmUtils.registerAuthenticatedUserInSession(user,
                (HttpServletResponse) JSFUtils.getExternalContext()
                        .getResponse(), (HttpServletRequest) JSFUtils
                        .getExternalContext().getRequest());
    }

    private User getCurrentLoggedInUser() {
        HttpServletRequest currentRequest = getCurrentRequest();
        User currentUser = DrmUtils.getCurrentUser(currentRequest);
        return currentUser;
    }

    private HttpServletRequest getCurrentRequest() {
        HttpServletRequest currentRequest = (HttpServletRequest) JSFUtils
                .getExternalContext().getRequest();
        return currentRequest;
    }

    public boolean isUserLoggedIn() {
        Object result = JSFUtils
                .getSessionValue(Constants.SESSION_KEYS_IS_USER_LOGGED_IN);
        boolean booleanResult = false;
        if (result != null) {
            booleanResult = (Boolean) result;
        }

        return booleanResult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashsum() {
        return hashsum;
    }

    public void setHashsum(String hashsum) {
        this.hashsum = hashsum;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
