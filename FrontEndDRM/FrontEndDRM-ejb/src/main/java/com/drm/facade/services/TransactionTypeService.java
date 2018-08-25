/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.TrxTypeMsg;
import com.drm.model.entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mohammed.ayad
 */
@Local
public interface TransactionTypeService extends DataService {

    List<TrxTypeMsg> getAllTransactionTypes();

    void deleteTransactionType(User currentUser, TrxTypeMsg selectedTrxType);

    void updateTrxTypeInfo(TrxTypeMsg updatedTrxType);

    boolean isTrxTypeExistBefore(String trxType);

    void addNewTrxType(TrxTypeMsg newTrxType);

}
