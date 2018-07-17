package org.drm.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("drmDAo")
@Transactional(propagation=Propagation.REQUIRED)
public class DRMDAOImpl  implements DRMDAO {
	
	@PersistenceContext
	public EntityManager entityManager;
	
	@Transactional(readOnly=false)
	public void insert(Object baseObject) {
		entityManager.persist(baseObject);	
	}

	public  List findByProperty(String propertyName, Object value , Class entityClass) {
		 String queryString = "from "+ entityClass.getName() + " sm where sm." +propertyName+"=:filedname";
		 Query query =  entityManager.createQuery(queryString);
		 query.setParameter("filedname", value);
		 return query.getResultList();
	}

	public Object findByID(long id, Class entityClass) {
		
		return entityManager.find(entityClass, id);
	}
	
	public void update( Object entity ){
	      entityManager.merge( entity );
	   }
	 
	   public void delete( Object entity ){
	      entityManager.remove( entity );
	   }
	   public void deleteById( Long entityId , Class entityClass){
	      Object entity = findByID( entityId , entityClass);
	      delete( entity );
	   }

	
	
	
	

}
