package com.tsystems.jschool.railway.dto;

import java.math.BigDecimal;

public class SuitableTripDto {
    private int boardId;
    private int waypointFromId;
    private int waypointToId;
    private String trainName;
    private String route;
    private String depatureDateTime;
    private String arrivalDateTime;
    private String stationFrom;
    private String stationTo;
    private BigDecimal price;

    public SuitableTripDto() {
    }

    public int getBoardId() {
        return boardId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public int getWaypointFromId() {
        return waypointFromId;
    }

    public void setWaypointFromId(int waypointFromId) {
        this.waypointFromId = waypointFromId;
    }

    public int getWaypointToId() {
        return waypointToId;
    }

    public void setWaypointToId(int waypointToId) {
        this.waypointToId = waypointToId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getDepatureDateTime() {
        return depatureDateTime;
    }

    public void setDepatureDateTime(String depatureDateTime) {
        this.depatureDateTime = depatureDateTime;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }
}
