package com.tsystems.jschool.railway.dao.interfaces;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.persistence.Waypoint;

import java.util.List;

public interface WaypointDao extends GenericDao<Waypoint> {
    Waypoint findWaypointByStationNameAndRouteId(String stationName, int routeId) throws DaoException;

    List<Waypoint> findWaypointByStationName(String stationName) throws DaoException;
}
