package com.drm.facade.services;

import static com.drm.facade.services.DataService.PU_NAME;
import com.drm.model.entities.AbstractEntity;
import com.drm.utils.Logger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

//@SuppressWarnAbstractEntityings("serial")
public abstract class AbstractService implements DataService {
//    @PersistenceContext(unitName = PU_NAME)
//    protected EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(AbstractService.class);

    @Override
    public abstract EntityManager getEntityManager();

    @Override
    public <T extends AbstractEntity> T insert(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    @Override
    public <T extends AbstractEntity> T AddRecord(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    @Override
    public <T extends AbstractEntity> T merge(T entity) {
        T merge = getEntityManager().merge(entity);
        return merge;
    }

    @Override
    public <T extends AbstractEntity> T refresh(T entity) {
        T merge = merge(entity);
        getEntityManager().refresh(merge);

        return merge;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.its.ethix.payhub.services.DataService#update(T)
     */
    public <T extends AbstractEntity> void update(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public <T extends AbstractEntity> void remove(T entity) {
        if (entity instanceof AbstractEntity) {
            update(entity);
        } else {
            getEntityManager().remove(entity);
        }
    }

    public <T extends AbstractEntity> void approve(T entity) {
        update(entity);
    }

    public <T extends AbstractEntity> void reject(T entity) {
        update(entity);
    }

    public <T extends AbstractEntity> void discard(T entity) {
        update(entity);
    }

    public <T extends AbstractEntity, Y> T delete(Class<T> clas, Y id) {
        T entity = find(clas, id);
        remove(entity);
        return entity;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.its.ethix.payhub.services.DataService#find(java
	 * .lang .Class, Y)
     */
    @Override
    public <T extends AbstractEntity, Y> T find(Class<T> clas, Y id) {
        return getEntityManager().find(clas, id);
    }

    @Override
    public <T extends AbstractEntity> List<T> getResult(String query,
            Object... params) {
        Query jpaQuery = getEntityManager().createQuery(query);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                Object object = params[i];

                if (object instanceof Date) {
                    Date searchDate = (Date) object;
                    //TODO We need to set the temporal type based on the entity bean setup not fixed as here
                    jpaQuery.setParameter(i + 1, searchDate, TemporalType.TIMESTAMP);
                    System.out.println("----------->Date detected");

                } else {
                    jpaQuery.setParameter(i + 1, object);
                }
            }
        }

        return jpaQuery.getResultList();
    }

    @Override
    public <T extends AbstractEntity> List<T> getNamedQueryResult(
            String namedQueryName, Object... params) {
        Query namedQuery = getEntityManager().createNamedQuery(namedQueryName);
//        log.debug(Arrays.asList(params));
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                namedQuery.setParameter(i + 1, params[i]);
            }
        }
        List<T> resultList = namedQuery.getResultList();
        return resultList;
    }

    @Override
    public List<String> getNamedQueryColumnResult(
            String namedQueryName, Object... params) {
        Query namedQuery = getEntityManager().createNamedQuery(namedQueryName);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                namedQuery.setParameter(i + 1, params[i]);
            }
        }
        List<String> resultList = namedQuery.getResultList();
        return resultList;
    }

    @Override
    public <T extends AbstractEntity> List<T> findByExample(T example) {

        if (true) {
            throw new UnsupportedOperationException(
                    "This method is not implemented yet");
        }

        if (example == null) {
            return Collections.EMPTY_LIST;
        }

        String jpql = "SELECT o from " + example.getClass().getName() + " o ";

        return null;
    }

}
