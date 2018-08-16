/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.FieldType;
import com.drm.model.entities.TrxFields;
import java.util.List;

/**
 *
 * @author mohammed.ayad
 */
public interface TrxFieldsService extends DataService {

    List<TrxFields> getAllTrxFields();

    void updateFieldTypeInfo(TrxFields updatedTrxField);

    boolean isTrxFieldNameExistBefore(String trxFieldName);

    void addNewTrxField(TrxFields newTrxField);

}
