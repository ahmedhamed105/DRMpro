package org.drm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("drmDao")
public interface DRMDAO <T> {
	
	public void insert ( T baseObject) ;
	public List findByProperty(String propertyName ,Object value,Class<T> entityClass);
	public T findByID(long id , Class<T> entityClass);
	public void update( T entity );
	public void delete( T entity );
	 public void deleteById( Long entityId , Class entityClass );
	

}
