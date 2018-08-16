/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.FieldType;
import java.util.List;

/**
 *
 * @author mohammed.ayad
 */
public interface FieldsTypeService extends DataService {

    List<FieldType> getAllFieldsType();

    void updateFieldTypeInfo(FieldType updatedFieldType);

}
