package com.tsystems.jschool.railway;

import com.tsystems.jschool.railway.dao.interfaces.RouteDao;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.Route;
import com.tsystems.jschool.railway.services.implementations.RouteServiceImpl;
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
public class RouteServiceImplTest {

    private Route existRoute;
    private Route newRoute;

    @Mock
    private RouteDao routeDao;


    @InjectMocks
    private RouteServiceImpl routeService;

    @Before
    public void setup(){
        existRoute = new Route("ExistRoute");
        newRoute = new Route("NewRoute");
        newRoute.setId(1);
    }

    @Test
    public void testGetAllRoutes() throws DaoException, ServiceException {
        //define
        when(routeDao.findAll()).thenReturn(new ArrayList<>());
        //test
        routeService.getAllRoutes();
        //check
        verify(routeDao).findAll();
    }

    @Test
    public void testFindRouteById() throws DaoException, ServiceException {
        when(routeDao.findById(1)).thenReturn(newRoute);
        routeService.findRouteById(newRoute.getId());
        verify(routeDao).findById(newRoute.getId());
        Assert.assertTrue(true);
    }

    @Test
    public void testNotFindRouteById() throws DaoException, ServiceException {
        when(routeDao.findById(-1)).thenReturn(null);
        Assert.assertEquals(null, routeService.findRouteById(-1));
    }

    @Test
    public void testDeleteRoute() throws DaoException, ServiceException{
        routeDao.delete(newRoute);
        when(routeDao.findById(newRoute.getId())).thenReturn(null);
    }

    @Test
    public void testUpdateRoute() throws DaoException, ServiceException{
        newRoute.setNumber("UpdateRoute");
        routeDao.update(newRoute);
        when(routeDao.findByNumber("UpdateRoute")).thenReturn(newRoute);
    }
}
