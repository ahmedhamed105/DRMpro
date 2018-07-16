/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entities.LoginPrev;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ahmed.elemam
 */
@Local
public interface LoginPrevFacadeLocal {

    void create(LoginPrev loginPrev);

    void edit(LoginPrev loginPrev);

    void remove(LoginPrev loginPrev);

    LoginPrev find(Object id);

    List<LoginPrev> findAll();

    List<LoginPrev> findRange(int[] range);

    int count();
    
}
