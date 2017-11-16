package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dao.interfaces.WaypointDao;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.StationDao;
import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    private final StationDao stationDao;
    private final WaypointDao waypointDao;
    private static final Logger LOGGER = Logger.getLogger(StationServiceImpl.class);

    @Autowired
    public StationServiceImpl(StationDao stationDao, WaypointDao waypointDao) {
        this.stationDao = stationDao;
        this.waypointDao = waypointDao;
    }

    @Override
    @Transactional
    public Station addStation(Station station) throws ServiceException {
        LOGGER.info("try add station with name (" + station.getName() + ")");
        Station result;
        try {
            if (stationDao.findByName(station.getName()) != null) {
                throw new ServiceException(ErrorService.DUPLICATE_STATION);
            }
            result = stationDao.create(station);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return result;
    }

    @Override
    @Transactional
    public List<Station> getAllStations() throws ServiceException {
        LOGGER.info("try to get all stations");
        List<Station> listOfStations;
        try {
            listOfStations = stationDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return listOfStations;
    }

    @Override
    @Transactional
    public Station findStationById(Integer id) throws ServiceException {
        LOGGER.info("try to find station by id (" + id + ")");
        Station station;
        try {
            station = stationDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return station;
    }

    @Override
    @Transactional
    public void updateStation(Station station) throws ServiceException {
        LOGGER.info("try to update station with id (" + station.getId() + ")");
        try {
            stationDao.update(station);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public void deleteStation(Station station) throws ServiceException {
        LOGGER.info("try to delete station with id (" + + station.getId() + ")");
        try {
            if (!waypointDao.findWaypointByStationName(station.getName()).isEmpty()){
                throw new ServiceException(ErrorService.CANNOT_DELETE_STATION);
            }
            stationDao.delete(station);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }
}
