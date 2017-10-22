package com.tsystems.jschool.railway.dao.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorDao;
import com.tsystems.jschool.railway.dao.interfaces.BoardDao;
import com.tsystems.jschool.railway.persistence.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class BoardDaoImpl extends GenericDaoImpl<Board> implements BoardDao {

    @Override
    public List<Board> findAllBoardsBetweenDates(Date dateTimeFrom, Date dateTimeTo) throws DaoException{
        try {
            TypedQuery<Board> query = em.createNamedQuery("Board.findBoardsBetweenDates", Board.class);
            query.setParameter("dateTimeFrom", dateTimeFrom);
            query.setParameter("dateTimeTo", dateTimeTo);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    public List<Board> findBoardByStationName(String stationName) throws DaoException{
        try {
            TypedQuery<Board> query = em.createNamedQuery("Board.findBoardByStationName", Board.class);
            query.setParameter("stationName", stationName);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }
}
