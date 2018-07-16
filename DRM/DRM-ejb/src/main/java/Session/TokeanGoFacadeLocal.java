/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entities.TokeanGo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ahmed.elemam
 */
@Local
public interface TokeanGoFacadeLocal {

    void create(TokeanGo tokeanGo);

    void edit(TokeanGo tokeanGo);

    void remove(TokeanGo tokeanGo);

    TokeanGo find(Object id);

    List<TokeanGo> findAll();

    List<TokeanGo> findRange(int[] range);

    int count();
    
}
