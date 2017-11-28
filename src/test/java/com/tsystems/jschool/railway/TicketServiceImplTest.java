package com.tsystems.jschool.railway;

import com.tsystems.jschool.railway.dao.interfaces.BoardDao;
import com.tsystems.jschool.railway.dao.interfaces.PassengerDao;
import com.tsystems.jschool.railway.dao.interfaces.TicketDao;
import com.tsystems.jschool.railway.dao.interfaces.WaypointDao;
import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.mail.MailService;
import com.tsystems.jschool.railway.persistence.*;
import com.tsystems.jschool.railway.persistence.roles.UserRole;
import com.tsystems.jschool.railway.services.implementations.TicketServiceImpl;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceImplTest {

    private Date startDate;
    private Date badStartDate;
    private Passenger passenger;
    private Board board;
    private Waypoint waypointFrom;
    private Waypoint waypointTo;
    private List<Ticket> tickets;
    private Ticket ticket;
    private List<SuitableTripDto> listTicketsDto;
    private SuitableTripDto ticketDto;
    private User user;

    @Mock
    private TicketDao ticketDao;

    @Mock
    private BoardDao boardDao;

    @Mock
    private WaypointDao waypointDao;

    @Mock
    private PassengerDao passengerDao;

    @Mock
    private MailService mailService;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Before
    public void setup(){
        user = new User("test@test.ru", "123456", UserRole.ROLE_USER);
        user.setId(1);

        DateTime dateTime = new DateTime(2000, 1, 30, 10, 0);
        Date birthdate = dateTime.toDate();
        passenger = new Passenger("Ivanov", "Ivan", birthdate, "1001123456", user);

        Train train = new Train("TestTrain", 2);
        train.setId(1);

        Route route = new Route("1");

        Station station1 = new Station("StationFrom");
        Station station2 = new Station("StationTo");
        waypointFrom = new Waypoint(station1, route, 0, 0, 0);
        waypointFrom.setId(2);
        waypointTo = new Waypoint(station2, route, 60, 60, 1);
        waypointTo.setId(3);
        Set<Waypoint> waypointSet = new TreeSet<>();
        waypointSet.add(waypointFrom);
        waypointSet.add(waypointTo);
        route.setWaypoints(waypointSet);

        board = new Board();
        board.setId(1);
        board.setTrain(train);
        board.setRoute(route);
        tickets = new ArrayList<>();
        board.setTickets(tickets);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, -5);
        badStartDate = c.getTime();
        c.add(Calendar.MINUTE, 20);
        startDate = c.getTime();
        board.setDateTime(startDate);

        ticket = new Ticket();
        ticket.setPassenger(passenger);
        ticket.setBoard(board);
        ticket.setWaypointFrom(waypointFrom);
        ticket.setWaypointTo(waypointTo);
        ticket.setPrice();

        ticketDto = new SuitableTripDto();
        ticketDto.setTrainName("TestTrain");
        ticketDto.setRoute("StationFrom - StationTo");
        ticketDto.setStationFrom("StationFrom");
        ticketDto.setStationTo("StationTo");
        ticketDto.setDepatureDateTime(ticket.getWaypointTo().departureDateTime(startDate));
        ticketDto.setArrivalDateTime(ticket.getWaypointFrom().arrivalDateTime(startDate));
        ticketDto.setPrice(ticket.getPrice());

        listTicketsDto = new ArrayList<>();
        listTicketsDto.add(ticketDto);
    }

    @Test
    public void testBuyTicketSuccess() throws DaoException, ServiceException {
        when(boardDao.findById(1)).thenReturn(board);
        when(waypointDao.findById(2)).thenReturn(waypointFrom);
        when(waypointDao.findById(3)).thenReturn(waypointTo);
        when(ticketDao.findTicketsByBoard(board)).thenReturn(tickets);
        when(ticketDao.create(ticket)).thenReturn(ticket);
        ticketService.buyTicket(passenger, 1, 2, 3, startDate);
        verify(ticketDao).create(ticket);
    }

    @Test(expected = ServiceException.class)
    public void testBuyTicketNoBoardFound() throws DaoException, ServiceException {
        try {
            when(boardDao.findById(1)).thenReturn(null);
            ticketService.buyTicket(passenger, 1, 2, 3, startDate);
        } catch (ServiceException e) {
            assertEquals(9, ErrorService.TRIP_NOT_EXIST.getId());
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void testBuyTicketNoWaypointFromFound() throws ServiceException, DaoException {
        try {
            when(boardDao.findById(1)).thenReturn(board);
            when(waypointDao.findById(2)).thenReturn(null);
            ticketService.buyTicket(passenger, 1, 2, 3, startDate);
        } catch (ServiceException e) {
            assertEquals(9, ErrorService.TRIP_NOT_EXIST.getId());
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void testBuyTicketNoWaypointToFound() throws ServiceException, DaoException {
        try {
            when(boardDao.findById(1)).thenReturn(board);
            when(waypointDao.findById(2)).thenReturn(waypointFrom);
            when(waypointDao.findById(3)).thenReturn(null);
            ticketService.buyTicket(passenger, 1, 2, 3, startDate);
        } catch (ServiceException e) {
            assertEquals(9, ErrorService.TRIP_NOT_EXIST.getId());
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void testBuyTicketLessTenMinutes() throws ServiceException, DaoException {
        try {
            when(boardDao.findById(1)).thenReturn(board);
            when(waypointDao.findById(2)).thenReturn(waypointFrom);
            when(waypointDao.findById(3)).thenReturn(waypointTo);
            when(ticketDao.findTicketsByBoard(board)).thenReturn(tickets);
            ticketService.buyTicket(passenger, 1, 2, 3, badStartDate);
        } catch (ServiceException e) {
            assertEquals(10, ErrorService.LESS_THAN_10_MIN_LEFT.getId());
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void testBuyTicketNoTickets() throws ServiceException, DaoException {
        try {
            when(boardDao.findById(1)).thenReturn(board);
            when(waypointDao.findById(2)).thenReturn(waypointFrom);
            when(waypointDao.findById(3)).thenReturn(waypointTo);
            tickets.add(new Ticket());
            tickets.add(new Ticket());
            when(ticketDao.findTicketsByBoard(board)).thenReturn(tickets);
            ticketService.buyTicket(passenger, 1, 2, 3, startDate);
        } catch (ServiceException e) {
            assertEquals(11, ErrorService.NO_AVAILABLE_TICKETS.getId());
            throw e;
        }
    }

    @Test(expected = ServiceException.class)
    public void testBuyTicketDuplicateTicket() throws ServiceException, DaoException {
        try {
            when(boardDao.findById(1)).thenReturn(board);
            when(waypointDao.findById(2)).thenReturn(waypointFrom);
            when(waypointDao.findById(3)).thenReturn(waypointTo);
            tickets.add(ticket);
            when(ticketDao.findTicketsByBoard(board)).thenReturn(tickets);
            ticketService.buyTicket(passenger, 1, 2, 3, startDate);
        } catch (ServiceException e) {
            assertEquals(6, ErrorService.DUPLICATE_PASSENGER.getId());
            throw e;
        }
    }

    @Test
    public void testFindTicketsByPassenger() throws ServiceException, DaoException {
        tickets.add(ticket);
        when(ticketDao.findTicketsByPassenger(passenger)).thenReturn(tickets);
        Assert.assertEquals(ticketService.findTicketsByPassenger(passenger), tickets);
    }

    @Test
    public void testFindAllTicketsByUser() throws DaoException, ServiceException {
        when(passengerDao.findPassengerByUserInfo(user)).thenReturn(passenger);
        testFindTicketsByPassenger();
        Assert.assertEquals(ticketService.findAllTicketsByUser(user), listTicketsDto);
    }
}
