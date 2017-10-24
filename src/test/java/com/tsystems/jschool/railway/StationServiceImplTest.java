package com.tsystems.jschool.railway;

import com.tsystems.jschool.railway.dao.interfaces.StationDao;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.implementations.StationServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StationServiceImplTest {

    @Mock
    private StationDao stationDao;

    @InjectMocks
    private StationServiceImpl stationService;

    @Test
    public void testGetAllStations() throws DaoException, ServiceException {
        //define
        when(stationDao.findAll()).thenReturn(new ArrayList<>());
        //test
        stationService.getAllStations();
        //check
        verify(stationDao).findAll();
    }
}
