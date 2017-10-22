package com.tsystems.jschool.railway.dao.interfaces;

import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.persistence.User;

public interface UserDao extends GenericDao<User>{
    User findUserByEmail(String email) throws DaoException;
}
