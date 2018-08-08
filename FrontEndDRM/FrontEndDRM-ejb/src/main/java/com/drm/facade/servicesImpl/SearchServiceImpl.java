/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.SearchService;
import com.drm.model.entities.FieldType;
import com.drm.model.entities.TrxFields;
import com.drm.model.entities.TrxFieldsValues;
import com.drm.model.entities.TrxTypeMsg;
import com.drm.utils.Logger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@Local(SearchService.class)
public class SearchServiceImpl extends AbstractService implements SearchService {

    private static final Logger logger = Logger.getLogger(SearchServiceImpl.class);

    @PostConstruct
    private void init() {
        logger.debug("SearchService has been initilized");
    }

    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Set<String> getAllTrxType() {
        List<String> trxTypeList = getNamedQueryColumnResult(TrxTypeMsg.TRX_TYPE_FIND_ALL_TYPES, null);
        Set<String> unDuplicateTrxTypeList = new HashSet<String>(trxTypeList);
        return unDuplicateTrxTypeList;
    }

    @Override
    public Set<String> getAllFieldsName() {
        List<String> trxFieldsList = getNamedQueryColumnResult(TrxFields.TRX_FIELDS_FIND_ALL_NAMES, null);
        Set<String> unDuplicateTrxFieldsList = new HashSet<String>(trxFieldsList);
        return unDuplicateTrxFieldsList;
    }

    @Override
    public List<TrxFieldsValues> getAdvancedSearchResult(String sql, List<Date> parmetersList) {
        Date[] parameters = parmetersList.toArray(new Date[parmetersList.size()]);
        List<TrxFieldsValues> searchResult = getResult(sql, parameters);
        return searchResult;
    }

    @Override
    public Set<String> getAllFieldsType() {
        List<String> fieldsType = getNamedQueryColumnResult(FieldType.FIELD_TYPE_FIND_ALL_TYPES, null);
        Set<String> unDuplicateFieldsType = new HashSet<String>(fieldsType);
        return unDuplicateFieldsType;
    }

    @Override
    public List<TrxFieldsValues> getAllTransactions() {
        List<TrxFieldsValues> transactionsList = getNamedQueryResult(TrxFieldsValues.FIND_ALL_TRANSACTIONS, null);
        return transactionsList;
    }
}
