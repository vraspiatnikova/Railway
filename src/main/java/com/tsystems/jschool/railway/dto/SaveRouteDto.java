package com.tsystems.jschool.railway.dto;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaveRouteDto {
    private String routeNumber;
    private List<String> waypointStations;
    private List<Integer> waypointTravellTime;
    private List<Integer> waypointTravelStopTime;

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public List<String> getWaypointStations() {
        return waypointStations;
    }

    public void setWaypointStations(List<String> waypointStations) {
        this.waypointStations = waypointStations;
    }

    public List<Integer> getWaypointTravellTime() {
        return waypointTravellTime;
    }

    public void setWaypointTravellTime(List<Integer> waypointTravellTime) {
        this.waypointTravellTime = waypointTravellTime;
    }

    public List<Integer> getWaypointTravelStopTime() {
        return waypointTravelStopTime;
    }

    public void setWaypointTravelStopTime(List<Integer> waypointTravelStopTime) {
        this.waypointTravelStopTime = waypointTravelStopTime;
    }
}
