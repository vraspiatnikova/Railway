package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.persistence.User;
import com.tsystems.jschool.railway.exceptions.ServiceException;

import java.util.List;

public interface UserService {

    User findUserById(Integer id) throws ServiceException;

    User findUserByEmail(String email) throws ServiceException;

    User regUser(String email, String password) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;

    void updateUser(User user) throws ServiceException;

    void deleteUser(User user) throws ServiceException;

    void addUser(User user) throws ServiceException;
}
