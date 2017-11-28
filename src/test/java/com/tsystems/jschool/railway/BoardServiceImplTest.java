package com.tsystems.jschool.railway;

import com.tsystems.jschool.railway.dao.interfaces.*;
import com.tsystems.jschool.railway.dto.BoardDto;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.jms.Sender;
import com.tsystems.jschool.railway.persistence.*;
import com.tsystems.jschool.railway.services.implementations.BoardServiceImpl;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.jms.JMSException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceImplTest {

    private Board newBoard;
    private Board board;
    private Train train;
    private Route route;
    private List<DateTime> dateTimeList;
    private BoardDto newBoardDto;
    private List<Board> boards;

    @Mock
    private BoardDao boardDao;

    @Mock
    private TrainDao trainDao;

    @Mock
    private RouteDao routeDao;

    @Mock
    private WaypointDao waypointDao;

    @Mock
    private Sender sender;

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private BoardServiceImpl boardService;

    public BoardServiceImplTest() {
    }

    @Before
    public void setup(){
        board = new Board();
        board.setId(1);

        dateTimeList = new ArrayList<>();
        DateTime dateTime = new DateTime(2018, 1, 30, 10, 0);
        dateTimeList.add(dateTime);
        Date date = dateTime.toDate();

        train = new Train("NewTrain", 20);
        route = new Route("1");
        Station station1 = new Station("StationFrom");
        Station station2 = new Station("StationTo");
        Waypoint wp1 = new Waypoint(station1, route, 0, 0, 0);
        Waypoint wp2 = new Waypoint(station2, route, 60, 60, 1);
        Set<Waypoint> waypointSet = new TreeSet<>();
        waypointSet.add(wp1);
        waypointSet.add(wp2);
        route.setWaypoints(waypointSet);

        newBoard = new Board();
        newBoard.setTrain(train);
        newBoard.setRoute(route);
        newBoard.setDateTime(date);

        newBoardDto = new BoardDto("NewTrain", "StationFrom StationTo", "2018/01/30 10:00");
        newBoardDto.setBoardId(newBoard.getId());
        boards = Arrays.asList(newBoard);
    }

    @Test
    public void testAddBoards() throws DaoException, ServiceException, JMSException {
        when(boardDao.create(newBoard)).thenReturn(newBoard);
        when(routeDao.findByNumber("1")).thenReturn(route);
        when(trainDao.findByName("NewTrain")).thenReturn(train);
        when(boardDao.findBoardByTrainName("NewTrain")).thenReturn(new ArrayList<>());
        boardService.addBoards(dateTimeList, "NewTrain", "1");
        verify(boardDao).create(newBoard);
    }

    @Test(expected = ServiceException.class)
    public void testAddBoardsFailed() throws DaoException, ServiceException, JMSException {
        try {
            when(boardDao.create(newBoard)).thenReturn(newBoard);
            when(routeDao.findByNumber("1")).thenReturn(route);
            when(trainDao.findByName("NewTrain")).thenReturn(train);
            when(boardDao.findBoardByTrainName("NewTrain")).thenReturn(boards);
            boardService.addBoards(dateTimeList, "NewTrain", "1");
        } catch (ServiceException e) {
            assertEquals(20, ErrorService.DUPLICATE_TRIP.getId());
            throw e;
        }
    }

    @Test
    public void testGetAllBoards() throws DaoException, ServiceException {
        when(boardDao.findAll()).thenReturn(new ArrayList<>());
        boardService.getAllBoards();
        verify(boardDao).findAll();
        Assert.assertTrue(true);
    }

    @Test
    public void testFindBoardById() throws DaoException, ServiceException {
        when(boardDao.findById(1)).thenReturn(board);
        boardService.findBoardById(board.getId());
        verify(boardDao).findById(board.getId());
    }

    @Test
    public void testConstructBoardDto(){
        assertEquals(boardService.constructBoardDto(newBoard), newBoardDto);
    }

    @Test
    public void testFindRegisteredPassengers() throws DaoException, ServiceException {
        when(ticketDao.findTicketsByBoard(newBoard)).thenReturn(new ArrayList<>());
        boardService.findRegisteredPassengers(newBoard);
        verify(ticketDao).findTicketsByBoard(newBoard);
        Assert.assertTrue(true);
    }

    @Test
    public void testFindBoardByTrainNameAndRoute() throws DaoException, ServiceException {
        when(boardDao.findBoardByTrainNameAndRoute("newTrain", "1")).thenReturn(boards);
        boardService.findBoardByTrainNameAndRoute("newTrain", "1");
        verify(boardDao).findBoardByTrainNameAndRoute("newTrain", "1");
        Assert.assertTrue(true);
    }

    @Test
    public void testUpdateBoard() throws DaoException, ServiceException{
        newBoard.setDateTime(new DateTime(2018, 1, 30, 0, 0).toDate());
        boardDao.update(newBoard);
        Assert.assertEquals(new DateTime(2018, 1, 30, 0, 0).toDate(), newBoard.getDateTime());
    }

    @Test
    public void testDeleteBoard() throws DaoException, ServiceException {
        when(ticketDao.findTicketsByBoard(newBoard)).thenReturn(new ArrayList<>());
        boardService.deleteBoard(newBoard);
        when(boardDao.findById(newBoard.getId())).thenReturn(null);
    }
}
