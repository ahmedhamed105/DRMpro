/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.Applications;
import java.util.List;

/**
 *
 * @author mohammed.ayad
 */
public interface ApplicationsService extends DataService{
    List<Applications> getAllApplications();
    void addNewApplication(Applications application);
    void updateApplicationInfo(Applications application);
    List<String> getAllApplicationCode();
    List<Applications> getApplicationByApplicationCode(String applicationCode);
    
}
