package com.tsystems.jschool.railway.exceptions;

public enum ErrorController {
    INCORRECT_DATE_FORMAT(1, "Date format must be DD-MM-YYYY HH:MM", "incorrect date format"),
    INCORRECT_STATION_NAME(2, "The field 'Station name' cannot be empty", "station's name is empty"),
    INCORRECT_TRAIN_NAME(3, "The field 'Name' cannot be empty", "train's name is empty"),
    INCORRECT_PASSENGER_FIRSTNAME(4, "Enter your first name please.", "user's name is empty"),
    INCORRECT_PASSENGER_LASTNAME(5, "Enter your last name please.", "user's surname is empty"),
    INCORRECT_PASSENGER_PASSPORT(6, "Enter your passport number. Minimum length of 6 characters", "incorrect passport number"),
    INCORRECT_PASSENGER_BIRTHDATE(7, "Enter your birth date, please", "birth date is empty"),
    INCORRECT_EMAIL(8, "Incorrect email.", "incorrect email"),
    INCORRECT_PASSWORD(9, "Password should be from 6 to 12 alphanumerical characters.", "incorrect password"),
    INCORRECT_CONFIRM_PASSWORD(10, "Confirm password does not match password.", "confirm password does not match password"),
    INCORRECT_CAPACITY(11, "The number of seats must be between 18 and 1080.", "incorrect number of seats"),
    INCORRECT_ROUTE_NUMBER(12, "Enter the number of the route, please.", "route's name is empty"),
    INCORRECT_TRAVEL_TIME(13, "The field 'Travel time' cannot be empty", "travel time is empty"),
    INCORRECT_STOP_TIME(14, "The field 'Stop time' cannot be empty", "stop time is empty");

    private final int id;
    private final String message;
    private final String messageForLog;

    ErrorController(int id, String message, String messageForLog) {
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
