/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.model.entities.FieldType;
import com.drm.utils.Logger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.drm.facade.services.TrxFieldsService;
import com.drm.model.entities.TrxFields;

/**
 *
 * @author mohammed.ayad
 */
@Stateless
@Local(TrxFieldsService.class)
public class TrxFieldsServiceImpl extends AbstractService implements TrxFieldsService {

    private static final Logger logger = Logger.getLogger(TrxFieldsServiceImpl.class);
    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;

    @PostConstruct
    private void init() {
        logger.debug("FieldsTypeServiceImpl has been initilized");
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<TrxFields> getAllTrxFields() {
        List<TrxFields> trxfieldsList = getNamedQueryResult(TrxFields.TRX_FIELDS_FIND_ALL, null);
        return trxfieldsList;
    }

    @Override
    public void updateFieldTypeInfo(TrxFields updatedTrxField) {
        TrxFields originalTrxField = find(TrxFields.class, updatedTrxField.getId());
        if (!updatedTrxField.getFieldtypeID().getFtype().equals(originalTrxField.getFieldtypeID().getFtype())
                || !updatedTrxField.getFieldtypeID().getFpaterren().equals(originalTrxField.getFieldtypeID().getFpaterren())) {
            updatedTrxField.getFieldtypeID().setUpdateDate(new Date());
        }
        updatedTrxField.setUpdateDate(new Date());
        update(updatedTrxField);
    }

    @Override
    public boolean isTrxFieldNameExistBefore(String trxFieldName) {
        List<TrxFields> trxFieldList = getNamedQueryResult(TrxFields.TRX_FIELDS_FIND_BY_FIELD_NAME, trxFieldName);
        if (trxFieldList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addNewTrxField(TrxFields newTrxField) {
        List<FieldType> originalFieldTypeList = getNamedQueryResult(FieldType.FIELD_TYPE_FIND_BY_TYPE_AND_PATTERN, newTrxField.getFieldtypeID().getFtype(),
                newTrxField.getFieldtypeID().getFpaterren());
        if (originalFieldTypeList.size() > 0) {//field type exist before so attache it to newTrxField
            FieldType originalFieldType = originalFieldTypeList.get(0);
            newTrxField.setFieldtypeID(originalFieldType);

        } else {//field type not exist before
            newTrxField.getFieldtypeID().setCreateDate(new Date());
            newTrxField.getFieldtypeID().setUpdateDate(new Date());

        }
        newTrxField.setCreateDate(new Date());
        newTrxField.setUpdateDate(new Date());
        insert(newTrxField);

    }
}
