package com.tsystems.jschool.railway.exceptions;

public enum ErrorService {

    DATABASE_EXCEPTION(1, "A database processing error.", "database processing error"),
    DUPLICATE_STATION(2, "Station with the same name is already exists.", "station is already exists"),
    DUPLICATE_TRAIN(3, "Train with the same name is already exists.", "train is already exists"),
    DUPLICATE_ROUTE(4, "The route with the same number is already exists.", "the route with the same number is already exists"),
    DUPLICATE_USER(5, "A user with the same email address is already exists", "user with the same email address is already exists"),
    DUPLICATE_PASSENGER(6, "You are already registered.", "passenger is already registered"),
    TRAIN_NOT_EXIST(7, "The train doesn't exist.", "the train doesn't exist"),
    STATION_NOT_EXIST(8, "The station doesn't exist.", "the station doesn't exist"),
    TRIP_NOT_EXIST(9, "The trip doesn't exist.", "the trip doesn't exist"),
    LESS_THAN_10_MIN_LEFT(10, "You cannot buy a ticket because there are less than 10 minutes left before the departure of the train", "less than 10 minutes left before the departure of the train"),
    NO_AVAILABLE_TICKETS(11, "Sorry, there are no available tickets", "there are no available tickets"),
    INCORRECT_DATE_FORMAT(11, "Date format must be DD-MM-YYYY HH:MM", "error while parsing date");

    private final int id;
    private final String message;
    private final String messageForLog;

    ErrorService(int id, String message, String messageForLog) {
        this.id = id;
        this.message = message;
        this.messageForLog = messageForLog;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageForLog() {
        return messageForLog + " ";
    }
}
