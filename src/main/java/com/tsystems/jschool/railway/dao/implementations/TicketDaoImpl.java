package com.tsystems.jschool.railway.dao.implementations;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorDao;
import com.tsystems.jschool.railway.dao.interfaces.TicketDao;
import com.tsystems.jschool.railway.persistence.Board;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.persistence.Ticket;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TicketDaoImpl extends GenericDaoImpl<Ticket> implements TicketDao {

    @Override
    public List<Ticket> findTicketsByBoard(Board board) throws DaoException {
        try {
            TypedQuery<Ticket> q = em.createNamedQuery("Ticket.findTicketsByBoard", Ticket.class);
            q.setParameter("board", board);
            return q.getResultList();
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    public List<Ticket> findTicketsByPassenger(Passenger passenger) throws DaoException{
        try {
            TypedQuery<Ticket> q = em.createNamedQuery("Ticket.findTicketsByPassenger", Ticket.class);
            q.setParameter("passenger", passenger);
            return q.getResultList();
        } catch (Exception e) {
            throw new DaoException(ErrorDao.DATABASE_EXCEPTION, e);
        }
    }

}
