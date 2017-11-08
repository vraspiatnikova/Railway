package com.tsystems.jschool.railway;

import com.tsystems.jschool.railway.dao.interfaces.WaypointDao;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Waypoint;
import com.tsystems.jschool.railway.services.implementations.WaypointServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WaypointServiceImplTest {

    private Waypoint waypoint;

    @Mock
    private WaypointDao waypointDao;

    @InjectMocks
    private WaypointServiceImpl waypointService;

    @Before
    public void setup(){
        waypoint = new Waypoint();
        waypoint.setId(1);
    }

    @Test
    public void testFindWaypointById() throws DaoException, ServiceException {
        when(waypointDao.findById(1)).thenReturn(waypoint);
        waypointService.findWaypointById(waypoint.getId());
        verify(waypointDao).findById(waypoint.getId());
        Assert.assertTrue(true);
    }

    @Test
    public void testFindWaypointByStationName() throws DaoException, ServiceException {
        //define
        when(waypointDao.findWaypointByStationName("TestStation")).thenReturn(new ArrayList<>());
        //test
        waypointDao.findWaypointByStationName("TestStation");
        //check
        verify(waypointDao).findWaypointByStationName("TestStation");
    }
}
