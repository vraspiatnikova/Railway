package com.tsystems.jschool.railway.persistence;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@NamedQueries({
        @NamedQuery(name = "Waypoint.findWaypointByStationNameAndRouteId",
                query = "SELECT wp FROM Waypoint wp WHERE wp.station.name=:stationName AND wp.route.id = :routeId"),
        @NamedQuery(name="Waypoint.findWaypointByStationName" ,
                query="SELECT wp FROM Waypoint wp WHERE wp.station.name=:stationName")

})
@Entity
@Table(name = "waypoint", schema = "railway")
public class Waypoint implements Comparable{
    private int id;
    private Station station;
    private Route route;
    private int travelTime;
    private int travelStopTime;
    private int seqNum;

    public Waypoint(Station station, Route route, int travelTime, int travelStopTime, int seqNum) {
        this.station = station;
        this.route = route;
        this.travelTime = travelTime;
        this.travelStopTime = travelStopTime;
        this.seqNum = seqNum;
    }

    public Waypoint() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "travel_time", nullable = false)
    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    @Basic
    @Column(name = "travel_stop_time", nullable = false)
    public int getTravelStopTime() {
        return travelStopTime;
    }

    public void setTravelStopTime(int travelStopTime) {
        this.travelStopTime = travelStopTime;
    }

    @Basic
    @Column(name = "seq_num", nullable = false)
    public int getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waypoint that = (Waypoint) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + travelTime;
        result = 31 * result + travelStopTime;
        result = 31 * result + seqNum;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "station_id", referencedColumnName = "id", nullable = false)
    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false)
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.seqNum, ((Waypoint)o).seqNum);
    }

    public String arrivalDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, this.travelTime);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(calendar.getTime());
    }

    public String departureDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, this.travelStopTime);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(calendar.getTime());
    }
}
