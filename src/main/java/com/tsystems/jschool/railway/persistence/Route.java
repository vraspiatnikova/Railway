package com.tsystems.jschool.railway.persistence;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@NamedQueries({
        @NamedQuery(name = "Route.findRouteByNumber",
                query = "SELECT route FROM Route route WHERE route.number=:routeNumber")
})
@Entity
@Table(name = "route", schema = "railway")
public class Route {
    private int id;
    private String number;
    private Set<Board> boards;
    private Set<Waypoint> waypoints;

    public Route(String number) {
        this.number = number;
    }

    public Route() {
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
    @Column(name = "number", nullable = false, length = 45)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route that = (Route) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
    public Set<Board> getBoards() {
        return boards;
    }

    public void setBoards(Set<Board> boards) {
        this.boards = boards;
    }

    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
    public Set<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(Set<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public Waypoint findFirstWaypoint() {
        Waypoint firstWaypoint = null;
        for (Waypoint w : waypoints) {
            if (firstWaypoint == null) {
                firstWaypoint = w;
            } else if (firstWaypoint.getSeqNum() > w.getSeqNum()) {
                firstWaypoint = w;
            }
        }
        return firstWaypoint;
    }

    public Waypoint findLastWaypoint() {
        Waypoint lastWaypoint = null;
        for (Waypoint w : waypoints) {
            if (lastWaypoint == null) {
                lastWaypoint = w;
            } else if (lastWaypoint.getSeqNum() < w.getSeqNum()) {
                lastWaypoint = w;
            }
        }
        return lastWaypoint;
    }

    public boolean ifWaypointsSuitable(String stationFrom, String stationTo) {
        Waypoint wpFrom = null;
        Waypoint wpTo = null;
        for (Waypoint wp : waypoints) {
            if (wp.getStation().getName().equals(stationFrom)) {
                wpFrom = wp;
            }
            if (wp.getStation().getName().equals(stationTo)) {
                wpTo = wp;
            }
        }
        return wpFrom != null && wpTo != null && wpFrom.getSeqNum() < wpTo.getSeqNum();
    }
}
