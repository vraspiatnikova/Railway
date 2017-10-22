package com.tsystems.jschool.railway.dao.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorDao;
import com.tsystems.jschool.railway.dao.interfaces.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDaoImpl<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;
    protected Class entityClass;

    public GenericDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class) genericSuperclass
                .getActualTypeArguments()[0];
    }

    @Override
    public T create(T t) throws DaoException {
        try {
            em.persist(t);
            return t;
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    public T update(T t) throws DaoException{
        try {
            em.merge(t);
            return t;
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    public void delete(T t) throws DaoException{
        try {
            t = em.merge(t);
            em.remove(t);
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    public T findById(Integer id) throws DaoException{
        try {
            return (T) em.find(entityClass, id);
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    public List<T> findAll()throws DaoException{
        try {
            TypedQuery<T> query = em.createQuery("from " + entityClass.getName(), entityClass);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }
}
