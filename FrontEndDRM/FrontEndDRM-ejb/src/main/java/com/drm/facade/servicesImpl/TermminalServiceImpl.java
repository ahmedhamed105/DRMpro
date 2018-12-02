/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.ReportService;
import com.drm.facade.services.SecurityService;
import com.drm.facade.services.TerminalService;
import com.drm.facade.services.UserService;
import com.drm.model.entities.DrmParameter;
import com.drm.model.entities.Reports;
import com.drm.model.entities.Terminal;
import com.drm.model.entities.User;
import com.drm.utils.Logger;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mohammed.ayad
 */
@Stateless
@Local(TerminalService.class)
public class TermminalServiceImpl extends AbstractService implements TerminalService {

    private static final Logger logger = Logger.getLogger(TermminalServiceImpl.class);
    @EJB
    private SecurityService securityService;

    @PostConstruct
    private void init() {
        logger.debug("UserService has been initilized");
    }

    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

  

    @Override
    public List<Terminal> getall() {
       Query user_username = entityManager.createNamedQuery("Terminal.findAll");
        try {
            List<Terminal> Report = (List<Terminal>) user_username.getResultList();
            return Report;
        } catch (Exception e) {
            return null;
        }
    
    }

    @Override
    public Terminal getbyid(int id) {
   Query user_username = entityManager.createNamedQuery("Terminal.findById");
        user_username.setParameter("id", id);
        try {
            Terminal Report = (Terminal) user_username.getSingleResult();
            return Report;
        } catch (Exception e) {
            return null;
        }
    
    }
    
    
    
      @Override
    public void updateParameterInfo(Terminal updatetid) {
        updatetid.setUpdateDate(new Date());
        update(updatetid);
    }

    @Override
    public boolean isParameterNameExistBefore(String tid) {
        List<Terminal> parameterList = getNamedQueryResult(Terminal.NAMED_QUERY_FIND_ALL_BY_Terminal_tid, tid);
        if (parameterList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addNewParameter(Terminal newParameter) {
        newParameter.setCreateDate(new Date());
        newParameter.setUpdateDate(new Date());
        insert(newParameter);
    }

}
