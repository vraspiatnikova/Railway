package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.dto.NewRouteDto;
import com.tsystems.jschool.railway.dto.RouteDto;
import com.tsystems.jschool.railway.dto.SaveRouteDto;
import com.tsystems.jschool.railway.persistence.Route;
import com.tsystems.jschool.railway.exceptions.ServiceException;

import java.util.List;

public interface RouteService {

    NewRouteDto getNewRoute() throws ServiceException;

    void saveRoute(SaveRouteDto saveRouteDto) throws ServiceException;

    List<Route> getAllRoutes() throws ServiceException;

    RouteDto constructRouteDto(Route route);

    List<RouteDto> getAllRoutesDto() throws ServiceException;

    Route findRouteById(Integer id) throws ServiceException;

    void updateRoute(Route route) throws ServiceException;

    void deleteRoute(Route route) throws ServiceException;
}
