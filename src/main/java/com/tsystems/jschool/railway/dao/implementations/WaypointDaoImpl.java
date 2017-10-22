package com.tsystems.jschool.railway.dao.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorDao;
import com.tsystems.jschool.railway.dao.interfaces.WaypointDao;
import com.tsystems.jschool.railway.persistence.Waypoint;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class WaypointDaoImpl extends GenericDaoImpl<Waypoint> implements WaypointDao {

    @Override
    public Waypoint findWaypointByStationNameAndRouteId(String stationName, int routeId) throws DaoException {
        try {
            return em.createNamedQuery("Waypoint.findWaypointByStationNameAndRouteId", Waypoint.class)
                    .setParameter("stationName", stationName)
                    .setParameter("routeId", routeId)
                    .getSingleResult();
        } catch (NoResultException nre){
            return null;
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    public List<Waypoint> findWaypointByStationName(String stationName) throws DaoException {
        try {
            return em.createNamedQuery("Waypoint.findWaypointByStationName", Waypoint.class)
                    .setParameter("stationName", stationName)
                    .getResultList();
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }
}
