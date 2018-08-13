/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.ApplicationUser;
import java.util.List;

/**
 *
 * @author mohammed.ayad
 */
public interface ApplicationUserService extends DataService {

    List<ApplicationUser> getAllApplicationUser();

    public List<String> getAllAvaiableUserNames();

    public List<String> getAllAvaiableApplicationCodes();

    void updateApplicationUserInfo(ApplicationUser applicationUser,String updatedUserName,String updatedApplicationCode);

    void addNewApplicationUser(ApplicationUser newApplicationUser,String selectedUserName, String selectedApplicationCode);

    boolean isApplicationUserExistBefore(String... params);

}
