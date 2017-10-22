package com.tsystems.jschool.railway.dao.interfaces;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.persistence.Station;

public interface StationDao extends GenericDao<Station> {
    Station findByName(String name) throws DaoException;
}
