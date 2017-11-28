package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dto.NewRouteDto;
import com.tsystems.jschool.railway.dto.RouteDto;
import com.tsystems.jschool.railway.dto.SaveRouteDto;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.*;
import com.tsystems.jschool.railway.persistence.*;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Service
public class RouteServiceImpl implements RouteService {

    private static final Logger LOGGER = Logger.getLogger(RouteServiceImpl.class);
    private final StationDao stationDao;
    private final RouteDao routeDao;
    private final WaypointDao waypointDao;
    private final BoardDao boardDao;
    private final TicketDao ticketDao;

    @Autowired
    public RouteServiceImpl(StationDao stationDao, RouteDao routeDao, WaypointDao waypointDao, BoardDao boardDao, TicketDao ticketDao) {
        this.stationDao = stationDao;
        this.routeDao = routeDao;
        this.waypointDao = waypointDao;
        this.boardDao = boardDao;
        this.ticketDao = ticketDao;
    }

    @Override
    @Transactional
    public List<Route> getAllRoutes() throws ServiceException {
        LOGGER.info("try to get all routes");
        List<Route> listOfRoutes;
        try {
            listOfRoutes = routeDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return listOfRoutes;
    }

    @Override
    @Transactional
    public Route findRouteById(Integer id) throws ServiceException {
        LOGGER.info("try to find route by id (" + id + ")");
        Route route;
        try {
            route = routeDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return route;
    }

    @Override
    @Transactional
    public NewRouteDto getNewRoute() throws ServiceException {
        LOGGER.info("try to get newRouteDto");
        NewRouteDto newRouteDto = new NewRouteDto();
        try {
            List<Station> stations = stationDao.findAll();
            newRouteDto.setStations(stations);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return newRouteDto;
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void saveRoute(SaveRouteDto saveRouteDto) throws ServiceException {
        LOGGER.info("try to save new route");
        try {
            String routeNumber = saveRouteDto.getRouteNumber();
            if (routeDao.findByNumber(routeNumber) != null) throw new ServiceException(ErrorService.DUPLICATE_ROUTE);
            Route route = routeDao.create(new Route(routeNumber));
            for (int i = 0; i < saveRouteDto.getWaypointStations().size(); i++) {
                Station station = stationDao.findByName(saveRouteDto.getWaypointStations().get(i));
                Integer travelTime = saveRouteDto.getWaypointTravellTime().get(i);
                Integer travelStopTime = saveRouteDto.getWaypointTravelStopTime().get(i);

                if (station == null)
                    throw new ServiceException(ErrorService.STATION_NOT_EXIST);
                waypointDao.create(new Waypoint(station, route, travelTime, travelStopTime, i));
            }
            LOGGER.info("new route with id = " + route.getId() + " has been created");
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public RouteDto constructRouteDto(Route route) {
        RouteDto routeDto = new RouteDto();
        routeDto.setRouteNumber(route.getNumber());
        routeDto.setId(route.getId());
        StringBuilder routeWaypoints = new StringBuilder();
        List<Station> stations = new ArrayList<>();
        TreeSet<Waypoint> waypoints = new TreeSet<>(route.getWaypoints());
        for (Waypoint wp: waypoints){
            stations.add(wp.getStation());
        }
        for (Station station: stations){
            routeWaypoints.append(station.getName()).append(" ");
        }
        routeDto.setRouteWaypoints(routeWaypoints.toString().trim());
        return routeDto;
    }

    @Override
    @Transactional
    public List<RouteDto> getAllRoutesDto() throws ServiceException {
        List<RouteDto> routeDtos = new ArrayList<>();
        for(Route route : getAllRoutes()) {
            routeDtos.add(constructRouteDto(route));
        }
        return routeDtos;
    }

    @Override
    @Transactional
    public void updateRoute(Route route) throws ServiceException {
        LOGGER.info("try to update route with id (" + route.getId() + ")");
        try {
            List<Board> boards = boardDao.findBoardByRouteNumber(route.getNumber());
            for (Board board: boards){
                if (!ticketDao.findTicketsByBoard(board).isEmpty()){
                    throw new ServiceException(ErrorService.CANNOT_UPDATE_ROUTE);
                }
            }
            if (routeDao.findByNumber(route.getNumber()) != null) throw new ServiceException(ErrorService.DUPLICATE_ROUTE);
            routeDao.update(route);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public void deleteRoute(Route route) throws ServiceException {
        LOGGER.info("try to delete route with id (" + route.getId() + ")");
        try {
            if (!boardDao.findBoardByRouteNumber(route.getNumber()).isEmpty()){
                throw new ServiceException(ErrorService.CANNOT_DELETE_ROUTE);
            }
            routeDao.delete(route);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }
}
