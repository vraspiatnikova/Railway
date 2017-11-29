package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.persistence.Station;
import com.tsystems.jschool.railway.exceptions.ServiceException;

import java.util.List;

public interface StationService {

    Station addStation(Station station) throws ServiceException;

    List<Station> getAllStations() throws ServiceException;

    Station findStationById(Integer id) throws ServiceException;

    void updateStation(Station station) throws ServiceException;

    void deleteStation(Station station) throws ServiceException;

}
