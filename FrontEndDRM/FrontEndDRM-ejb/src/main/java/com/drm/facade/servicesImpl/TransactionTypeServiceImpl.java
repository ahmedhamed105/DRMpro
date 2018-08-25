/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.TransactionTypeService;
import com.drm.model.entities.TransactionHistory;
import com.drm.model.entities.Trx;
import com.drm.model.entities.TrxFieldsValues;
import com.drm.model.entities.TrxTypeMsg;
import com.drm.model.entities.User;
import com.drm.utils.Logger;
import java.util.Collection;
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
@Local(TransactionTypeService.class)
public class TransactionTypeServiceImpl extends AbstractService implements TransactionTypeService {

    private static final Logger logger = Logger.getLogger(TransactionTypeServiceImpl.class);

    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;

    @PostConstruct
    private void init() {
        logger.debug("TransactionTypeServiceImpl has been initilized");
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<TrxTypeMsg> getAllTransactionTypes() {
        List<TrxTypeMsg> transactionTypeList = getNamedQueryResult(TrxTypeMsg.TRX_TYPE_FIND_ALL, null);
        return transactionTypeList;
    }

    @Override
    public void deleteTransactionType(User currentUser, TrxTypeMsg selectedTrxType) {
        saveDeletedTrxHistory(currentUser, selectedTrxType);
        remove(selectedTrxType);
    }

    private void saveDeletedTrxHistory(User currentUser, TrxTypeMsg dletedTrxType) {
        logger.debug("saveDeletedTrxHistory----->>>Transaction Type " + dletedTrxType.getTtype());
//        List<Trx> trxListResult = getNamedQueryResult(Trx.TRX_FIND_ALL_BY_TRX_TYPE, dletedTrxType.getId());

        Collection<Trx> trxListResult = dletedTrxType.getTrxCollection();
        logger.debug("saveDeletedTrxHistory----->>>Transactions List size " + trxListResult.size());
        for (Trx trx : trxListResult) {
            logger.debug("saveDeletedTrxHistory----->>>Transaction Number " + trx.getTRXnumber());
            Collection<TrxFieldsValues> trxFieldsValuesListResult = trx.getTrxFieldsValuesCollection();
            logger.debug("saveDeletedTrxHistory----->>>Transaction Fields Values List size " + trxFieldsValuesListResult.size());
            for (TrxFieldsValues trxFieldsValues : trxFieldsValuesListResult) {
                TransactionHistory transactionHistory = new TransactionHistory();
                transactionHistory.setCreateDate(new Date());
                transactionHistory.setUpdateDate(new Date());
                transactionHistory.setUserId(currentUser);
                transactionHistory.setFieldName(trxFieldsValues.getTRXfieldsID().getFName());
                transactionHistory.setFieldDescription(trxFieldsValues.getTRXfieldsID().getFDescription());
                transactionHistory.setFieldType(trxFieldsValues.getTRXfieldsID().getFieldtypeID().getFtype());
                transactionHistory.setFieldPattern(trxFieldsValues.getTRXfieldsID().getFieldtypeID().getFpaterren());
                transactionHistory.setFieldValue(trxFieldsValues.getTRXValuesID().getFValue());
                transactionHistory.setTransactionType(trxFieldsValues.getTrxId().getTRXtypemsgID().getTtype());
                transactionHistory.setTransactionNumber(trxFieldsValues.getTrxId().getTRXnumber());
                transactionHistory.setTerminalId(trxFieldsValues.getTrxId().getTerminalID());
                insert(transactionHistory);

            }

        }

    }

    @Override
    public void updateTrxTypeInfo(TrxTypeMsg updatedTrxType) {
        updatedTrxType.setUpdateDate(new Date());
        update(updatedTrxType);
    }

    @Override
    public boolean isTrxTypeExistBefore(String trxType) {
        List<TrxTypeMsg> trxTypeList = getNamedQueryResult(TrxTypeMsg.TRX_TYPE_FIND_ALL_BY_TYPE, trxType);
        if (trxTypeList.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void addNewTrxType(TrxTypeMsg newTrxType) {
        newTrxType.setCreateDate(new Date());
        newTrxType.setUpdateDate(new Date());
        insert(newTrxType);
    }

}
