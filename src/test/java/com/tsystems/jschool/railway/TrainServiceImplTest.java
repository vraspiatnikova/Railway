package com.tsystems.jschool.railway;

import com.tsystems.jschool.railway.dao.interfaces.BoardDao;
import com.tsystems.jschool.railway.dao.interfaces.TrainDao;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Train;
import com.tsystems.jschool.railway.services.implementations.TrainServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceImplTest {

    private Train existTrain;
    private Train newTrain;

    @Mock
    private TrainDao trainDao;

    @Mock
    private BoardDao boardDao;

    @InjectMocks
    private TrainServiceImpl trainService;

    @Before
    public void setup(){
        existTrain = new Train("ExistTrain", 20);
        newTrain = new Train("NewTrain", 20);
        newTrain.setId(1);
    }

    @Test
    public void testGetAllTrains() throws DaoException, ServiceException {
        //define
        when(trainDao.findAll()).thenReturn(new ArrayList<>());
        //test
        trainService.getAllTrains();
        //check
        verify(trainDao).findAll();
    }

    @Test(expected = ServiceException.class)
    public void testAddExistTrain() throws DaoException, ServiceException {
        when(trainDao.findByName(existTrain.getName())).thenReturn(existTrain);
        trainService.addTrain(existTrain);
    }

    @Test
    public void testAddNewTrain() throws DaoException, ServiceException {
        when(trainDao.create(newTrain)).thenReturn(newTrain);
        assertEquals(newTrain, trainService.addTrain(newTrain));
    }

    @Test
    public void testFindTrainById() throws DaoException, ServiceException {
        when(trainDao.findById(1)).thenReturn(newTrain);
        trainService.findTrainById(newTrain.getId());
        verify(trainDao).findById(newTrain.getId());
        Assert.assertTrue(true);
    }

    @Test
    public void testDeleteTrain() throws DaoException, ServiceException{
        trainService.deleteTrain(newTrain);
        when(trainDao.findById(newTrain.getId())).thenReturn(null);
    }

    @Test
    public void testUpdateTrain() throws DaoException, ServiceException{
        newTrain.setName("UpdateTrain");
        trainDao.update(newTrain);
        when(trainDao.findByName("UpdateTrain")).thenReturn(newTrain);
    }
}
