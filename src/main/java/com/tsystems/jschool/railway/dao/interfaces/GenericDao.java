package com.tsystems.jschool.railway.dao.interfaces;

import com.tsystems.jschool.railway.exceptions.DaoException;

import java.util.List;

public interface GenericDao<T> {

    T create(T t) throws DaoException;

    T update(T t) throws DaoException;

    void delete(T t) throws DaoException;

    T findById(Integer id) throws DaoException;

    List<T> findAll() throws DaoException;
}
