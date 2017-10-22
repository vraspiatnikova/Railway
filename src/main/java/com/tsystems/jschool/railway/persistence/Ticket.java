package com.tsystems.jschool.railway.persistence;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
@NamedQuery(name = "Ticket.findTicketsByBoard",
        query = "SELECT ticket FROM Ticket ticket WHERE ticket.board = :board")
@Entity
@Table(name = "ticket", schema = "railway")
public class Ticket {
    public static final BigDecimal FACTOR = new BigDecimal(10);
    private int id;
    private BigDecimal price;
    private Passenger passenger;
    private Board board;
    private Waypoint waypointFrom;
    private Waypoint waypointTo;
    private Date date;

    public Ticket() {
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
    @Column(name = "price", nullable = false, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPrice() {
        Integer startTime = this.waypointFrom.getTravelTime();
        Integer finishTime =this.waypointTo.getTravelStopTime();
        this.price = new BigDecimal(Math.abs(finishTime - startTime)).multiply(FACTOR);
    }

    @Basic
    @Column(name="date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket that = (Ticket) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + price.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @ManyToOne
    @JoinColumn(name = "board_id")
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @ManyToOne
    @JoinColumn(name = "waypoint_from_id", referencedColumnName = "id", nullable = false)
    public Waypoint getWaypointFrom() {
        return waypointFrom;
    }

    public void setWaypointFrom(Waypoint waypointFrom) {
        this.waypointFrom = waypointFrom;
    }

    @ManyToOne
    @JoinColumn(name = "waypoint_to_id", referencedColumnName = "id", nullable = false)
    public Waypoint getWaypointTo() {
        return waypointTo;
    }

    public void setWaypointTo(Waypoint waypointTo) {
        this.waypointTo = waypointTo;
    }
}
