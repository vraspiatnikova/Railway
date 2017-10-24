package com.tsystems.jschool.railway.dto;

public class BoardByStationDto {
    private String trainName;
    private String route;
    private String depatureDateTime;
    private String arrivaDatelTime;

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
