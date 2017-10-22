package com.tsystems.jschool.railway.exceptions;

public class DaoException extends Exception {

    public DaoException(ErrorDao error, Throwable cause) {
        super(error.getMessage(), cause);
    }
}
