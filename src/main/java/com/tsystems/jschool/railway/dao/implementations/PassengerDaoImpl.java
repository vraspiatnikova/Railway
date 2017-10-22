package com.tsystems.jschool.railway.dao.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorDao;
import com.tsystems.jschool.railway.dao.interfaces.PassengerDao;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.persistence.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class PassengerDaoImpl extends GenericDaoImpl<Passenger> implements PassengerDao {

    @Override
    public Passenger findPassengerByUserInfo(User user) throws DaoException {
        try {
            return em.createNamedQuery("Passenger.findPassengerByUserInfo", Passenger.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException nre){
            return null;
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }
}
