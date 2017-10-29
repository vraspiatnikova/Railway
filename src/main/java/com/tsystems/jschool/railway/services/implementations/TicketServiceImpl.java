package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.BoardDao;
import com.tsystems.jschool.railway.dao.interfaces.PassengerDao;
import com.tsystems.jschool.railway.dao.interfaces.TicketDao;
import com.tsystems.jschool.railway.dao.interfaces.WaypointDao;
import com.tsystems.jschool.railway.persistence.*;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = Logger.getLogger(TicketServiceImpl.class);
    private final TicketDao ticketDao;
    private final BoardDao boardDao;
    private final WaypointDao waypointDao;
    private final PassengerDao passengerDao;

    @Autowired
    public TicketServiceImpl(TicketDao ticketDao, BoardDao boardDao, WaypointDao waypointDao, PassengerDao passengerDao) {
        this.ticketDao = ticketDao;
        this.boardDao = boardDao;
        this.waypointDao = waypointDao;
        this.passengerDao = passengerDao;
    }

    @Override
    @Transactional
    public Ticket buyTicket(Passenger passenger, int boardId, int wpFromId, int wpToId, Date startDate) throws ServiceException {
        LOGGER.info("passenger with passport " + passenger.getPassport() + " tries to buy a ticket");
        try{
            Board board = boardDao.findById(boardId);
            if (board == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);

            Waypoint wpFrom = waypointDao.findById(wpFromId);
            if (wpFrom == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);

            Waypoint wpTo = waypointDao.findById(wpToId);
            if (wpTo == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);

            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date());
            long diff = c1.getTimeInMillis() - c2.getTimeInMillis();
            if (diff / 60000 < 10) throw new ServiceException(ErrorService.LESS_THAN_10_MIN_LEFT);

            Train train = board.getTrain();
            if (train.getCapacity() <= board.getTickets().size()) throw new ServiceException(ErrorService.NO_AVAILABLE_TICKETS);

            List<Ticket> tickets = ticketDao.findTicketsByBoard(board);
            for (Ticket ticket : tickets) {
                if (ticket.getPassenger().equals(passenger)) throw new ServiceException(ErrorService.DUPLICATE_PASSENGER);
            }
            Passenger pass = passengerDao.findPassengerByUserInfo(passenger.getUserInfo());
            if (pass == null) {
                pass = passengerDao.create(passenger);
            }
            Ticket ticket = new Ticket();
            ticket.setPassenger(pass);
            ticket.setBoard(board);
            ticket.setWaypointFrom(wpFrom);
            ticket.setWaypointTo(wpTo);
            ticket.setPrice();
            return ticketDao.create(ticket);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public List<Ticket> findTicketsByPassenger(Passenger passenger) throws ServiceException {
        LOGGER.info("try to get all tickets by passenger");
        List<Ticket> ticketList;
        try {
            ticketList = ticketDao.findTicketsByPassenger(passenger);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return ticketList;
    }

    @Override
    @Transactional
    public List<SuitableTripDto> findAllTicketsByUser(User user) throws ServiceException {
        try {
            Passenger passenger = passengerDao.findPassengerByUserInfo(user);
            if (passenger == null) throw new ServiceException(ErrorService.NO_PASSENGER_BY_USER);
            List<SuitableTripDto> tickets = new ArrayList<>();
            for(Ticket ticket : findTicketsByPassenger(passenger)) {
                tickets.add(constructTicket(ticket));
            }

            return tickets;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    private SuitableTripDto constructTicket(Ticket ticket) throws ServiceException {
        SuitableTripDto ticketDto = new SuitableTripDto();
        Board board = ticket.getBoard();
        ticketDto.setTrainName(board.getTrain().getName());
        Route route = board.getRoute();
        String routeName = route.findFirstWaypoint().getStation().getName()+" - "+route.findLastWaypoint().getStation().getName();
        ticketDto.setRoute(routeName);
        ticketDto.setStationFrom(ticket.getWaypointFrom().getStation().getName());
        ticketDto.setStationTo(ticket.getWaypointTo().getStation().getName());
        ticketDto.setDepatureDateTime(ticket.getWaypointTo().departureDateTime(board.getDateTime()));
        ticketDto.setArrivalDateTime(ticket.getWaypointFrom().arrivalDateTime(board.getDateTime()));
        ticketDto.setPrice(ticket.getPrice());
        return ticketDto;
    }
}
