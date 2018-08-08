/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.servicesImpl;

import com.drm.facade.services.AbstractService;
import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.facade.services.MenuService;
import com.drm.model.entities.Menu;
import com.drm.utils.Logger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mohammed.ayad
 */
@Stateless
@Local(MenuService.class)
public class MenuServiceImpl extends AbstractService implements MenuService {

    private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);

    @PersistenceContext(unitName = PU_NAME)
    private EntityManager entityManager;
    
    @PostConstruct
    private void init() {
        logger.debug("MenuService has been initilized");

    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<Menu> getUserRootMenus() {
        logger.debug("getUserRootMenus");
        List<Menu> menus = getAllMenus();
        List<Menu> rootMenus = new ArrayList<>();
        logger.debug("All Menus " + menus);
        for (Menu menu : menus) {
            if (menu.getLink().trim().equalsIgnoreCase("root")) {
                rootMenus.add(menu);
            }

        }
        logger.debug("Root Menus " + rootMenus);
        return rootMenus;
    }

    @Override
    public List<Menu> getAllMenus() {
        logger.debug("getAllMenus");
        List<Menu> result = getNamedQueryResult(Menu.FIND_BY_MODEL_TYPE, "DRM");
        logger.debug(result);
        return result;
    }
}
