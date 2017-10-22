package com.tsystems.jschool.railway.dao.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorDao;
import com.tsystems.jschool.railway.dao.interfaces.RouteDao;
import com.tsystems.jschool.railway.persistence.Route;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class RouteDaoImpl extends GenericDaoImpl<Route> implements RouteDao {

    @Override
    public Route findByNumber(String routeNumber) throws DaoException {
        try {
            return em.createNamedQuery("Route.findRouteByNumber", Route.class)
                    .setParameter("routeNumber", routeNumber)
                    .getSingleResult();
        } catch (NoResultException nre){
            return null;
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

}
