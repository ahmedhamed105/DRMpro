/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.Reports;
import com.drm.model.entities.Terminal;
import com.drm.model.entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mohammed.ayad
 */
@Local
public interface TerminalService extends DataService {

    Terminal getbyid(int id);
    
    List<Terminal> getall();
    
    public void addNewParameter(Terminal newParameter);
    
    public boolean isParameterNameExistBefore(String tid) ;
    
    public void updateParameterInfo(Terminal updatetid) ;

}
