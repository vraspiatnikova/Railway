package com.tsystems.jschool.railway.dao.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorDao;
import com.tsystems.jschool.railway.dao.interfaces.TrainDao;
import com.tsystems.jschool.railway.persistence.Train;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class TrainDaoImpl extends GenericDaoImpl<Train> implements TrainDao {

    @Override
    public Train findByName(String trainName) throws DaoException {
        try {
            return em.createNamedQuery("Train.findTrainByName", Train.class)
                    .setParameter("trainName", trainName)
                    .getSingleResult();
        } catch (NoResultException nre){
            return null;
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }
}
