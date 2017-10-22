package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.TrainDao;
import com.tsystems.jschool.railway.persistence.Train;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    private static final Logger LOGGER = Logger.getLogger(TrainServiceImpl.class);
    private final TrainDao trainDao;

    @Autowired
    public TrainServiceImpl(TrainDao trainDao) {
        this.trainDao = trainDao;
    }

    @Override
    @Transactional
    public Train addTrain(Train train) throws ServiceException {
        LOGGER.info("try to add train with name " + train.getName() + " and capacity " + train.getCapacity());
        Train result;
        try {
            if (trainDao.findByName(train.getName()) != null) throw new ServiceException(ErrorService.DUPLICATE_TRAIN);
            result = trainDao.create(train);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return result;
    }

    @Override
    @Transactional
    public List<Train> getAllTrains() throws ServiceException {
        LOGGER.info("try to get all trains");
        List<Train> listOfTrains;
        try {
            listOfTrains = trainDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return listOfTrains;
    }

    @Override
    @Transactional
    public Train findTrainById(Integer id) throws ServiceException {
        LOGGER.info("try to find train by id (" + id + ")");
        Train train;
        try {
            train = trainDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return train;
    }
}
