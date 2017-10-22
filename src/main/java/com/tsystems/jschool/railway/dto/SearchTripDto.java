package com.tsystems.jschool.railway.dto;

import org.springframework.stereotype.Component;

@Component
public class SearchTripDto {
    private String stationFrom;
    private String stationTo;
    private String dateTimeFrom;
    private String dateTimeTo;

    public SearchTripDto() {
    }

    public SearchTripDto(String stationFrom, String stationTo, String dateTimeFrom, String dateTimeTo) {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
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

    public String getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(String dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public String getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(String dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }
}
