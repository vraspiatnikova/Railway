package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.persistence.Waypoint;
import com.tsystems.jschool.railway.exceptions.ServiceException;

import java.util.List;

public interface WaypointService {
    Waypoint findWaypointById(Integer id) throws ServiceException;
    List<Waypoint> findWaypointByStationName(String stationName) throws ServiceException;
}
