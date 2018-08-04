/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.TrxFieldsValues;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author mohammed.ayad
 */
@Local
public interface SearchService extends DataService {

    public Set<String> getAllTrxType();

    public Set<String> getAllFieldsName();
    
    public Set<String> getAllFieldsType();

    public List<TrxFieldsValues> getAdvancedSearchResult(String sql, List<Date> parmetersList);
    public List<TrxFieldsValues> getAllTransactions();

}
