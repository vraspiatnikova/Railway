package com.tsystems.jschool.railway.exceptions;

public class ControllerException extends Exception{

    private final ErrorController error;

    public ControllerException(ErrorController error, Throwable cause) {
        super(error.getMessage(), cause);
        this.error = error;
    }

    public ControllerException(ErrorController error) {
        this.error = error;
    }

    public ErrorController getError() {
        return error;
    }
}
