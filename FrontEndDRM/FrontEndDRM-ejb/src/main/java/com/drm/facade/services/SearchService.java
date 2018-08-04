/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.TrxFieldsValues;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mohammed.ayad
 */
@Local
public interface SearchService extends DataService {

    public List<String> getAllTrxType();

    public List<String> getAllFieldsName();
    
    public List<String> getAllFieldsType();

    public List<TrxFieldsValues> getAdvancedSearchResult(String sql, List<Date> parmetersList);

}
