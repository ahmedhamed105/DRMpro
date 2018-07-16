/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entities.LoginPrev;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ahmed.elemam
 */
@Stateless
public class LoginPrevFacade extends AbstractFacade<LoginPrev> implements LoginPrevFacadeLocal {

    @PersistenceContext(unitName = "com.guardianpro_DRM-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginPrevFacade() {
        super(LoginPrev.class);
    }
    
}
