/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.FieldsTypeService;
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

/**
 *
 * @author mohammed.ayad
 */
@Stateless
@Local(FieldsTypeService.class)
public class FieldsTypeServiceImpl extends AbstractService implements FieldsTypeService {

    private static final Logger logger = Logger.getLogger(FieldsTypeServiceImpl.class);
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
    public List<FieldType> getAllFieldsType() {
        List<FieldType> fieldsTypeList = getNamedQueryResult(FieldType.FIELD_TYPE_FIND_ALL, null);
        return fieldsTypeList;
    }

    @Override
    public void updateFieldTypeInfo(FieldType updatedFieldType) {
        updatedFieldType.setUpdateDate(new Date());
        update(updatedFieldType);
    }
}
