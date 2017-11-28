package com.tsystems.jschool.railway.services.implementations;

import com.tsystems.jschool.railway.dao.interfaces.PassengerDao;
import com.tsystems.jschool.railway.exceptions.DaoException;
import com.tsystems.jschool.railway.dao.interfaces.UserDao;
import com.tsystems.jschool.railway.persistence.User;
import com.tsystems.jschool.railway.persistence.roles.UserRole;
import com.tsystems.jschool.railway.exceptions.ErrorService;
import com.tsystems.jschool.railway.exceptions.ServiceException;
import com.tsystems.jschool.railway.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.log4j.Logger;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final PassengerDao passengerDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, PassengerDao passengerDao) {
        this.userDao = userDao;
        this.passengerDao = passengerDao;
    }

    @Override
    @Transactional
    public User findUserById(Integer id) throws ServiceException {
        LOGGER.info("try to find user by id (" + id + ")");
        User user;
        try {
            user = userDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return user;
    }

    @Override
    @Transactional
    public User findUserByEmail(String email) throws ServiceException {
        LOGGER.info("try to find user by email (" + email + ")");
        User user;
        try {
            user = userDao.findUserByEmail(email);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return user;
    }

    @Override
    @Transactional
    public User regUser(String email, String password) throws ServiceException {
        LOGGER.info("try to register new user");
        User result;
        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(UserRole.ROLE_USER);
            if (userDao.findUserByEmail(email) != null) {
                throw new ServiceException(ErrorService.DUPLICATE_USER);
            }
            result = userDao.create(user);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return result;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() throws ServiceException {
        LOGGER.info("try to get all users");
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
        return users;
    }

    @Override
    @Transactional
    public void updateUser(User user) throws ServiceException {
        LOGGER.info("try to update user with email " + user.getEmail());
        try {
            if (userDao.findUserByEmail(user.getEmail()) != null) {
                throw new ServiceException(ErrorService.DUPLICATE_USER);
            }
            userDao.update(user);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(User user) throws ServiceException {
        LOGGER.info("try to delete user with email " + user.getEmail());
        try {
            if (passengerDao.findPassengerByUserInfo(user) != null){
                throw new ServiceException(ErrorService.CANNOT_DELETE_USER);
            }
            userDao.delete(user);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }

    @Override
    @Transactional
    public void addUser(User user) throws ServiceException {
        LOGGER.info("try to add new user");
        try {
            if (userDao.findUserByEmail(user.getEmail()) != null) {
                throw new ServiceException(ErrorService.DUPLICATE_USER);
            }
            userDao.create(user);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(ErrorService.DATABASE_EXCEPTION, e);
        }
    }
}
