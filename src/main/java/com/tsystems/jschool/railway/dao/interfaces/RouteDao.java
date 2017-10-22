package com.tsystems.jschool.railway.dao.interfaces;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.persistence.Route;

public interface RouteDao extends GenericDao<Route> {
    Route findByNumber(String routeNumber) throws DaoException;
}
