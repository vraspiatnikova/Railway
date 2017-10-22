package com.tsystems.jschool.railway.services.interfaces;

import com.tsystems.jschool.railway.persistence.User;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    User findUserById(Integer id) throws ServiceException;

    @Transactional
    User findUserByEmail(String email) throws ServiceException;

    @Transactional
    User regUser(String email, String password) throws ServiceException;
}
