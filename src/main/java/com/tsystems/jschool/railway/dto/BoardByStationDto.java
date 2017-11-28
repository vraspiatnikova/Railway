package com.tsystems.jschool.railway.dto;

public class BoardByStationDto {
    private int boardId;
    private String trainName;
    private String route;
    private String depatureDateTime;
    private String arrivaDatelTime;

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

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getArrivaDatelTime() {
        return arrivaDatelTime;
    }

    public void setArrivaDatelTime(String arrivaDatelTime) {
        this.arrivaDatelTime = arrivaDatelTime;
    }

    public String getDepatureDateTime() {
        return depatureDateTime;
    }

    public void setDepatureDateTime(String depatureDateTime) {
        this.depatureDateTime = depatureDateTime;
    }
}
