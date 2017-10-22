package com.tsystems.jschool.railway.exceptions;

public enum ErrorDao {

    DATABASE_EXCEPTION(1, "A database processing error.");

    private final int id;
    private final String message;

    ErrorDao(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
       return id;
    }

    public String getMessage() {
        return message;
    }
}
