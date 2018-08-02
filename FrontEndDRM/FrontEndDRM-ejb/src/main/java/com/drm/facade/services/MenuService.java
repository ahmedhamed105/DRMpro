/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.Menu;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author mohammed.ayad
 */
@Local
public interface MenuService extends DataService{
    public List<Menu> getUserRootMenus();
    public List<Menu> getAllMenus();
    
}
