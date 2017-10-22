package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.WaypointDao;
import com.tsystems.jschool.railway.persistence.Waypoint;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.List;

@Service
public class WaypointServiceImpl implements WaypointService {

    private static final Logger LOGGER = Logger.getLogger(WaypointServiceImpl.class);
    private final WaypointDao waypointDao;

    @Autowired
    public WaypointServiceImpl(WaypointDao waypointDao) {
        this.waypointDao = waypointDao;
    }

    @Override
    @Transactional
    public Waypoint findWaypointById(Integer id) throws ServiceException {
        LOGGER.info("try to find waypoint by id (" + id + ")");
        Waypoint waypoint;
        try {
            waypoint = waypointDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return waypoint;
    }

    @Override
    @Transactional
    public List<Waypoint> findWaypointByStationName(String stationName) throws ServiceException {
        LOGGER.info("try to get all waypoints by station name (" + stationName + ")");
        List<Waypoint> listOfwaypoints;
        try {
            listOfwaypoints = waypointDao.findWaypointByStationName(stationName);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return listOfwaypoints;
    }
}
