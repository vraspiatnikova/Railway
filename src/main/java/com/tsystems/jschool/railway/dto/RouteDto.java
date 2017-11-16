package com.tsystems.jschool.railway.dto;

import org.springframework.stereotype.Component;

@Component
public class RouteDto {
    private int id;
    private String routeNumber;
    private String routeWaypoints;

    public RouteDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteWaypoints() {
        return routeWaypoints;
    }

    public void setRouteWaypoints(String routeWaypoints) {
        this.routeWaypoints = routeWaypoints;
    }
}
