package com.tsystems.jschool.railway.dao.interfaces;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.persistence.User;

public interface PassengerDao extends GenericDao<Passenger> {
    Passenger findPassengerByUserInfo(User user) throws DaoException;
}
