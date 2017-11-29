package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.persistence.User;

public interface PassengerService {

    Passenger findPassengerByUser(User user) throws ServiceException;

    Passenger findPassengerById(Integer id) throws ServiceException;

    Passenger updatePassenger(Passenger passenger) throws ServiceException;

    Passenger addPassenger(Passenger passenger) throws ServiceException;
}
