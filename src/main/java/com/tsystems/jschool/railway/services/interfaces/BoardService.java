package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.dto.BoardByStationDto;
import com.tsystems.jschool.railway.dto.BoardDto;
import com.tsystems.jschool.railway.dto.SearchTripDto;
import com.tsystems.jschool.railway.dto.SuitableTripDto;
import com.tsystems.jschool.railway.persistence.Board;
import com.tsystems.jschool.railway.persistence.Passenger;
import com.tsystems.jschool.railway.exceptions.ServiceException;

import java.util.Date;
import java.util.List;

public interface BoardService {
    Board addBoard(Date dateTime, String trainName, String routeNumber) throws ServiceException;
    List<Board> getAllBoards() throws ServiceException;
    List<BoardDto> getAllBoardsDto() throws ServiceException;
    List<SuitableTripDto> findAllSuitableTrips(SearchTripDto searchTripDto) throws ServiceException;
    Board findBoardById(Integer id) throws ServiceException;
    SuitableTripDto constractSuitableTripDto(Board board, String stationFrom, String stationTo) throws ServiceException;
    List<Passenger> findRegisteredPassengers(Board board) throws ServiceException;
    List<BoardByStationDto> getAllBoardByStationDto(String stationName) throws ServiceException;
}
