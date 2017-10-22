package com.tsystems.jschool.railway.persistence;

import javax.persistence.*;
import java.util.Collection;

@NamedQuery(name="Station.findStationByName" ,
        query="SELECT station FROM Station station WHERE station.name=:stationName")

@Entity
@Table(name = "station", schema = "railway")
public class Station {
    private int id;
    private String name;
    private Collection<Waypoint> waypoints;

    public Station(String name) {
        this.name = name;
    }

    public Station() {
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

    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station that = (Station) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "station", fetch = FetchType.EAGER)
    public Collection<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(Collection<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }
}
