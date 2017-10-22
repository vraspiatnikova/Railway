package com.tsystems.jschool.railway.dao.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorDao;
import com.tsystems.jschool.railway.dao.interfaces.StationDao;
import com.tsystems.jschool.railway.persistence.Station;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class StationDaoImpl extends GenericDaoImpl<Station> implements StationDao {

    @Override
    public Station findByName(String stationName) throws DaoException {
        try {
            return em.createNamedQuery("Station.findStationByName", Station.class)
                    .setParameter("stationName", stationName)
                    .getSingleResult();
        } catch (NoResultException nre){
            return null;
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }
}
