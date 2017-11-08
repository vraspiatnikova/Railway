package com.tsystems.jschool.railway;

import com.tsystems.jschool.railway.dao.interfaces.StationDao;
import com.tsystems.jschool.railway.dao.interfaces.WaypointDao;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.services.implementations.StationServiceImpl;
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
public class StationServiceImplTest {

    Station existStation;
    Station newStation;

    @Mock
    private StationDao stationDao;

    @InjectMocks
    private StationServiceImpl stationService;

    @Before
    public void setup(){
        existStation = new Station("ExistStation");
        newStation = new Station("NewStation");
    }

    @Test
    public void testGetAllStations() throws DaoException, ServiceException {
        //define
        when(stationDao.findAll()).thenReturn(new ArrayList<>());
        //test
        stationService.getAllStations();
        //check
        verify(stationDao).findAll();
    }

    @Test(expected = ServiceException.class)
    public void testAddExistStation() throws DaoException, ServiceException {
        when(stationDao.findByName(existStation.getName())).thenReturn(existStation);
        verify(stationService.addStation(existStation));
    }

    @Test
    public void testAddNewStation() throws DaoException, ServiceException {
        when(stationDao.create(newStation)).thenReturn(newStation);
        assertEquals(newStation, stationService.addStation(newStation));
    }
}
