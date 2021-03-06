package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.persistence.Train;
import com.tsystems.jschool.railway.exceptions.ServiceException;

import java.util.List;

public interface TrainService {

    Train addTrain(Train train) throws ServiceException;

    Train findTrainById(Integer id) throws ServiceException;

    List<Train> getAllTrains() throws ServiceException;

    void deleteTrain(Train train) throws ServiceException;

    void updateTrain(Train train) throws ServiceException;
}
