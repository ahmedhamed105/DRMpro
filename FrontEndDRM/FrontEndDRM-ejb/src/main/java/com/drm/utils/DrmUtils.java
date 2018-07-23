/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.utils;

import com.drm.exceptions.GeneralException;
import com.drm.model.entities.User;
import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mohammed.ayad
 */
public class DrmUtils {

    private static final Logger logger = Logger.getLogger(DrmUtils.class);

    // FIXME Password should be sent plain and Security component will handle it
    public static String getHash(String plainTxt) {
        String hashedTxt = "";
        try {
            byte[] hash = MessageDigest.getInstance(
                    Constants.SYSTEM_SECURITY_HASH_ALGORITHM).digest(
                            plainTxt.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            hashedTxt = hexString.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hashedTxt;
    }

    /**
     * This method returns the current {@link User} instance associated with
     * current active {@link HttpSession}.
     *
     * @return current User in active session or null if no active session is
     * defined
     */
    public static User getCurrentUser(HttpServletRequest currentRequest) {
        Object attribute = null;

        HttpSession session = getCurrentSession(currentRequest);
        if (session != null) {
            attribute = session.getAttribute(Constants.SESSION_KEYS_LOGGED_IN_USER);
        }

        return (User) attribute;
    }

    /**
     * This method return the current active session, it doesn't create new
     * session if no session defined in the current context
     *
     * @return instance of HttpSession if session is active or null
     * @see HttpServletRequest#getSession(boolean)
     * @see PayHubUtils#getCurrentRequest()
     */
    public static HttpSession getCurrentSession(
            HttpServletRequest currentRequest) {
        logger.debug("getCurrentSession");
        HttpSession session = null;
        // HttpServletRequest currentRequest = getCurrentRequest();
        if (currentRequest != null) {
            logger.debug("Current Session not null");
            session = currentRequest.getSession(false);
        } else {
            logger.debug("Current Session is null");
        }
        return session;
    }

    /**
     * This method register the loggedIn user and associate him in the session
     *
     * @param userEntity
     * @param httpSession
     */
    public static void registerAuthenticatedUserInSession(User userEntity,
            HttpServletResponse response, HttpServletRequest currentRequest) {
        logger.debug("registerAuthenticatedUserInSession " + userEntity.getUsername());

        userEntity.setLoginTime(new Date());

        HttpSession httpSession = getSessionForLogin(currentRequest);
//        log.debug("Create new session ", httpSession.getId());
        if (httpSession != null) {
            logger.debug("start save user data into session");
            httpSession.setAttribute(Constants.SESSION_KEYS_IS_USER_LOGGED_IN,
                    true);
            httpSession.setAttribute(Constants.SESSION_KEYS_USER_ID,
                    userEntity.getUsername());
            httpSession.setAttribute(Constants.SESSION_KEYS_LAST_LOGIN_TIME,
                    userEntity.getLoginTime());
            httpSession.setAttribute(Constants.SESSION_KEYS_LOGGED_IN_USER,
                    userEntity);
            logger.debug(Constants.SESSION_KEYS_IS_USER_LOGGED_IN + " true "
                    + Constants.SESSION_KEYS_USER_ID + " " + userEntity.getUsername() + " "
                    + Constants.SESSION_KEYS_LAST_LOGIN_TIME
                    + " " + userEntity.getLoginTime() + " "
                    + Constants.SESSION_KEYS_LOGGED_IN_USER + " " + userEntity);
            addLoginCookie(response);

        } else {
            logger.debug("there isn not any session in request so will throw exception");
            GeneralException generalException = new GeneralException();
            generalException
                    .setDescription("No active session to associate user "
                            + userEntity.getUsername());
            logger.debug("No active session to associate user " + userEntity.getUsername());
            throw generalException;
        }
    }

    public static void addLoginCookie(HttpServletResponse response) {
        // try to put cookie to track session
        logger.debug("addLoginCookie in response " + response);
        String uuID = UUID.randomUUID().toString();
        logger.debug("Cookie name" + Constants.KEYS_LOGIN_COOKIE_NAME + " uuID value " + uuID);
        Cookie loginCookie = new Cookie(Constants.KEYS_LOGIN_COOKIE_NAME, uuID);
        response.addCookie(loginCookie);
        logger.debug("added cookie to response");
    }

    public static void removeLoginCookie(HttpServletResponse response) {
        // try to put cookie to track session

        Cookie loginCookie = new Cookie(Constants.KEYS_LOGIN_COOKIE_NAME, "");
        response.addCookie(loginCookie);
    }

    private static HttpSession getSessionForLogin(
            HttpServletRequest currentRequest) {
        logger.debug("getSessionForLogin");
        HttpSession httpSession = getCurrentSession(currentRequest);
        // disable session regeneration because of IE issue that keeps the old
        // session
        // TODO we need to enable it and test it properly
        // log.debug("Invalidate session ", httpSession.getId());
        // httpSession.invalidate();
        //
        // httpSession = currentRequest.getSession(true);
        return httpSession;
    }

}
