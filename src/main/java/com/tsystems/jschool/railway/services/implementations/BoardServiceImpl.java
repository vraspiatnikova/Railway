package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dto.BoardByStationDto;
import com.tsystems.jschool.railway.dto.BoardDto;
import com.tsystems.jschool.railway.dto.SearchTripDto;
import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.*;
import com.tsystems.jschool.railway.persistence.*;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.BoardService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BoardServiceImpl implements BoardService {

    private static final Logger LOGGER = Logger.getLogger(BoardServiceImpl.class);
    private final BoardDao boardDao;
    private final RouteDao routeDao;
    private final TrainDao trainDao;
    private final WaypointDao waypointDao;
    private final TicketDao ticketDao;

    @Autowired
    public BoardServiceImpl(BoardDao boardDao, RouteDao routeDao, TrainDao trainDao, WaypointDao waypointDao, TicketDao ticketDao) {
        this.boardDao = boardDao;
        this.routeDao = routeDao;
        this.trainDao = trainDao;
        this.waypointDao = waypointDao;
        this.ticketDao = ticketDao;
    }

    @Override
    @Transactional
    public void addBoard(List<DateTime> dateTimeList, String trainName, String routeNumber) throws ServiceException {
        LOGGER.info("try to add trip with train name " + trainName + " and route number " + routeNumber);
        try {
            Route route = routeDao.findByNumber(routeNumber);
            Train train = trainDao.findByName(trainName);
            for (DateTime dateTime : dateTimeList) {
                boardDao.create(new Board(dateTime.toDate(), route, train));
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public List<Board> getAllBoards() throws ServiceException {
        LOGGER.info("try to get all boards");
        List<Board> listOfBoards;
        try {
            listOfBoards = boardDao.findAll();
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

    private BoardDto constructBoardDto(Board board) {
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
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
        LOGGER.info("try to construct suitable trip");
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
            try {
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
            } catch (ParseException e) {
                throw new ServiceException(ErrorService.INCORRECT_DATE_FORMAT, e);
            }
            return suitableTripDtos;

        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
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
    public List<BoardByStationDto> getAllBoardByStationDto(String stationName) throws ServiceException {
        try {
            List<BoardByStationDto> boardByStationDtos = new ArrayList<>();
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
            return boardByStationDtos;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    private BoardByStationDto constructBoardByStationDto(Board board, String stationName) throws ServiceException {
        try {
            BoardByStationDto boardByStationDto = new BoardByStationDto();

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
