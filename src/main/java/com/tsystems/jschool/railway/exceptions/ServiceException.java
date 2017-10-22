package com.tsystems.jschool.railway.exceptions;

public class ServiceException extends Exception {

    private ErrorService error;

    public ServiceException(ErrorService error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    public ServiceException(ErrorService error) {
        this.error = error;
    }

    public ErrorService getError() {
        return error;
    }
}
