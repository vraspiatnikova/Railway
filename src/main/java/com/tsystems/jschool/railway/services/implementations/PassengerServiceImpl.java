package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dao.interfaces.PassengerDao;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.persistence.User;
import com.tsystems.jschool.railway.services.interfaces.PassengerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PassengerServiceImpl implements PassengerService {
    private static final Logger LOGGER = Logger.getLogger(PassengerServiceImpl.class);
    private final PassengerDao passengerDao;

    @Autowired
    public PassengerServiceImpl(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
    }

    @Override
    @Transactional
    public Passenger findPassengerByUser(User user) throws ServiceException {
        LOGGER.info("try to find passenger by user " + user.getEmail());
        Passenger passenger;
        try {
            passenger = passengerDao.findPassengerByUserInfo(user);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return passenger;
    }

    @Override
    @Transactional
    public Passenger findPassengerById(Integer id) throws ServiceException {
        LOGGER.info("try to find passenger by id (" + id + ")");
        Passenger passenger;
        try {
            passenger = passengerDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return passenger;
    }

    @Override
    @Transactional
    public Passenger updatePassenger(Passenger passenger) throws ServiceException {
        LOGGER.info("try to update passenger with passport " + passenger.getPassport());
        Passenger result;
        try {
            result = passengerDao.update(passenger);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return result;
    }

    @Override
    @Transactional
    public Passenger addPassenger(Passenger passenger) throws ServiceException {
        LOGGER.info("try to add passenger with name " + passenger.getFirstName()+ " " + passenger.getLastName() + " and passport " + passenger.getPassport());
        Passenger result;
        try {
            result = passengerDao.create(passenger);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return result;
    }
}
