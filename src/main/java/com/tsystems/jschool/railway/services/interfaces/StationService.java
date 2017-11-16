package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StationService {

    Station addStation(Station station) throws ServiceException;

    List<Station> getAllStations() throws ServiceException;

    @Transactional
    Station findStationById(Integer id) throws ServiceException;

    @Transactional
    void updateStation(Station station) throws ServiceException;

    @Transactional
    void deleteStation(Station station) throws ServiceException;

//    Station getStationById(Integer id) throws ServiceException;
}
