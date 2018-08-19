/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.DrmParameter;
import com.drm.model.entities.User;
import java.util.List;

/**
 *
 * @author mohammed.ayad
 */
public interface DrmParametersService extends DataService {

    List<DrmParameter> getAllDrmParameters();
    void updateParameterInfo(DrmParameter updateParameter);
    boolean isParameterNameExistBefore(String parameterName);
    void addNewParameter(DrmParameter newParameter,User user);

}
