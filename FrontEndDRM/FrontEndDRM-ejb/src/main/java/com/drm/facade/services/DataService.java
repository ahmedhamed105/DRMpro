/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.drm.facade.services;

import com.drm.model.entities.AbstractEntity;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author mohammed.ayad
 */
public interface DataService {

    public static String PU_NAME = "com.drm_FrontEndDRM-ejb_ejb_1.0-SNAPSHOTPU";

    public EntityManager getEntityManager();

    public <T extends AbstractEntity> T insert(T entity);

    public <T extends AbstractEntity> void update(T entity);

    public <T extends AbstractEntity> void remove(T entity);

    public <T extends AbstractEntity> void approve(T entity);

    public <T extends AbstractEntity> void reject(T entity);

    public <T extends AbstractEntity> void discard(T entity);

    /**
     * This method relies on {@link #remove(AbstractEntity)}
     *
     * @param clas
     * @param id
     * @return
     */
    public <T extends AbstractEntity, Y> T delete(Class<T> clas, Y id);

    public <T extends AbstractEntity, Y> T find(Class<T> clas, Y id);

    public <T extends AbstractEntity> List<T> findByExample(T example);

    public <T extends AbstractEntity> List<T> getResult(String query,
            Object... params);

    public <T extends AbstractEntity> List<T> getNamedQueryResult(
            String namedQuery, Object... params);

    public List<String> getNamedQueryColumnResult(String namedQuery, Object... params);

    <T extends AbstractEntity> T refresh(T entity);

    <T extends AbstractEntity> T merge(T entity);

    public <T extends AbstractEntity> T AddRecord(T entity);

}
