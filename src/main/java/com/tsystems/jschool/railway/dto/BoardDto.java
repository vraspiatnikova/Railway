package com.tsystems.jschool.railway.dto;

import org.springframework.stereotype.Component;

@Component
public class BoardDto {
    private int boardId;
    private String trainName;
    private String routeWaypoints;
    private String date;

    public BoardDto() {
    }

    public BoardDto(String trainName, String routeWaypoints, String date) {
        this.trainName = trainName;
        this.routeWaypoints = routeWaypoints;
        this.date = date;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getRouteWaypoints() {
        return routeWaypoints;
    }

    public void setRouteWaypoints(String routeWaypoints) {
        this.routeWaypoints = routeWaypoints;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
