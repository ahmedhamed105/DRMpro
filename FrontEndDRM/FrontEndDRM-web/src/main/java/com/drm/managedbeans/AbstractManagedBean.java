/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.managedbeans;

import com.drm.model.entities.User;
import com.drm.utils.DrmUtils;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mohammed.ayad
 */
public abstract class AbstractManagedBean implements Serializable {

    public abstract void auditAction(String actionResult, String actionValue);

    public User getCurrentLoggedInUser() {
        HttpServletRequest currentRequest = getCurrentRequest();
        User currentUser = DrmUtils.getCurrentUser(currentRequest);
        return currentUser;
    }

    private HttpServletRequest getCurrentRequest() {
        HttpServletRequest currentRequest = (HttpServletRequest) JSFUtils
                .getExternalContext().getRequest();
        return currentRequest;
    }

}
