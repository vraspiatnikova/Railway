package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dao.interfaces.BoardDao;
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
    private final BoardDao boardDao;

    @Autowired
    public TrainServiceImpl(TrainDao trainDao, BoardDao boardDao) {
        this.trainDao = trainDao;
        this.boardDao = boardDao;
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

    @Override
    @Transactional
    public void deleteTrain(Train train) throws ServiceException {
        LOGGER.info("try to delete train with name " + train.getName());
        try {
            if (!boardDao.findBoardByTrainName(train.getName()).isEmpty()){
                throw new ServiceException(ErrorService.CANNOT_DELETE_TRAIN);
            }
            trainDao.delete(train);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public void updateTrain(Train train) throws ServiceException {
        LOGGER.info("try to update train with name " + train.getName());
        try {
            if (trainDao.findByName(train.getName()) != null) throw new ServiceException(ErrorService.DUPLICATE_TRAIN);
            trainDao.update(train);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }
}
