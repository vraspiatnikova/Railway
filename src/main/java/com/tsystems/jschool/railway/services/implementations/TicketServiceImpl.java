package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.BoardDao;
import com.tsystems.jschool.railway.dao.interfaces.PassengerDao;
import com.tsystems.jschool.railway.dao.interfaces.TicketDao;
import com.tsystems.jschool.railway.dao.interfaces.WaypointDao;
import com.tsystems.jschool.railway.mail.MailService;
import com.tsystems.jschool.railway.persistence.*;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private final MailService mailService;

    @Autowired
    public TicketServiceImpl(TicketDao ticketDao, BoardDao boardDao, WaypointDao waypointDao, PassengerDao passengerDao, MailService mailService) {
        this.ticketDao = ticketDao;
        this.boardDao = boardDao;
        this.waypointDao = waypointDao;
        this.passengerDao = passengerDao;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public Ticket findTicketById(Integer id) throws ServiceException {
        LOGGER.info("try to find ticket by id (" + id + ")");
        Ticket ticket;
        try {
            ticket = ticketDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return ticket;
    }

    @Override
    @Transactional
    public void deleteTicket(Ticket ticket) throws ServiceException {
        LOGGER.info("try to delete ticket with id (" + ticket.getId() + ")");
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Calendar calendar = Calendar.getInstance();
            Date date = format.parse(ticket.getWaypointFrom().arrivalDateTime(ticket.getBoard().getDateTime()));
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            if (calendar.getTime().before(new Date())){
                throw new ServiceException(ErrorService.CANNOT_CANCEL_TICKET);
            }
            ticketDao.delete(ticket);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.INCORRECT_DATE_FORMAT, e);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
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
            Ticket ticket = new Ticket();
            ticket.setPassenger(passenger);
            ticket.setBoard(board);
            ticket.setWaypointFrom(wpFrom);
            ticket.setWaypointTo(wpTo);
            ticket.setPrice();
            mailService.sendBuyTicketEmail(passenger, constructTicket(ticket));
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

    private SuitableTripDto constructTicket(Ticket ticket) {
        SuitableTripDto ticketDto = new SuitableTripDto();
        ticketDto.setTicketId(ticket.getId());
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
