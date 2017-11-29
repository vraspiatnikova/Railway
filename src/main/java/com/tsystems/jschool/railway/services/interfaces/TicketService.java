package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.persistence.Ticket;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.persistence.User;

import java.util.Date;
import java.util.List;

public interface TicketService {

    Ticket findTicketById(Integer id) throws ServiceException;

    void deleteTicket(Ticket ticket) throws ServiceException;

    Ticket buyTicket(Passenger passenger, int boardId, int wpFromId, int wpToId, Date startDate) throws ServiceException;

    List<Ticket> findTicketsByPassenger(Passenger passenger) throws ServiceException;

    List<SuitableTripDto> findAllTicketsByUser(User user) throws ServiceException;
}
