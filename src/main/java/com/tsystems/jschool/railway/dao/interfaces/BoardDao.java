package com.tsystems.jschool.railway.dao.interfaces;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.persistence.Board;
import com.tsystems.jschool.railway.persistence.Route;

import java.util.Date;
import java.util.List;

public interface BoardDao extends GenericDao<Board> {
    List<Board> findAllBoardsBetweenDates(Date dateTimeFrom, Date dateTimeTo) throws DaoException;

    List<Board> findBoardByTrainName(String trainName) throws DaoException;

    List<Board> findBoardByRouteNumber(String routeNumber) throws DaoException;

    List<Board> findBoardByTrainNameAndRoute(String trainName, String routeNumber) throws DaoException;
}
