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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardDto boardDto = (BoardDto) o;

        if (boardId != boardDto.boardId) return false;
        if (!trainName.equals(boardDto.trainName)) return false;
        if (!routeWaypoints.equals(boardDto.routeWaypoints)) return false;
        return date.equals(boardDto.date);
    }

    @Override
    public int hashCode() {
        int result = boardId;
        result = 31 * result + trainName.hashCode();
        result = 31 * result + routeWaypoints.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
