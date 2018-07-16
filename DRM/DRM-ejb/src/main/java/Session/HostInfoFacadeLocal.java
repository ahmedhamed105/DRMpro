/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session;

import Entities.HostInfo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ahmed.elemam
 */
@Local
public interface HostInfoFacadeLocal {

    void create(HostInfo hostInfo);

    void edit(HostInfo hostInfo);

    void remove(HostInfo hostInfo);

    HostInfo find(Object id);

    List<HostInfo> findAll();

    List<HostInfo> findRange(int[] range);

    int count();
    
}
