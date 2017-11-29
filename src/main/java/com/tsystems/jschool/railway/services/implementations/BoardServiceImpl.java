package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dto.*;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.*;
import com.tsystems.jschool.railway.jms.Sender;
import com.tsystems.jschool.railway.persistence.*;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BoardServiceImpl implements BoardService {

    private static final Logger LOGGER = Logger.getLogger(BoardServiceImpl.class);
    private final BoardDao boardDao;
    private final RouteDao routeDao;
    private final TrainDao trainDao;
    private final WaypointDao waypointDao;
    private final TicketDao ticketDao;
    private final Sender sender;

    @Autowired
    public BoardServiceImpl(BoardDao boardDao, RouteDao routeDao, TrainDao trainDao, WaypointDao waypointDao, TicketDao ticketDao, Sender sender) {
        this.boardDao = boardDao;
        this.routeDao = routeDao;
        this.trainDao = trainDao;
        this.waypointDao = waypointDao;
        this.ticketDao = ticketDao;
        this.sender = sender;
    }

    @Override
    @Transactional
    public void addBoards(List<DateTime> dateTimeList, String trainName, String routeNumber) throws ServiceException {
        try {
            Route route = routeDao.findByNumber(routeNumber);
            Train train = trainDao.findByName(trainName);
            List<Board> boards = boardDao.findBoardByTrainName(trainName);
            if (!boards.isEmpty()) {
                checkDuplicateTrips(boards, dateTimeList, route);
            }
            else {
                for (DateTime dateTime : dateTimeList) {
                    Date date = dateTime.toDate();
                    LOGGER.info("try to add trip with date: " + date + ", train name " + train.getName() + " and route number " + route.getNumber());
                    boardDao.create(new Board(date, route, train));
                    LOGGER.info("try to send message 'boards added' to topic");
                    sender.sendMessage("boards added");
                }
            }
        } catch (JMSException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.JMS_EXCEPTION, e);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    private void checkDuplicateTrips(List<Board> boards, List<DateTime> dateTimeList, Route route) throws ServiceException {
        for (Board board : boards) {
            Date start = board.getDateTime();
            Date end = board.getRoute().findLastWaypoint().departureDate(start);
            for (DateTime dateTime : dateTimeList) {
                Date newStart = dateTime.toDate();
                Date newEnd = route.findLastWaypoint().departureDate(newStart);
                if (!newStart.after(end) || !newStart.before(start) ||
                        !newEnd.after(end) || !newEnd.before(start) ||
                        (start.after(newStart) && end.after(newStart) && start.before(newEnd) && end.before(newEnd))) {
                    throw new ServiceException(ErrorService.DUPLICATE_TRIP);
                }
            }
        }
    }

    @Override
    @Transactional
    public List<Board> getAllBoards() throws ServiceException {
        LOGGER.info("try to get all boards");
        List<Board> listOfBoards = new ArrayList<>();
        try {
            List<Board> tmp = boardDao.findAll();
            for (Board board: tmp){
                if (board.getRoute().findLastWaypoint().departureDate(board.getDateTime()).compareTo(new Date())>0){
                    listOfBoards.add(board);
                }
            }
            Collections.sort(listOfBoards);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return listOfBoards;
    }

    @Override
    @Transactional
    public Board findBoardById(Integer id) throws ServiceException {
        LOGGER.info("try to find board by id (" + id + ")");
        Board board;
        try {
            board = boardDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return board;
    }

    @Override
    @Transactional
    public void updateBoard(Board board, Board newBoard) throws ServiceException {
        LOGGER.info("try to update board with id (" + board.getId() + ")");
        try {
            boardDao.update(newBoard);
            Date oldDate = board.getDateTime();
            Date newDate = newBoard.getDateTime();
            String msg = "edit " + Integer.toString(board.getId());
            long duration;
            if (oldDate.compareTo(newDate) > 0){
                duration = oldDate.getTime() - newDate.getTime();
                msg += " -";
            }
            else {
                duration = newDate.getTime() - oldDate.getTime();
                msg += " ";
            }
            long delta = TimeUnit.MILLISECONDS.toMinutes(duration);
            msg += Long.toString(delta);
            sender.sendMessage(msg);
        } catch (JMSException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.JMS_EXCEPTION, e);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public void deleteBoard(Board board) throws ServiceException {
        LOGGER.info("try to delete board with id (" + board.getId() + ")");
        try {
            if (!ticketDao.findTicketsByBoard(board).isEmpty()){
                throw new ServiceException(ErrorService.CANNOT_DELETE_BOARD);
            }
            boardDao.delete(board);
            sender.sendMessage("delete " + board.getId());
        } catch (JMSException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.JMS_EXCEPTION, e);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public BoardDto constructBoardDto(Board board) {
        LOGGER.info("try to construct trip with board id (" + board.getId() + ")");
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getId());
        StringBuilder routeWaypoints = new StringBuilder();
        boardDto.setTrainName(board.getTrain().getName());
        Route route = board.getRoute();
        List<Station> stations = new ArrayList<>();
        TreeSet<Waypoint> waypoints = new TreeSet<>(route.getWaypoints());
        for (Waypoint wp: waypoints){
            stations.add(wp.getStation());
        }
        for (Station station: stations){
            routeWaypoints.append(station.getName()).append(" ");
        }
        boardDto.setRouteWaypoints(routeWaypoints.toString().trim());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        boardDto.setDate(simpleDateFormat.format(board.getDateTime()));
        return boardDto;
    }

    @Override
    @Transactional
    public List<BoardDto> getAllBoardsDto() throws ServiceException {
        LOGGER.info("try to get all trips");
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : getAllBoards()) {
            boardDtos.add(constructBoardDto(board));
        }
        return boardDtos;
    }

    @Override
    @Transactional
    public SuitableTripDto constractSuitableTripDto(Board board, String stationFrom, String stationTo) throws ServiceException {
        LOGGER.info("try to construct suitable trip from " + stationFrom + " to " + stationTo);
        try {
            SuitableTripDto suitableTripDto = new SuitableTripDto();

            if (board.getRoute().ifWaypointsSuitable(stationFrom, stationTo)){

                suitableTripDto.setBoardId(board.getId());
                suitableTripDto.setTrainName(board.getTrain().getName());

                Route route = board.getRoute();
                String routeName = route.findFirstWaypoint().getStation().getName()+" - "+route.findLastWaypoint().getStation().getName();
                suitableTripDto.setRoute(routeName);

                Waypoint wpFrom = waypointDao.findWaypointByStationNameAndRouteId(stationFrom, route.getId());
                if (wpFrom == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);

                Waypoint wpTo = waypointDao.findWaypointByStationNameAndRouteId(stationTo, route.getId());
                if (wpTo == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);

                Integer from = wpFrom.getTravelStopTime();
                Integer to = wpTo.getTravelTime();

                suitableTripDto.setWaypointFromId(wpFrom.getId());
                suitableTripDto.setWaypointToId(wpTo.getId());
                suitableTripDto.setArrivalDateTime(wpFrom.arrivalDateTime(board.getDateTime()));
                suitableTripDto.setDepatureDateTime(wpTo.departureDateTime(board.getDateTime()));
                suitableTripDto.setStationFrom(stationFrom);
                suitableTripDto.setStationTo(stationTo);
                suitableTripDto.setPrice(new BigDecimal(Math.abs(from - to)).multiply(Ticket.FACTOR));
            }
            else suitableTripDto = null;
            return suitableTripDto;

        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public List<SuitableTripDto> findAllSuitableTrips(SearchTripDto searchTripDto) throws ServiceException {
        LOGGER.info("try to get all suitable trips");
        try {
            List<SuitableTripDto> suitableTripDtos = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy - HH:mm", Locale.ENGLISH);
            Date dateTimeFrom = dateFormat.parse(searchTripDto.getDateTimeFrom());
            Date dateTimeTo = dateFormat.parse(searchTripDto.getDateTimeTo());
            List<Board> suitableBoards = boardDao.findAllBoardsBetweenDates(dateTimeFrom, dateTimeTo);
            String stationFrom = searchTripDto.getStationFrom();
            String stationTo = searchTripDto.getStationTo();

            for(Board board : suitableBoards) {
                SuitableTripDto suitableTripDto = constractSuitableTripDto(board, stationFrom, stationTo);
                if (suitableTripDto != null){
                    suitableTripDtos.add(suitableTripDto);
                }
            }
            suitableTripDtos.sort(new SortedSuitableTripDto());
            return suitableTripDtos;
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.INCORRECT_DATE_FORMAT, e);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    private BoardByStationDto constructBoardByStationDto(Board board, String stationName) throws ServiceException {
        try {
            BoardByStationDto boardByStationDto = new BoardByStationDto();

            boardByStationDto.setBoardId(board.getId());
            boardByStationDto.setTrainName(board.getTrain().getName());
            Route route = board.getRoute();
            String routeName = route.findFirstWaypoint().getStation().getName()+" - "+route.findLastWaypoint().getStation().getName();
            boardByStationDto.setRoute(routeName);

            Waypoint wp = waypointDao.findWaypointByStationNameAndRouteId(stationName, route.getId());
            if (wp == null) throw new ServiceException(ErrorService.TRIP_NOT_EXIST);

            boardByStationDto.setArrivaDatelTime(wp.arrivalDateTime(board.getDateTime()));
            boardByStationDto.setDepatureDateTime(wp.departureDateTime(board.getDateTime()));

            return boardByStationDto;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public List<BoardByStationDto> getAllBoardByStationDto(String stationName) throws ServiceException {
        LOGGER.info("try to get all boards by station " + stationName);
        try {
            List<BoardByStationDto> boardByStationDtos = new ArrayList<>();
            List<BoardByStationDto> result = new ArrayList<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            List<Waypoint> waypoints = waypointDao.findWaypointByStationName(stationName);
            Set<Route> routes = new HashSet<>();
            for (Waypoint wp: waypoints){
                routes.add(wp.getRoute());
            }
            Set<Board> boards = new HashSet<>();
            for (Route route: routes){
                boards.addAll(route.getBoards());
            }
            for(Board board : boards) {
                boardByStationDtos.add(constructBoardByStationDto(board, stationName));
            }
            for (BoardByStationDto boardByStationDto: boardByStationDtos){
                if (simpleDateFormat.parse(boardByStationDto.getDepatureDateTime()).compareTo(new Date()) > 0){
                result.add(boardByStationDto);
                }
            }
            result.sort(new SortedBoardByStationDto());
            return result;
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
    public List<BoardByStationDto> getFirstTenBoardByStationDtos(String stationName){
        LOGGER.info("try to get first ten boards by station " + stationName);
        List<BoardByStationDto> boardByStationDtos = new ArrayList<>(10);
        try {
            List<BoardByStationDto> allBoardByStationDto = getAllBoardByStationDto(stationName);
            if (allBoardByStationDto.size() <= 10){
                boardByStationDtos = allBoardByStationDto;
            }
            else {
                boardByStationDtos = allBoardByStationDto.subList(0, 10);
            }
        } catch (ServiceException e) {
            LOGGER.warn(e.getError().getMessageForLog(), e);
        }
        return boardByStationDtos;
    }

    @Override
    @Transactional
    public List<Passenger> findRegisteredPassengers(Board board) throws ServiceException {
        LOGGER.info("try to find all registered passengers");
        List<Passenger> passengers = new ArrayList<>();
        try{
            List<Ticket> tickets = ticketDao.findTicketsByBoard(board);
            for (Ticket ticket: tickets){
                passengers.add(ticket.getPassenger());
            }
            return passengers;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public List<Board> findBoardByTrainNameAndRoute(String trainName, String routeNumber) throws ServiceException {
        List<Board> boardList;
        LOGGER.info("try to get all boards by train name and route");
        try {
            boardList = boardDao.findBoardByTrainNameAndRoute(trainName, routeNumber);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return boardList;
    }
}
